package com.vrctech.aproundup.activities.mla;

import android.content.res.Configuration;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vrctech.aproundup.DatabaseInstance;
import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;

import java.util.ArrayList;
import java.util.Objects;

public class PartyWiseMlasListActivity extends BaseActivity {

    public static String PARTY_CODE = "PARTY_CODE";
    String REQUESTED_PARTY_CODE;

    RecyclerView mlasList;
    private DatabaseReference databaseReference;

    ArrayList<Mlas> electedMlas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_wise_mlas_list);
        init();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        REQUESTED_PARTY_CODE = getIntent().getStringExtra(PARTY_CODE);
        setTitle(REQUESTED_PARTY_CODE+" MLAs List");

        getMlasList(REQUESTED_PARTY_CODE);
    }

    public void init(){
        mlasList = findViewById(R.id.mlasList);
        databaseReference = DatabaseInstance.getDatabaseReference(this).child("AP MLA").child("Districts");
        electedMlas = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void getMlasList(final String requestedPartyCode){
        NotifyHelper.showLoading(this, R.string.loading);
        final String[] districts = getResources().getStringArray(R.array.ap_districts);
        DatabaseReference districtsReference;
        for (final String district : districts) {
            districtsReference = databaseReference.child(district);
            districtsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final ArrayList<Mlas> mlas = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        DataSnapshot wonMla = item.child("Candidates").child("0");
                        if(wonMla.hasChildren()) {
                            if (wonMla.child("party").getValue().toString().equals(requestedPartyCode)) {
                                String name = wonMla.child("name").getValue().toString();
                                String age = wonMla.child("age").getValue().toString();
                                String qualification = wonMla.child("qualification").getValue().toString();
                                String majority = wonMla.child("majority").getValue().toString();
                                String constituency = item.getKey();
                                String mlaPhoto = wonMla.child("photo").getValue().toString();
                                String portfolio = getPortfolio(wonMla);
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

    public String getPortfolio(DataSnapshot wonMls){
        if(wonMls.hasChild("portfolio")){
            return wonMls.child("portfolio").getValue().toString();
        }
        return "";
    }

    public void addMlas(ArrayList<Mlas> mlasInformation){
        electedMlas.addAll(mlasInformation);
        if((REQUESTED_PARTY_CODE.equals(getString(R.string.nt_ysrcp)) && electedMlas.size() == 151)
                || (REQUESTED_PARTY_CODE.equals(getString(R.string.nt_tdp)) && electedMlas.size() == 23)
                || (REQUESTED_PARTY_CODE.equals(getString(R.string.nt_jsp)) && electedMlas.size() == 1)){
            setLayoutManager();
            MlaAdapter mlaAdapter = new MlaAdapter(electedMlas, this, REQUESTED_PARTY_CODE);
            mlasList.setAdapter(mlaAdapter);
            Log.e("SIZE", Integer.toString(electedMlas.size()));
            NotifyHelper.hideLoading();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        GlobalMethods.setLocale(GlobalMethods.getPreferredLanguage(this), this, getApplicationContext());
        setLayoutManager();
    }

    private void setLayoutManager(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            mlasList.setLayoutManager(new GridLayoutManager(this, 2));
        else
            mlasList.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
