 package com.example.ykx.cameratry;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

 public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

     private ArrayList<Fragment> fragments;
     private DrawerLayout mDrawerLayout;

     private BottomNavigationBar bottomNavigationBar;
//     private FloatingActionButton fabHome;
     private TextView message;
     private TextView scrollableText;

     int lastSelectedPosition = 1;
     private String messageText;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

//        fabHome = (FloatingActionButton) findViewById(R.id.fab_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        message = (TextView) findViewById(R.id.message);
        scrollableText = (TextView) findViewById(R.id.scrollable_text);

        bottomNavigationBar.setTabSelectedListener(this);
//        fabHome.setOnClickListener(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

//         setDefaultFragment();
        refresh();
    }

     /**
      * 设置默认的
      */
//     private void setDefaultFragment() {
//         FragmentManager fragmentManager = getSupportFragmentManager();
//         FragmentTransaction transaction = fragmentManager.beginTransaction();
//         transaction.replace()
//     }

     /**
      * 更新bottomNavitionBar，并显示
      */
     private void refresh() {
         bottomNavigationBar.clearAll();
//         bottomNavigationBar.setFab(fabHome);

         setScrollableText(lastSelectedPosition);

         bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
         bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

         bottomNavigationBar
                 .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "体检").setActiveColorResource(R.color.orange))
                 .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "治疗").setActiveColorResource(R.color.teal))
                 .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "助手").setActiveColorResource(R.color.blue))
                 .setFirstSelectedPosition(1)
                 .initialise();
     }

     private void setScrollableText(int position) {
         switch (position){
             case 0:
                 scrollableText.setText(R.string.examination);
                 break;
             case 1:
                 scrollableText.setText(R.string.treat);
                 break;
             case 2:
                 scrollableText.setText(R.string.aid);
                 break;
             default:
                 break;
         }
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case android.R.id.home:
                 mDrawerLayout.openDrawer(GravityCompat.START);
                 break;
             default:
                 break;
         }
         return true;
     }

     @Override
     public void onTabSelected(int position) {
         lastSelectedPosition = position;
         setMessageText(position + "Tab Selected");
         setScrollableText(position);
     }

     @Override
     public void onTabUnselected(int position) {

     }

     @Override
     public void onTabReselected(int position) {
         setMessageText(position + "Tab Reselected");
     }

     public void setMessageText(String messageText) {
         message.setText(messageText);
     }
 }
