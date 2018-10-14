package com.futuremesalabs.coinmarketmath;

import android.graphics.Color;
import android.os.Handler;
import android.os.StrictMode;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futuremesalabs.coinmarketmath.Connector.Connection;
import com.futuremesalabs.coinmarketmath.Utils.Values;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connection.getSymbolPriceData();

        ListView listView = (ListView) findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                Connection.getSymbolPriceData();
                customAdapter.notifyDataSetChanged();
                handler.postDelayed( this, 3000 );
            }
        }, 0 );

        changeBackgroundColors();
    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(Values.data == null)
                return 0;

            return Values.data.size();
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
            convertView = getLayoutInflater().inflate(R.layout.row_layout,null);
            TextView symbol = (TextView) convertView.findViewById(R.id.txt_symbol);
            TextView price = (TextView) convertView.findViewById(R.id.txt_price);
            TextView pricePower = (TextView) convertView.findViewById(R.id.txt_price_power);
            TextView priceColor = (TextView) convertView.findViewById(R.id.txt_priceColor);

            symbol.setText(Values.data.get(position).getSymbol());
            price.setText(Values.data.get(position).getPrice());
            pricePower.setText(Values.data.get(position).getPricePower());

            if(Double.parseDouble(Values.data.get(position).getPricePower()) > 90)
                priceColor.setBackgroundColor(Color.CYAN);
            else if(Double.parseDouble(Values.data.get(position).getPricePower()) > 70)
                priceColor.setBackgroundColor(Color.GREEN);
            else if(Double.parseDouble(Values.data.get(position).getPricePower()) > 50)
                priceColor.setBackgroundColor(Color.YELLOW);
            else
                priceColor.setBackgroundColor(Color.RED);

            RelativeLayout row_layout = (RelativeLayout) convertView.findViewById(R.id.row_layout);
            row_layout.setBackgroundColor(Color.rgb(29,39,48));

            return convertView;
        }
    }

    private void changeBackgroundColors() {

        final RelativeLayout top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        top_layout.setBackgroundColor(Color.rgb(24,33,42));



    }

}