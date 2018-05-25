package fr.djambae.pilulierautomatique;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;

public class NotificationService extends IntentService {
    static String CHANNEL_ID = "Pilu";
    static int ID = 199;
    public NotificationService() {
        super("NotificationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        boolean Bool = intent.getBooleanExtra("Suppr", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Not_Channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
//        Intent Int = Intent()
        NotificationCompat.Builder Constr = new NotificationCompat.Builder(this,CHANNEL_ID)
//                .
                .setContentTitle("Pilulier Automatique")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText("Penser a prendre de vos médicament")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat Nm = NotificationManagerCompat.from(this);
        Nm.notify(ID,Constr.build());
        Calendar Maitenant = Calendar.getInstance();
        int H = Maitenant.get(Calendar.HOUR)+1;
        long Date  = System.currentTimeMillis();
       if( Maitenant.get(Calendar.AM_PM) == 0){
           if(H-7<=0){
               int EcH = (7-H)*(3600)*1000;
               int EcM = (60-Maitenant.get(Calendar.MINUTE))*60*1000;
               Log.d("Not","Pour 7 "+EcH+" "+EcM);
               Date += EcH+EcM;
           }else{
               int EcH = (12-H)*(3600)*1000;
               int EcM = (60-Maitenant.get(Calendar.MINUTE))*60*1000;
               Log.d("Not","Pour 12 "+EcH+" "+EcM);
               Date += EcH+EcM;

           }
        }else{
           if(H-19<=0){
               int EcH = (19-H)*(3600)*1000;
               int EcM = (60-Maitenant.get(Calendar.MINUTE))*60*1000;
               Log.d("Not","Pour 19 "+EcH+" "+EcM);
               Date += EcH+EcM;
           }else if(H-22 <=0){
               int EcH = (22-H)*(3600)*1000;
               int EcM = (60-Maitenant.get(Calendar.MINUTE))*60*1000;
               Log.d("Not","Pour 22"+EcH+" "+EcM);
               Date += EcH+EcM;
           }else {
               int EcH = ((H-12)-7)*(3600)*1000;
               int EcM = (60-Maitenant.get(Calendar.MINUTE))*60*1000;
               Log.d("Not","Pour 12-7"+EcH+" "+EcM);
               Date += EcH+EcM;
           }
       }
        Intent Int = new Intent(this, NotificationService.class);
        Int.putExtra("Suppr",true);
        Int.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        AlarmManager Mm = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        PendingIntent PIntent = PendingIntent.getService(this,0,Int,0);
        Log.d("Not","DAte "+Date);
        Mm.setExact(AlarmManager.RTC_WAKEUP,Date,PIntent);
    }

}
