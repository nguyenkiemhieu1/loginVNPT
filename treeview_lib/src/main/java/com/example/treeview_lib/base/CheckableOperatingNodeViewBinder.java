package com.example.treeview_lib.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.treeview_lib.TreeNode;

public abstract class CheckableOperatingNodeViewBinder extends BaseNodeViewBinder {
    public CheckableOperatingNodeViewBinder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract int getCheckableViewId();


    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}
