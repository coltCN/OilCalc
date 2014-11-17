package com.coltcn.majf.oilcalc.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coltcn.majf.oilcalc.MainActivity;
import com.coltcn.majf.oilcalc.R;

/**
 * Created by majf on 2014/11/14.
 */
public class TimerFragment extends Fragment {
    private long date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_detail,null);
    }

    private void setNameAndText(View v,int nameId,String value){
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView text = (TextView) v.findViewById(R.id.text);
        String s = getResources().getString(nameId);
        name.setText(s);
        text.setText(value);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        TextView counter = (TextView) activity.findViewById(R.id.counter);
        counter.setText(DateUtils.formatElapsedTime(0));
        Button startButton = (Button) activity.findViewById(R.id.start_stop);
        startButton.setOnClickListener(activity);
        View v = activity.findViewById(R.id.task_name);
        String text = getResources().getString(R.string.task_name);
        setNameAndText(v,R.string.detail_name,text);
        v = activity.findViewById(R.id.task_date);
        text = DateUtils.formatDateTime(activity,date,MainActivity.DATE_FLAGS);
        setNameAndText(v,R.string.detail_date,text);
        v = activity.findViewById(R.id.task_desc);
        text = getResources().getString(R.string.description);
        setNameAndText(v,R.string.detail_desc,text);

    }
}
