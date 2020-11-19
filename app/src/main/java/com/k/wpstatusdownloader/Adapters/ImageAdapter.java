package com.k.wpstatusdownloader.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {


    private final List<StatusModel> imageList;
    Context context;
    ImageFragment imageFragment;


    public ImageAdapter(List<StatusModel> imageList, Context context, ImageFragment imageFragment) {
        this.imageList = imageList;
        this.context = context;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false );
        return  new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            StatusModel statusModel=imageList.get(position);
            holder.imageThumb.setImageBitmap(statusModel.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.imageThumb) ImageView imageThumb;
        @BindView(R.id.SaveToGallery) ImageButton saveToGallery;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            saveToGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StatusModel statusModel= imageList.get(getAdapterPosition());
                    if(statusModel!=null){
                        imageFragment.downloadImage(statusModel);
                    }
                }
            });

        }
    }
}
