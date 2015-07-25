package com.example.barnes.ummoq;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.barnes.ummoq.fragments.Qfragment;
import com.google.common.collect.Lists;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

public class MainActivity extends AppCompatActivity
{
    private int i = -1;
    private ViewPager viewPager;
    private ModelPagerAdapter adapter;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    PagerModelManager factory = new PagerModelManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q_services);

        final Context context = this;
        final Button service_1 = (Button) findViewById(R.id.service_1);
        final Button service_2 = (Button) findViewById(R.id.service_2);
        final Button service_3 = (Button) findViewById(R.id.service_3);
        final Button service_4 = (Button) findViewById(R.id.service_4);
        final Button service_5 = (Button) findViewById(R.id.service_5);

        final TextView a = new TextView(this);
        a.setText("Deposit");
        a.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        a.setPadding(20,20,20,20);
        final TextView b = new TextView(this);
        b.setText("Withdrawals");
        b.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        b.setPadding(20, 20, 20, 20);
        final TextView c = new TextView(this);
        c.setText("Foreign Exchange");
        c.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        c.setPadding(20, 20, 20, 20);
        final TextView d = new TextView(this);
        d.setText("Enquires");
        d.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        d.setPadding(20, 20, 20, 20);
        final TextView e = new TextView(this);
        e.setText("Enquires");
        e.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        e.setPadding(20, 20, 20, 20);
        final TextView f = new TextView(this);
        f.setText("Exchange");
        f.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        f.setPadding(20, 20, 20, 20);
        final TextView g = new TextView(this);
        g.setText("Teller");
        g.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        g.setPadding(20, 20, 20, 20);
        final TextView h = new TextView(this);
        h.setText("Deposit");
        h.setBackgroundResource(R.drawable.button);
        //a.setBackgroundColor(getResources().getColor(R.color.lightblue1));
        h.setPadding(20,20,20,20);

        final FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        a.setLayoutParams(tvParams);

        b.setLayoutParams(tvParams);
        c.setLayoutParams(tvParams);
        d.setLayoutParams(tvParams);
        e.setLayoutParams(tvParams);
        f.setLayoutParams(tvParams);
        g.setLayoutParams(tvParams);
        h.setLayoutParams(tvParams);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final SweetAlertDialog pDialog;
                pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("Swazi Bank Deposits Q")
                        .setContentText("Loading");

                pDialog.show();
                pDialog.setCancelable(false);
                new CountDownTimer(800 * 7, 800) {
                    public void onTick(long millisUntilFinished) {
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

                    public void onFinish() {
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
                                        // reuse previous dialog instance, keep widget user state, reset them if you need


                                        // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.setTitleText("Queued In")
                                                .setContentText("You have joined the Swazi Bank Deposit Q, your position is #25")
                                                .setConfirmText("OK")
                                                .showCancelButton(false)
                                                .setCancelClickListener(null)
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }.start();
            }
        });

        final ImageView expand = new ImageView(this);
        expand.setVisibility(View.GONE);

        expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder((Activity) context)
                .setContentView(expand)
                .build();
        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder((Activity) context);
        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder((Activity) context)
                .attachTo(rightLowerButton)
                .build();
        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                expand.setRotation(360);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(expand, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                expand.setRotation(360);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(expand, pvhR);
                animation.start();
            }
        });
        rightLowerButton.setVisibility(View.GONE);

        SubActionButton.Builder subBuilder = new SubActionButton.Builder((Activity) context);
        final FloatingActionMenu circleMenu = new FloatingActionMenu.Builder((Activity) context)
                .setStartAngle(0) // A whole circle!
                .setEndAngle(360)
                .setRadius(getResources().getDimensionPixelSize(R.dimen.radius_large))
                .addSubActionView(a)
                .addSubActionView(b)
                .addSubActionView(c)
                .addSubActionView(d)
                .addSubActionView(e)
                .addSubActionView(f)
                .addSubActionView(g)
                .addSubActionView(h)
                .attachTo(service_1)
                .build();

        service_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightLowerButton.setVisibility(View.VISIBLE);
                service_2.setVisibility(View.GONE);
                service_3.setVisibility(View.GONE);
                service_4.setVisibility(View.GONE);
                service_5.setVisibility(View.GONE);
                expand.setVisibility(View.VISIBLE);

                if (service_1.getText().equals("Select Q") && circleMenu.isOpen())
                {
                    circleMenu.close(true);
                }
                if (service_1.getText().equals("Select Q"))
                {
                    circleMenu.open(true);
                }
                //Toast t = Toast.makeText(context,"Height = "+height+"\n Width = "+ width,Toast.LENGTH_SHORT);
                //t.show();
                TextView heading = (TextView) findViewById(R.id.heading);
                heading.setText("Please Select Q");
                service_1.setText("Select Q");
                RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                service_1.setLayoutParams(btnParams);

                expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (circleMenu.isOpen())
                        {
                            circleMenu.close(true);
                        }
                        else
                        {
                            TextView heading = (TextView) findViewById(R.id.heading);
                            heading.setText("Please Select Service Provider");
                            RelativeLayout.LayoutParams btn_Params = new RelativeLayout.LayoutParams(600, 150);
                            btn_Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            btn_Params.setMargins(30,30,30,30);
                            service_2.setVisibility(View.VISIBLE);
                            service_3.setVisibility(View.VISIBLE);
                            service_4.setVisibility(View.VISIBLE);
                            service_5.setVisibility(View.VISIBLE);
                            service_1.setVisibility(View.VISIBLE);
                            expand.setVisibility(View.GONE);
                            rightLowerButton.setVisibility(View.GONE);
                            service_1.setText("Swazi Bank");
                            service_1.setLayoutParams(btn_Params);
                        }
                    }
                });
            }
        });

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                service_2.setVisibility(View.VISIBLE);
                service_3.setVisibility(View.VISIBLE);
                service_4.setVisibility(View.VISIBLE);
                service_5.setVisibility(View.VISIBLE);
                service_1.setVisibility(View.VISIBLE);
                expand.setVisibility(View.GONE);
                rightLowerButton.setVisibility(View.GONE);
                service_1.setText("Swazi Bank");
            }
        });
    }

    private List<String> getTitles()
    {
        return Lists.newArrayList("#FNB1");
    }

    private List<Fragment> getFragments()
    {
        List<Fragment> list = new ArrayList<>();
        Fragment listFragment = new Qfragment();
        list.add(listFragment);
        return list;
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
        }

        return super.onOptionsItemSelected(item);
    }
}
