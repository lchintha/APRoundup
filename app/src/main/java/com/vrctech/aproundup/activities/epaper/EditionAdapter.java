package com.vrctech.aproundup.activities.epaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.R;

import java.util.ArrayList;

public class EditionAdapter extends RecyclerView.Adapter<EditionAdapter.EditionHolder>{

    private final ArrayList<Edition> editions;
    private final Context context;
    private final EditionClickImpl editionClick;

    EditionAdapter(ArrayList<Edition> editions, Context context, EditionClickImpl editionClick) {
        this.editions = editions;
        this.context = context;
        this.editionClick = editionClick;
    }

    @NonNull
    @Override
    public EditionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EditionHolder(LayoutInflater.from(context).inflate(R.layout.layout_paper_edition_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EditionHolder holder, int position) {
        Edition edition = editions.get(position);
        holder.icon.setImageResource(edition.getIcon());
        holder.name.setText(edition.getName());
        holder.date.setText(GlobalMethods.getFormattedDate(edition.getDate()));

        holder.view.setOnClickListener(view -> editionClick.editionOnClick(edition));
    }

    @Override
    public int getItemCount() {
        return editions.size();
    }

    static class EditionHolder extends RecyclerView.ViewHolder{

        View view;
        ImageView icon;
        TextView name;
        TextView date;

        EditionHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.edition);
            date = itemView.findViewById(R.id.date);
        }
    }
}
