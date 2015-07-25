package com.ummo.blinker.ummoqmasternavsample;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.ummo.blinker.ummoqmasternavsample.actions.CreateQ;
import com.ummo.blinker.ummoqmasternavsample.actions.InitQ;
import com.ummo.blinker.ummoqmasternavsample.actions.ReviewQ;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    //private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ListView navList;

    private CharSequence navDrawerTitle;
    private CharSequence appTitle;

    private String[] navDrawerItemTitles;
    private TypedArray navDrawerIcons;

    //private ArrayList<NavigationDrawerItem> navDrawerItems;
    //private NavigationDrawerListAdaptor navDrawerAdaptor;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager  fragmentManager;

    //Button createQButton, initQButton, reviewQButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navList = (ListView) findViewById(R.id.navListV);

        //this.appTitle = this.getTitle();
        //this.navDrawerTitle = this.getTitle();
        //this.navDrawerItemTitles = this.getResources().getStringArray(R.array.nav_drawer_strings);
        //this.navDrawerIcons = this.getResources().getStringArray(R.array.nav_drawer_icons);

        /*this.navDrawerItems.add(new NavigationDrawerItem(navDrawerItemTitles[0], navDrawerIcons.getResourceId(0, -1), "10", true));
        this.navDrawerItems.add(new NavigationDrawerItem(navDrawerItemTitles[1], navDrawerIcons.getResourceId(1, -1)));
        this.navDrawerItems.add(new NavigationDrawerItem(navDrawerItemTitles[2], navDrawerIcons.getResourceId(2, -1)));
        this.navDrawerItems.add(new NavigationDrawerItem(navDrawerItemTitles[3], navDrawerIcons.getResourceId(3, -1)));
        this.navDrawerItems.add(new NavigationDrawerItem(navDrawerItemTitles[4], navDrawerIcons.getResourceId(4, -1)));
        this.navDrawerItems.add(new NavigationDrawerItem(navDrawerItemTitles[5], navDrawerIcons.getResourceId(5, -1)));
        this.navDrawerItems.add(new NavigationDrawerItem(navDrawerItemTitles[6], navDrawerIcons.getResourceId(6, -1)));
        navDrawerIcons.recycle();*/

        ArrayList<String> navArray = new ArrayList<>();
        navArray.add(getString(R.string.account));
        navArray.add(getString(R.string.myQText));
        navArray.add(getString(R.string.historyText));
        navArray.add(getString(R.string.settingsText));
        navArray.add(getString(R.string.helpText));
        navArray.add(getString(R.string.aboutUsText));


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navArray);
        navList.setAdapter(adapter);
        navList.setOnItemClickListener(this);
        /*{

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadSelection(position);
                setTitle(navDrawerItemTitles[position]);
                navList.setItemChecked(position, true);
                drawerLayout.closeDrawer(navList);
            }
        });*/

        //navDrawerAdaptor = new NavigationDrawerListAdaptor(this.getApplicationContext(), navDrawerItems);
        //navList.setAdapter(adaptor);

        //this.getActionBar().setDisplayHomeAsUpEnabled(true);
        //this.getActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.openedDrawer, R.string.closedDrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);   //listen to drawer open || closed


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);      //puts hamBurger menu
        actionBar.setDisplayHomeAsUpEnabled(true);      //puts back to home button
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
        loadSelection(0);

         //initCreateQButton();
        /*initInitQButton();
        initReviewQButton();*/
    }

    private void loadSelection(int i) {
        navList.setItemChecked(i, true);
        switch (i) {
            case 0:
                AccountFragment accountFragment = new AccountFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, accountFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                MyQFragment myQFragment = new MyQFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, myQFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                HistoryFragment historyFragment = new HistoryFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, historyFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, settingsFragment);
                fragmentTransaction.commit();
                break;
            case 4:
                HelpFragment helpFragment = new HelpFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, helpFragment);
                fragmentTransaction.commit();
                break;
            case 5:
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, aboutUsFragment);
                fragmentTransaction.commit();
                break;
        }

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();      //sync actionBar with drawerLayout
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(navList)){
                drawerLayout.closeDrawer(navList);
            } else {
                drawerLayout.openDrawer(navList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                loadSelection(position);
                break;
            case 1:
                loadSelection(position);
                break;
            case 2:
                loadSelection(position);
                break;
            case 3:
                loadSelection(position);
                break;
            case 4:
                loadSelection(position);
                break;
            case 5:
                loadSelection(position);
                break;
            case 6:
                loadSelection(position);
                break;
        }

        drawerLayout.closeDrawer(navList);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btCreateQ:
                Intent i = new Intent(this, CreateQ.class);
                startActivity(i);
                break;

            case R.id.btInitQ:
                Intent j = new Intent(this, InitQ.class);
                startActivity(j);
                break;

            case R.id.btReviewQ:
                Intent k = new Intent(this, ReviewQ.class);
                startActivity(k);
                break;
        }
    }
}
