package com.example.barnes.ummo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.expandable.view.ExpandableView;

/**
 * Created by barnes on 8/1/15.
 */
public class ExpandableList_MainActivity extends AppCompatActivity
{
    private ExpandableView middleExpandableView;
    private ExpandableView expandableViewLevel1;
    private ExpandableView expandableViewLevel2;
    private ExpandableView expandableViewLevel3;
    private ExpandableView expandableViewLevel4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandablelist_qservice_provider);

        final Context c = this;
        middleExpandableView = (ExpandableView) findViewById(R.id.activity_main_middle_expandable_view);

        expandableViewLevel1 = new ExpandableView(this);
        expandableViewLevel2 = new ExpandableView(this);
        expandableViewLevel3 = new ExpandableView(this);
        expandableViewLevel4 = new ExpandableView(this);

        createMiddleExpandableView();

        createInnerExpandableViewLevel1();
        createInnerExpandableViewLevel2();
        createInnerExpandableViewLevel3();
        createInnerExpandableViewLevel4();

        expandableViewLevel1.setOutsideContentLayout(middleExpandableView.getContentLayout());
        expandableViewLevel2.setOutsideContentLayout(middleExpandableView.getContentLayout());
        expandableViewLevel3.setOutsideContentLayout(middleExpandableView.getContentLayout());
        expandableViewLevel4.setOutsideContentLayout(middleExpandableView.getContentLayout());
    }

    public void addContentView(ExpandableView view, String[] stringList, boolean showCheckbox)
    {
        for (int i = 0; i < stringList.length; i++)
        {
            ExpandedListItemView itemView = new ExpandedListItemView(this);
            itemView.setText(stringList[i], showCheckbox);
            view.addContentView(itemView);
        }
    }

    private void createMiddleExpandableView()
    {
        String[] androidVersionNameList = getResources().getStringArray(R.array.android_version_names);

        middleExpandableView.fill_Data("Financial Services", false);
        middleExpandableView.setVisibleLayoutHeight(getResources().getDimensionPixelSize(R.dimen.new_visible_height));

        //middleExpandableView.setTag("View");
        middleExpandableView.addContentView(expandableViewLevel1);
        middleExpandableView.addContentView(expandableViewLevel2);
        middleExpandableView.addContentView(expandableViewLevel3);
        middleExpandableView.addContentView(expandableViewLevel4);
        //middleExpandableView.setBackgroundResource(R.color.appleWhite);
        addContentView(middleExpandableView, androidVersionNameList, false);
    }

    private void createInnerExpandableViewLevel1()
    {
        String[] androidVersionNumberList = getResources().getStringArray(R.array.android_version_numbers);

        expandableViewLevel1.setBackgroundResource(R.color.appleWhite);
        expandableViewLevel1.fill_Data("FNB", true);
        addContentView(expandableViewLevel1, androidVersionNumberList, true);
        //expandableViewLevel1.addContentView(expandableViewLevel2);
    }

    private void createInnerExpandableViewLevel2()
    {
        String[] androidVersionNameList = getResources().getStringArray(R.array.swazibank);

        expandableViewLevel2.setBackgroundResource(R.color.appleWhite);
        expandableViewLevel2.fill_Data("Swazi Bank", true);
        addContentView(expandableViewLevel2, androidVersionNameList, true);
        //expandableViewLevel2.addContentView(expandableViewLevel3);
    }

    private void createInnerExpandableViewLevel3()
    {
        String[] androidVersionNumberList = getResources().getStringArray(R.array.nedbank);

        expandableViewLevel3.setBackgroundResource(R.color.appleWhite);
        expandableViewLevel3.fill_Data("Nedbank", true);
        addContentView(expandableViewLevel3, androidVersionNumberList, true);
    }

    private void createInnerExpandableViewLevel4()
    {
        String[] androidVersionNumberList = getResources().getStringArray(R.array.sra);

        expandableViewLevel4.setBackgroundResource(R.color.appleWhite);
        expandableViewLevel4.fill_Data("SRA", true);
        addContentView(expandableViewLevel4, androidVersionNumberList, true);
        //expandableViewLevel1.addContentView(expandableViewLevel2);
    }
}
