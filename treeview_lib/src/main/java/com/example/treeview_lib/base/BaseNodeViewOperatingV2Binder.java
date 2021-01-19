package com.example.treeview_lib.base;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treeview_lib.TreeNode;
import com.example.treeview_lib.TreeViewOperatingV2;

public abstract class BaseNodeViewOperatingV2Binder extends RecyclerView.ViewHolder {
    protected TreeViewOperatingV2 treeViewOperatingV2;

    public BaseNodeViewOperatingV2Binder(@NonNull View itemView) {
        super(itemView);
    }
    public void setTreeView(TreeViewOperatingV2 treeViewOperatingV2) {
        this.treeViewOperatingV2 = treeViewOperatingV2;
    }

    public abstract int getLayoutId();

    public abstract void bindView(TreeNode treeNode, Context context);

    public int getToggleTriggerViewId() {
        return 0;
    }

    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        //empty
    }
}
