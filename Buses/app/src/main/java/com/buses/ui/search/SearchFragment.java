package com.buses.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.buses.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchFragment extends Fragment {

    private SearchViewModel galleryViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final String API_BASE = getActivity().getApplicationContext().getResources().getString(R.string.api_base_name);
        final String API_URL = getActivity().getApplicationContext().getResources().getString(R.string.api_base_url);


        galleryViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        String departureCityInput = getArguments().getString("DEPARTURE_CITY");
        String arrivalCityInput = getArguments().getString("ARRIVAL_CITY");

        if (departureCityInput != null && arrivalCityInput != null) {
            TextView resultsTitle = (TextView) root.findViewById(R.id.searchResultTitleTextView);

            ListView searchResultsListView = (ListView) root.findViewById(R.id.searchResultListView);

            List<String> departure_times = new ArrayList<>();
            List<String> arrival_times = new ArrayList<>();
            List<String> totals = new ArrayList<>();
            List<String> prices = new ArrayList<>();
            List<String> companies = new ArrayList<>();


            String url = API_BASE + API_URL + "filter/trips/?departure_city=" + departureCityInput + "&arrival_city=" + arrivalCityInput;

            try {
                String res = new RequestTask()
                        .execute(url)
                        .get();
                JSONArray object = new JSONArray(res);
                System.out.println(object);

                for (int i = 0; i < object.length(); i++) {
                    JSONObject el = (JSONObject) object.get(i);

                    String dc = el.getJSONObject("departure_city").getString("name");
                    String ac = el.getJSONObject("arrival_city").getString("name");

                    String[] dt = el.getString("departure_time").split("T")[1].split("\\.")[0].split(":");
                    String[] at = el.getString("arrival_time").split("T")[1].split("\\.")[0].split(":");


                    departure_times.add(dt[0]+":"+dt[1]);
                    arrival_times.add(at[0]+":"+at[1]);
                    prices.add(el.getString("starting_price")+"$");
                    companies.add(el.getJSONObject("company").getString("name"));

                    Integer hours = Integer.parseInt(at[0]) - Integer.parseInt(dt[0]);
                    Integer minutes = Integer.parseInt(at[1]) - Integer.parseInt(dt[1]);
                    if (minutes < 0) {
                        hours -= 1;
                        minutes = 60 + minutes;
                    }

                    totals.add(hours.toString()+":"+minutes.toString());
                    resultsTitle.setText(dc + " - " + ac);

                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            SearchResultItemAdapter itemAdapter = new SearchResultItemAdapter(getActivity().getApplicationContext(), departure_times, arrival_times, totals, prices, companies);
            searchResultsListView.setAdapter(itemAdapter);
        }
        return root;
    }
}
