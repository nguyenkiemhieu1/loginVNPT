package com.example.treeview_lib.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.treeview_lib.TreeNode;

public abstract class CheckableOperatingV2NodeViewBinder extends BaseNodeViewOperatingV2Binder{
    public CheckableOperatingV2NodeViewBinder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract int getCheckableViewId();

    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}
