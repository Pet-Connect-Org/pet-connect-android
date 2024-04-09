package com.example.petconnect.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.R;
import com.example.petconnect.adapter.FollowingAdapter;
import com.example.petconnect.models.Follow;

import java.util.ArrayList;
import java.util.List;

public class FollowingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFollowing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Nhận danh sách người theo dõi từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Chuyển đổi dữ liệu từ Parcelable sang Follow
            ArrayList<Follow> followingList = new ArrayList<>();
            ArrayList<Parcelable> parcelableList = bundle.getParcelableArrayList("followingList");
            if (parcelableList != null) {
                for (Parcelable parcelable : parcelableList) {
                    if (parcelable instanceof Follow) {
                        followingList.add((Follow) parcelable);
                    }
                }
            }

            if (!followingList.isEmpty()) {
                // Khởi tạo adapter và thiết lập cho RecyclerView
                FollowingAdapter adapter = new FollowingAdapter(followingList);
                recyclerView.setAdapter(adapter);
            }
        }

        return view;
    }
}
