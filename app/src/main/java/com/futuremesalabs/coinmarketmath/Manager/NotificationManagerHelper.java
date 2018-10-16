package com.futuremesalabs.coinmarketmath.Manager;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.futuremesalabs.coinmarketmath.DTO.SymbolPriceDTO;
import com.futuremesalabs.coinmarketmath.MainActivity;
import com.futuremesalabs.coinmarketmath.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationManagerHelper extends Application {

    private NotificationManager _notificationManager;

    private int notificationId = 0;
    private final String notificationChannelId = "1234123411";
    private List<String> sentNotificationsList;
    private Context mainContext = null;

    public NotificationManagerHelper() {
        sentNotificationsList = new ArrayList<String>();
    }

    public NotificationManager getNotificationManager( Context context) {

        if(this._notificationManager != null){
            return this._notificationManager;
        }

        _notificationManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        return _notificationManager;
    }


    public void notificationCheck(List<SymbolPriceDTO> symbolPriceDTOList, Context context) {
        try {
            if (symbolPriceDTOList == null) {
                return;
            }
            this.mainContext = context;

            for (SymbolPriceDTO symbolPriceDTO : symbolPriceDTOList) {
                if (Double.parseDouble(symbolPriceDTO.getPricePower()) > 39.99 && !sentNotificationsList.contains(symbolPriceDTO.getSymbol())) {
                    sendNotification(symbolPriceDTO.getSymbol());
                    sentNotificationsList.add(symbolPriceDTO.getSymbol());
                } else if (Double.parseDouble(symbolPriceDTO.getPricePower()) > 13 && Double.parseDouble(symbolPriceDTO.getPricePower()) < 25 && sentNotificationsList.contains(symbolPriceDTO.getSymbol())) {
                    sentNotificationsList.remove(symbolPriceDTO.getSymbol());
                }
            }
        } catch (Exception e) {

        }
    }

    private void sendNotification(String coinName) {
        try {
            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(mainContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(mainContext, 0, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mainContext, notificationChannelId)
                    .setSmallIcon(R.mipmap.cmmnw200)
                    .setContentTitle("Important Event")
                    .setContentText(coinName + ": There is a sudden increase in price!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE);

//            NotificationManagerCompat notificationManager = this._notificationManager;

            // notificationId is a unique int for each notification that you must define
            this._notificationManager.notify(notificationId++, mBuilder.build());

        } catch (Exception e) {

        }
    }

    public void createNotificationChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                if(this._notificationManager == null){
                    return;
                }

                CharSequence name = "channelName9991";
                String description = "channelDesc9991";
                int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(notificationChannelId, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = this._notificationManager;
                notificationManager.createNotificationChannel(channel);
            }
    }
}