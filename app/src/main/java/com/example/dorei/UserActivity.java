package com.example.dorei;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class UserActivity extends AppCompatActivity {

    private int userId;
    private TextView tv;

    ViewPager mviewPager;
    UserFragmentPagerAdapter mFragmentPagerAdapter;
    TabLayout mtabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userId = getIntent().getIntExtra("userId", -1);

        mviewPager= findViewById(R.id.pager);

        //---ADDING ADAPTER FOR FRAGMENTS IN VIEW PAGER----
        mFragmentPagerAdapter=new UserFragmentPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(mFragmentPagerAdapter);

        //---SETTING TAB LAYOUT WITH VIEW PAGER
        mtabLayout=(TabLayout)findViewById(R.id.tab_layout);
        mtabLayout.setupWithViewPager(mviewPager);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.info:
                Intent homeIntent = new Intent(UserActivity.this,UserInfoActivity.class);
                homeIntent.putExtra("userId", userId);
                startActivity(homeIntent);
                return true;
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class  UserFragmentPagerAdapter extends FragmentPagerAdapter{

        public UserFragmentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new BookDonateFragment();
                case 1:
                    return new BookRequestFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "DONATE";
                case 1:
                    return "REQUEST";
            }
            return super.getPageTitle(position);
        }
    }

}



