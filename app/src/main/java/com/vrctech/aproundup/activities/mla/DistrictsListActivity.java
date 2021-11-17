package com.vrctech.aproundup.activities.mla;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;

import java.util.Objects;

public class DistrictsListActivity extends BaseActivity {

    ListView districtsList;
    String[] districts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_districts);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.districts);

        districtsList = findViewById(R.id.districtsList);
        DistrictsAdapter adapter = new DistrictsAdapter(this, getDistrictsWithNumberOfConstituencies());
        districtsList.setAdapter(adapter);

        districtClickListener();

    }

    public void districtClickListener(){
        districtsList.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(DistrictsListActivity.this, AssemblyConstituenciesListActivity.class);
            intent.putExtra(AssemblyConstituencyResultsActivity.DISTRICT_TAG, districts[position]);
            startActivity(intent);
        });
    }

    public String[] getDistrictsWithNumberOfConstituencies(){
        districts = getResources().getStringArray(R.array.ap_districts);
        String[] numberOfConstituencies = getResources().getStringArray(R.array.district_constituencies);

        String[] results = new String[13];
        for(int i=0; i<districts.length; i++){
            results[i] = districts[i] + " (" + numberOfConstituencies[i] + ")";
        }
        return results;
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
