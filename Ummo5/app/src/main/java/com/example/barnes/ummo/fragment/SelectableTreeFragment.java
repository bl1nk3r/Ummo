package com.example.barnes.ummo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barnes.ummo.Qs;
import com.example.barnes.ummo.R;
import com.example.barnes.ummo.SingleFragmentActivity;
import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.holder.IconTreeItemHolder;
import com.example.barnes.ummo.holder.ProfileHolder;
import com.example.barnes.ummo.holder.SelectableHeaderHolder_2;
import com.example.barnes.ummo.holder.SelectableItemHolder;
import com.software.shell.fab.ActionButton;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
        TreeNode root = TreeNode.root();
        String string = ((SingleFragmentActivity)getActivity()).getCategoriesJSON();
        try {
            ArrayList<TreeNode> treeNodeList = new ArrayList<>();
            ArrayList<TreeNode> treeNodeList2 = new ArrayList<>();
            ArrayList<JSONObject> serviceNameColl = new ArrayList<>();
            JSONArray array= new JSONArray(string);
            qServiceTypeList = new ArrayList<String>();
            for (int i =0; i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                String categoryName = object.getString("name");
                qServiceTypeList.add(categoryName);
                treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(categoryName,R.string.ic_sd_storage)).setViewHolder(new ProfileHolder(getActivity())));
                if (object.has("Providers")){
                    JSONArray providers = object.getJSONArray("Providers");
                    for (int j = 0; j < providers.length(); j++)
                    {
                        //Setting the Service Categories
                        JSONObject providerObj = providers.getJSONObject(j);
                        Log.d("object",providerObj.toString());
                        treeNodeList2.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(providerObj.getString("name"),R.string.ic_folder)).setViewHolder(new SelectableHeaderHolder_2(getActivity())));
                        treeNodeList.get(i).addChildren(treeNodeList2.get(j));
                       /* getServicePID = db.getQServicePid(qServiceProviderName.get(j));
                        string_serviceProviderid = getServicePID.get(0);
                        int_serviceProviderid = Integer.parseInt(string_serviceProviderid);
                        qServiceName = db.getQServiceName(int_serviceProviderid);
                         */

                        if (providerObj.has("Services")){
                            JSONArray services = providerObj.getJSONArray("Services");
                            
                            for (int k = 0; k < services.length(); k++) {
                                serviceNameColl.add(services.getJSONObject(k));


                            }

                            customfillFolder(treeNodeList2.get(j), serviceNameColl, "qServiceProviderName.get(i)");
                        }
                    }



                }

                serviceNameColl.clear();
                treeNodeList2.clear();
            }
            Log.d("List",array.toString());
            root.addChildren(treeNodeList);
        }

        catch (JSONException jse){
            Log.e("JSON Error",jse.toString());
        }
        actionButton = (ActionButton) rootView.findViewById(R.id.action_button);
        actionButton.setImageResource(R.drawable.fab_plus_icon);
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_light_blue_500));
        //actionButton.hide();
        db = new Db(rootView.getContext());
        db.open();
        qsJoined = db.getQsJoined();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SingleFragmentActivity) getActivity()).getUser().updateJoinedQs();
                if (qsJoined.size() > 0) {

                } else {
                    Toast.makeText(c, "You have not joined any ques yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // = db.getAllQServiceTypesName();



        int qServicesTypeSize = qServiceTypeList.size();



        int int_servicetypeid;
        String string_servicetypeid;
        int int_serviceProviderid;
        String string_serviceProviderid;

        List<String> getServiceTId;
        List<String> getServicePID;

        for (int i = 0;i < qServicesTypeSize;i++)
        {
            /*if (qServiceTypeList.get(i) == "Financial Services_")
            {
                treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(qServiceTypeList.get(i).toString(),R.drawable.currency9)).setViewHolder(new ProfileHolder(getActivity())));
            }
            else
            {
                treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(qServiceTypeList.get(i).toString(),R.string.ic_sd_storage)).setViewHolder(new ProfileHolder(getActivity())));
            }

            //string_servicetypeid = getServiceTId.get(0);//id for displayed q service type

            //int_servicetypeid = Integer.parseInt(string_servicetypeid);
            //qServiceProviderName = db.getQServiceProviderName(int_servicetypeid);

            */

        }



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

    private void customfillFolder(TreeNode folder, ArrayList<JSONObject> list, String serviceName)
    {
        List<String> qJoinedList = null;
        //qJoinedList = db.getQName(serviceName);
        try {
            if (true) {
                for (int i = 0; i < list.size(); i++) {
                    folder.addChildren(new TreeNode(list.get(i).getString("name")).setViewHolder(new SelectableItemHolder(getActivity(), list.get(i), "serviceName")));
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    folder.addChildren(new TreeNode(list.get(i)).setViewHolder(new SelectableItemHolder(getActivity(), list.get(i), "serviceName")));
                }
            }
        }
        catch(JSONException jse){
             Log.e("Json Error Passing Qs",jse.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}