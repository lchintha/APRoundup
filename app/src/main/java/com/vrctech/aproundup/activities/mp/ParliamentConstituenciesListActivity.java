package com.vrctech.aproundup.activities.mp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.vrctech.aproundup.DatabaseInstance;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;
import com.vrctech.aproundup.activities.mla.DistrictsAdapter;

import java.util.Objects;

public class ParliamentConstituenciesListActivity extends BaseActivity {

    ListView constituenciesList;
    DatabaseReference databaseReference;
    String[] constituencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parliament_constituncies_list);

        init();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.parliament_constituencies);

        displayParliamentConstituencies();
    }

    public void init(){
        constituenciesList = findViewById(R.id.constituenciesList);
        databaseReference = DatabaseInstance.getDatabaseReference(this).child("AP MP");
    }

    public void displayParliamentConstituencies(){
        constituencies = getResources().getStringArray(R.array.ap_parliament_constituencies);
        DistrictsAdapter adapter = new DistrictsAdapter(ParliamentConstituenciesListActivity.this, constituencies);
        constituenciesList.setAdapter(adapter);
        constituenciesListClickListener();
    }

    public void constituenciesListClickListener(){
        constituenciesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ParliamentConstituenciesListActivity.this, ParliamentConstituencyResultsActivity.class);
                intent.putExtra(ParliamentConstituencyResultsActivity.CONSTITUENCY_TAG, constituencies[position]);
                startActivity(intent);
            }
        });
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
