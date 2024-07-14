package com.designproject.Hardwareapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull HomeActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:return new BuildingFragment();
            case 1: return new ToolsFragment();
            case 2: return new RoofingFragment();
            case 3: return new WorkersFragment();
            default: return new BuildingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
