package com.designproject.Hardwareapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CartFragment extends Fragment {
    private Button btnOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        btnOrder = view.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOrder();
            }
        });

        return view;
    }
    public void btnOrder() {
        Intent intent = new Intent(getActivity(), OrderConfirm.class);
        startActivity(intent);
    }
}
