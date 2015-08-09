package com.example.barnes.ummo;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barnes.ummo.db.Db;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by barnes on 8/1/15.
 */
public class ExpandedListItemView extends RelativeLayout
{
    private RelativeLayout mRoot;

    private TextView mText;

    private AppCompatCheckBox mCheckbox;

    private View mViewSeparator;

    int tabPos= 0;
    public List<String> qNameList = null;
    Db db;
    private int i = -1;
    public ExpandedListItemView(Context context)
    {
        super(context);
        init();
    }

    public ExpandedListItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ExpandedListItemView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        inflate(getContext(), R.layout.expandlablelist_qservice_provider_textview, this);

        db = new Db(getContext());
        db.open();
        mRoot = (RelativeLayout) findViewById(R.id.expandable_list_item_view_root);
        mText = (TextView) findViewById(R.id.expandable_list_item_view_text);
        mCheckbox = (AppCompatCheckBox) findViewById(R.id.expandable_list_item_view_checkbox);
        mViewSeparator = findViewById(R.id.expandable_list_item_view_separator);

        this.mRoot.setOnClickListener(
                new OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        mCheckbox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final SweetAlertDialog pDialog;
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText(mText.getText().toString())
                        .setContentText("Loading");

                pDialog.show();
                pDialog.setCancelable(false);
                new CountDownTimer(800 * 7, 800)
                {
                    public void onTick(long millisUntilFinished)
                    {
                        // you can change the progress bar color by ProgressHelper every 800 millis
                        i++;
                        switch (i){
                            case 0:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                break;
                            case 2:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                            case 3:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                break;
                            case 4:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                break;
                            case 5:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                break;
                            case 6:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                        }
                    }

                    public void onFinish()
                    {
                        i = -1;
                        pDialog.setTitleText("Deposits Q")
                                .setContentText("Queue Length                                                  " +
                                        "\n" + "  Queue DQ time                                                  ")
                                .setConfirmText("OK")
                                .setConfirmText("Join Q")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog)
                                    {
                                        sDialog.dismiss();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog)
                                    {

                                        qNameList = db.getQName("Swazi Bank Deposit Q");
                                        if (qNameList.isEmpty())
                                        {
                                            tabPos++;
                                            db.insertQ("Swazi Bank Deposit Q", tabPos, "3");
                                            sDialog.setTitleText("Queued In")
                                                    .setContentText("You have joined the Swazi Bank Deposit Q, your position is #25")
                                                    .setConfirmText("OK")
                                                    .showCancelButton(false)
                                                    .setCancelClickListener(null)
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {
                                                            Intent i = new Intent();
                                                            i.setClass(getContext(),Qs.class);
                                                            String tabTitle = "Swazi Bank Deposits Q";
                                                            i.putExtra("tab", tabTitle);
                                                            getContext().startActivity(i);
                                                            pDialog.dismiss();
                                                        }
                                                    })
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            mCheckbox.setChecked(!mCheckbox.isChecked());
                                        }
                                        else
                                        {
                                            String name = qNameList.get(0);
                                            Toast t = Toast.makeText(getContext(),name,Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }.start();
            }
        });
    }

    public void setText(String text, boolean showCheckbox)
    {
        this.mText.setText(text);
        if (!showCheckbox)
        {
            this.mCheckbox.setVisibility(View.GONE);
            this.mViewSeparator.setVisibility(View.GONE);
        }
    }
}
