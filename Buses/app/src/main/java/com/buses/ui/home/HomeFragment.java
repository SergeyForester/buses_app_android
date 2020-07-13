package com.buses.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.buses.R;
import com.buses.ui.search.SearchFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button findTripBtn = (Button) root.findViewById(R.id.findTripsButton);
        findTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText departureCityEditText = (EditText) root.findViewById(R.id.departureCityEditText);
                EditText arrivalCityEditText = (EditText) root.findViewById(R.id.arrivalCityEditText);

                Bundle bundle = new Bundle();
                bundle.putString("DEPARTURE_CITY", departureCityEditText.getText().toString());
                bundle.putString("ARRIVAL_CITY", arrivalCityEditText.getText().toString());

                // set arguments
                SearchFragment fragment = new SearchFragment();
                fragment.setArguments(bundle);

                // Create new fragment and transaction
                Fragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);

                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.nav_host_fragment, searchFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        return root;
    }
}
