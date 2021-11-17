package com.vrctech.aproundup.activities.common;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vrctech.aproundup.R;

import java.util.ArrayList;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateHolder> {

    private ArrayList<Candidates> candidates;
    private Context context;

    public CandidateAdapter(ArrayList<Candidates> candidates, Context context) {
        this.candidates = candidates;
        this.context = context;
    }

    @NonNull
    @Override
    public CandidateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CandidateHolder(LayoutInflater.from(context).inflate(R.layout.layout_candidate_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateHolder candidateHolder, int position) {
        Candidates candidate = candidates.get(position);
        candidateHolder.name.setText(candidate.getName());
        candidateHolder.majority.setText(candidate.getMajority());
        candidateHolder.voteShare.setText(candidate.getVoteShare());
        candidateHolder.voteShare.append("%");
        candidateHolder.totalVotes.setText(candidate.getVotes());

        String partyCode = candidate.getParty();
        if(partyCode.equals(context.getResources().getString(R.string.nt_ysrcp))){
            candidateHolder.partyFlag.setImageResource(R.drawable.ysrcp_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_tdp))){
            candidateHolder.partyFlag.setImageResource(R.drawable.tdp_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_jsp))){
            candidateHolder.partyFlag.setImageResource(R.drawable.jsp_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_inc))){
            candidateHolder.partyFlag.setImageResource(R.drawable.inc_flag);
        }else if(partyCode.equals(context.getResources().getString(R.string.nt_bjp))){
            candidateHolder.partyFlag.setImageResource(R.drawable.bjp_flag);
        }else{
            candidateHolder.partyFlag.setImageResource(R.drawable.ind_flag);
        }

        if(candidate.getPhoto().isEmpty())
            candidateHolder.candidatePhoto.setImageResource(R.drawable.politician_placeholder);
        else
            Picasso.get().load(candidate.getPhoto()).placeholder(R.drawable.politician_placeholder).into(candidateHolder.candidatePhoto);

        if(candidate.getPortfolio().toLowerCase().equals("cm")){
            candidateHolder.ribbon.setImageResource(R.drawable.cm_ribbon);
            candidateHolder.ribbon.setVisibility(View.VISIBLE);
        }else if(candidate.getPortfolio().toLowerCase().equals("speaker")){
            candidateHolder.ribbon.setImageResource(R.drawable.speaker_ribbon);
            candidateHolder.ribbon.setVisibility(View.VISIBLE);
        }else if(!candidate.getPortfolio().isEmpty()) {
            candidateHolder.ribbon.setImageResource(R.drawable.minister_ribbon);
            candidateHolder.ribbon.setVisibility(View.VISIBLE);
        }else {
            candidateHolder.ribbon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }

    class CandidateHolder extends RecyclerView.ViewHolder {

        //TextView qualification, age;
        TextView name, majority, voteShare, totalVotes;
        ImageView partyFlag, candidatePhoto, ribbon;
        View root;

        CandidateHolder(View itemView){
            super(itemView);
            root = itemView;

            name = itemView.findViewById(R.id.name);
            //age = itemView.findViewById(R.id.age);
            //qualification = itemView.findViewById(R.id.qualification);
            majority = itemView.findViewById(R.id.majority);
            voteShare = itemView.findViewById(R.id.voteShare);
            totalVotes = itemView.findViewById(R.id.totalVotes);

            partyFlag = itemView.findViewById(R.id.partyFlag);
            candidatePhoto = itemView.findViewById(R.id.candidatePhoto);
            ribbon = itemView.findViewById(R.id.ministerRibbon);
        }
    }
}
