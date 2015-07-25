package com.ummo.blinker.qmasternav;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;

import com.ummo.blinker.qmasternav.com.ummo.blinker.viewModel.NavigationDrawerItem;
import com.ummo.blinker.qmasternav.com.ummo.blinker.viewModel.NavigationDrawerListAdaptor;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ActionBarDrawerToggle actionBarDrawerToggle;
    //private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ListView navList;

    private CharSequence navDrawerTitle;
    private CharSequence appTitle;

    private String[] navDrawerItemTitles;
    private TypedArray navDrawerIcons;

    private ArrayList<NavigationDrawerItem> navDrawerItems;
    private NavigationDrawerListAdaptor navDrawerAdaptor;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager  fragmentManager;

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
        navArray.add(getString(R.string.home));
        navArray.add(getString(R.string.createQ));
        navArray.add(getString(R.string.initQ));
        navArray.add(getString(R.string.manageQ));
        navArray.add(getString(R.string.modifyQ));
        navArray.add(getString(R.string.reviewQ));
        navArray.add(getString(R.string.closeQ));

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

        navDrawerAdaptor = new NavigationDrawerListAdaptor(this.getApplicationContext(), navDrawerItems);
        navList.setAdapter(navDrawerAdaptor);

        //this.getActionBar().setDisplayHomeAsUpEnabled(true);
        //this.getActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.openedDrawer, R.string.closedDrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);   //listen to drawer open || closed

        //if (savedInstanceState == null)
                       //instantiate to home page

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);      //puts hamBurger menu
        actionBar.setDisplayHomeAsUpEnabled(true);      //puts back to home button
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
        loadSelection(0);
    }

    private void loadSelection(int i) {
        navList.setItemChecked(i, true);
        switch (i) {
            case 0:
                homeFragment homeFrag = new homeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, homeFrag);
                fragmentTransaction.commit();
                break;
            case 1:
                createQFragment createQFrag = new createQFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, createQFrag);
                fragmentTransaction.commit();
                break;
            case 2:
                initQFragment initQFrag = new initQFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, initQFrag);
                fragmentTransaction.commit();
                break;
            case 3:
                manQFragment manQFrag = new manQFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, manQFrag);
                fragmentTransaction.commit();
                break;
            case 4:
                modQFragment modQFrag = new modQFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, modQFrag);
                fragmentTransaction.commit();
                break;
            case 5:
                revQFragment revQFrag = new revQFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, revQFrag);
                fragmentTransaction.commit();
                break;
            case 6:
                closeQFragment closeQFrag = new closeQFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, closeQFrag);
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
}
