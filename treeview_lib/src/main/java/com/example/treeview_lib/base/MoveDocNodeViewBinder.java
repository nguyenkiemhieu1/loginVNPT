package com.example.treeview_lib.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.treeview_lib.TreeNode;

public abstract class MoveDocNodeViewBinder extends  BaseNodeViewBinder{
    public MoveDocNodeViewBinder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract int getCheckXLCViewId();

    public abstract int getCheckPHViewId();

    public abstract int getCheckXEMViewId();
    public abstract int getNodeNameViewId();

    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}
