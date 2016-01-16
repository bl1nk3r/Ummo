package com.example.barnes.ummo.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.barnes.ummo.Main_Activity;
import com.example.barnes.ummo.MyAdapter;
import com.example.barnes.ummo.R;
import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.holder.IconTreeItemHolder;
import com.example.barnes.ummo.holder.MaterialLetterIconTreeItemHolder;
import com.example.barnes.ummo.holder.ProfileHolder;
import com.example.barnes.ummo.holder.SelectableHeaderHolder_2;
import com.example.barnes.ummo.holder.SelectableItemHolder;
import com.example.barnes.ummo.ummoAPI.QUser;
import com.example.barnes.ummo.ummoAPI.QUserListner;
import com.github.fabtransitionactivity.SheetLayout;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by barnes on 8/6/15.
 */

public class SelectableTreeFragment extends BaseActivity implements SheetLayout.OnFabAnimationEndListener, QUserListner
{
    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are holded in an Array as you can see
    String TITLES[] = {"Home","Frequently Used Qs","Tokens","Request Uber","Map view"};
    int ICONS[] = {R.drawable.ic_home,R.drawable.ic_frequency,R.drawable.ic_tokens,R.drawable.uber,R.drawable.ic_map};
    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view
    int PROFILE = R.drawable.cole;
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;

    @Bind(R.id.bottom_sheet) SheetLayout mSheetLayout;
    @Bind(R.id.fab) FloatingActionButton mFab;
    private AndroidTreeView tView;
    Db db;
    public List<String> qServiceTypeList = null;
    public List<String> qServiceProviderName = null;
    public List<String> qServiceName = null;
    public List<String> qsJoined = null;
    //ActionButton actionButton;
    Context c;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REQUEST_CODE = 1;
    Activity activity;
    private QUser user;
    private vQ selectedQ;
    private String categoriesJSON;
    private String qsJSON;
    private String qJSON;
    private String joinedQsJSON;
    private String serviceProvidersJSON;
    //Some setters and getters for qs
    public void setSelectedQ(vQ vq)
    {
        selectedQ=vq;
    }
    public String getJoinedQsJSON()
    {
        return joinedQsJSON;
    }

    public void joinSelectedQ()
    {
        user.joinQ(selectedQ.getCellNumb());
    }
    public void setQinfoDialog(SelectableItemHolder info)
    {
        selectedQ.setInfo(info);
    }
    public vQ getSelectedQ()
    {
        return selectedQ;
    }
    public void setSelectedQ(String cell)
    {
        selectedQ = new vQ();
        selectedQ.setCellNumb(cell);
    }
    public void setSelectedQ(JSONObject obj)
    {
        selectedQ = new vQ();
        selectedQ.setFromJSON(obj);
    }
    //end setters and getters for qs
    public QUser getUser(){return user;}
    //Overriding for QUserListener Begins Here
    //sucess Handlers
    @Override
    public void gotJoinedQs(String string)
    {
        Log.d("JoinedQs",string);
    }
    @Override
    public void qReady(String string)
    {
        qJSON=string;
        Log.d("QDATA",string);
        try
        {
            JSONObject object = new JSONObject(qJSON);
            selectedQ.getInfo().dialog(qJSON);
        }
        catch (JSONException jse)
        {
            Log.d("Error",jse.toString());
        }
    }
    @Override
    public void qError(String err)
    {
    }
    public String getCategoriesJSON()
    {
        return categoriesJSON;
    }
    public String getQsJSON()
    {
        return qsJSON;
    }
    public String getServiceProvidersJSON()
    {
        return serviceProvidersJSON;
    }
    public final static String FRAGMENT_PARAM = "fragment";
    @Override
    public void userRegistered(String string)
    {
    }
    @Override
    public void qJoined(String string)
    {
    }
    @Override
    public void qLeft(String string)
    {
    }

    /*@Override
    public void updated(String string, String position) {

    }*/

    @Override
    public void updated(String string)
    {
        Log.d("joinedQs",string);
        try
        {
            //JSONObject object = new JSONObject(string);
            JSONArray JSONqs = new JSONArray(string);
            joinedQsJSON = JSONqs.toString();
            if(JSONqs.length() > 0)
            {
                Intent i = new Intent();
                i.putExtra("joinedQs",joinedQsJSON);
                //i.putExtra("qpos");
                i.setClass(this, Main_Activity.class);
                this.startActivity(i);
            }
        }
        catch (JSONException jse)
        {
            Log.e("JoinedQs",jse.toString());
        }
    }

