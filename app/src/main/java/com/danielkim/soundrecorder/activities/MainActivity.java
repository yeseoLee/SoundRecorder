package com.danielkim.soundrecorder.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.danielkim.soundrecorder.R;
import com.danielkim.soundrecorder.fragments.FileViewerFragment;
import com.danielkim.soundrecorder.fragments.RecordFragment;


public class MainActivity extends AppCompatActivity {
    //Permission
    static final int RECEIVE_PERMISSION =1;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //사용자에게 권한 요청하기
        int audioPermissonCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int storagePermissonCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(audioPermissonCheck == PackageManager.PERMISSION_GRANTED && storagePermissonCheck == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(getApplicationContext(), "Permissions denied: 0", Toast.LENGTH_SHORT).show();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)){
                Toast.makeText(getApplicationContext(), "권한이 필요합니다", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE}, RECEIVE_PERMISSION);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE}, RECEIVE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[]){
        switch(requestCode){
            case RECEIVE_PERMISSION:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast toast = Toast.makeText(getApplicationContext(), "오디오 권한 승인함", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 300);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "오디오 권한 거부함", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 300);
                    toast.show();
                }
                if(grantResults.length>0 && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    Toast toast = Toast.makeText(getApplicationContext(), "저장공간 쓰기 권한 승인함", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 300);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "저장공간 쓰기 권한 거부함", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 300);
                    toast.show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {
        private String[] titles = { getString(R.string.tab_title_record),
                getString(R.string.tab_title_saved_recordings) };

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:{
                    return RecordFragment.newInstance(position);
                }
                case 1:{
                    return FileViewerFragment.newInstance(position);
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    public MainActivity() {
    }
}
