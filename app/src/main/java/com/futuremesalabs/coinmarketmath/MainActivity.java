package com.futuremesalabs.coinmarketmath;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.futuremesalabs.coinmarketmath.Connector.Connection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Connection c = new Connection();
            c.getDateFromBinance();
        } catch (Exception e) {
            System.out.print("");
        }

    }
}
