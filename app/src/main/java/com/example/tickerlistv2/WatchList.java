package com.example.tickerlistv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WatchList extends AppCompatActivity {

    private ArrayList<CompanyProfile> tickers;

    FirebaseDatabase database;
    DatabaseReference ref;

    ListView lv;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Tickers");

        tickers = new ArrayList<>();
        back = findViewById(R.id.btn_Back);

        lv = (ListView) findViewById(R.id.list_tickers);
        TickersAdapter adapter = new TickersAdapter(tickers, this);
        lv.setAdapter(adapter);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    CompanyProfile profile = ds.getValue(CompanyProfile.class);
                    tickers.add(profile);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CompanyProfile profile = tickers.get(position);

                Intent switchToCompanyInfo = new Intent(WatchList.this, ViewCompanyInfo.class);
                switchToCompanyInfo.putExtra("company", profile);
                startActivity(switchToCompanyInfo);

            }
        });
    }

    public void backToMain(View view)
    {
        finish();
    }
}