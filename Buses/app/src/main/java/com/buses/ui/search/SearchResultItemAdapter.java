package com.buses.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.buses.R;

import java.util.List;

public class SearchResultItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    List<String> departure_city;
    List<String> arrival_city;
    List<String> total;
    List<String> price;
    List<String> companies;

    public SearchResultItemAdapter(Context c, List<String> h, List<String> r, List<String> d, List<String> q, List<String> com) {
        departure_city = h;
        arrival_city = r;
        total = d;
        price = q;
        companies = com;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return departure_city.size();
    }

    @Override
    public Object getItem(int position) {
        return departure_city.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.search_result_item, null);
        TextView departureCityTextView = (TextView) v.findViewById(R.id.departureCityTimeTextView);
        TextView arrivalCityTextView = (TextView) v.findViewById(R.id.arrivalCityTimeTextView);
        TextView totalTimeTextView = (TextView) v.findViewById(R.id.totalTimeTextView);
        TextView priceTextView = (TextView) v.findViewById(R.id.tripPriceTextView);
        TextView companyTextView = (TextView) v.findViewById(R.id.companyTextView);

        String departure = departure_city.get(position);
        String arrival = arrival_city.get(position);
        String time = total.get(position);
        String price_ = price.get(position);
        String company = companies.get(position);

        departureCityTextView.setText(departure);
        arrivalCityTextView.setText(arrival);
        totalTimeTextView.setText(time);
        priceTextView.setText(price_);
        companyTextView.setText(company);

        return v;
    }
}
