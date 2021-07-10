package com.example.tickerlistv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
@TODO:

    2. Work on clear database feature

 */

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference ref;

    public static final String API_KEY = "bbb5a2b3b8df785da7e6599c387f889b";

    CompanyProfile profile;
    Set<CompanyProfile> profiles = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Tickers");
        profile = new CompanyProfile();
        getData();


    }

    public void getData(){

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                profile = snapshot.getValue(CompanyProfile.class);
                if (!profiles.contains(profile))
                    profiles.add(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public void updateTicker(String userTicker, String changes, String pct)
    {
        ref.child(userTicker).child("changes").setValue(changes);
        ref.child(userTicker).child("changesPercentage").setValue(pct);

        switchToCompanyInfo(userTicker);

    }

    public void Save(CompanyProfile tprofile, String userTicker) {

        String percUrl = "https://financialmodelingprep.com/api/v3/quote/" + userTicker + "?apikey=" + API_KEY;
        if (tprofile != null) {

            ref.child(userTicker).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    // if the ticker doesn't already exist in the DB
                    if (snapshot.getValue() == null)
                    {
                        ref.child(userTicker).setValue(tprofile);

                        if (tprofile.getChanges().equals("Temp")) {
                            readJsonQuote(percUrl, userTicker);
                        }
                        Toast.makeText(getApplicationContext(), "Saved ticker to Database.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "That already exists!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    public void SaveToDatabase(View view) throws IOException, ParseException {

        EditText tick = findViewById(R.id.et_tickerName);
        String userTicker = tick.getText().toString().toUpperCase();
        boolean validTicker = true;

        for (int i = 0; i < userTicker.length(); i++)
        {
            if (!Character.isAlphabetic(userTicker.charAt(i)))
            {
                validTicker = false;
            }
        }
        if (userTicker.length() < 3 || userTicker.length() > 6 || validTicker == false)
        {
            Toast.makeText(this, "Invalid Ticker!", Toast.LENGTH_SHORT).show();
            tick.setText("");
            return;
        }
        String sUrl = "https://financialmodelingprep.com/api/v3/profile/" + userTicker + "?apikey=" + API_KEY;

        if (userTicker != null)
        {
            try {
                readJson(sUrl, userTicker);
            } catch (Exception e)
            {
                Toast.makeText(this, "Error saving to DB.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            tick.setText("");

        }

    }

    public void readJsonQuote(String url, String userTicker)
    {
        if (url == null || userTicker == null ||
                url.equals("") || userTicker.equals(""))
        {
            Toast.makeText(this, "Error reading API information.", Toast.LENGTH_SHORT).show();
        }
        ANRequest req = AndroidNetworking.get(url).setPriority(Priority.LOW).build();
        req.getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject object = response.getJSONObject(0);

                    double changesPercentage = object.getDouble("changesPercentage");
                    String changesPct = Double.toString(changesPercentage);

                    double change = object.getDouble("change");
                    String changes = Double.toString(change);

                    updateTicker(userTicker, changes, changesPct);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }


    public void readJson(String url, String userTicker) {


        if (url == null || userTicker == null ||
        url.equals("") || userTicker.equals(""))
        {
            Toast.makeText(this, "Error reading API information.", Toast.LENGTH_SHORT).show();
        }
        ANRequest req = AndroidNetworking.get(url).setPriority(Priority.LOW).build();
        req.getAsJSONArray(new JSONArrayRequestListener() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    CompanyProfile companyProfile;

                    JSONObject object = response.getJSONObject(0);

                    String companyName = object.getString("companyName");

                    double priceDouble = object.getDouble("price");
                    String price = Double.toString(priceDouble);

                    double betaDouble = object.getDouble("beta");
                    String beta = Double.toString(betaDouble);

                    long volAvgLong = object.getLong("volAvg");
                    String volAvg = Long.toString(volAvgLong);

                    long mktCapLong = object.getLong("mktCap");
                    String mktCap = Long.toString(mktCapLong);

                    double lastDivDouble = object.getDouble("lastDiv");
                    String lastDiv = Double.toString(lastDivDouble);

                    String range = object.getString("range");

                    String exchange = object.getString("exchange");

                    String industry = object.getString("industry");

                    String website = object.getString("website");

                    String description = object.getString("description");

                    String ceo = object.getString("ceo");

                    String sector = object.getString("sector");

                    String image = object.getString("image");

                    String ipoDate = object.getString("ipoDate");

                    companyProfile = new CompanyProfile(
                            companyName,
                            price,
                            beta,
                            volAvg,
                            mktCap,
                            lastDiv,
                            range,
                            "Temp",
                            exchange,
                            industry,
                            website,
                            description,
                            ceo,
                            sector,
                            image,
                            ipoDate,
                            "Temp"
                    );


                    Save(companyProfile, userTicker);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
               Toast.makeText(getApplicationContext(),"Error on getting data ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void switchToDBView(View view)
    {
        Intent switchToList = new Intent(this, WatchList.class);
        startActivity(switchToList);
    }

    public void switchToCompanyInfo(String ticker)
    {
        Intent switchToCompanyInfo = new Intent(MainActivity.this, ViewCompanyInfo.class);
        switchToCompanyInfo.putExtra("ticker", ticker);
        startActivity(switchToCompanyInfo);
    }

    public void clearDatabase(View view)
    {
        ref = FirebaseDatabase.getInstance().getReference();
        ref.setValue(null);
        Toast.makeText(this, "Cleared the database!", Toast.LENGTH_SHORT).show();
    }

}