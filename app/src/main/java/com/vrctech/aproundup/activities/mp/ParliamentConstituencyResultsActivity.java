package com.vrctech.aproundup.activities.mp;

import android.content.Intent;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vrctech.aproundup.DatabaseInstance;
import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;
import com.vrctech.aproundup.activities.common.CandidateAdapter;
import com.vrctech.aproundup.activities.common.Candidates;

import java.util.ArrayList;
import java.util.Objects;

public class ParliamentConstituencyResultsActivity extends BaseActivity {

    public static String CONSTITUENCY_TAG = "CONSTITUENCY_TAG";

    public String constituency;

    DatabaseReference databaseReference;
    ArrayList<Candidates> candidates;

    TextView totalVoters, maleVoters, femaleVoters, thirdGenders, votesPolled, pollingPercentage;
    RecyclerView candidatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constituency_result);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        constituency = intent.getStringExtra(CONSTITUENCY_TAG);
        setTitle(constituency);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = DatabaseInstance.getDatabaseReference(this).child("AP MP").child(constituency);
        candidates = new ArrayList<>();

        getCandidatesInfo(databaseReference.child("Candidates"));
        getConstitutionInfo(databaseReference);
    }

    public void init(){
        totalVoters = findViewById(R.id.totalVoters);
        maleVoters = findViewById(R.id.maleVoters);
        femaleVoters = findViewById(R.id.femaleVoters);
        thirdGenders = findViewById(R.id.thirdGenders);
        votesPolled = findViewById(R.id.votesPolled);
        pollingPercentage = findViewById(R.id.pollingPercentage);

        candidatesList = findViewById(R.id.candidatesList);
    }

    public void getCandidatesInfo(DatabaseReference databaseReference){
        NotifyHelper.showLoading(this);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    String name = item.child("name").getValue().toString();
                    String age = item.child("age").getValue().toString();
                    String qualification = item.child("qualification").getValue().toString();
                    String majority = item.child("majority").getValue().toString();
                    String party = item.child("party").getValue().toString();
                    String photo = item.child("photo").getValue().toString();
                    String status = item.child("status").getValue().toString();
                    String voteShare = item.child("votePercentage").getValue().toString();
                    String totalVotes = item.child("votes").getValue().toString();

                    candidates.add(new Candidates(name, age, qualification, majority, party, photo, status, voteShare, totalVotes, ""));
                }
                displayCandidates(candidates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void displayCandidates(ArrayList<Candidates> candidates){
        setLayoutManager();
        CandidateAdapter adapter = new CandidateAdapter(candidates, this);
        candidatesList.setAdapter(adapter);
        NotifyHelper.hideLoading();
    }

    public void getConstitutionInfo(DatabaseReference databaseReference){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String totalNumberOfVoters = dataSnapshot.child("totalVotes").getValue().toString();
                String numberOfVotesPolled = dataSnapshot.child("votesPolled").getValue().toString();
                totalVoters.setText(totalNumberOfVoters);
                maleVoters.setText(dataSnapshot.child("maleVoters").getValue().toString());
                femaleVoters.setText(dataSnapshot.child("femaleVoters").getValue().toString());
                thirdGenders.setText(dataSnapshot.child("thirdGender").getValue().toString());
                votesPolled.setText(numberOfVotesPolled);
                pollingPercentage.setText(GlobalMethods.getPercentage(totalNumberOfVoters, numberOfVotesPolled));
                pollingPercentage.append("%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutManager();
    }

    private void setLayoutManager(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            candidatesList.setLayoutManager(new GridLayoutManager(this, 2));
        else
            candidatesList.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
