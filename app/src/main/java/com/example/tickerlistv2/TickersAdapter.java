package com.example.tickerlistv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TickersAdapter extends BaseAdapter {

    public ArrayList<CompanyProfile> tickers;
    private Context context;

    public TickersAdapter(ArrayList<CompanyProfile> tickers, Context context) {
        this.tickers = tickers;
        this.context = context;
    }


    @Override
    public int getCount() {
        return this.tickers.size();
    }

    @Override
    public Object getItem(int position) {
        return this.tickers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, null);

            holder = new ViewHolder();
            holder.companyName = (TextView) convertView.findViewById(R.id.row_companyName);
            holder.industry = (TextView) convertView.findViewById(R.id.row_Industry);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        CompanyProfile profile = tickers.get(position);
        holder.companyName.setText(profile.getCompanyName());
        holder.industry.setText(profile.getIndustry());

        return convertView;
    }

    private static class ViewHolder
    {
        TextView companyName;
        TextView industry;
    }
}
