package com.vrctech.aproundup.activities.epaper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vrctech.aproundup.R;

import java.util.ArrayList;

public class EpaperAdapter extends RecyclerView.Adapter<EpaperAdapter.NewsPaperHolder>{

    private final ArrayList<Epaper> epapers;
    private final Context context;
    private final FragmentManager fragmentManager;

    EpaperAdapter(ArrayList<Epaper> epapers, Context context, FragmentManager fragmentManager) {
        this.epapers = epapers;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public NewsPaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsPaperHolder(LayoutInflater.from(context).inflate(R.layout.layout_news_paper_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPaperHolder holder, int position) {

        Epaper epaper = epapers.get(position);

        holder.logo.setImageResource(epaper.getPaperLogo());
        holder.name.setText(epaper.getPaperName());
        holder.date.setText(epaper.getDate());

        holder.view.setOnClickListener(view -> {
            EditionsBottomSheetDialog bottomSheetDialog = new EditionsBottomSheetDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable(EditionsBottomSheetDialog.EDITIONS_DATA, epaper.getEditions());
            bundle.putInt(EditionsBottomSheetDialog.PAPER_NAME, epaper.getPaperName());
            bottomSheetDialog.setArguments(bundle);
            bottomSheetDialog.show(fragmentManager , "EDITIONS_BOTTOM_SHEET");
        });
    }

    @Override
    public int getItemCount() {
        return epapers.size();
    }

    static class NewsPaperHolder extends RecyclerView.ViewHolder{

        ImageView logo;
        TextView name;
        TextView date;

        View view;

        NewsPaperHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            logo = view.findViewById(R.id.logo);
            name = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
        }
    }

}
