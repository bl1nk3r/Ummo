package com.example.barnes.ummo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.barnes.ummo.R;

/**
 * Created by barnes on 12/4/15.
 */
public class BaseActivityFragment extends Fragment
{
    public static BaseActivityFragment newInstance()
    {

        Bundle args = new Bundle();

        BaseActivityFragment fragment = new BaseActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }
    protected void enterFromBottomAnimation()
    {
        getActivity().overridePendingTransition(R.anim.activity_open_translate_from_bottom, R.anim.activity_no_animation);
    }

    protected void exitToBottomAnimation()
    {
        getActivity().overridePendingTransition(R.anim.activity_no_animation, R.anim.activity_close_translate_to_bottom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_send:
                Toast.makeText(getActivity(), "Yeah!", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause()
    {
        exitToBottomAnimation();
        super.onPause();
    }
}