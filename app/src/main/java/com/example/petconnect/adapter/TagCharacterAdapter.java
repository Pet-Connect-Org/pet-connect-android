package com.example.petconnect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.R;
import com.example.petconnect.models.TagCharacter;

import java.util.List;

public class TagCharacterAdapter extends RecyclerView.Adapter<TagCharacterAdapter.ViewHolder> {


    private List<TagCharacter> tagCharacterList;
    private Context context;
    public TagCharacterAdapter( Context context, List<TagCharacter> tagCharacterList) {
        this.tagCharacterList = tagCharacterList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TagCharacter tagCharacter = tagCharacterList.get(position);
//        holder.bind(tagCharacter);
        // Đặt dữ liệu cho TextView từ TagCharacter
        holder.tagName.setText(tagCharacter.getTagName());
    }

    @Override
    public int getItemCount() {
        Log.d("TagCharacterList", "Size: " + tagCharacterList.size());
        for (TagCharacter tagCharacter : tagCharacterList) {
            Log.d("TagCharacterList", "Item: " + tagCharacter.getTagName());
        }
        return tagCharacterList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tagCharacter);
        }

        public void bind(TagCharacter tagCharacter) {
            tagName.setText(tagCharacter.getTagName());
        }
    }
}