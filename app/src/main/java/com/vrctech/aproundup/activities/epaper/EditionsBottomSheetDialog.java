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
import com.vrctech.aproundup.R;

import java.util.ArrayList;

public class EditionsBottomSheetDialog extends BottomSheetDialogFragment implements EditionClickImpl{

    static String PAPER_NAME = "PAPER_NAME";
    static String EDITIONS_DATA = "EDITIONS_DATA";

    private TextView paperName;
    private RecyclerView editionsList;

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
        Intent intent = new Intent(getActivity(), EpaperActivity.class);
        intent.putExtra(EpaperActivity.PAPER_CODE, edition.getId());
        intent.putExtra(EpaperActivity.PAGES, edition.getPages());
        intent.putExtra(EpaperActivity.ORIGINAL_DATE, edition.getDate());
        startActivity(intent);
        dismiss();
    }

}
