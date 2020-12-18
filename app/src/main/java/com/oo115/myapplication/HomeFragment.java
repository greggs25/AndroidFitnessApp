package com.oo115.myapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oo115.myapplication.Workout_feature.Exercises_for_muscle_groupsFragment;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView greeting;
    private String user_firstName;
    public static PrefConFig prefConFig;
    private Toolbar toolbar;
    private Button btnQuickWorkout;
    public HomeFragment() {
        // Required empty public constructor
    }

    //this method sets the greeting in the page depending on the time of the day and the users name
    public String greeting(int timeOfDay, String user_firstName) {
        String greeting_txt;
        if ((timeOfDay != 0 && timeOfDay <= 12)) {
            greeting_txt = "Good Morning " + user_firstName;
        } else if (timeOfDay == 0) {
            greeting_txt = "Good Morning " + user_firstName;
        } else if (timeOfDay < 16) {
            greeting_txt = "Good Afternoon " + user_firstName;

        } else if (timeOfDay < 21) {
            greeting_txt = "Good Evening " + user_firstName;
        } else {
            greeting_txt = "Good Evening " + user_firstName;
        }
        return greeting_txt;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //setting up the tool bar to have a back arrow on create and setting the title of the fragment
        toolbar = view.findViewById(R.id.homeFragmentToolbar);
        btnQuickWorkout = view.findViewById(R.id.btnQuickWork);
        prefConFig = new PrefConFig(Objects.requireNonNull(getContext()));

        //removiing the actionbar so that tool bar can show
        Objects.requireNonNull(getActivity()).setTitle("Home");


        btnQuickWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercises_for_muscle_groupsFragment fragment = Exercises_for_muscle_groupsFragment.newInstance(7, true);
                FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_containerHome, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });




        greeting = view.findViewById(R.id.greeting_txt);
        user_firstName = prefConFig.readName();
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        greeting.setText(greeting(timeOfDay, prefConFig.readName()));



        return view;
    }

}
