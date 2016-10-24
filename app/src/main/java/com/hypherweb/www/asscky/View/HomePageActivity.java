package com.hypherweb.www.asscky.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hypherweb.www.asscky.Adaptor.HomePageAdaptor;
import com.hypherweb.www.asscky.R;


public class HomePageActivity extends AppCompatActivity {

    private static final String LOG_TAG = HomePageActivity.class.getSimpleName();

    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(LOG_TAG, mUser.getEmail());

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        HomePageAdaptor homePageAdaptor = new HomePageAdaptor(HomePageActivity.this, getSupportFragmentManager());
        // Set the adapter onto the view pager
        viewPager.setAdapter(homePageAdaptor);

        //Find the tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        //Set the tab layout with view pager
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOut) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(HomePageActivity.this, R.string.signed_out_toast, Toast.LENGTH_LONG).show();
            Intent signOut = new Intent(HomePageActivity.this, MainActivity.class);
            startActivity(signOut);
        }
        return true;
    }


}

