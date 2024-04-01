package com.example.petconnect.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.petconnect.fragment.Follower;
import com.example.petconnect.fragment.Following;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    private  String[] titles = new String[] {"Followers", "Following"};
    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Follower();
            case 1:
                return new Following();
        }
        return new Follower();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
