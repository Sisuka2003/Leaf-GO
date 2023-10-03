package com.leaf.leafgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.maps.android.PolyUtil;
import com.leaf.leafgo.model.shopOwners;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class shopmap extends AppCompatActivity implements OnMapReadyCallback {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1312;
    private static final String TAG = "shopmap";
    private GoogleMap map;
    private Marker shopMarker,marker_me,marker_pin;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebasestorage;
    LatLng latLng;
    private int moveEnable =0;
    private CollectionReference shopOwners;
    private Polyline polyline;
    public static String tappedShopEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmap);

        firestore=FirebaseFirestore.getInstance();
        firebasestorage=FirebaseStorage.getInstance();



        findViewById(R.id.satellite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
        findViewById(R.id.backtoprofilebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoprofileintent = new Intent(shopmap.this, user_profile.class);
                startActivity(backtoprofileintent);
                finish();
            }
        });

        findViewById(R.id.defaultView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        shopOwners = firestore.collection("shopOwners");

        shopOwners.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot snapshot : task.getResult()){
                    shopOwners owners = snapshot.toObject(shopOwners.class);

                    String shopName = owners.getShopName();
                    latLng = new LatLng(Double.parseDouble(owners.getLatitudeLocation()), Double.parseDouble(owners.getLongitudeLocation()));
                    MarkerOptions markerOptionsShop = new MarkerOptions();
                    markerOptionsShop.icon(BitmapDescriptorFactory.fromResource(R.drawable.store));
                    markerOptionsShop.title(shopName);
                    markerOptionsShop.position(latLng);
                    shopMarker=map.addMarker(markerOptionsShop);
                }
            }
        });




        findViewById(R.id.viewProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopProfile = new Intent(shopmap.this, shop_profile.class);
                startActivity(shopProfile);
            }
        });




    }

    @SuppressLint({"MissingPermission", "NewApi"})
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;
        // map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        if (checkPermissions()){
            map.setMyLocationEnabled(true);
        }else {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }

        //taking the current location->START
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(5000)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) { super.onLocationAvailability(locationAvailability); }

                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        Location lastLocation = locationResult.getLastLocation();
                        LatLng latLng2 = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                if(marker_me == null) {
                    MarkerOptions markerOptionsMe = new MarkerOptions();
                    markerOptionsMe.title("ME");
                    markerOptionsMe.position(latLng2);
                    marker_me = map.addMarker(markerOptionsMe);
                }else{
                    marker_me.setPosition(latLng2);
                }
                        if(moveEnable == 0){
                         moveCamera(latLng2);
                         moveEnable++;
                        }
                    }
                }, Looper.getMainLooper());

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    callprofile(marker);
                    getDirections(marker_me.getPosition(),marker.getPosition());
                    return false;
                }
            });
    }

    private void callprofile(Marker marker) {

        LatLng markerTappedPosition = marker.getPosition();

        String tappedlatitude=String.valueOf(markerTappedPosition.latitude);
        String tappedlongitude =String.valueOf(markerTappedPosition.longitude);


        shopOwners.whereEqualTo("latitudeLocation",tappedlatitude)
                .whereEqualTo("longitudeLocation",tappedlongitude)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot snapshot : task.getResult()){
                    shopOwners owners = snapshot.toObject(shopOwners.class);
//                    Log.i(TAG, owners.getShopName());
                    tappedShopEmail= owners.getShopEmail();
                }
            }
        });
    }

    public void getDirections(LatLng start,LatLng end){
        OkHttpClient client = new OkHttpClient();
        String URL="https://maps.googleapis.com/maps/api/directions/json?origin="
                +start.latitude
                +","
                +start.longitude
                +"&destination="
                +end.latitude
                +","
                +end.longitude
                +"&key="
                +getString(R.string.direction_api_key);

        Log.i(TAG,URL);

        Request request = new Request.Builder().url(URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                Log.i(TAG,"responsed");
                String json = response.body().string();

                try{
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray routes = jsonObject.getJSONArray("routes");
                    Log.i(TAG,routes.length()+" ");


                    JSONObject route = routes.getJSONObject(0);
                    JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
                    Log.i(TAG,overviewPolyline.toString());

                    List<LatLng> points = PolyUtil.decode(overviewPolyline.getString("points"));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(polyline==null){

                                PolylineOptions polylineOptions = new PolylineOptions();
                                polylineOptions.width(15);
                                polylineOptions.color(getColor(R.color.color_road));
                                polylineOptions.addAll(points);
                                polyline = map.addPolyline(polylineOptions);

                            }else {
                                polyline.setPoints(points);
                            }
                        }
                    });


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }




    public void moveCamera(LatLng latLng2){

        CameraPosition position = CameraPosition.builder()
                .target(latLng2)
                .zoom(10f)
                .build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(cameraUpdate);
    }


    //Checking whether the permissions are allowed ->START

    @SuppressLint("NewApi")
    public boolean checkPermissions(){
        boolean permissions = false;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            permissions = true;
        }
        return permissions;
    }
//Checking whether the permissions are allowed ->END



//checking whether the requested permission were allowed ->START

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean permissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE){

            for (int i = 0; i < permissions.length; i++){

                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED){

                    permissionsGranted = true;

                }else if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED){

                    permissionsGranted = true;
                }
            }

            if (permissionsGranted){
                map.setMyLocationEnabled(true);
            }
        }
    }
    //checking whether the requested permission were allowed ->END
}