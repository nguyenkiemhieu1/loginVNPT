package com.example.treeview_lib.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treeview_lib.TreeNode;
import com.example.treeview_lib.TreeViewTaoCV;

public abstract class BaseNodeViewTaoCVBinder  extends RecyclerView.ViewHolder {
    protected TreeViewTaoCV treeView;

    public BaseNodeViewTaoCVBinder(@NonNull View itemView) {
        super(itemView);
    }
    public void setTreeView(TreeViewTaoCV treeView) {
        this.treeView = treeView;
    }

    public abstract int getLayoutId();

    public abstract void bindView(TreeNode treeNode);

    public int getToggleTriggerViewId() {
        return 0;
    }

    public void onNodeToggled(TreeNode treeNode, boolean expand) {}
}
