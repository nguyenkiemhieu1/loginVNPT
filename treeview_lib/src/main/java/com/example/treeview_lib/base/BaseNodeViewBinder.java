package com.example.treeview_lib.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treeview_lib.TreeNode;
import com.example.treeview_lib.TreeView;

public abstract class BaseNodeViewBinder extends RecyclerView.ViewHolder {
    protected TreeView treeView;
    public void setTreeView(TreeView treeView) {
        this.treeView = treeView;
    }
    public BaseNodeViewBinder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract int getLayoutId();
    public abstract void bindView(TreeNode treeNode);
    public int getToggleTriggerViewId() {
        return 0;
    }
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        //empty
    }


}
