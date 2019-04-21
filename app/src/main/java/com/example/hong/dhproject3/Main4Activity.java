package com.example.hong.dhproject3;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String u_id,d_id,u_type, master;
    View v1,v2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent = getIntent();
        u_id = intent.getStringExtra("U_ID");
        d_id = intent.getStringExtra("D_ID");
        //u_type = intent.getStringExtra("USER_TYPE");
        master = intent.getStringExtra("MASTER");
        v1 = View.inflate(getApplicationContext(),R.layout.tapitem1,null);
        v2 = View.inflate(getApplicationContext(),R.layout.tapitem2, null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.getTabAt(0).setCustomView(v1);
        tabLayout.getTabAt(1).setCustomView(v2);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
          switch(position){
              case 0:
                  Tab1Content t1 = new Tab1Content();
                  Bundle bundle = new Bundle();
                  bundle.putString("U_ID", u_id);
                  bundle.putString("D_ID", d_id);
               //   bundle.putString("U_TYPE", u_type);
                  bundle.putString("MASTER", master);
                  t1.setArguments(bundle);
                  return t1;
              case 1:
                  Tab2Content t2 = new Tab2Content();
                  Bundle bundle2 = new Bundle();
                  bundle2.putString("U_ID", u_id);
                  bundle2.putString("D_ID", d_id);
                 // bundle2.putString("U_TYPE", u_type);
                 // bundle2.putString("MASTER", master);
                  t2.setArguments(bundle2);
                  return t2;
              default:
                  return null;
          }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
