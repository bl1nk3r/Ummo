package com.example.barnes.ummoqmasterdashboard.Dq;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * Created by barnes on 11/29/15.
 */
public class BaseAdapterDecorator extends BaseAdapter
{
    protected final BaseAdapter mDecoratedBaseAdapter;

    private AbsListView mListView;

    public BaseAdapterDecorator(BaseAdapter baseAdapter) {
        mDecoratedBaseAdapter = baseAdapter;
    }

    @Deprecated
    /**
     * @deprecated use setAbsListView(AbsListView) instead.
     */
    public void setListView(AbsListView listView)
    {
        mListView = listView;

        if (mDecoratedBaseAdapter instanceof BaseAdapterDecorator)
        {
            ((BaseAdapterDecorator) mDecoratedBaseAdapter).setListView(listView);
        }
    }

    public void setAbsListView(AbsListView listView)
    {
        mListView = listView;
        if (mDecoratedBaseAdapter instanceof BaseAdapterDecorator)
        {
            ((BaseAdapterDecorator) mDecoratedBaseAdapter).setAbsListView(listView);
        }
    }

    @Deprecated
    public AbsListView getListView(){
        return mListView;
    }

    public AbsListView getAbsListView() {
        return mListView;
    }

    @Override
    public int getCount() {
        return mDecoratedBaseAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mDecoratedBaseAdapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mDecoratedBaseAdapter.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return mDecoratedBaseAdapter.getView(position, convertView, parent);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mDecoratedBaseAdapter.areAllItemsEnabled();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return mDecoratedBaseAdapter.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position)
    {
        return mDecoratedBaseAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return mDecoratedBaseAdapter.getViewTypeCount();
    }

    @Override
    public boolean hasStableIds() {
        return mDecoratedBaseAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return mDecoratedBaseAdapter.isEmpty();
    }

    @Override
    public boolean isEnabled(int position) {
        return mDecoratedBaseAdapter.isEnabled(position);
    }

    @Override
    public void notifyDataSetChanged() {
        mDecoratedBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        mDecoratedBaseAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {
        mDecoratedBaseAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
        mDecoratedBaseAdapter.unregisterDataSetObserver(observer);
    }

    public BaseAdapter getDecoratedBaseAdapter() {
        return mDecoratedBaseAdapter;
    }
}
