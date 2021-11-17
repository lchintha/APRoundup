package com.vrctech.aproundup.activities.mp;

import android.content.res.Configuration;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vrctech.aproundup.DatabaseInstance;
import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;
import com.vrctech.aproundup.activities.mla.PartyWiseMlasListActivity;

import java.util.ArrayList;
import java.util.Objects;

public class PartyWiseMpsListActivity extends BaseActivity {

    String REQUESTED_PARTY_CODE;

    RecyclerView mpsList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_wise_mps_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        init();

        REQUESTED_PARTY_CODE = getIntent().getStringExtra(PartyWiseMlasListActivity.PARTY_CODE);
        setTitle(REQUESTED_PARTY_CODE+" MPs List");

        getMpsList(REQUESTED_PARTY_CODE);
    }

    public void init(){
        mpsList = findViewById(R.id.mpsList);
        databaseReference = DatabaseInstance.getDatabaseReference(this).child("AP MP");
    }

    public void getMpsList(final String requestedPartyCode){
        NotifyHelper.showLoading(this, R.string.loading);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Mps> mps = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String district = item.child("district").getValue().toString();
                    DataSnapshot wonMp = item.child("Candidates").child("0");
                    if(wonMp.hasChildren()) {
                        if (wonMp.child("party").getValue().toString().equals(requestedPartyCode)) {
                            String name = wonMp.child("name").getValue().toString();
                            String age = wonMp.child("age").getValue().toString();
                            String qualification = wonMp.child("qualification").getValue().toString();
                            String majority = wonMp.child("majority").getValue().toString();
                            String constituency = item.getKey();
                            String mlaPhoto = wonMp.child("photo").getValue().toString();
                            mps.add(new Mps(mlaPhoto, name, age, qualification, majority, constituency, district));
                        }
                    }
                }
                showMpsList(mps);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showMpsList(ArrayList<Mps> electedMps){
        setLayoutManager();
        MpAdapter mpAdapter = new MpAdapter(electedMps, this, REQUESTED_PARTY_CODE);
        mpsList.setAdapter(mpAdapter);
        NotifyHelper.hideLoading();
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
            mpsList.setLayoutManager(new GridLayoutManager(this, 2));
        else
            mpsList.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
