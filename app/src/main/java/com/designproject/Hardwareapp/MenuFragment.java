package com.designproject.Hardwareapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MenuFragment extends Fragment {

    CardView orders, payments, delivery, settings, about, logout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orders = view.findViewById(R.id.orders);
        delivery = view.findViewById(R.id.delivery);
        settings = view.findViewById(R.id.settings);
        about = view.findViewById(R.id.about);
        logout = view.findViewById(R.id.logout);

        //when click the orders in menu, it will go to order screen
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOrders = new Intent(getActivity(), OrdersActivity.class);
                startActivity(intentOrders);
//                replaceFragment(new CartFragment());
            }
        });

        //when click the delivery in menu, it will go to delivery screen
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDelivery = new Intent(getActivity(), DeliveryActivity.class);
                startActivity(intentDelivery);
//                replaceFragment(new CartFragment());
            }
        });

        //when click the settings in menu, it will go to settings screen
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intentSettings);
//                replaceFragment(new CartFragment());
            }
        });

        //when click the about us in menu, it will go to About Us screen
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAbout = new Intent(getActivity(), AboutActivity.class);
                startActivity(intentAbout);
//                replaceFragment(new CartFragment());
            }
        });

        //when click the logout in menu, user can logout from the app
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("Do you want to logout BuildMate ?");

                //When click "Yes" it will execute this
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goLogin();
                    }
                });

                //When click "No" it will execute this
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

    }

    //Method for replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
    public void goLogin() {
        Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
        startActivity(intentLogin);
    }

}