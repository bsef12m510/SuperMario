package com.example.zeeshan.supermario;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshan.supermario.model.ResponseModel;
import com.example.zeeshan.supermario.news.fragment.FeedDetailActivity;
import com.example.zeeshan.supermario.news.fragment.NewsfeedFragment;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

public class MainActivity extends AppCompatActivity implements NewsfeedFragment.OnFeedFragmentInteractionListener{

    private TextView userNameTextView, emailTextView;
    private MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        Toolbar toolbar = mViewPager.getToolbar();

        /*if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }*/

      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = (View) navigationView.getHeaderView(0);
        userNameTextView = (TextView) headerView.findViewById(R.id.header_username);
        emailTextView = (TextView) headerView.findViewById(R.id.header_email);
        getSavedHeaderData();

       *//* // get the listview
        expListView = (ExpandableListView) findViewById(R.id.navigationmenu);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
*//*
//        getSavedHeaderData();*/
//        replaceFragment(new AdminDashboardFragment(), "adminDashboardFragment");

        setAdapter();
        animateHeader();
    }


    public void animateHeader(){
        final ImageView headerImg = mViewPager.getHeaderBackgroundContainer().findViewById(R.id.header_img);
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        headerImg.setImageResource(R.drawable.news_header);
                        return HeaderDesign.fromColorResAndDrawable(R.color.white, ContextCompat.getDrawable(getApplicationContext(),R.drawable.news_header));
                    case 1:
                        headerImg.setImageResource(R.drawable.guides_header);
                        return HeaderDesign.fromColorResAndDrawable(R.color.white,ContextCompat.getDrawable(getApplicationContext(),R.drawable.guides_header));
                    case 2:
                        headerImg.setImageResource(R.drawable.characters_header);
                        return HeaderDesign.fromColorResAndDrawable(R.color.white,ContextCompat.getDrawable(getApplicationContext(),R.drawable.characters_header));
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });
    }


    public void setAdapter(){
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 3) {
                    case 0:
                        return NewsfeedFragment.newInstance();
                    case 1:
                        return NewsfeedFragment.newInstance();
                    case 2:
                        return NewsfeedFragment.newInstance();
                    default:
                        return NewsfeedFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        return "NEWSFEED";
                    case 1:
                        return "GUIDES";
                    case 2:
                        return "CHARACTERS";
                }
                return "";
            }
        });


        mViewPager.getViewPager().setOffscreenPageLimit(1);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
//        mViewPager.getPagerTitleStrip().setIndicatorColor(R.color.colorPrimary);
//        mViewPager.getPagerTitleStrip().setTextColor(Color.parseColor("#000000"));

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public void onFeedFragmentInteraction( ResponseModel selectedFeed){
        Intent intent = new Intent(MainActivity.this, FeedDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("selectedFeed",selectedFeed);
        startActivity(intent);
    }
}
