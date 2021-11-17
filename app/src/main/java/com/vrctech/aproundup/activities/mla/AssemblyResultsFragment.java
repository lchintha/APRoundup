package com.vrctech.aproundup.activities.mla;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;

public class AssemblyResultsFragment extends Fragment implements View.OnClickListener {

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_election_results, container, false);
        init(view);

        return view;
    }

    public void init(View view){
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
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.ysrcpMlas){
            Intent intent = new Intent(context, PartyWiseMlasListActivity.class);
            intent.putExtra(PartyWiseMlasListActivity.PARTY_CODE, getString(R.string.nt_ysrcp));
            startActivity(intent);
        }else if(id == R.id.tdpMlas){
            Intent intent = new Intent(context, PartyWiseMlasListActivity.class);
            intent.putExtra(PartyWiseMlasListActivity.PARTY_CODE, getString(R.string.nt_tdp));
            startActivity(intent);
        }else if(id == R.id.jspMlas){
            Intent intent = new Intent(context, PartyWiseMlasListActivity.class);
            intent.putExtra(PartyWiseMlasListActivity.PARTY_CODE, getString(R.string.nt_jsp));
            startActivity(intent);
        }else if(id == R.id.incMlas){
            NotifyHelper.snackBar(view, R.string.no_mlas);
        }else if(id == R.id.bjpMlas){
            NotifyHelper.snackBar(view, R.string.no_mlas);
        }
    }
}
