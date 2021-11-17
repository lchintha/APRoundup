package com.vrctech.aproundup.activities.minister;

import android.app.Activity;
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
import com.vrctech.aproundup.activities.mla.AssemblyConstituencyResultsActivity;
import com.vrctech.aproundup.activities.mla.Mlas;

import java.util.ArrayList;

public class MinisterAdapter extends RecyclerView.Adapter<MinisterAdapter.MinisterHolder> {

    private ArrayList<Mlas> mlasList;
    private Activity context;

    private int lastPosition = -1;

    MinisterAdapter(ArrayList<Mlas> mlasList, Activity context) {
        this.mlasList = mlasList;
        this.context = context;
    }

    @NonNull
    @Override
    public MinisterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MinisterHolder(LayoutInflater.from(context).inflate(R.layout.layout_minister_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MinisterHolder ministerHolder, int position) {
        final Mlas mlaInfo = mlasList.get(position);

        ministerHolder.name.setText(mlaInfo.getMlaName());
        ministerHolder.constituency.setText(mlaInfo.getMlaConstituency());
        ministerHolder.district.setText(mlaInfo.getMlaDistrict());
        ministerHolder.portfolio.setText(mlaInfo.getPortfolio());

        ministerHolder.majority.setText(mlaInfo.getMlaMajority());

        if(mlaInfo.getMlaPhotoUrl().isEmpty()) {
            ministerHolder.candidatePhoto.setImageResource(R.drawable.politician_placeholder);
        }else {
            Picasso.get().load(mlaInfo.getMlaPhotoUrl()).placeholder(R.drawable.politician_placeholder).into(ministerHolder.candidatePhoto);
        }

        if(mlaInfo.getPortfolio().toLowerCase().equals("cm")){
            ministerHolder.ribbon.setImageResource(R.drawable.cm_ribbon);
            ministerHolder.portfolio.setText(R.string.chief_minister);
        }else {
            ministerHolder.ribbon.setImageResource(R.drawable.minister_ribbon);
        }

        ministerHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AssemblyConstituencyResultsActivity.class);
                intent.putExtra(AssemblyConstituencyResultsActivity.DISTRICT_TAG, mlaInfo.getMlaDistrict());
                intent.putExtra(AssemblyConstituencyResultsActivity.CONSTITUENCY_TAG, mlaInfo.getMlaConstituency());
                context.startActivity(intent);
            }
        });

        //setAnimation(ministerHolder.root, position);
    }

    @Override
    public int getItemCount() {
        return mlasList.size();
    }

    class MinisterHolder extends RecyclerView.ViewHolder {

        //TextView age, qualification;
        TextView name, majority, constituency, district, portfolio;
        ImageView candidatePhoto, ribbon;
        View root;

        MinisterHolder(View itemView){
            super(itemView);
            root = itemView;

            name = itemView.findViewById(R.id.name);
            //age = itemView.findViewById(R.id.age);
            //qualification = itemView.findViewById(R.id.qualification);
            majority = itemView.findViewById(R.id.majority);
            constituency = itemView.findViewById(R.id.constituency);
            district = itemView.findViewById(R.id.district);
            portfolio = itemView.findViewById(R.id.portfolio);

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
