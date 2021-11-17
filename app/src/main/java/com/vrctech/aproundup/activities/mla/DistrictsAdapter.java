package com.vrctech.aproundup.activities.mla;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vrctech.aproundup.R;

public class DistrictsAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] districts;

    public DistrictsAdapter(Context context, String[] districts) {
        super(context, 0, districts);
        this.context = context;
        this.districts = districts;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.layout_districts_list, parent, false);
        }

        TextView name = view.findViewById(R.id.name);
        name.setText(districts[position]);

        return view;
    }
}
