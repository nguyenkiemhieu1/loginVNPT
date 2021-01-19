package com.example.treeview_lib.animator;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TreeItemAnimator  extends DefaultItemAnimator{
    public boolean animateAdd(RecyclerView.ViewHolder holder){
        super.animateAdd(holder);
        ViewCompat.setAlpha(holder.itemView, 1);
        return  true;
    }
}
