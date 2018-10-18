package com.futuremesalabs.coinmarketmath.Manager;

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
import com.futuremesalabs.coinmarketmath.Utils.Values;

import java.util.ArrayList;
import java.util.List;

public class CustomNotificationManager extends MainActivity {

    private int notificationId = 0;
    private final String notificationChannelId = "1234123411";
    private Context mainContext = null;

    public CustomNotificationManager(Context context) {
        this.mainContext = context;
        createNotificationChannel();
    }

    public void notificationCheck(List<SymbolPriceDTO> symbolPriceDTOList) {
        try {
            if (symbolPriceDTOList == null) {
                return;
            }
            for (SymbolPriceDTO symbolPriceDTO : symbolPriceDTOList) {
                if (Double.parseDouble(symbolPriceDTO.getPricePower()) > 99.99 && !Values.sentNotificationsList.contains(symbolPriceDTO.getSymbol())) {
                    sendNotification(symbolPriceDTO.getSymbol());
                    Values.sentNotificationsList.add(symbolPriceDTO.getSymbol());
                } else if (Double.parseDouble(symbolPriceDTO.getPricePower()) > 13 && Double.parseDouble(symbolPriceDTO.getPricePower()) < 25 && Values.sentNotificationsList.contains(symbolPriceDTO.getSymbol())) {
                    Values.sentNotificationsList.remove(symbolPriceDTO.getSymbol());
                }
            }
        } catch (Exception e) {

        }
    }

    private void sendNotification(String coinName) {
        try {
            Intent intent=new Intent(mainContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mainContext);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(getNotificationIdByCoinName(coinName), mBuilder.build());
        } catch (Exception e) {

        }
    }

    private void createNotificationChannel() {
        try {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "channelName9991";
                String description = "channelDesc9991";
                int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(notificationChannelId, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = (NotificationManager) mainContext.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
            }
        } catch (Exception e) {

        }
    }

    private int getNotificationIdByCoinName(String coinName) {
        int total = 0;
        for (int i=0; i < coinName.length(); i++) {
            total += (int)coinName.charAt(i);
        }
        return total;
    }
    
}
