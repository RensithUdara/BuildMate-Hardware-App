package com.designproject.Hardwareapp;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WorkersFragment extends Fragment {

    CardView cardViewMasons, cardViewPainters, cardViewConstructors, cardViewCarpenters, cardViewPlumbers, cardViewElectricians;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workers, container, false);
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        cardViewMasons = view.findViewById(R.id.cardMasons);
        cardViewPainters = view.findViewById(R.id.cardPainters);
        cardViewConstructors = view.findViewById(R.id.cardConstructors);
        cardViewCarpenters = view.findViewById(R.id.cardCarpenters);
        cardViewPlumbers = view.findViewById(R.id.cardPlumbers);
        cardViewElectricians = view.findViewById(R.id.cardElectricians);

        cardViewMasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMasons = new Intent(getActivity(), MasonsActivity.class);
                startActivity(intentMasons);
            }
        });

        cardViewPainters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPainters = new Intent(getActivity(), PainterActivity.class);
                startActivity(intentPainters);
            }
        });

        cardViewConstructors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentConstructors = new Intent(getActivity(), ConstructorActivity.class);
                startActivity(intentConstructors);
            }
        });

        cardViewCarpenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCarpenters= new Intent(getActivity(), CarpentersActivity.class);
                startActivity(intentCarpenters);
            }
        });

        cardViewPlumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPlumbers = new Intent(getActivity(), PlumbersActivity.class);
                startActivity(intentPlumbers);
            }
        });

        cardViewElectricians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentElectricians = new Intent(getActivity(), ElectriciansActivity.class);
                startActivity(intentElectricians);
            }
        });

    }
}
