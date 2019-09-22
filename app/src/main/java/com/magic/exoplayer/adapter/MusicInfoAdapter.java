package com.magic.exoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.magic.exoplayer.R;
import com.magic.exoplayer.RecycleViewOnItemClickListenser;
import com.magic.exoplayer.bean.MusicInfo;
import com.magic.exoplayer.holder.MusicInfoHolder;

import java.util.List;

/**
 * @author Magic
 * @date on 2019/9/22
 * @description
 */
public class MusicInfoAdapter extends RecyclerView.Adapter<MusicInfoHolder> implements
        View.OnClickListener {

    private Context mContext;

    private List<MusicInfo> musicInfoList;

    private RecycleViewOnItemClickListenser itemClickListenser;

    public MusicInfoAdapter(List<MusicInfo> musicInfoList, Context context) {
        this.musicInfoList = musicInfoList;
        mContext = context;
    }

    public List<MusicInfo> getMusicInfoList() {
        return musicInfoList;
    }

    public void setItemClickListenser(RecycleViewOnItemClickListenser itemClickListenser) {
        this.itemClickListenser = itemClickListenser;
    }

    @NonNull
    @Override
    public MusicInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_music, parent, false);
        MusicInfoHolder homeViewHolder = new MusicInfoHolder(itemView);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicInfoHolder holder, int position) {
        holder.setText(musicInfoList.get(position).getMusicName());
        holder.musicInoNameView.setTag(position);
        holder.musicInoNameView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return musicInfoList.size();
    }

    public MusicInfo getMusicInfo(int position) {
        return musicInfoList.get(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_info:
                if (itemClickListenser != null) {
                    int position = (int) v.getTag();
                    itemClickListenser.onItemClick(position);
                }
                break;
            default:
                break;
        }

    }
}
