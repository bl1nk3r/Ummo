package com.example.barnes.ummo.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barnes.ummo.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.Random;

/**
 * Created by barnes on 12/14/15.
 */
public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>
{
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private String mValues;
    private int[] mMaterialColors;
    private int mType;

    private static final int PROVIDERS = 0;
    private static final int COUNTRIES = 1;
    private static final Random RANDOM = new Random();

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public String mBoundString;
        public final View mView;
        public final MaterialLetterIcon mIcon;
        public final TextView mTextView;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mIcon = (MaterialLetterIcon) view.findViewById(R.id.icon);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
        }

        @Override public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public String getValueAt(int position)
    {
        return mValues;
    }

    public SimpleStringRecyclerViewAdapter(Context context, String item, int type)
    {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mMaterialColors = context.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
        mValues = item;
        mType = type;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_icon_node, parent, false);
        //final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(final ViewHolder holder, int position)
    {
        switch (mType)
        {
            case PROVIDERS:
                holder.mIcon.setInitials(true);
                holder.mIcon.setInitialsNumber(4);
                holder.mIcon.setLetterSize(18);
                break;
            case COUNTRIES:
                holder.mIcon.setInitials(true);
                holder.mIcon.setLettersNumber(4);
                holder.mIcon.setLetterSize(16);
                break;
        }
        holder.mBoundString = mValues;
        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.mTextView.setText(mValues);
        holder.mIcon.setLetter(mValues);
    }
    @Override public int getItemCount()
    {
        return 1;
    }
}
