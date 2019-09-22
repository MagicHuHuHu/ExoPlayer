package com.magic.exoplayer.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.magic.exoplayer.R;

/**
 * @author Magic
 * @date on 2019/9/22
 * @description
 */
public class MusicInfoHolder extends RecyclerView.ViewHolder {
    public TextView musicInoNameView;

    public MusicInfoHolder(@NonNull View itemView) {
        super(itemView);
        musicInoNameView = itemView.findViewById(R.id.music_info);
    }

    public void setText(String name) {
        musicInoNameView.setText(name);
    }

}
