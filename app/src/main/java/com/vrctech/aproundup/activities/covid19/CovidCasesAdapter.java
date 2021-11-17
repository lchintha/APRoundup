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

public class CovidCasesAdapter extends RecyclerView.Adapter<CovidCasesAdapter.CovidCasesHolder>{

    private ArrayList<CovidCase> cases;
    private Context context;

    private int lastPosition = -1;

    CovidCasesAdapter(Context context, ArrayList<CovidCase> cases){
        this.context = context;
        this.cases = cases;
    }

    @NonNull
    @Override
    public CovidCasesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CovidCasesHolder(LayoutInflater.from(context).inflate(R.layout.layout_row_state_cases, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCasesHolder holder, int position) {
        CovidCase covidCase = cases.get(position);
        holder.name.setText(covidCase.getName());
        //holder.active.setText(covidCase.getActive());
        holder.deceased.setText(covidCase.getDeceased());
        //holder.recovered.setText(covidCase.getRecovered());
        holder.total.setText(covidCase.getTotal());

        if(position % 2 == 0){
            holder.name.setBackgroundColor(context.getColor(R.color.white));
            //holder.active.setBackgroundColor(context.getColor(R.color.white));
            holder.deceased.setBackgroundColor(context.getColor(R.color.white));
            //holder.recovered.setBackgroundColor(context.getColor(R.color.white));
            holder.total.setBackgroundColor(context.getColor(R.color.white));
        }else{
            holder.name.setBackgroundColor(context.getColor(R.color.indColorThree));
            //holder.active.setBackgroundColor(context.getColor(R.color.indColorThree));
            holder.deceased.setBackgroundColor(context.getColor(R.color.indColorThree));
            //holder.recovered.setBackgroundColor(context.getColor(R.color.indColorThree));
            holder.total.setBackgroundColor(context.getColor(R.color.indColorThree));
        }
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    static class CovidCasesHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView deceased, total;
        View root;

        CovidCasesHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;

            name = root.findViewById(R.id.name);
            //active = root.findViewById(R.id.active);
            deceased = root.findViewById(R.id.deceased);
            //recovered = root.findViewById(R.id.recovered);
            total = root.findViewById(R.id.total);
        }
    }
}
