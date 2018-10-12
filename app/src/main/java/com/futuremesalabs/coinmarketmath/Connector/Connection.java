package com.futuremesalabs.coinmarketmath.Connector;

import com.futuremesalabs.coinmarketmath.DTO.SymbolPriceDTO;
import com.futuremesalabs.coinmarketmath.DTO.SymbolPriceResponseDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Connection {

    public void getDateFromBinance() throws Exception  {

        String url = "http://coinmarketmath.com/AndroidEndpointService";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //JSONArray myResponse = new JSONArray(response.toString());

        Gson gson = new Gson();
        String json = response.toString();
        SymbolPriceResponseDTO data = gson.fromJson(json, new TypeToken<SymbolPriceResponseDTO>(){}.getType());

        System.out.print("");

    }
}
