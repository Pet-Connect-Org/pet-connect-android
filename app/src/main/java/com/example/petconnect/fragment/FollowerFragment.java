package com.example.petconnect.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petconnect.R;
import com.example.petconnect.adapter.FollowerAdapter;
import com.example.petconnect.models.Follow;

import java.util.ArrayList;
import java.util.List;

public class FollowerFragment extends Fragment {

    private RecyclerView recyclerView;
    private FollowerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewFollower);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

// Nhận danh sách người theo dõi từ bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<Follow> followerList = new ArrayList<>();
            ArrayList<Follow> followerArrayList = bundle.<Follow>getParcelableArrayList("followerList");
            if (followerArrayList != null) {
                followerList.addAll(followerArrayList);
                // Khởi tạo adapter và thiết lập cho RecyclerView
                adapter = new FollowerAdapter(followerList);
                recyclerView.setAdapter(adapter);
            }
        }

        return view;
    }
}
