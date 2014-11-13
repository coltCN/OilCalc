package com.coltcn.majf.oilcalc.serverice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;

import com.coltcn.majf.oilcalc.MainActivity;
import com.coltcn.majf.oilcalc.R;


/**
 * Created by majf on 2014/11/13.
 */
public class TimerService extends Service {
    private static final String TAG = "TimeService";
    public static int TIME_NOTIFICATION = 0;
    private NotificationManager notificationManager = null;
    private Notification notification = null;
    private long mStart = 0;
    private long mTime = 0;

    public class LocalBinder extends Binder{
        TimerService getService(){
            return TimerService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Long current = System.currentTimeMillis();
            mTime +=current -mStart;
            mStart = current;
            updateTime(mTime);
            mHandler.sendEmptyMessageDelayed(0,250);
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        showNotification
        mStart = System.currentTimeMillis();
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessage(0);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        notificationManager.cancel(TIME_NOTIFICATION);
        mHandler.removeMessages(0);
    }
    public void stop(){
        mHandler.removeMessages(0);
        stopSelf();
        notificationManager.cancel(TIME_NOTIFICATION);
    }

    public boolean isStopped(){
        return !mHandler.hasMessages(0);
    }

    public void reset(){
        stop();
        timeStopped(mTime);
        mTime = 0;
    }

    private void timeStopped(long time) {
        Intent intent = new Intent(MainActivity.ACTION_TIMER_FINSHED);
        intent.putExtra("time",time);
        sendBroadcast(intent);
    }

    private  void updateTime(long time){
        Intent intent = new Intent(MainActivity.ACTION_TIME_UPDATE);
        intent.putExtra("time",time);
        sendBroadcast(intent);
    }

    private void updateNotification(long time){
        String title = getResources().getString(R.string.running_timer_notification_title);
            String message = DateUtils.formatElapsedTime(time/1000);
        Context context = getApplicationContext();
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
//        notification.setLatestEventInfo(context,title,message,pendingIntent);
        notification = new Notification.Builder(context)
                .setContentText(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        notificationManager.notify(TIME_NOTIFICATION,notification);
    }

}
