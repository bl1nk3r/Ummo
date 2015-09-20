package com.example.barnes.ummo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barnes.ummo.Qs;
import com.example.barnes.ummo.R;
import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.holder.IconTreeItemHolder;
import com.example.barnes.ummo.holder.ProfileHolder;
import com.example.barnes.ummo.holder.SelectableHeaderHolder_2;
import com.example.barnes.ummo.holder.SelectableItemHolder;
import com.software.shell.fab.ActionButton;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by barnes on 8/6/15.
 */

public class SelectableTreeFragment extends Fragment
{
    private AndroidTreeView tView;
    Db db;
    public List<String> qServiceTypeList = null;
    public List<String> qServiceProviderName = null;
    public List<String> qServiceName = null;
    public List<String> qsJoined = null;
    ActionButton actionButton;
    Context c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_selectable_nodes, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        c = this.getActivity();
        actionButton = (ActionButton) rootView.findViewById(R.id.action_button);
        actionButton.setImageResource(R.drawable.fab_plus_icon);
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_light_blue_500));
        //actionButton.hide();
        db = new Db(rootView.getContext());
        db.open();
        qsJoined = db.getQsJoined();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                qsJoined = db.getQsJoined();
                if (qsJoined.size() > 0)
                {
                    Intent i = new Intent();
                    i.setClass(c, Qs.class);
                    c.startActivity(i);
                }
                else
                {
                    Toast.makeText(c, "You have not joined any ques yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        qServiceTypeList = db.getAllQServiceTypesName();

        TreeNode root = TreeNode.root();

        int qServicesTypeSize = qServiceTypeList.size();

        ArrayList<TreeNode> treeNodeList = new ArrayList<>();
        ArrayList<TreeNode> treeNodeList2 = new ArrayList<>();
        ArrayList<String> serviceNameColl = new ArrayList<>();

        int int_servicetypeid;
        String string_servicetypeid;
        int int_serviceProviderid;
        String string_serviceProviderid;

        List<String> getServiceTId;
        List<String> getServicePID;

        for (int i = 0;i < qServicesTypeSize;i++)
        {
            treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(qServiceTypeList.get(i).toString(),R.string.ic_sd_storage)).setViewHolder(new ProfileHolder(getActivity())));
            getServiceTId = db.getQServiceTypeid(qServiceTypeList.get(i));
            string_servicetypeid = getServiceTId.get(0);//id for displayed q service type
            int_servicetypeid = Integer.parseInt(string_servicetypeid);
            qServiceProviderName = db.getQServiceProviderName(int_servicetypeid);
            for (int j = 0; j < qServiceProviderName.size(); j++)
            {
                treeNodeList2.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(qServiceProviderName.get(j),R.string.ic_folder)).setViewHolder(new SelectableHeaderHolder_2(getActivity())));
                treeNodeList.get(i).addChildren(treeNodeList2.get(j));
                getServicePID = db.getQServicePid(qServiceProviderName.get(j));
                string_serviceProviderid = getServicePID.get(0);
                int_serviceProviderid = Integer.parseInt(string_serviceProviderid);
                qServiceName = db.getQServiceName(int_serviceProviderid);
                for (int k = 0;k < qServiceName.size();k++)
                {
                    serviceNameColl.add(qServiceName.get(k));
                }
            }
            customfillFolder(treeNodeList2.get(i), serviceNameColl,qServiceProviderName.get(i));
            serviceNameColl.clear();
            treeNodeList2.clear();
        }

        root.addChildren(treeNodeList);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        containerView.addView(tView.getView());

        if (savedInstanceState != null)
        {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state))
            {
                tView.restoreState(state);
            }
        }
        tView.setSelectionModeEnabled(true);
        return rootView;
    }

    private void customfillFolder(TreeNode folder, ArrayList<String> list, String serviceName)
    {
        List<String> qJoinedList = null;
        qJoinedList = db.getQName(serviceName);
        if (qJoinedList.isEmpty())
        {
            for (int i = 0;i < list.size();i++)
            {
                folder.addChildren(new TreeNode(list.get(i)).setViewHolder(new SelectableItemHolder(getActivity(),serviceName, "cool")));
            }
        }
        else
        {
            for (int i = 0;i < list.size();i++)
            {
                folder.addChildren(new TreeNode(list.get(i)).setViewHolder(new SelectableItemHolder(getActivity(), serviceName, serviceName)));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}