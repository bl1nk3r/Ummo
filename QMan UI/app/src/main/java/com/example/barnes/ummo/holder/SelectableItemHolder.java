package com.example.barnes.ummo.holder;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barnes.ummo.R;
import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.fragment.SelectableTreeFragment;
import com.example.barnes.ummo.ummoAPI.QUser;
import com.unnamed.b.atv.model.TreeNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by barnes on 8/6/15.
 */
public class SelectableItemHolder extends TreeNode.BaseNodeViewHolder<String>
{
    private TextView tvValue;
    private CheckBox nodeSelector;
    private Button exit_q_menu;
    public String selecHeader;
    String tabTitle;
    private int i = -1;
    SelectableTreeFragment parent;
    int tabPos = 0;
    public List<String> qsJoinedNum = null;
    public List<String> qNameList = null;
    public List<String> qNameServiceId = null;
    public List<String> qJoinedQid = null;
    List<String> qName_List;
    String qsJoined;
    Db db;
    JSONObject vq;
    public void dialog(final String text)
    {
        final Context c = context;
        final String text_ = text;
        final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(selecHeader)
                .setContentText("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);
        //Extracts Qstats from text
        int mins=0;
        int qLength=0;
        try
        {
            JSONObject jobject = new JSONObject(text);
            if (jobject.has("ttdq"))
            {
                mins = jobject.getInt("ttdq");
            }
            if (jobject.has("qErs"))
            {
                qLength = jobject.getJSONObject("qErs").length();
            }
        }
        catch (JSONException jse)
        {
            Log.e("Erorr","Can't Extract Qstats form text"+jse.toString());
        }
        final int ttdq_mins = mins/(60*1000);
        final int ttqd_secs = mins/(1000)-ttdq_mins*60;
        final int final_length=qLength;
        //End Extracts Qstats from text
        new CountDownTimer(800 * 7, 800)
        {
            public void onTick(long millisUntilFinished)
            {
                i++;
                switch (i)
                {
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
                pDialog.setTitleText(selecHeader + " Queue")
                        .setContentText(
                                "Location of service : ".toUpperCase() + "\nMbabane\n" +
                                        "Length of queue : ".toUpperCase() + "\n"+final_length+"\n" +
                                        "Time for single process : ".toUpperCase() + "\n"+ttdq_mins+":"+ttqd_secs+"\n" +
                                        "Service Requirements:".toUpperCase() + "\nNational Id")
                        .setConfirmText("Join Q")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
                                sDialog.dismiss();
                                pDialog.dismiss();
                                nodeSelector.setChecked(false);
                            }
                        })
                        .showCancelButton(true)
                        .setConfirmClickListener(
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        qsJoinedNum = db.getAllQs();
                                        if (!qsJoinedNum.isEmpty())
                                        {
                                            int qsJoinedSize = qsJoinedNum.size();
                                            tabPos = qsJoinedSize;
                                            Log.e("tabPos", "" + tabPos);
                                        }
                                        qNameList = db.getQName(text);
                                        if (qNameList.isEmpty())
                                        {
                                            ++tabPos;
                                            //Log.e("++tabPos", "" + tabPos);
                                            //Log.e("Position", "" + tabPos);
                                            Random r = new Random();
                                            int i1 = r.nextInt(101 - 1) + 1;
                                            final int qpos = final_length + 1;
                                            final String pos = Integer.toString(i1);
                                            sDialog.setTitleText("Queued in...")
                                                    .setContentText("You have joined the " + selecHeader + " " + selecHeader + " queue, your position is #" + qpos)
                                                    .setConfirmText("OK")
                                                    .showCancelButton(false)
                                                    .setCancelClickListener(null)
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {
                                                            parent.joinSelectedQ();
                                                            tabTitle = selecHeader + " " + text;
                                                            qNameServiceId = db.getQService_id(text);
                                                            //String serviceid_string = qNameServiceId.get(0);
                                                            //int serviceid = Integer.parseInt(serviceid_string);
                                                            //db.insertQ(serviceid, text, tabPos, pos);
                                                            //select = true;
                                                            //Intent i = new Intent();
                                                            //i.setClass(context, Qs.class);
                                                            //i.putExtra("tab", "tabTitle");
                                                            //String positionTab = Integer.toString(tabPos);
                                                            //String int_serviceid = Integer.toString(serviceid);
                                                            //i.putExtra("serviceid", "int_serviceid");
                                                            //i.putExtra("tabPos", positionTab);
                                                            sDialog.dismiss();
                                                            nodeSelector.setChecked(true);
                                                            exit_q_menu.setVisibility(View.VISIBLE);
                                                            nodeSelector.setClickable(false);
                                                            //context.startActivity(i);
                                                            /*final String qpos_string = Integer.toString(qpos);*/
                                                                    ((SelectableTreeFragment) c).getUser().updateJoinedQs();
                                                            //Intent i = new Intent(c, Main_Activity.class);
                                                            //Log.e("++tabPos", "" + tabPos);
                                                            //context.startActivity(i);
                                                            pDialog.dismiss();
                                                            db.close();
                                                        }
                                                    })
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        } else {
                                            pDialog.setTitleText("Exit Queue")
                                                    .setContentText("You are already queued in to this que. Do you want to exit the " + selecHeader + " " + text + " queue")
                                                    .setConfirmText("yes")
                                                    .showCancelButton(true)
                                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {
                                                            sDialog.dismiss();
                                                            pDialog.dismiss();
                                                            nodeSelector.setChecked(true);
                                                            exit_q_menu.setVisibility(View.VISIBLE);
                                                        }
                                                    })
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {
                                                            qJoinedQid = db.getJoinedQ_id(text);
                                                            String qid_string = qJoinedQid.get(0);
                                                            db.deleteQ(qid_string);
                                                            //select = false;
                                                            sDialog.dismiss();
                                                            pDialog.dismiss();
                                                            exit_q_menu.setVisibility(View.GONE);
                                                            nodeSelector.setChecked(false);
                                                            nodeSelector.setEnabled(true);
                                                            nodeSelector.setClickable(true);
                                                            tvValue.setClickable(true);
                                                            db.close();
                                                        }
                                                    })
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        }
                                    }
                                })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();
    }

    public void dialogEjectQ(final String text, final CheckBox nodeSelector)
    {
        final Context c = context;
        final String text_ = text;
        final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(selecHeader+" "+text)
                .setContentText("Exiting " + text);

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
                pDialog.setTitleText("Exit Queue")
                        .setContentText("You are already queued in to this que. Do you want to exit the " + selecHeader + " " + text + " queue")
                        .setConfirmText("yes")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                pDialog.dismiss();
                                nodeSelector.setChecked(true);
                                exit_q_menu.setVisibility(View.VISIBLE);
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                qJoinedQid = db.getJoinedQ_id(text);
                                String qid_string = qJoinedQid.get(0);
                                db.deleteQ(qid_string);
                                qName_List = db.getQName(text);
                                //select = false;
                                sDialog.dismiss();
                                pDialog.dismiss();
                                nodeSelector.setChecked(false);
                                nodeSelector.setClickable(true);
                                exit_q_menu.setVisibility(View.GONE);
                                db.close();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();
    }

    public SelectableItemHolder(Activity context, JSONObject itemHeader, String qjoined)
    {
        super(context);
        vq = itemHeader;
        parent = (SelectableTreeFragment)context;
        db = new Db(context);
        //String s =((SingleFragmentActivity)context).getUser().getName();
        Log.e("User","s");
        try
        {
            selecHeader = itemHeader.getString("name");
        }
        catch (JSONException jse)
        {
            Log.e("Error",jse.toString());
            selecHeader = "Error, get Help";
        }
        qsJoined = qjoined;
    }

    @Override
    public View createNodeView(final TreeNode node, String value)
    {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_item, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value);
        tvValue.isClickable();
        tvValue.setTextIsSelectable(true);
        nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
        exit_q_menu = (Button) view.findViewById(R.id.btn_exit_q_menu);
        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.open();
                //Log.e("User", parent.getUser().getName());
                qNameList = db.getQName(tvValue.getText().toString());
                if (nodeSelector.isChecked())
                {
                    //dialog(tvValue.getText().toString());
                    try
                    {
                        parent.setSelectedQ(vq.getString("Id"));
                        QUser user =parent.getUser();
                        if(user!=null){
                            user.getQ(vq.getString("Id"));
                            Log.e("USER",user.getName());
                        }

                        else {
                            Log.e("USER","NULL");
                        }

                        parent.setQinfoDialog(SelectableItemHolder.this);
                    }
                    catch (JSONException jse)
                    {
                        Toast.makeText(parent,"Cannot Get The Q information, Q ID is Broken",Toast.LENGTH_LONG).show();
                    }
                }
                db.close();
            }
        });

        exit_q_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEjectQ(tvValue.getText().toString(), nodeSelector);
            }
        });

        db.open();
        qName_List = db.getQName(tvValue.getText().toString());
        if (!qName_List.isEmpty())
        {
            exit_q_menu.setVisibility(View.VISIBLE);
            nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (qName_List.isEmpty())
                    {
                        nodeSelector.setChecked(false);
                        nodeSelector.setEnabled(true);
                        nodeSelector.setClickable(true);
                        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                qNameList = db.getQName(tvValue.getText().toString());
                                if (nodeSelector.isChecked())
                                {
                                    dialog(tvValue.getText().toString());
                                }
                            }
                        });
                    } else if (!qName_List.isEmpty()) {
                        nodeSelector.setChecked(true);
                        nodeSelector.setClickable(false);
                    }
                }
            });
        }
        db.close();

        tvValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (tvValue.isPressed())
                {
                    nodeSelector.setChecked(true);
                }
            }
        });

        if (node.isLastChild())
        {
            view.findViewById(R.id.bot_line).setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled)
    {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(false);
    }
}