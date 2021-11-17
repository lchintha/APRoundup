package com.vrctech.aproundup.activities.mp;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vrctech.aproundup.R;

import java.util.ArrayList;

public class MpAdapter extends RecyclerView.Adapter<MpAdapter.MpHolder> {

    private ArrayList<Mps> mpsList;
    private Context context;
    private String partyCode;

    private int lastPosition = -1;

    MpAdapter(ArrayList<Mps> mpsList, Context context, String partyCode) {
        this.mpsList = mpsList;
        this.context = context;
        this.partyCode = partyCode;
    }

    @NonNull
    @Override
    public MpHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MpAdapter.MpHolder(LayoutInflater.from(context).inflate(R.layout.layout_mla_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MpAdapter.MpHolder mpHolder, int position) {
        final Mps mpInfo = mpsList.get(position);
        mpHolder.name.setText(mpInfo.getMpName());
        mpHolder.majority.setText(mpInfo.getMpMajority());
        mpHolder.constituency.setText(mpInfo.getMpConstituency());
        mpHolder.district.setText(mpInfo.getDistrict());

        if(partyCode.equals(context.getResources().getString(R.string.nt_ysrcp))){
            mpHolder.partyFlag.setImageResource(R.drawable.ysrcp_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_tdp))){
            mpHolder.partyFlag.setImageResource(R.drawable.tdp_flag);
        }

        if(mpInfo.getMpPhotoUrl().isEmpty())
            mpHolder.candidatePhoto.setImageResource(R.drawable.politician_placeholder);
        else
            Picasso.get().load(mpInfo.getMpPhotoUrl()).placeholder(R.drawable.politician_placeholder).into(mpHolder.candidatePhoto);

        mpHolder.root.setOnClickListener(view -> {
            Intent intent = new Intent(context, ParliamentConstituencyResultsActivity.class);
            intent.putExtra(ParliamentConstituencyResultsActivity.CONSTITUENCY_TAG, mpInfo.getMpConstituency());
            context.startActivity(intent);
        });

        setAnimation(mpHolder.root, position);
    }

    @Override
    public int getItemCount() {
        return mpsList.size();
    }

    class MpHolder extends RecyclerView.ViewHolder {

        //TextView age, qualification;
        TextView name, majority, constituency, district;
        ImageView partyFlag, candidatePhoto;
        View root;

        MpHolder(View itemView){
            super(itemView);
            root = itemView;

            name = itemView.findViewById(R.id.name);
            //age = itemView.findViewById(R.id.age);
            //qualification = itemView.findViewById(R.id.qualification);
            majority = itemView.findViewById(R.id.majority);
            constituency = itemView.findViewById(R.id.constituency);
            district = itemView.findViewById(R.id.district);

            partyFlag = itemView.findViewById(R.id.partyFlag);
            candidatePhoto = itemView.findViewById(R.id.candidatePhoto);
        }
    }

    private void setAnimation(View viewToAnimate, int position){
        Animation animation;
        if(position > lastPosition){
            animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_slide_down);
        }else{
            animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_slide_up);
        }
        viewToAnimate.startAnimation(animation);
        lastPosition = position;
    }
}
