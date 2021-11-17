package com.vrctech.aproundup.activities.covid19;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.JSH;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;
import com.vrctech.aproundup.services.RestCallback;
import com.vrctech.aproundup.services.RestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class StateWiseCasesActivity extends BaseActivity {

    LinearLayout header;
    RecyclerView list;

    static String PLACE = "PLACE";
    static String INDIA = "INDIA";
    static String AP = "AP";
    static String TG = "TG";
    static String ANDHRA_PRADESH = "Andhra Pradesh";
    static String TELANGANA = "Telangana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise_cases);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        init();

        String place = getIntent().getStringExtra(PLACE);
        if(place.equals(INDIA)){
            header.setVisibility(View.VISIBLE);
            setTitle(R.string.state_wise_list);
            displayStateWiseCases();
        }else if(place.equals(AP)){
            setTitle(R.string.ap_district_wise_list);
            displayCases(ANDHRA_PRADESH);
        }else if(place.equals(TG)){
            setTitle(R.string.tg_district_wise_list);
            displayCases(TELANGANA);
        }
    }

    private void init(){
        header = findViewById(R.id.header);
        list = findViewById(R.id.list);
    }

    private void displayStateWiseCases(){
        NotifyHelper.showLoading(this);
        RestClient.getTotalCases(new RestCallback(this){
            @Override
            public void result(JSONObject response) {
                CovidCasesAdapter adapter = new CovidCasesAdapter(StateWiseCasesActivity.this, getStateWiseCases(response));
                setAdapter(adapter);
                NotifyHelper.hideLoading();
            }
        });
    }

    private ArrayList<CovidCase> getStateWiseCases(JSONObject response){
        ArrayList<CovidCase> covidCases = new ArrayList<>();
        JSONArray stateWiseCases = JSH.getJSONArray(response, "statewise");
        for(int i=0; i<stateWiseCases.length(); i++){
            JSONObject object = JSH.getJSONObject(stateWiseCases, i);
            if(!JSH.getString(object, "statecode").equals("TT")) {
                covidCases.add(new CovidCase(JSH.getString(object, "state"),
                                    GlobalMethods.getCommaSeparatedString(JSH.getString(object, "active")),
                                    GlobalMethods.getCommaSeparatedString(JSH.getString(object, "deaths")),
                                    GlobalMethods.getCommaSeparatedString(JSH.getString(object, "recovered")),
                                    GlobalMethods.getCommaSeparatedString(JSH.getString(object, "confirmed"))));
            }
        }
        return covidCases;
    }

    private void setAdapter(RecyclerView.Adapter adapter){
        runOnUiThread(() -> {
            list.setLayoutManager(new LinearLayoutManager(StateWiseCasesActivity.this));
            list.setAdapter(adapter);
        });
    }

    private void displayCases(String place){
        NotifyHelper.showLoading(this);
        RestClient.getStateWiseCases(new RestCallback(this) {
            @Override
            public void result(JSONObject response) {
                DistricWiseCasesAdapter adapter = new DistricWiseCasesAdapter(StateWiseCasesActivity.this, getDistrictWiseCases(response, place));
                setAdapter(adapter);
                NotifyHelper.hideLoading();
            }
        });
    }

    private ArrayList<CovidCase> getDistrictWiseCases(JSONObject response, String place){

        ArrayList<CovidCase> covidCases = new ArrayList<>();
        JSONArray stateWiseData = JSH.getJSONArray(response, "stateWiseData");
        for(int i=0; i<stateWiseData.length(); i++){
            JSONObject object = JSH.getJSONObject(stateWiseData, i);
            if(JSH.getString(object, "state").equals(place)){
                JSONArray districtData = JSH.getJSONArray(object, "districtData");
                for(int j=0; j<districtData.length(); j++){
                    JSONObject districtObject = JSH.getJSONObject(districtData, j);
                    covidCases.add(new CovidCase(JSH.getString(districtObject, "district"),
                            "", "", "", JSH.getString(districtObject, "confirmed")));
                }
                break;
            }
        }
        return covidCases;
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
