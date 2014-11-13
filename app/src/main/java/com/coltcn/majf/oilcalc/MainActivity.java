package com.coltcn.majf.oilcalc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.coltcn.majf.oilcalc.adpater.TimeListAdpater;


public class MainActivity extends Activity {

    public static final String ACTION_TIME_UPDATE = "action_time_update";
    public static final String ACTION_TIMER_FINSHED = "action_timer_finshed";
    private TimeListAdpater mTimeListAdpater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView counter = (TextView) findViewById(R.id.counter);
        counter.setText(DateUtils.formatElapsedTime(0));
        if (mTimeListAdpater == null) {
            mTimeListAdpater = new TimeListAdpater(this, 0);
        }
        ListView list = (ListView) findViewById(R.id.time_list);
        list.setAdapter(mTimeListAdpater);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TIME_UPDATE);
        filter.addAction(ACTION_TIMER_FINSHED);
//        registerReceiver(mTimeReceiver,filter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.men_clear_all) {
            FragmentManager fm = getFragmentManager();
            if (fm.findFragmentByTag("dialog") == null) {
                ConfirmClaerDialogFragment frag = new ConfirmClaerDialogFragment();
                frag.show(fm,"dialog");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ListView listView = (ListView) findViewById(R.id.time_list);
        int pos  = listView.getFirstVisiblePosition();
        outState.putInt("first_position",pos);
        super.onSaveInstanceState(outState);
    }

    public class ConfirmClaerDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity()).setTitle("Sure?").setMessage("Are you want to clear all?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mTimeListAdpater.clear();
                        }
                    })
                    .setNegativeButton("Cancel", null).create();
        }
    }

}