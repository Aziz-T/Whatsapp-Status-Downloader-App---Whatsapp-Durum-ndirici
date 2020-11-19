package com.k.wpstatusdownloader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.k.wpstatusdownloader.Model.StatusModel;
import com.k.wpstatusdownloader.R;
import com.k.wpstatusdownloader.fragments.ImageFragment;
import com.k.wpstatusdownloader.fragments.VideoFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {


    private final List<StatusModel> videoList;
    Context context;
    VideoFragment videoFragment;


    public VideoAdapter(List<StatusModel> videoList, Context context, VideoFragment videoFragment) {
        this.videoList = videoList;
        this.context = context;
        this.videoFragment = videoFragment;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false );
        return  new VideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        StatusModel statusModel=videoList.get(position);
        holder.imageThumb.setImageBitmap(statusModel.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.imageThumb)
        ImageView imageThumb;
        @BindView(R.id.SaveToGallery)
        ImageButton saveToGallery;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            saveToGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StatusModel statusModel= videoList.get(getAdapterPosition());
                    if(statusModel!=null){
                        videoFragment.downloadVideo(statusModel);
                    }
                }
            });

        }
    }
}
