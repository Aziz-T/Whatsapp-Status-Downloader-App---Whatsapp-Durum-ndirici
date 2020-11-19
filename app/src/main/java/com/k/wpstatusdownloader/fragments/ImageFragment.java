package com.k.wpstatusdownloader.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.k.wpstatusdownloader.Adapters.ImageAdapter;
import com.k.wpstatusdownloader.Model.StatusModel;
import com.k.wpstatusdownloader.R;
import com.k.wpstatusdownloader.Utils.MyConstrains;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFragment extends Fragment {

    @BindView(R.id.recycleViewImage) RecyclerView recyclerView;
    @BindView(R.id.progressBarImage) ProgressBar progressBar;

    ArrayList<StatusModel> imageModelArrayList;
    ImageAdapter imageAdapter;

    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        imageModelArrayList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        getStatus();

    }

    private void getStatus() {
        if(MyConstrains.STATUS_DIRECTORY.exists()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] statusFiles=MyConstrains.STATUS_DIRECTORY.listFiles();

                    if(statusFiles!=null && statusFiles.length>0){
                        Arrays.sort(statusFiles);

                        for(final File statusfile:statusFiles){
                            StatusModel statusModel= new StatusModel(statusfile,
                                    statusfile.getName(),statusfile.getAbsolutePath());
                            statusModel.setThumbnail(getThumbnail(statusModel));
                        if(!statusModel.isVideo()){
                            imageModelArrayList.add(statusModel);
                        }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            imageAdapter=new ImageAdapter(imageModelArrayList, getContext(),ImageFragment.this);
                            recyclerView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            }
                        });
                    }else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(),"konum bulunmuyor",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private Bitmap getThumbnail(StatusModel statusModel) {
        if(statusModel.isVideo()){
            return ThumbnailUtils.createVideoThumbnail(statusModel.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        }else{
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(statusModel.getFile().getAbsolutePath()),
                    MyConstrains.THUMBSIZE,MyConstrains.THUMBSIZE);
        }
    }

    public void downloadImage(StatusModel statusModel)
    {
        File file = new File(MyConstrains.APP_DIR);
        if(!file.exists()){
            file.mkdirs();
        }
        File destFile = new File(file+File.separator+statusModel.getTitle());
        if(destFile.exists()){
            destFile.delete();
        }

        copyFile(statusModel.getFile(),destFile);
        Toast.makeText(getActivity(),"İndirme tamamlandı.",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(destFile));
        getActivity().sendBroadcast(intent);
    }

    private void copyFile(File file, File destFile) {
        if(!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdirs();
        }
        if(!destFile.exists()){
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileChannel source=null;
        FileChannel destination=null;
        try {
            source=new FileInputStream(file).getChannel();
            destination= new FileInputStream(destFile).getChannel();
            destination.transferFrom(source,0,source.size());
            source.close();
            destination.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
