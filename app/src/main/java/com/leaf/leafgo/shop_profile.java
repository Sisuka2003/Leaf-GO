package com.leaf.leafgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.leaf.leafgo.model.shopOwners;

public class shop_profile extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1312;
    private GoogleMap map;
    private Marker mymarker;

    private static final String TAG="shop_profile";
    private FirebaseFirestore firestore;
    private FirebaseStorage firebasestorage;
//    String registerEmail=register.emailPublic;
//    String loginEmail=login.emailPublic;
    String email=shopmap.tappedShopEmail;
    private Marker shopMarker;

    String shopNameBanner;
    String shopNameText;
    String shopEmail;
    String shopNumber;
    String shopAddress;
    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile);

        firestore=FirebaseFirestore.getInstance();
        firebasestorage=FirebaseStorage.getInstance();


        TextView bannerView=findViewById(R.id.shopBannerView);
        TextView emailView=findViewById(R.id.shopEmailView);
        TextView numberView=findViewById(R.id.shopNumberView);
        TextView addressView=findViewById(R.id.shopAddressView);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        CollectionReference shopOwners = firestore.collection("shopOwners");
        ProgressBar progress = findViewById(R.id.progressBar2);


        shopOwners.whereEqualTo("shopEmail",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot snapshot : task.getResult()){
                    shopOwners owners = snapshot.toObject(shopOwners.class);
                    shopNameBanner= owners.getShopName();
                    shopNameText = owners.getShopName();
                    shopEmail = owners.getShopEmail();
                    shopNumber = owners.getShopNumber();
                    shopAddress = owners.getShopAddress();
                    latitude = owners.getLatitudeLocation();
                    longitude = owners.getLongitudeLocation();

                    bannerView.setText(shopNameBanner);
                    numberView.setText(shopNumber);
                    emailView.setText(shopEmail);
                    addressView.setText(shopAddress);


                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    MarkerOptions markerOptionsShop = new MarkerOptions();
                    markerOptionsShop.icon(BitmapDescriptorFactory.fromResource(R.drawable.store));
                    markerOptionsShop.title(shopNameText);
                    markerOptionsShop.position(latLng);
                    shopMarker=map.addMarker(markerOptionsShop);

                    CameraPosition position = CameraPosition.builder()
                            .target(latLng)
                            .zoom(15f)
                            .build();

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
                    map.animateCamera(cameraUpdate);
                    progress.setVisibility(View.INVISIBLE);

                }}
        });


        findViewById(R.id.profileToMapBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopmap = new Intent(shop_profile.this, shopmap.class);
                startActivity(shopmap);
            }
        });

    }


    @SuppressLint({"MissingPermission", "NewApi"})
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        map = googleMap;
        // map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        map.getUiSettings().setZoomControlsEnabled(true);
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