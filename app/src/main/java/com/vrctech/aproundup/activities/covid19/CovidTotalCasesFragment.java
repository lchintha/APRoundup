package com.vrctech.aproundup.activities.covid19;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.Globals;
import com.vrctech.aproundup.JSH;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.services.RestCallback;
import com.vrctech.aproundup.services.RestClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class CovidTotalCasesFragment extends Fragment implements View.OnClickListener {

    private TextView active;
    private TextView deceased;
    private TextView recovered;
    private TextView total;
    private TextView ap_active;
    private TextView ap_deceased;
    private TextView ap_recovered;
    private TextView ap_total;
    private TextView tg_active;
    private TextView tg_deceased;
    private TextView tg_recovered;
    private TextView tg_total;

    private SwipeRefreshLayout refreshPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_covid_total_cases, container, false);
        init(view);

        //Call Corona Cases
        getTotalCases();

        refreshPageListener();

        return view;
    }

    private void init(View view){
        active = view.findViewById(R.id.active);
        deceased = view.findViewById(R.id.deceased);
        recovered = view.findViewById(R.id.recovered);
        total = view.findViewById(R.id.total);
        ap_active = view.findViewById(R.id.ap_active);
        ap_deceased = view.findViewById(R.id.ap_deceased);
        ap_recovered = view.findViewById(R.id.ap_recovered);
        ap_total = view.findViewById(R.id.ap_total);
        tg_active = view.findViewById(R.id.tg_active);
        tg_deceased = view.findViewById(R.id.tg_deceased);
        tg_recovered = view.findViewById(R.id.tg_recovered);
        tg_total = view.findViewById(R.id.tg_total);

        refreshPage = view.findViewById(R.id.refreshPage);
        refreshPage.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        CardView india_card = view.findViewById(R.id.india_card);
        CardView ap_card = view.findViewById(R.id.ap_card);
        CardView tg_card = view.findViewById(R.id.tg_card);

        TextView globalCases = view.findViewById(R.id.globalCases);
        TextView riskScanner = view.findViewById(R.id.riskScanner);

        globalCases.setText(Globals.GLOBAL_CASES);

        india_card.setOnClickListener(this);
        ap_card.setOnClickListener(this);
        tg_card.setOnClickListener(this);

        globalCases.setOnClickListener(this);
        riskScanner.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(getActivity(), StateWiseCasesActivity.class);
        switch (id){
            case R.id.india_card:
                intent.putExtra(StateWiseCasesActivity.PLACE, StateWiseCasesActivity.INDIA);
                startActivity(intent);
                break;
            case R.id.ap_card:
                intent.putExtra(StateWiseCasesActivity.PLACE, StateWiseCasesActivity.AP);
                startActivity(intent);
                break;
            case R.id.tg_card:
                intent.putExtra(StateWiseCasesActivity.PLACE, StateWiseCasesActivity.TG);
                startActivity(intent);
                break;
            case R.id.globalCases:
                GlobalMethods.startWebViewActivity(getActivity(), R.string.covid_global_cases, Globals.GLOBAL_CASES);
                break;
            case R.id.riskScanner:
                Intent covidIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Globals.RISK_SCAN_URL));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(covidIntent);
                break;
        }
    }

    private void getTotalCases(){
        NotifyHelper.showLoading(getActivity());
        RestClient.getTotalCases(new RestCallback(getActivity()){
            @Override
            public void result(JSONObject response) {
                setData(response);
                NotifyHelper.hideLoading();
            }
        });
    }

    private void setData(JSONObject response){
        Activity activity = getActivity();
        if(activity == null)
            return;
        activity.runOnUiThread(() -> {
            int count = 0;
            JSONArray stateWiseCases = JSH.getJSONArray(response, "statewise");
            for(int i=0; i<stateWiseCases.length(); i++){
                JSONObject object = JSH.getJSONObject(stateWiseCases, i);
                switch (JSH.getString(object, "statecode")) {
                    case "TT":
                        count++;
                        active.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "active")));
                        deceased.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "deaths")));
                        recovered.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "recovered")));
                        total.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "confirmed")));
                        break;
                    case "AP":
                        count++;
                        ap_active.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "active")));
                        ap_deceased.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "deaths")));
                        ap_recovered.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "recovered")));
                        ap_total.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "confirmed")));
                        break;
                    case "TG":
                        count++;
                        tg_active.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "active")));
                        tg_deceased.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "deaths")));
                        tg_recovered.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "recovered")));
                        tg_total.setText(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "confirmed")));
                        break;
                }
                if(count == 3)
                    break;
            }
        });
    }

    private void refreshPageListener(){
        refreshPage.setOnRefreshListener(() -> {
            getTotalCases();
            refreshPage.setRefreshing(false);
        });
    }
}
