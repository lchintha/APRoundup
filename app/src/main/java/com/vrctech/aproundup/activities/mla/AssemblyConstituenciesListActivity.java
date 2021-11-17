package com.vrctech.aproundup.activities.mla;

import android.content.Intent;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vrctech.aproundup.DatabaseInstance;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;

import java.util.ArrayList;
import java.util.Objects;

public class AssemblyConstituenciesListActivity extends BaseActivity {

    String district;
    ListView constituenciesList;
    String[] constituencies;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constituencies_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        district = getIntent().getStringExtra(AssemblyConstituencyResultsActivity.DISTRICT_TAG);
        setTitle(district);

        constituenciesList = findViewById(R.id.constituenciesList);
        databaseReference = DatabaseInstance.getDatabaseReference(this).child("AP MLA").child("Districts").child(district);
        getConstituencies();

    }

    public void getConstituencies(){
        NotifyHelper.showLoading(this, R.string.loading);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> constituencies = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    constituencies.add(item.getKey());
                }
                DistrictsAdapter adapter = new DistrictsAdapter(AssemblyConstituenciesListActivity.this, convertArrayListToStrings(constituencies));
                constituenciesList.setAdapter(adapter);
                constituenciesListClickListener();
                NotifyHelper.hideLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void constituenciesListClickListener(){
        constituenciesList.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(AssemblyConstituenciesListActivity.this, AssemblyConstituencyResultsActivity.class);
            intent.putExtra(AssemblyConstituencyResultsActivity.DISTRICT_TAG, district);
            intent.putExtra(AssemblyConstituencyResultsActivity.CONSTITUENCY_TAG, constituencies[position]);
            startActivity(intent);
        });
    }

    public String[] convertArrayListToStrings(ArrayList<String> strings){
        constituencies = new String[strings.size()];
        return strings.toArray(constituencies);
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
}
