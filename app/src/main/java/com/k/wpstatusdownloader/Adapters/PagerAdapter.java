package com.k.wpstatusdownloader.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.k.wpstatusdownloader.fragments.ImageFragment;
import com.k.wpstatusdownloader.fragments.VideoFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        imageFragment = new ImageFragment();
        videoFragment = new VideoFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       if(position==0){
           return imageFragment;
       }else{
           return videoFragment;
       }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Resimler";
        }else{
            return "Videolar";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
