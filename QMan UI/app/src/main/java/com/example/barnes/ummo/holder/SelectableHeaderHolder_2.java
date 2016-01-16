package com.example.barnes.ummo.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.barnes.ummo.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

import java.util.Random;

/**
 * Created by barnes on 8/6/15.
 */
public class SelectableHeaderHolder_2 extends TreeNode.BaseNodeViewHolder<MaterialLetterIconTreeItemHolder.IconTreeItem>
{
    public TextView tvValue;
    private PrintView arrowView;
    String serviceProviderName;
    //private CheckBox nodeSelector;
    //public MaterialLetterIcon mIcon;
    public SelectableHeaderHolder_2(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, MaterialLetterIconTreeItemHolder.IconTreeItem value)
    {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_header, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);
        serviceProviderName = value.text.toString();
        int[] mMaterialColors;
        Random RANDOM = new Random();
        MaterialLetterIcon mIcon = (MaterialLetterIcon) view.findViewById(R.id.icon);
        //iconView.setIconText(context.getResources().getString(value.icon));
        mMaterialColors = view.getContext().getResources().getIntArray(R.array.colors);
        mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        mIcon.setInitials(true);
        mIcon.setInitialsNumber(1);
        mIcon.setLetterSize(18);
        mIcon.setLetter(serviceProviderName);
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        if (node.isLeaf())
        {
            arrowView.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void toggle(boolean active)
    {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled)
    {
    }
}