package com.vrctech.aproundup.activities.mp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.mla.PartyWiseMlasListActivity;

public class ParliamentResultsFragment extends Fragment implements View.OnClickListener {

    private TextView pageTitle;
    private TextView ysrcpSeats;
    private TextView ysrcpVoteShare;
    private TextView tdpSeats;
    private TextView tdpVoteShare;
    private TextView jspSeats;
    private TextView jspVoteShare;
    private TextView incVoteShare;
    private TextView bjpVoteShare;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_election_results, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        pageTitle = view.findViewById(R.id.pageTitle);
        ysrcpSeats = view.findViewById(R.id.ysrcpSeats);
        ysrcpVoteShare = view.findViewById(R.id.ysrcpVoteShare);
        tdpSeats = view.findViewById(R.id.tdpSeats);
        tdpVoteShare = view.findViewById(R.id.tdpVoteShare);
        jspSeats = view.findViewById(R.id.jspSeats);
        jspVoteShare = view.findViewById(R.id.jspVoteShare);
        incVoteShare = view.findViewById(R.id.incVoteShare);
        bjpVoteShare = view.findViewById(R.id.bjpVoteShare);

        CardView ysrcpMlas = view.findViewById(R.id.ysrcpMlas);
        CardView tdpMlas = view.findViewById(R.id.tdpMlas);
        CardView jspMlas = view.findViewById(R.id.jspMlas);
        CardView incMlas = view.findViewById(R.id.incMlas);
        CardView bjpMlas = view.findViewById(R.id.bjpMlas);

        context = getActivity();
        ysrcpMlas.setOnClickListener(this);
        tdpMlas.setOnClickListener(this);
        jspMlas.setOnClickListener(this);
        incMlas.setOnClickListener(this);
        bjpMlas.setOnClickListener(this);

        setStaticText();
    }

    private void setStaticText(){
        pageTitle.setText(R.string.parliament_election_results);
        ysrcpSeats.setText(R.string.ysrcp_mp_seats);
        ysrcpVoteShare.setText(R.string.ysrcp_plmnt_vote_percentage);
        tdpSeats.setText(R.string.tdp_mp_seats);
        tdpVoteShare.setText(R.string.tdp_plmnt_vote_percentage);
        jspSeats.setText(R.string.jsp_mp_seats);
        jspVoteShare.setText(R.string.jsp_plmnt_vote_percentage);
        incVoteShare.setText(R.string.inc_plmnt_vote_percentage);
        bjpVoteShare.setText(R.string.bjp_plmnt_vote_percentage);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.ysrcpMlas){
            Intent intent = new Intent(context, PartyWiseMpsListActivity.class);
            intent.putExtra(PartyWiseMlasListActivity.PARTY_CODE, getString(R.string.nt_ysrcp));
            startActivity(intent);
        }else if(id == R.id.tdpMlas){
            Intent intent = new Intent(context, PartyWiseMpsListActivity.class);
            intent.putExtra(PartyWiseMlasListActivity.PARTY_CODE, getString(R.string.nt_tdp));
            startActivity(intent);
        }else if(id == R.id.jspMlas){
            NotifyHelper.snackBar(view, R.string.no_mps);
        }else if(id == R.id.incMlas){
            NotifyHelper.snackBar(view, R.string.no_mps);
        }else if(id == R.id.bjpMlas){
            NotifyHelper.snackBar(view, R.string.no_mps);
        }
    }
}
