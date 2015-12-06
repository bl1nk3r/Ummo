package com.example.barnes.ummoqmasterdashboard.Dq;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.barnes.ummoqmasterdashboard.R;

import java.util.ArrayList;

/**
 * Created by barnes on 11/29/15.
 */
public class MyListActivity extends ListActivity
{
    public ArrayAdapter<String> createListAdapter()
    {
        return new MyListAdapter(this, getItems());
    }


    public static ArrayList<String> getItems()
    {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 1000; i++)
        {
            items.add(String.valueOf(i));
        }
        return items;
    }

    private static class MyListAdapter extends ArrayAdapter<String>
    {
        private Context mContext;

        public MyListAdapter(Context context, ArrayList<String> items)
        {
            super(items);
            mContext = context;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Button btn = (Button) convertView;
            //TextView tv = (TextView) convertView;
            if (btn == null)
            {
                btn = (Button) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
            }
            btn.setText("XSFSNDFG-" + getItem(position));
            return btn;
        }
    }
}
