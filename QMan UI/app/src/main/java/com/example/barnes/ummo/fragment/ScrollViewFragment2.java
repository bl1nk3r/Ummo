package com.example.barnes.ummo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.barnes.ummo.R;
import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by barnes on 1/1/16.
 */
public class ScrollViewFragment2 extends Fragment
{
    @Bind(R.id.scrollView)
    ObservableScrollView scrollView;

    @Bind(R.id.title)
    TextView title;

    private int i = -1;
    Context c;

    static String qName;
    static String qPosition;
    static String qAlphanum;

    public static ScrollViewFragment2 newInstance(String title)
    {
        Bundle args = new Bundle();
        args.putString("title",title);
        ScrollViewFragment2 fragment = new ScrollViewFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    public void loadQinfo(Button qPositionbtn, final String position)
    {
        qPositionbtn.setText("#" + position);
        qPositionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(c, "", position);
            }
        });
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) qPositionbtn.getLayoutParams();
        params.width = 150;
        params.height = 150;
        //params.rightMargin = 150;
        //params.rightMargin = 90;
        qPositionbtn.setLayoutParams(params);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_scroll, container, false);
        c = view.getContext();
        //int pos = Integer.parseInt(qPosition);
        qPosition = "1";
        String lastNumber = qPosition.substring(qPosition.length() - 1);
        Button man_Q_1 = (Button)view.findViewById(R.id.man_one);
        Button man_Q_2 = (Button)view.findViewById(R.id.man_two);
        Button man_Q_3 = (Button)view.findViewById(R.id.man_three);
        Button man_Q_4 = (Button)view.findViewById(R.id.man_four);
        Button man_Q_5 = (Button)view.findViewById(R.id.man_five);
        //man_Q_1.setText("5");
        //loadQinfo(man_Q_1, "5");

        if (lastNumber.equals("0"))
        {
            loadQinfo(man_Q_5, qPosition);
        }
        else if (lastNumber.equals("1"))
        {
            loadQinfo(man_Q_1, qPosition);
        }
        else if (lastNumber.equals("2"))
        {
            loadQinfo(man_Q_2, qPosition);
        }
        else if (lastNumber.equals("3"))
        {
            loadQinfo(man_Q_3, qPosition);
        }
        else if (lastNumber.equals("4"))
        {
            loadQinfo(man_Q_4, qPosition);
        }
        else if (lastNumber.equals("5"))
        {
            loadQinfo(man_Q_5, qPosition);
        }
        else if (lastNumber.equals("6"))
        {
            loadQinfo(man_Q_1, qPosition);
        }
        else if (lastNumber.equals("7"))
        {
            loadQinfo(man_Q_2, qPosition);
        }
        else if (lastNumber.equals("8"))
        {
            loadQinfo(man_Q_3, qPosition);
        }
        else if (lastNumber.equals("9"))
        {
            loadQinfo(man_Q_4, qPosition);
        }
        //return inflater.inflate(R.layout.fragment_scroll, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        title.setText(getArguments().getString("title"));
        HollyViewPagerBus.registerScrollView(getActivity(), scrollView);
    }

    public void dialog(Context context, final String text, final String position)
    {
        final Context con = context;
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
                switch (i)
                {
                    case 0:
                        pDialog.getProgressHelper().setBarColor(con.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(con.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(con.getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(con.getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(con.getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(con.getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(con.getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }
            public void onFinish()
            {
                i = -1;
                pDialog.setTitleText(" " + text_ + " Queue")
                        .setContentText("Queue Position:"+ position +
                                "\n" + " Queue Length:22" +
                                "\n" + "Queue DQ time:00:05" +
                                "\n" + "Queue Time Left to DQ:01:05")
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
