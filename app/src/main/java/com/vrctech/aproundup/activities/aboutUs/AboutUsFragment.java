package com.vrctech.aproundup.activities.aboutUs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.Globals;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;

import java.util.ArrayList;


public class AboutUsFragment extends Fragment {

    private TextView policy;
    private CardView termsAndConditions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_about_us, container, false);

        init(view);
        displayPolicies();
        termsAndConditionsClickListener();

        return view;
    }

    public void init(View view){
        policy = view.findViewById(R.id.policy);
        termsAndConditions = view.findViewById(R.id.termsAndConditions);
    }

    private void displayPolicies(){
        String[] policies = getResources().getStringArray(R.array.policies);
        policy.setText("");
        for(int i=0; i<policies.length; i++){
            if(i != policies.length - 1)
                policy.append(Html.fromHtml("&#8226; "+policies[i]+"<br>"));
            else
                policy.append(Html.fromHtml("&#8226; "+policies[i]));
        }
        NotifyHelper.hideLoading();
    }

    private void termsAndConditionsClickListener(){
        termsAndConditions.setOnClickListener(view -> {
            GlobalMethods.startWebViewActivity(getActivity(), R.string.terms_and_conditions, Globals.TERMS_AND_CONDITIONS);
        });
    }

}
