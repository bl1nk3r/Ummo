package com.example.barnes.ummoqmasterdashboard;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.octicons_typeface_library.Octicons;

/**
 * Created by barnes on 10/26/15.
 */
public class HomeDrawer extends AppCompatActivity
{
    private static final int PROFILE_SETTING = 1;
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedded);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.drawer_item_embedded_drawer);
        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withEmail("Deposit").withName("bane@ummo.xyz").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        final IProfile profile2 = new ProfileDrawerItem().withEmail("Withdrawals").withName("rego@ummo.xyz").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
        final IProfile profile3 = new ProfileDrawerItem().withEmail("Foreign Exchange").withName("mosaic@ummo.xyz").withIcon(getResources().getDrawable(R.drawable.profile2));
        final IProfile profile4 = new ProfileDrawerItem().withEmail("Bank Account Application").withName("prince@ummo.xyz").withIcon(getResources().getDrawable(R.drawable.profile3));
        final IProfile profile5 = new ProfileDrawerItem().withEmail("Forex").withName("bheki@ummo.xyz").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Que").withDescription("Add new Que").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(PROFILE_SETTING)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        Intent intent = null;
                        /*if (profile instanceof Nameable) {
                            if (profile.getName().getText(HomeDrawer.this).equals("Add Que")) {
                                intent = new Intent(HomeDrawer.this, KeyboardUtilActivity.class);
                            }
                        }*/
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING)
                        {
                            intent = new Intent(HomeDrawer.this, QCreateQForm.class);
                            //IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            /*if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }*/
                        }
                        if (intent != null)
                        {
                            HomeDrawer.this.startActivity(intent);
                        }
                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.q_length).withIcon(GoogleMaterial.Icon.gmd_wb_sunny).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.q_Tag).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.q_service_name).withIcon(FontAwesome.Icon.faw_gamepad).withBadge(headerResult.getActiveProfile().getEmail()).withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(3),
                        new PrimaryDrawerItem().withDescription("Documents required for service").withName(R.string.q_description).withIcon(FontAwesome.Icon.faw_eye).withBadge("National Id").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(4),
                        new SectionDrawerItem().withName(R.string.q_start_end_time_header),
                        new PrimaryDrawerItem().withDescription("Time when que is scheduled to be active").withName(R.string.q_start_time).withIcon(GoogleMaterial.Icon.gmd_adb).withBadge("8:00").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(5),
                        new PrimaryDrawerItem().withDescription("Time when que is scheduled to be be inactive").withName(R.string.q_end_time).withIcon(GoogleMaterial.Icon.gmd_adb).withBadge("17:00").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(5),
                        new SectionDrawerItem().withName(R.string.q_limit_header),
                        new ToggleDrawerItem().withName("Limit").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SecondaryDrawerItem().withName(R.string.q_limit).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withBadge("45").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withTag("Bullhorn"),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Active").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new PrimaryDrawerItem().withName(R.string.q_location).withIcon(GoogleMaterial.Icon.gmd_style).withBadge("Mbabane").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(6),
                        new PrimaryDrawerItem().withName(R.string.q_manage).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(7)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()
                {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
                    {
                        Intent intent = null;
                        if (drawerItem instanceof Nameable)
                        {
                            if (((Nameable) drawerItem).getName().getText(HomeDrawer.this).equals("Manage Que"))
                            {
                                intent = new Intent(HomeDrawer.this, ManageQDrawer.class);
                            }
                            Toast.makeText(HomeDrawer.this, ((Nameable) drawerItem).getName().getText(HomeDrawer.this), Toast.LENGTH_SHORT).show();
                        }
                        if (intent != null)
                        {
                            HomeDrawer.this.startActivity(intent);
                        }
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .buildView();
        ((ViewGroup) findViewById(R.id.frame_container)).addView(result.getSlider());
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked)
        {
            if (drawerItem instanceof Nameable)
            {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            }
            else
            {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed()
    {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen())
        {
            result.closeDrawer();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //handle the click on the back arrow click
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}