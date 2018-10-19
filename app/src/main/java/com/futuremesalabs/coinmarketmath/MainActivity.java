package com.futuremesalabs.coinmarketmath;

import android.app.admin.DeviceAdminInfo;
import android.graphics.Color;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futuremesalabs.coinmarketmath.DTO.SymbolPriceDTO;
import com.futuremesalabs.coinmarketmath.Manager.DataManager;
import com.futuremesalabs.coinmarketmath.Manager.CustomNotificationManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataManager dataManager = null;
    CustomNotificationManager customNotificationManager = null;
    List<SymbolPriceDTO> data = null;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String deviceId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        // Biri uygulama biri ise reklam birimi idsi. ~ ve / den ayırt et.
        // Test device id yi  id -> md5'le -> uppercase yap
        MobileAds.initialize(this, "ca-app-pub-1542737248922945~8853553478");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("5fb43077862fedc6c115bc31a12fc4d5".toUpperCase())
                .addTestDevice("714afb8a89f8f173c66ce53d8a74bf05".toUpperCase()).build();
        mAdView.loadAd(adRequest);

        dataManager = new DataManager();
        customNotificationManager = new CustomNotificationManager(getApplicationContext());

        data = dataManager.getSymbolPriceData();

        ListView listView = (ListView) findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                data = dataManager.getSymbolPriceData();
                customAdapter.notifyDataSetChanged();
                customNotificationManager.notificationCheck(data);
                handler.postDelayed( this, 3000 );
            }
        }, 0 );

        changeBackgroundColors();
    }

    public class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(data == null) {
                return 0;
            }
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                convertView = getLayoutInflater().inflate(R.layout.row_layout, null);
                TextView symbol = (TextView) convertView.findViewById(R.id.txt_symbol);
                TextView price = (TextView) convertView.findViewById(R.id.txt_price);
                TextView priceColor = (TextView) convertView.findViewById(R.id.txt_priceColor);
                ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

                symbol.setText(data.get(position).getSymbol());
                price.setText(data.get(position).getPrice());
                priceColor.setBackgroundColor(getProgressBarColor(Double.parseDouble(data.get(position).getPricePower())));
                progressBar.setProgress((int)Double.parseDouble(data.get(position).getPricePower()));
                progressBar.getProgressDrawable().setColorFilter(getProgressBarColor(Double.parseDouble(data.get(position).getPricePower())), android.graphics.PorterDuff.Mode.SRC_IN);


                RelativeLayout row_layout = (RelativeLayout) convertView.findViewById(R.id.row_layout);
                row_layout.setBackgroundColor(Color.rgb(29, 39, 48));
            } catch (Exception e) {

            }
            return convertView;
        }
    }

    private void changeBackgroundColors() {
        try {
            final RelativeLayout top_layout = (RelativeLayout) findViewById(R.id.top_layout);
            top_layout.setBackgroundColor(Color.rgb(24, 33, 42));
        } catch (Exception e) {

        }
    }

    private int getProgressBarColor(double val) {
        double greenValue = val * 2.55;
        double redValue = 255 - greenValue;
        return  Color.rgb((int)redValue, (int)greenValue, 0);
    }
}