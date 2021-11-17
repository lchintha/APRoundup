package com.vrctech.aproundup.activities.minister;

import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vrctech.aproundup.DatabaseInstance;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.mla.Mlas;

import java.util.ArrayList;

public class MinistersFragment extends Fragment {

    private RecyclerView ministersList;
    private DatabaseReference databaseReference;
    private ArrayList<Mlas> ministers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_ministers, container, false);

        init(view);
        getMinistersList();

        return view;
    }

    public void init(View view){
        ministersList = view.findViewById(R.id.ministersList);
        databaseReference = DatabaseInstance.getDatabaseReference(getActivity()).child("AP MLA").child("Districts");
        ministers = new ArrayList<>();
    }

    private void getMinistersList(){
        NotifyHelper.showLoading(getActivity());
        final String[] districts = getResources().getStringArray(R.array.ap_districts);
        DatabaseReference districtsReference;
        for (final String district : districts) {
            districtsReference = databaseReference.child(district);
            districtsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final ArrayList<Mlas> mlas = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        DataSnapshot wonMla = item.child("Candidates").child(getCandidateNumber(item));
                        if(wonMla.hasChildren()) {
                            String portfolio = getPortfolio(wonMla);
                            if(!portfolio.isEmpty()) {
                                String name = wonMla.child("name").getValue().toString();
                                String age = wonMla.child("age").getValue().toString();
                                String qualification = wonMla.child("qualification").getValue().toString();
                                String majority = wonMla.child("majority").getValue().toString();
                                String constituency = item.getKey();
                                String mlaPhoto = wonMla.child("photo").getValue().toString();
                                mlas.add(new Mlas(mlaPhoto, name, age, qualification, majority, constituency, district, portfolio));
                            }
                        }
                    }
                    addMlas(mlas);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private String getCandidateNumber(DataSnapshot item){
        String constituency = item.getKey();
        if(constituency == null){
            return "0";
        }
        if(constituency.toLowerCase().equals("mandapeta") || constituency.toLowerCase().equals("repalle") ||
                constituency.toLowerCase().equals("మండపేట") || constituency.toLowerCase().equals("రేపల్లె")){
            return "1";
        }
        return "0";
    }

    private String getPortfolio(DataSnapshot wonMls){
        if(wonMls.hasChild("portfolio")){
            String portfolio = wonMls.child("portfolio").getValue().toString();
            if(!portfolio.toLowerCase().equals("speaker")){
                return portfolio;
            }else{
                return "";
            }
        }
        return "";
    }

    private void addMlas(ArrayList<Mlas> mlas){
        ministers.addAll(mlas);
        if(ministers.size() == 26) {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                ministersList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            else
                ministersList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            putCMFirstInTheList();
            MinisterAdapter ministerAdapter = new MinisterAdapter(ministers, getActivity());
            ministersList.setAdapter(ministerAdapter);
            NotifyHelper.hideLoading();
        }
    }

    private void putCMFirstInTheList(){
        for(int i=0; i<ministers.size(); i++){
            if(ministers.get(i).getMlaConstituency().toLowerCase().equals("pulivendla") ||
                    ministers.get(i).getMlaConstituency().toLowerCase().equals("పులివెందుల")){
                ministers.add(0, ministers.get(i));
                ministers.remove(i+1);
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            ministersList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        else
            ministersList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        Log.d("CALLING", "CALLED");
    }
}
