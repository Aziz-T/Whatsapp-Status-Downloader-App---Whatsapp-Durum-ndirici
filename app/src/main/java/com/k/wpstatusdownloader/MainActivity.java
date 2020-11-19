package com.k.wpstatusdownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.k.wpstatusdownloader.Adapters.PagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainToolbar) Toolbar toolbar;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private int STORAGE_READ_PERMISSION_CODE = 1;
    private int STORAGE_WRITE_PERMISSION_CODE= 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Whatsapp Durumları");
        setSupportActionBar(toolbar);
        //permissionOlustur();
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);




    }

   /*private void permissionOlustur() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Zaten izin verildi!",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestReadPermission();
        }

    }

    private void requestReadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("İstek gerekli!")
                    .setMessage("Uygulama içi istek gerekli!")
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_READ_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Geri", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_READ_PERMISSION_CODE);
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_READ_PERMISSION_CODE )  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "İzin verildi!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "İzin reddedildi!", Toast.LENGTH_SHORT).show();
            }
        }
    }//*/

}