    @Override
    public void categoriesReady(String string)
    {
        categoriesJSON = string;
      /*  Bundle b = getIntent().getExtras();
        Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);
        Fragment f = (Fragment)(new SelectableTreeFragment());//Fragment.instantiate(this, SelectableTreeFragment.class);
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();
        //  user.makeNotification();
        //Intent i = new Intent(SingleFragmentActivity.this, SelectableTreeFragment.class);
        //i.putExtra("message", b);
        //startActivity(i);*/
    }
    @Override
    public void allQsReady(String string)
    {
        qsJSON = string;
        Log.d("qs",string);
    }
    //Error Handlers
    @Override
    public void joinedQsError(String err)
    {
    }
    @Override
    public void userRegistrationError(String err)
    {
    }
    @Override
    public void qJoinedError(String err)
    {
    }
    @Override
    public void qLeftError(String err)
    {
    }
    @Override
    public void updateError(String err)
    {
    }
    @Override
    public void categoriesError(String err)
    {
    }
    @Override
    public void allQError(String err)
    {
    }
    //End Overriding for QUserListener
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_selectable_nodes);
        user = new QUser(this);
        if(user==null)
        {
            Log.e("USER","User Is Null");
        }
        else
        {
            Log.e("USER:",user.getName());
        }

        String NAME = user.getName();
        String EMAIL = "+268 "+user.getCellNumb();

        /* Assinging the toolbar object ot the view
        and setting the the Action bar to our toolbar
        */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);
        //int width = getResources().getDisplayMetrics().widthPixels/2;// Letting the system know that the list objects are of fixed size
        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,this);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture
        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        int width = getResources().getDisplayMetrics().widthPixels - 100;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mRecyclerView.getLayoutParams();
        params.width = width;
        mRecyclerView.setLayoutParams(params);

        //final View rootView = inflater.inflate(R.layout.fragment_selectable_nodes, null, false);
        final ViewGroup containerView = (ViewGroup) findViewById(R.id.container);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        ButterKnife.bind(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        //toolbar.setTitle("mmo");
        //toolbar.setLogo(R.mipmap.ummo_logo);
        //setUpToolbarWithTitle("Ummo", false);
        //setUpToolbarWithTitle(getString(R.string.app_name), false);
        //setActionBar();
        //getSupportActionBar().setIcon(R.mipmap.ummo_logo);
        //mFab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab);
        mSheetLayout.setFab(mFab);
        mFab.setImageResource(R.drawable.fab_plus_icon);
        mSheetLayout.setFabAnimationEndListener(this);
        //mSheetLayout.setFab(mFab);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefCategory);
        //swipeRefreshLayout.setOnRefreshListener(this.);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
            }
        });
        activity = this;
        Intent intent = getIntent();
        containerView.removeAllViews();
        String mess = intent.getStringExtra("categoriesJSON");
        //Log.e("Greeting",mess);
        c = SelectableTreeFragment.this;
        TreeNode root = TreeNode.root();

        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        RelativeLayout list = (RelativeLayout) findViewById(R.id.container);

        mFab.attachToScrollView(scrollView);

        String string = mess;
        try
        {
            ArrayList<TreeNode> treeNodeList = new ArrayList<>();
            ArrayList<TreeNode> treeNodeList2 = new ArrayList<>();
            ArrayList<JSONObject> serviceNameColl = new ArrayList<>();
            JSONArray array= new JSONArray(string);
            qServiceTypeList = new ArrayList<String>();
            for (int i =0; i<array.length();i++)
            {
                JSONObject object = array.getJSONObject(i);
                String categoryName = object.getString("name");
                qServiceTypeList.add(categoryName);
                if (categoryName.equals("Governmental"))
                {
                    treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(categoryName,R.string.fa_globe)).setViewHolder(new ProfileHolder(activity)));
                }
                else if(categoryName.equals("Financial"))
                {
                    treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(categoryName,R.string.fa_credit_card)).setViewHolder(new ProfileHolder(activity)));
                }
                else if(categoryName.equals("Entertainment"))
                {
                    treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(categoryName,R.string.fa_music)).setViewHolder(new ProfileHolder(activity)));
                }
                else if(categoryName.equals("Medical"))
                {
                    treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(categoryName,R.string.fa_stethoscope)).setViewHolder(new ProfileHolder(activity)));
                }
                else if(categoryName.equals("Miscellaneous"))
                {
                    treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(categoryName,R.string.fa_sort_alpha_desc)).setViewHolder(new ProfileHolder(activity)));
                }
                else
                {
                    treeNodeList.add(new TreeNode(new IconTreeItemHolder.IconTreeItem(categoryName, R.string.ic_sd_storage)).setViewHolder(new ProfileHolder(activity)));
                }
                if (object.has("Providers"))
                {
                    JSONArray providers = object.getJSONArray("Providers");
                    for (int j = 0; j < providers.length(); j++)
                    {
                        //Setting the Service Categories
                        JSONObject providerObj = providers.getJSONObject(j);
                        Log.d("object",providerObj.toString());
                        treeNodeList2.add(new TreeNode(new MaterialLetterIconTreeItemHolder.IconTreeItem(providerObj.getString("name"))).setViewHolder(new SelectableHeaderHolder_2(activity)));
                        treeNodeList.get(i).addChildren(treeNodeList2.get(j));
                       /* getServicePID = db.getQServicePid(qServiceProviderName.get(j));
                        string_serviceProviderid = getServicePID.get(0);
                        int_serviceProviderid = Integer.parseInt(string_serviceProviderid);
                        qServiceName = db.getQServiceName(int_serviceProviderid);
                         */
                        if (providerObj.has("Services"))
                        {
                            JSONArray services = providerObj.getJSONArray("Services");
                            for (int k = 0; k < services.length(); k++)
                            {
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
        catch (JSONException jse)
        {
            Log.e("JSON Error",jse.toString());
        }
        //actionButton = (ActionButton) rootView.findViewById(R.id.action_button);
        //actionButton.setImageResource(R.drawable.fab_plus_icon);
        //actionButton.setButtonColor(getResources().getColor(R.color.fab_material_light_blue_500));
        //actionButton.hide();
        db = new Db(SelectableTreeFragment.this);
        db.open();
        qsJoined = db.getQsJoined();
                /*actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((SingleFragmentActivity) getActivity()).getUser().updateJoinedQs();
                        if (qsJoined.size() > 0) {

                        } else {
                            Toast.makeText(c, "You have not joined any ques yet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
        // = db.getAllQServiceTypesName();
        //int qServicesTypeSize = qServiceTypeList.size();
        int int_servicetypeid;
        String string_servicetypeid;
        int int_serviceProviderid;
        String string_serviceProviderid;

        List<String> getServiceTId;
        List<String> getServicePID;

        //for (int i = 0;i < qServicesTypeSize;i++)
        //{
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
        //}
        tView = new AndroidTreeView(SelectableTreeFragment.this, root);
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
    }

    //activity.registerReceiver(broadcastReceiver, new IntentFilter("com.example.barnes.ummo.CATEGORIES"));
    //return rootView;


    private void customfillFolder(TreeNode folder, ArrayList<JSONObject> list, String serviceName)
    {
        List<String> qJoinedList = null;
        //qJoinedList = db.getQName(serviceName);
        try
        {
            if (true)
            {
                for (int i = 0; i < list.size(); i++)
                {
                    folder.addChildren(new TreeNode(list.get(i).getString("name")).setViewHolder(new SelectableItemHolder(activity, list.get(i), "serviceName")));
                }
            }
            else
            {
                for (int i = 0; i < list.size(); i++)
                {
                    folder.addChildren(new TreeNode(list.get(i)).setViewHolder(new SelectableItemHolder(activity, list.get(i), "serviceName")));
                }
            }
        }
        catch(JSONException jse)
        {
            Log.e("Json Error Passing Qs",jse.toString());
        }
    }

    @OnClick(R.id.fab)
    void onFabClick()
    {
        mSheetLayout.expandFab();
    }

    @Override
    public void onFabAnimationEnd()
    {
        Intent intent = new Intent(this, Main_Activity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            mSheetLayout.contractFab();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("com.example.barnes.ummo.CATEGORIES"));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            Log.e("receiver", "Got message: " + message);
        }
    };
}

class vQ{
    private String cellNumb;
    private SelectableItemHolder info;
    private String qLocation;
    private int qlength;
    private int ttdqMin;
    private int ttdqSec;

    public void setInfo(SelectableItemHolder info1){
        info=info1;
    }

    public SelectableItemHolder getInfo(){
        return info;
    }

    public vQ()
    {
    }

    public void calculateStats()
    {
    }

    public String getCellNumb(){
        return cellNumb;
    }

    public void setFromJSON(JSONObject vq){
        Log.e("cellNumber",cellNumb);
        info.dialog(vq.toString());
    }

    public void setCellNumb(String cellNumb1){
        cellNumb = cellNumb1;
    }
}