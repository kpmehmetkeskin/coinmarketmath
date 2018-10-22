package com.futuremesalabs.coinmarketmath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        SharedPreferences prefs = getSharedPreferences("A_VARIABLE_781411", MODE_PRIVATE);
        Boolean igotit = prefs.getBoolean("igotit", false);
        if (igotit != null && igotit.equals(true)) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setTitle("User Guide");
        Toast.makeText(getApplicationContext(), "Tap to pass", Toast.LENGTH_SHORT).show();

        final ImageView imageView = findViewById(R.id.splashImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("A_VARIABLE_781411", MODE_PRIVATE).edit();
                editor.putBoolean("igotit", true);
                editor.apply();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
