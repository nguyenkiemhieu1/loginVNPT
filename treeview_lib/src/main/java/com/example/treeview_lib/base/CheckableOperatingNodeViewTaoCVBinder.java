package com.example.treeview_lib.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.treeview_lib.TreeNode;

public  abstract class CheckableOperatingNodeViewTaoCVBinder extends BaseNodeViewTaoCVBinder {
    public CheckableOperatingNodeViewTaoCVBinder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract int getCheckableViewId();

    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}
