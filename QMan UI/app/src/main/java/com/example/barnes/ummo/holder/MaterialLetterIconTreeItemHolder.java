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
 * Created by barnes on 12/14/15.
 */
public class MaterialLetterIconTreeItemHolder extends TreeNode.BaseNodeViewHolder<MaterialLetterIconTreeItemHolder.IconTreeItem> {
    public TextView tvValue;
    private PrintView arrowView;
    String serviceProviderName;

    public MaterialLetterIconTreeItemHolder(Context context)
    {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItem value)
    {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        serviceProviderName = value.text.toString();
        int[] mMaterialColors;
        Random RANDOM = new Random();
        //final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        MaterialLetterIcon mIcon = (MaterialLetterIcon) view.findViewById(R.id.icon);
        mMaterialColors = view.getContext().getResources().getIntArray(R.array.colors);
        mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        mIcon.setInitials(true);
        mIcon.setInitialsNumber(1);
        mIcon.setLetterSize(18);
        mIcon.setLetter(serviceProviderName);
        //mIcon.setIconText(context.getResources().getString(value.icon));
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        view.findViewById(R.id.btn_addFolder).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TreeNode newFolder = new TreeNode(new IconTreeItem(serviceProviderName));
                getTreeView().addNode(node, newFolder);
            }
        });
        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getTreeView().removeNode(node);
            }
        });
        //if My computer
        if (node.getLevel() == 1)
        {
            view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void toggle(boolean active)
    {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem
    {
        public String text;
        public IconTreeItem(String text)
        {
            this.text = text;
        }
    }
}