package com.vrctech.aproundup.activities.mla;

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

public class MlaAdapter extends RecyclerView.Adapter<MlaAdapter.MlaHolder> {

    private ArrayList<Mlas> mlasList;
    private Context context;
    private String partyCode;

    private int lastPosition = -1;

    MlaAdapter(ArrayList<Mlas> mlasList, Context context, String partyCode) {
        this.mlasList = mlasList;
        this.context = context;
        this.partyCode = partyCode;
    }

    @NonNull
    @Override
    public MlaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MlaHolder(LayoutInflater.from(context).inflate(R.layout.layout_mla_cell, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return mlasList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MlaHolder mlaHolder, int position) {
        final Mlas mlaInfo = mlasList.get(position);
        mlaHolder.name.setText(mlaInfo.getMlaName());
        mlaHolder.majority.setText(mlaInfo.getMlaMajority());
        mlaHolder.constituency.setText(mlaInfo.getMlaConstituency());
        mlaHolder.district.setText(mlaInfo.getMlaDistrict());

        if(partyCode.equals(context.getResources().getString(R.string.nt_ysrcp))){
            mlaHolder.partyFlag.setImageResource(R.drawable.ysrcp_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_tdp))){
            mlaHolder.partyFlag.setImageResource(R.drawable.tdp_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_jsp))){
            mlaHolder.partyFlag.setImageResource(R.drawable.jsp_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_inc))){
            mlaHolder.partyFlag.setImageResource(R.drawable.inc_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_bjp))){
            mlaHolder.partyFlag.setImageResource(R.drawable.bjp_flag);
        }

        if(mlaInfo.getMlaPhotoUrl().isEmpty()) {
            mlaHolder.candidatePhoto.setImageResource(R.drawable.politician_placeholder);
        }else {
            Picasso.get().load(mlaInfo.getMlaPhotoUrl()).placeholder(R.drawable.politician_placeholder).into(mlaHolder.candidatePhoto);
        }

        if(mlaInfo.getPortfolio().toLowerCase().equals("cm")){
            mlaHolder.ribbon.setImageResource(R.drawable.cm_ribbon);
            mlaHolder.ribbon.setVisibility(View.VISIBLE);
        }else if(mlaInfo.getPortfolio().toLowerCase().equals("speaker")){
            mlaHolder.ribbon.setImageResource(R.drawable.speaker_ribbon);
            mlaHolder.ribbon.setVisibility(View.VISIBLE);
        }else if(!mlaInfo.getPortfolio().isEmpty()) {
            mlaHolder.ribbon.setImageResource(R.drawable.minister_ribbon);
            mlaHolder.ribbon.setVisibility(View.VISIBLE);
        }else {
            mlaHolder.ribbon.setVisibility(View.GONE);
        }

        mlaHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AssemblyConstituencyResultsActivity.class);
                intent.putExtra(AssemblyConstituencyResultsActivity.DISTRICT_TAG, mlaInfo.getMlaDistrict());
                intent.putExtra(AssemblyConstituencyResultsActivity.CONSTITUENCY_TAG, mlaInfo.getMlaConstituency());
                context.startActivity(intent);
            }
        });

        setAnimation(mlaHolder.root, position);
    }

    class MlaHolder extends RecyclerView.ViewHolder {

        //TextView age, qualification;
        TextView name, majority, constituency, district;
        ImageView partyFlag, candidatePhoto, ribbon;
        View root;

        MlaHolder(View itemView){
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
            ribbon = itemView.findViewById(R.id.ministerRibbon);
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
