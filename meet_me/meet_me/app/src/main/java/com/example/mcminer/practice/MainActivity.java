package com.example.mcminer.practice;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;

public class MainActivity extends Activity implements BootstrapNotifier {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ((mcminerApp) getApplication() ).setmainactivity(this);
        // stop listening to touches
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // remove the icon from App Drawer
       // PackageManager p = getPackageManager();
       // ComponentName componentName = new ComponentName(this, com.example.mcminer.practice.MainActivity.class);
       // p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);


   /*     Intent myIntent;

        myIntent = new Intent();

        myIntent.setAction(Intent.ACTION_VIEW);
        myIntent.setData(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI); */


    }

 /*   public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
	
	public void cancelNotification(int notificationId){
		
		if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(notificationId);
        }
	}

    @Override
    public void didEnterRegion(Region region) {

            // define sound URI, the sound to be played when there's a notification
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
        PendingIntent pIntent2 = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);


        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dropbox.com/s/f7mndrexm7za02x/minion.png?dl=0"));
        notificationIntent.setClassName("com.android.browser",
                "com.android.browser.BrowserActivity");
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, 0);

            // this is it, we'll build the notification!
            // in the addAction method, if you don't want any icon, just set the first param to 0
            Notification mNotification = new Notification.Builder(this)

                    .setContentTitle("Meet the Minions!")
                    .setContentText("Want to interact and bring the Minions to life?")
                    .setPriority(Notification.PRIORITY_MAX)
                    .setWhen(0)
                    .setSmallIcon(R.drawable.minion)
                    .setContentIntent(pIntent)
                    .setSound(soundUri)

                    .addAction(R.drawable.yesfinal, "Yes", pIntent)
                    .addAction(R.drawable.cancelfinal, "No", pIntent2)

                    .build();



            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            // If you want to hide the notification after it was selected, do the code below
            // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, mNotification);


    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
