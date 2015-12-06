package com.example.barnes.ummoqmasterdashboard.Dq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.barnes.ummoqmasterdashboard.R;

/**
 * Created by barnes on 11/29/15.
 */
public class ContextualUndoActivity extends MyListActivity
{
    public ArrayAdapter<String> mAdapter = createListAdapter();
    ContextualUndoAdapter contextualUndoAdapter;
    ContextualUndoAdapter contextualUndoAdapter_;
    TextView undo_row;
    AbsListView abs;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewGroup parent = (ViewGroup) getWindow().getDecorView().findViewById(R.id.undo_id);
        LayoutInflater inflater = this.getLayoutInflater();
        View row = inflater.inflate(R.layout.undo_row, parent, false);
        contextualUndoAdapter = new ContextualUndoAdapter(mAdapter, R.layout.activity_persistent_drawer, R.id.undo_row_undobutton);
        contextualUndoAdapter_ = new ContextualUndoAdapter(mAdapter, R.layout.activity_persistent_drawer, R.id.undo_row_texttv);
        undo_row = (TextView)row.findViewById(R.id.undo_row_texttv);
        contextualUndoAdapter.setAbsListView(getListView());
        getListView().setAdapter(contextualUndoAdapter);
        contextualUndoAdapter.setDeleteItemCallback(new MyDeleteItemCallback());
    }

    private class MyDeleteItemCallback implements ContextualUndoAdapter.DeleteItemCallback
    {
        @Override
        public void deleteItem(int position)
        {
            String item = mAdapter.getItem(position);
            undo_row.setText(item);
            mAdapter.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }
}
