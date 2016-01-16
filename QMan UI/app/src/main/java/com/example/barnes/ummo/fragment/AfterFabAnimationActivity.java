package com.example.barnes.ummo.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.barnes.ummo.R;

import butterknife.ButterKnife;

/**
 * Created by barnes on 12/4/15.
 */
public class AfterFabAnimationActivity extends BaseActivity_2
{
    static int mFrag;
    int nFrag;

    public static AfterFabAnimationActivity newInstance(int frag, int frag_n)
    {
        //Bundle args = new Bundle();
        //args.putInt(ARG_FRAG, frag);
        //args.putInt("ARGFRAG", frag_n);
        AfterFabAnimationActivity fragment = new AfterFabAnimationActivity();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        enterFromBottomAnimation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q_list_items);
        ButterKnife.bind(this);
        setUpToolbarWithTitle(getString(R.string.app_name), true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_);
        //toolbar.setTitle("mmo");
        //toolbar.setNavigationIcon(R.mipmap.ummo_logo);
        toolbar.setLogo(R.mipmap.ummo_logo);
    }

    @Override
    protected void onPause()
    {
        exitToBottomAnimation();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_send:
                Toast.makeText(getApplicationContext(), "Yeah!", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}