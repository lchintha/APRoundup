package com.vrctech.aproundup.activities.epaper;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.Globals;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.services.RestCallback;
import com.vrctech.aproundup.services.RestClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class EpaperFragment extends Fragment {

    private RecyclerView newsPapersList;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_epapers_fragment, container, false);

        init(view);
        GlobalMethods.setLocale(GlobalMethods.getPreferredLanguage(activity), activity, activity.getBaseContext());
        displayLatestAvailablePapers();

        return view;
    }

    private void init(View view){
        newsPapersList = view.findViewById(R.id.newsPapersList);
        activity = getActivity();
    }

    private void displayLatestAvailablePapers(){
        NotifyHelper.showLoading(activity);
        RestClient.getAvailableDateForEpaper(Globals.CODE_ANDHRAJYOTHI_HYD, new RestCallback() {
            @Override
            public void result(JSONObject response) {
                Log.d("RESPONSE", response.toString());
                Iterator<String> keys = response.keys();
                String latestDate = keys.next();
                ArrayList<Epaper> epapers = NewsPapers.getPapersList(latestDate);
                setRecyclerAdapter(epapers);
                NotifyHelper.hideLoading();
            }
        });
    }

    private void setRecyclerAdapter(ArrayList<Epaper> epapers){
        activity.runOnUiThread(() -> {
            EpaperAdapter adapter = new EpaperAdapter(epapers, activity, getActivity().getSupportFragmentManager());
            newsPapersList.setAdapter(adapter);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                newsPapersList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            else
                newsPapersList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        });
    }

}
