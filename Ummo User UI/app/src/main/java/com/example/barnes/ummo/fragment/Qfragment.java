package com.example.barnes.ummo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.barnes.ummo.R;
import com.example.barnes.ummo.db.Db;
import com.software.shell.fab.ActionButton;

import cn.pedant.SweetAlert.SweetAlertDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by barnes on 8/26/15.
 */
public class Qfragment extends Fragment
{
    ActionButton actionButton;
    Db db;
    Context c;
    Context c_;
    private int i = -1;
    private android.widget.ScrollView scrollView;

    public String num;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.q_list_items,container, false);
        View view_ = LayoutInflater.from(container.getContext()).inflate(R.layout.q,container, false);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        c_ = view_.getContext();
        c = view.getContext();
        db = new Db(view.getContext());
        db.open();

        num = "25";
        actionButton = (ActionButton) view.findViewById(R.id.action_button_);
        actionButton.setImageResource(R.drawable.ic_exposure_neg_1_black_18dp);
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_light_blue_500));
        Button man_Q_6 = (Button)view.findViewById(R.id.man_six);
        man_Q_6.setText("#"+num);
        man_Q_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog(c, "");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void dialog(Context context, final String text)
    {
        final Context c = context;
        final String text_ = text;
        final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(" "+text)
                .setContentText("Loading...");

        pDialog.show();
        pDialog.setCancelable(false);
        new CountDownTimer(800 * 7, 800)
        {
            public void onTick(long millisUntilFinished)
            {
                i++;
                switch (i){
                    case 0:
                        pDialog.getProgressHelper().setBarColor(c.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(c.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(c.getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(c.getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(c.getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(c.getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(c.getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }

            public void onFinish()
            {
                i = -1;
                pDialog.setTitleText(" " + text_ + " Queue")
                        .setContentText("Queue Position                                                  " +
                                "\n" + "  Queue DQ time                                                  ")
                        .setConfirmText("OK")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                pDialog.dismiss();
                            }
                        });
            }
        }.start();
    }
}
