package com.example.treeview_lib.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.treeview_lib.TreeNode;

public  abstract class MoveDocNodeViewTaoCVBinder extends BaseNodeViewTaoCVBinder{
    public MoveDocNodeViewTaoCVBinder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract int getCheckXLCViewId();

    public abstract int getCheckPHViewId();

    public abstract int getCheckXEMViewId();

    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}
