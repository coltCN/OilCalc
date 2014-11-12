package com.coltcn.majf.oilcalc.adpater;


import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coltcn.majf.oilcalc.R;

/**
 * Created by majf on 2014/11/12.
 */
public class TimeListAdpater extends ArrayAdapter {
    public TimeListAdpater(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =convertView;
        if (view ==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.time_row,null);
        }
        Long time = (Long) getItem(position);
        TextView name = (TextView) view.findViewById(R.id.lap_name);
        String taskString =getContext().getResources().getString(R.string.lap_name);
        name.setText(String.format(taskString,position+1));
        TextView lapTime = (TextView) view.findViewById(R.id.lap_time);
        lapTime.setText(DateUtils.formatElapsedTime(time));
        return view;
    }
}
