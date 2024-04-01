package com.example.petconnect.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.R;
import com.example.petconnect.adapter.FollowingListAdapter;
import com.example.petconnect.models.Follow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Following extends Fragment {

    public RecyclerView recyclerView;
    private FollowingListAdapter adapter;
    private List<Follow> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo danh sách followingList và add dữ liệu giả lập
        followingList = new ArrayList<>();

        // Khởi tạo RecyclerView và adapter
        recyclerView = view.findViewById(R.id.recycler_view_following);
        adapter = new FollowingListAdapter(getContext(), followingList);

        // Thiết lập LinearLayoutManager cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter cho RecyclerView
        recyclerView.setAdapter(adapter);
    }
}
