package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.CustomTimeAgo;
import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.comment.UpdateCommentRequest;
import com.example.petconnect.services.comment.UpdateCommentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {
    private Context context;
    private List<ExtendedComment> commentList;

    public CommentListAdapter(Context context, List<ExtendedComment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentListAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.CommentViewHolder holder, int position) {
        ExtendedComment comment = commentList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment_author_name, comment_time;
        EditText comment_content;

        CustomAvatar comment_author_avatar;
        UserManager userManager;
        Button comment_delete_button, comment_edit_button, comment_like_button;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_author_name = itemView.findViewById(R.id.comment_author_name);
            comment_content = itemView.findViewById(R.id.comment_content);
            comment_author_avatar = itemView.findViewById(R.id.comment_author_avatar);
            comment_time = itemView.findViewById(R.id.comment_time);

            comment_delete_button = itemView.findViewById(R.id.comment_delete_button);
            comment_edit_button = itemView.findViewById(R.id.comment_edit_button);
        }

        public void bind(ExtendedComment comment) {
            comment_author_name.setText(comment.getUser().getName());
            comment_content.setText(comment.getContent());
            comment_author_avatar.setName(comment.getUser().getName());
            userManager = new UserManager(CommentListAdapter.this.context);
            comment_time.setText(CustomTimeAgo.toTimeAgo((comment.getCreated_at().getTime())));

            if (comment.getUser().getId() == userManager.getUser().getId()) {
                comment_delete_button.setVisibility(View.VISIBLE);
                comment_edit_button.setVisibility(View.VISIBLE);
            }

            final boolean[] isEditing = {false}; // Khai báo biến isEditing là một mảng một phần tử
            comment_edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (!isEditing[0]) {
                            // if not in edit mode -> enable
                            comment_content.setEnabled(true);
                            comment_content.requestFocus();
                            comment_content.setSelection(comment_content.getText().length());
                            comment_edit_button.setText("OK");
                            isEditing[0] = true;
                        } else {
                            // if in edit mode -> disable
                            // Get the updated content from the EditText
                            String updatedContent = comment_content.getText().toString();
                            // Check if the updated content is empty
                            if (!updatedContent.trim().isEmpty()) {
                                // Update the comment object
                                comment.setContent(updatedContent);
                                // Disable editing for the comment content
                                comment_content.setEnabled(false);
                                // Change the "OK" button back to "Edit"
                                comment_edit_button.setText("Edit");
                                // Thiết lập biến isEditing thành false để chỉ ra không còn ở chế độ chỉnh sửa
                                isEditing[0] = false;

                                // Call API to update the comment
                                String token = userManager.getAccessToken();
                                ApiService.apiService.updateComment("Bearer " + token, new UpdateCommentRequest(updatedContent, comment.getPost_id()), comment.getId()).enqueue(new Callback<UpdateCommentResponse>() {
                                    @Override
                                    public void onResponse(Call<UpdateCommentResponse> call, Response<UpdateCommentResponse> response) {
                                        if (response.isSuccessful()) {
                                            // Notify RecyclerView that the data has changed
                                            notifyItemChanged(position);
                                            // Update the dataset in the RecyclerView
                                            commentList.set(position, comment);
                                            // Hiển thị thông báo
                                            Toast.makeText(context, "Update Comment Success", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Xử lý khi gửi yêu cầu cập nhật comment thất bại
                                            Toast.makeText(context, "Update Comment Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UpdateCommentResponse> call, Throwable t) {
                                        // Xử lý khi gửi yêu cầu cập nhật comment thất bại
                                        Toast.makeText(context, "Update Comment Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // Hiển thị thông báo lỗi nếu nội dung comment rỗng
                                Toast.makeText(context, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

            // Set OnTouchListener for comment_content
            comment_content.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Kiểm tra xem người dùng đã ở chế độ chỉnh sửa hay chưa
                    // Nếu không ở chế độ chỉnh sửa, không cho phép chỉnh sửa trên EditText
                    return !isEditing[0]; // Trả về true để không xử lý sự kiện chạm vào EditText
                    // Trả về false để xử lý sự kiện chạm vào EditText
                }
            });
        }
    }
}