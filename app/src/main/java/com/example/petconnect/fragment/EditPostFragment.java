package com.example.petconnect.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.Post;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.UpdatePostRequest;
import com.example.petconnect.services.post.UpdatePostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public EditPostFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment EditPostFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static EditPostFragment newInstance(String param1, String param2) {
//        EditPostFragment fragment = new EditPostFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
    private EditText editPost;
    private Button btnEditPost;
    private Post originalPost;
    UserManager userManager;
    public EditPostFragment(){

    }
    public EditPostFragment( Post post){

        this.originalPost = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_post,container,false);
        editPost = view.findViewById(R.id.txtstatus_editpost);
        btnEditPost = view.findViewById(R.id.btnSavePost);
        editPost.setText(originalPost.getContent());
        btnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateContent = editPost.getText().toString();
                updatePost(originalPost.getId(),updateContent);
            }
        });
        Bundle args = getArguments();
        if (args!=null){
            String content = args.getString("content");
            int id = args.getInt("id");
            Post post = new Post();
            post.setContent(content);
            post.setId(id);
            editPost.setText(post.getContent());
        }
        return view;
    }
    private void updatePost(int postId,String updateContent){
        String token =userManager.getAccessToken();
       ApiService.apiService.updatepost("Bearer "+token,new UpdatePostRequest(updateContent,originalPost.getId()), originalPost.getUser_id()).enqueue(new Callback<UpdatePostResponse>() {
           @Override
           public void onResponse(Call<UpdatePostResponse> call, Response<UpdatePostResponse> response) {
               UpdatePostResponse updatePostResponse = response.body();
               if (updatePostResponse!=null){
                    OnPostUpdateListener listener = (OnPostUpdateListener) getActivity();
                    listener.OnPostUpdate(updatePostResponse);
                 Toast.makeText(getContext().getApplicationContext(),"Update post successfully.",Toast.LENGTH_LONG).show();
               }else {
                   Toast.makeText(getContext().getApplicationContext(),"Not authorized to update this post.",Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<UpdatePostResponse> call, Throwable t) {
               Toast.makeText(getContext().getApplicationContext(),"Post not found.",Toast.LENGTH_LONG).show();
           }
       });


    }
    public interface OnPostUpdateListener{
        void OnPostUpdate(UpdatePostResponse updatePostResponse);
    }
    public static  EditPostFragment newInstance(Post post){
        EditPostFragment fragment = new EditPostFragment();
        Bundle args = new Bundle();
        args.putString("content", post.getContent());
        args.putInt("id", post.getId());
        // Truyền các thuộc tính khác của Post vào Bundle
        fragment.setArguments(args);
        return fragment;

    }


}