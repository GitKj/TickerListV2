package com.example.tickerlistv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ViewCompanyInfo extends AppCompatActivity {

    CompanyProfile profile;
    TextView company;
    TextView price;
    TextView beta;
    TextView volAvg;
    TextView mktCap;
    TextView lastDiv;
    TextView range;
    TextView changes;
    TextView changesPct;
    TextView exchange;
    TextView industry;
    TextView website;
    TextView description;
    TextView ceo;
    TextView sector;
    TextView ipoDate;
    ImageView image;

    FirebaseDatabase database;
    DatabaseReference ref;

    String ticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company_info);

        profile = (CompanyProfile) getIntent().getSerializableExtra("company");
        ticker = getIntent().getStringExtra("ticker");

        company = findViewById(R.id.tv_CompanyName);
        price = findViewById(R.id.tv_Price);
        beta = findViewById(R.id.tv_Beta);
        volAvg = findViewById(R.id.tv_volAvg);
        mktCap = findViewById(R.id.tv_mktCap);
        lastDiv = findViewById(R.id.tv_lastDiv);
        range = findViewById(R.id.tv_range);
        changes = findViewById(R.id.tv_changes);
        changesPct = findViewById(R.id.tv_changesPct);
        exchange = findViewById(R.id.tv_exchange);
        industry = findViewById(R.id.tv_industry);
        website = findViewById(R.id.tv_website);
        description = findViewById(R.id.tv_Description);
        ceo = findViewById(R.id.tv_ceo);
        sector = findViewById(R.id.tv_sector);
        ipoDate = findViewById(R.id.tv_ipoDate);
        image = findViewById(R.id.iv_Image);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Tickers");

        setCompanyInfo(ticker);

    }

    public void setCompanyInfo(String tick) {

        if (tick != null && profile == null)
        {
            try {
                ref.child(tick).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        new DownloadImageTask(image).execute(Objects.requireNonNull(snapshot.child("image").getValue()).toString());
                        beta.setText("Beta: " + snapshot.child("beta").getValue(String.class));
                        company.setText(snapshot.child("companyName").getValue(String.class));
                        ceo.setText("CEO: " + snapshot.child("ceo").getValue(String.class));
                        changes.setText("Changes: " + snapshot.child("changes").getValue(String.class));
                        changesPct.setText("Changes PCT: " + snapshot.child("changesPercentage").getValue(String.class));
                        description.setText(snapshot.child("description").getValue(String.class));
                        exchange.setText("Exchange: " + snapshot.child("exchange").getValue(String.class));
                        industry.setText("Industry: " + snapshot.child("industry").getValue(String.class));
                        ipoDate.setText("IPO Date: " + snapshot.child("ipoDate").getValue(String.class));
                        lastDiv.setText("Last Div: " + snapshot.child("lastDiv").getValue(String.class));
                        mktCap.setText("Market Cap: " + snapshot.child("mktCap").getValue(String.class));
                        price.setText("Price: " + snapshot.child("price").getValue(String.class));
                        range.setText("Range: " + snapshot.child("range").getValue(String.class));
                        sector.setText("Sector: " + snapshot.child("sector").getValue(String.class));
                        volAvg.setText("Vol Avg: " + snapshot.child("volAvg").getValue(String.class));
                        website.setText("Website: " + snapshot.child("website").getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (profile != null)
        {
            new DownloadImageTask(image).execute(profile.getImage());
            company.setText(profile.getCompanyName());
            price.setText("Price: " + profile.getPrice());
            beta.setText("Beta: " + profile.getBeta());
            volAvg.setText("Vol Avg: " + profile.getVolAvg());
            mktCap.setText("Market Cap: " + profile.getMktCap());
            lastDiv.setText("Last Dividend: " + profile.getLastDiv());
            range.setText("52-week Range: " + profile.getRange());
            changes.setText("Changes: " + profile.getChanges());
            changesPct.setText("Changes PCT: " + profile.getChangesPercentage());
            exchange.setText("Exchange: " + profile.getExchange());
            industry.setText("Industry: " + profile.getIndustry());
            website.setText("Website: " + profile.getWebsite());
            description.setText(profile.getDescription());
            ceo.setText("CEO: " + profile.getCeo());
            sector.setText("Sector: " + profile.getSector());
            ipoDate.setText("IPO Date: " + profile.getIpoDate());
        }
    }
}