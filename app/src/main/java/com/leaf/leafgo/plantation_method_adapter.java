package com.leaf.leafgo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leaf.leafgo.model.planting_steps;

import java.util.ArrayList;

public class plantation_method_adapter  extends RecyclerView.Adapter<plantation_method_adapter.myViewHolder> {

    private ArrayList<planting_steps> stepsArray;

    public plantation_method_adapter(ArrayList<planting_steps> steps){

        this.stepsArray=steps;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plant_method_item, parent, false);

        myViewHolder myviewholder=new myViewHolder(view);

        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        planting_steps um = stepsArray.get(position);
        holder.description.setText(String.valueOf(um.getStep()));
    }

    @Override
    public int getItemCount() {

        return stepsArray.size();
    }

    public static final class  myViewHolder extends RecyclerView.ViewHolder{

        TextView description;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.descriptionSteps);

        }
    }
}
