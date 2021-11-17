package com.vrctech.aproundup.activities.covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vrctech.aproundup.R;

import java.util.ArrayList;

public class DistricWiseCasesAdapter extends RecyclerView.Adapter<DistricWiseCasesAdapter.CovidCasesHolder> {

    private Context context;
    private ArrayList<CovidCase> cases;

    DistricWiseCasesAdapter(Context context, ArrayList<CovidCase> cases){
        this.context = context;
        this.cases = cases;
    }

    @NonNull
    @Override
    public CovidCasesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DistricWiseCasesAdapter.CovidCasesHolder(LayoutInflater.from(context).inflate(R.layout.layout_row_district_cases, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCasesHolder holder, int position) {
        CovidCase covidCase = cases.get(position);

        holder.name.setText(covidCase.getName());
        holder.confirmed.setText(covidCase.getTotal());

        if(position % 2 == 0){
            holder.name.setBackgroundColor(context.getColor(R.color.white));
            holder.confirmed.setBackgroundColor(context.getColor(R.color.white));
        }else{
            holder.name.setBackgroundColor(context.getColor(R.color.indColorThree));
            holder.confirmed.setBackgroundColor(context.getColor(R.color.indColorThree));
        }
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    class CovidCasesHolder extends RecyclerView.ViewHolder{

        TextView name, confirmed;
        View root;

        CovidCasesHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;

            name = root.findViewById(R.id.name);
            confirmed = root.findViewById(R.id.confirmed);
        }
    }

}
