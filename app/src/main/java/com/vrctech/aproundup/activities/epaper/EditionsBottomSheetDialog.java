package com.vrctech.aproundup.activities.epaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vrctech.aproundup.JSH;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.services.RestCallback;
import com.vrctech.aproundup.services.RestClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class EditionsBottomSheetDialog extends BottomSheetDialogFragment implements EditionClickImpl{

    static String PAPER_NAME = "PAPER_NAME";
    static String EDITIONS_DATA = "EDITIONS_DATA";

    private TextView paperName;
    private RecyclerView editionsList;

    private Edition paperEdition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottomsheet_epaper_editions, container, false);

        init(view);

        Bundle bundle = getArguments();
        if(bundle != null) {
            paperName.setText(bundle.getInt(PAPER_NAME));
            displayEditionsData(bundle);
        }

        return view;
    }

    private void init(View view){
        paperName = view.findViewById(R.id.paperName);
        editionsList = view.findViewById(R.id.editionsList);
    }

    private void displayEditionsData(Bundle bundle){
        ArrayList<Edition> editions = (ArrayList<Edition>) bundle.getSerializable(EDITIONS_DATA);
        EditionAdapter adapter = new EditionAdapter(editions, getActivity(), this);
        editionsList.setAdapter(adapter);
        editionsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void editionOnClick(Edition edition) {
        paperEdition = edition;
        getNecessaryInfoToDisplayPaper();
    }

    private void getNecessaryInfoToDisplayPaper(){
        NotifyHelper.showLoading(getActivity());
        RestClient.getAvailableDateForEpaper(paperEdition.getId(), new RestCallback() {
            @Override
            public void result(JSONObject response) {
                Iterator<String> keys = response.keys();
                String latestDate = keys.next();
                String paperId = JSH.getString(response, latestDate);
                getEPaperKeys(paperId);
            }
        });
    }

    private void getEPaperKeys(String paperCode){
        RestClient.getEPaperKeys(paperCode, new RestCallback() {
            @Override
            public void result(JSONObject response) {
                ArrayList<PaperKey> paperKeys = getPaperKeys(response);
                NotifyHelper.hideLoading();
                Intent intent = new Intent(getActivity(), EpaperActivity.class);
                intent.putExtra(EpaperActivity.PAPER_CODE, paperCode);
                intent.putExtra(EpaperActivity.PAPER_KEYS, paperKeys);
                startActivity(intent);
                dismiss();
            }
        });
    }

    private ArrayList<PaperKey> getPaperKeys(JSONObject response){
        ArrayList<PaperKey> paperKeys = new ArrayList<>();
        Iterator<String> keys = response.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject value = JSH.getJSONObject(response, key);
            paperKeys.add(new PaperKey(JSH.getString(value, "pagenum"),
                    JSH.getString(value, "key")));
        }
        return paperKeys;
    }
}
