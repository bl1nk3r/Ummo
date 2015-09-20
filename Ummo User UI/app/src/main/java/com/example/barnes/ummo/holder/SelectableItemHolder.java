package com.example.barnes.ummo.holder;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.barnes.ummo.Qs;
import com.example.barnes.ummo.R;
import com.example.barnes.ummo.db.Db;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by barnes on 8/6/15.
 */
public class SelectableItemHolder extends TreeNode.BaseNodeViewHolder<String>
{
    private TextView tvValue;
    private CheckBox nodeSelector;
    public String selecHeader;
    public boolean select = false;
    String tabTitle;

    private int i = -1;

    int tabPos= 0;
    public List<String> qNameList = null;
    public List<String> qJoinedList = null;
    public List<String> qNameServiceId = null;
    public List<String> qJoinedQid = null;
    String qsJoined;
    Db db;

    public void dialog(final String text)
    {
        final Context c = context;
        final String text_ = text;
        final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(selecHeader+" "+text)
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
                pDialog.setTitleText(selecHeader + " " + text_ + " Queue")
                        .setContentText("Queue Length                                                  " +
                                "\n" + "  Queue DQ time                                                  ")
                        .setConfirmText("Join Q")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                select = false;
                                sDialog.dismiss();
                                pDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(
                                new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                qNameList = db.getQName(text);
                                if (qNameList.isEmpty())
                                {
                                    tabPos++;
                                    sDialog.setTitleText("Queued In")
                                            .setContentText("You have joined the " + selecHeader + " " + text + " queue, your position is #25")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    tabTitle = selecHeader + " " + text;
                                                    qNameServiceId = db.getQService_id(text);
                                                    String serviceid_string = qNameServiceId.get(0);
                                                    int serviceid = Integer.parseInt(serviceid_string);
                                                    db.insertQ(serviceid, text, tabPos, "3");
                                                    select = true;
                                                    Intent i = new Intent();
                                                    i.setClass(context, Qs.class);
                                                    i.putExtra("tab", tabTitle);
                                                    sDialog.dismiss();
                                                    nodeSelector.setChecked(true);
                                                    context.startActivity(i);
                                                    pDialog.dismiss();
                                                }
                                            })
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                                else
                                {
                                    sDialog.setTitleText("Exit Queue")
                                            .setContentText("You are already queued in to this que. Do you want to exit the " + selecHeader + " " + text + " queue")
                                            .setConfirmText("yes")
                                            .showCancelButton(true)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    qJoinedQid = db.getJoinedQ_id(text);
                                                    String qid_string = qJoinedQid.get(0);
                                                    db.deleteQ(qid_string);
                                                    nodeSelector.setChecked(false);
                                                    select = false;
                                                    sDialog.dismiss();
                                                    pDialog.dismiss();
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

    public SelectableItemHolder(Context context, String itemHeader, String qjoined)
    {
        super(context);
        db = new Db(context);
        db.open();
        selecHeader = itemHeader;
        qsJoined = qjoined;
    }

    @Override
    public View createNodeView(final TreeNode node, String value)
    {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_item, null, false);

        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value);

        nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Context c = context;
                dialog(tvValue.getText().toString());
                if (select == false)
                {
                    //nodeSelector.setChecked(false);
                }
                else
                {
                    //nodeSelector.setSelected(true);
                }
            }
        });
        //nodeSelector.setChecked(false);
        //tvValue.isClickable();
        /*tvValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Context c = context;
                if (!node.isSelected())
                {
                    dialog(c, tvValue.getText().toString(), node);
                    //nodeSelector.setChecked(node.isSelected());
                }
                if (select == true)
                {
                    nodeSelector.setChecked(mNode.isSelected());
                }
                else
                {
                    nodeSelector.setChecked(!mNode.isSelected());
                }
            }
        });*/
        if (value == qsJoined)
        {
            nodeSelector.setChecked(true);
        }
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