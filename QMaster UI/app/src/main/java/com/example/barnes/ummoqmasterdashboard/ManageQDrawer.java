package com.example.barnes.ummoqmasterdashboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barnes.ummoqmasterdashboard.utils.CrossfadeWrapper;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;
import com.mikepenz.octicons_typeface_library.Octicons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barnes on 10/26/15.
 */
public class ManageQDrawer extends AppCompatActivity
{
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private Crossfader crossFader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistent_drawer);
        init((ListView) findViewById(R.id.list_view));
        //Remove line to test RTL support
        // getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //example how to implement a persistentDrawer as shown in the google material design guidelines
        //https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0Bx4BSt6jniD7YVdKQlF3TEo2S3M/patterns_navdrawer_behavior_persistent2.png
        //https://www.google.com/design/spec/patterns/navigation-drawer.html#navigation-drawer-behavior

        // Handle Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_persistent_compact_header);

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withEmail("Deposit").withName("bane@ummo.xyz").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        final IProfile profile2 = new ProfileDrawerItem().withEmail("Withdrawals").withName("rego@ummo.xyz").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
        final IProfile profile3 = new ProfileDrawerItem().withEmail("Foreign Exchange").withName("mosaic@ummo.xyz").withIcon(getResources().getDrawable(R.drawable.profile2));
        final IProfile profile4 = new ProfileDrawerItem().withEmail("Bank Account Application").withName("prince@ummo.xyz").withIcon(getResources().getDrawable(R.drawable.profile3));
        final IProfile profile5 = new ProfileDrawerItem().withEmail("Forex").withName("bheki@ummo.xyz").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(new ColorDrawable(Color.parseColor("#FDFDFD")))
                .withHeightPx(UIUtils.getActionBarHeight(this))
                .withAccountHeader(R.layout.material_drawer_compact_persistent_header)
                .withTextColor(Color.BLACK)
                .addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.q_length).withIcon(GoogleMaterial.Icon.gmd_wb_sunny).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.q_Tag).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.q_service_name).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withDescription("Documents required for service").withName(R.string.q_description).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                        new SectionDrawerItem().withName(R.string.q_start_end_time_header),
                        new PrimaryDrawerItem().withDescription("Time when que is scheduled to be active").withName(R.string.q_start_time).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                        new PrimaryDrawerItem().withDescription("Time when que is scheduled to be be inactive").withName(R.string.q_end_time).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                        new SectionDrawerItem().withName(R.string.q_limit_header),
                        new ToggleDrawerItem().withName("Limit").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SecondaryDrawerItem().withName(R.string.q_limit).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn"),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Active").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new PrimaryDrawerItem().withName(R.string.q_location).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(6),
                        new PrimaryDrawerItem().withName(R.string.q_manage).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(7)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        if (drawerItem instanceof Nameable) {
                            if (((Nameable) drawerItem).getName().getText(ManageQDrawer.this).equals("Manage Que")) {
                                intent = new Intent(ManageQDrawer.this, ManageQDrawer.class);
                            }
                            Toast.makeText(ManageQDrawer.this, ((Nameable) drawerItem).getName().getText(ManageQDrawer.this), Toast.LENGTH_SHORT).show();
                        }
                        if (intent != null) {
                            ManageQDrawer.this.startActivity(intent);
                        }
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState);

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
        result = builder.buildView();
        // create the MiniDrawer and define the drawer and header to be used (it will automatically use the items from them)
        miniResult = new MiniDrawer()
                .withDrawer(result)
                .withIncludeSecondaryDrawerItems(true)
                .withAccountHeader(headerResult);

        //get the widths in px for the first and second panel
        int firstWidth = (int) com.mikepenz.crossfader.util.UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) com.mikepenz.crossfader.util.UIUtils.convertDpToPixel(72, this);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        crossFader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define and create the arrow ;)
        ImageView toggle = (ImageView) headerResult.getView().findViewById(R.id.material_drawer_account_header_toggle);
        //for RTL you would have to define the other arrow
        toggle.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_keyboard_arrow_left).sizeDp(16).color(Color.BLACK));
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crossFader.crossFade();
            }
        });
    }

    private void init(ListView listView) {
        final MyBaseAdapter adapter = new MyBaseAdapter();
        listView.setAdapter(adapter);
        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(listView),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                adapter.remove(position);
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    //Toast.makeText(ManageQDrawer.this, "Position " + position, LENGTH_SHORT).show();
                }
            }
        });
    }

    static class MyBaseAdapter extends BaseAdapter {

        private static final int SIZE = 100;

        private final List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter() {
            for (int i = 0; i < SIZE; i++)
                mDataSet.add(i, "This is row number " + i);
        }

        @Override
        public int getCount() {
            return mDataSet.size();
        }

        @Override
        public String getItem(int position) {
            return mDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void remove(int position) {
            mDataSet.remove(position);
            notifyDataSetChanged();
        }

        static class ViewHolder {
            TextView dataTextView;
            ViewHolder(View view) {
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
                view.setTag(this);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = convertView == null
                    ? new ViewHolder(convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false))
                    : (ViewHolder) convertView.getTag();

            viewHolder.dataTextView.setText(mDataSet.get(position));
            return convertView;
        }
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        //add the values which need to be saved from the crossFader to the bundle
        outState = crossFader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
