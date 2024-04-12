package com.example.petconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.R;
import com.example.petconnect.activity.CreateNewPetActivity;
import com.example.petconnect.activity.ProfileActivity;
import com.example.petconnect.models.ExtendedPet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.ViewHolder> {
    private List<ExtendedPet> petList;
    private Context context;

    public PetListAdapter(Context context, List<ExtendedPet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExtendedPet pet = petList.get(position);
        holder.petName.setText(pet.getName());
        holder.petType.setText(pet.getPet_type().getName());
        Picasso.with(context).load(pet.getImage()).fit().into(holder.petImage);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName;
        TextView petType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImage);
            petName = itemView.findViewById(R.id.petName);
            petType = itemView.findViewById(R.id.petType);
        }
    }

}
