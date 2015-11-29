package com.example.barnes.ummo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.barnes.ummo.R;
import com.example.barnes.ummo.db.Db;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by barnes on 8/26/15.
 */
public class Qfragment extends Fragment
{
    Db db;
    Context c;
    Context c_;
    private int i = -1;
    private android.widget.ScrollView scrollView;
    public List<String> joinedq = null;
    public List<String> qPosition = null;
    public List<String> qJoined = null;
    public String num;
    private ProgressDialog p_Dialog;
    public static final String ARG_FRAG = "ARG_FRAG";
    //public static final String ARGFRAG = "ARG_FRAG";
    static int mFrag;
    int nFrag;

    public static Qfragment newInstance(int frag, int frag_n)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_FRAG, frag);
        args.putInt("ARGFRAG", frag_n);
        Qfragment fragment = new Qfragment();
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
        params.rightMargin = 150;
        //params.rightMargin = 90;
        qPositionbtn.setLayoutParams(params);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mFrag = getArguments().getInt("ARG_FRAG");
        nFrag = getArguments().getInt("ARGFRAG");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.q_list_items, container, false);
        View view_ = LayoutInflater.from(container.getContext()).inflate(R.layout.q, container, false);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        String lastNumber;
        c_ = view_.getContext();
        c = view.getContext();
        db = new Db(view.getContext());
        db.open();

        Log.e("nFrag Position", "" + nFrag);
        Log.e("mFrag Position", "" + mFrag);
        joinedq = db.getQNameJoinedFromTabPosition(mFrag);
        String qname = joinedq.get(0).toString();
        qPosition = db.getQPositionJoined(qname);
        String pos = qPosition.get(0).toString();
        num = pos;
        lastNumber = num.substring(num.length() - 1);
        db.close();

        Button man_Q_1 = (Button)view.findViewById(R.id.man_one);
        Button man_Q_2 = (Button)view.findViewById(R.id.man_two);
        Button man_Q_3 = (Button)view.findViewById(R.id.man_three);
        Button man_Q_4 = (Button)view.findViewById(R.id.man_four);
        Button man_Q_5 = (Button)view.findViewById(R.id.man_five);

        if (lastNumber.equals("0"))
        {
            loadQinfo(man_Q_5, num);
        }
        else if (lastNumber.equals("1"))
        {
            loadQinfo(man_Q_1, num);
        }
        else if (lastNumber.equals("2"))
        {
            loadQinfo(man_Q_2, num);
        }
        else if (lastNumber.equals("3"))
        {
            loadQinfo(man_Q_3, num);
        }
        else if (lastNumber.equals("4"))
        {
            loadQinfo(man_Q_4, num);
        }
        else if (lastNumber.equals("5"))
        {
            loadQinfo(man_Q_5, num);
        }
        else if (lastNumber.equals("6"))
        {
            loadQinfo(man_Q_1, num);
        }
        else if (lastNumber.equals("7"))
        {
            loadQinfo(man_Q_2, num);
        }
        else if (lastNumber.equals("8"))
        {
            loadQinfo(man_Q_3, num);
        }
        else if (lastNumber.equals("9"))
        {
            loadQinfo(man_Q_4, num);
        }
        db.close();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialog(Context context, final String text, final String position)
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
