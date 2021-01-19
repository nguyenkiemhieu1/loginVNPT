package com.example.treeview_lib;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.treeview_lib.animator.TreeItemAnimator;
import com.example.treeview_lib.base.BaseNodeViewOperatingV2Factory;
import com.example.treeview_lib.base.SelectableTreeAction;
import com.example.treeview_lib.helper.TreeHelper;

import java.util.List;

public class TreeViewOperatingV2 implements SelectableTreeAction {

    private TreeNode root;

    private Context context;

    private BaseNodeViewOperatingV2Factory baseNodeViewOperatingV2Factory;

    private RecyclerView rootView;

    private TreeViewOperatingAdapterV2 adapter;
    private RecyclerView.ItemAnimator itemAnimator;

    private boolean itemSelectable = true;
    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        if (rootView != null && itemAnimator != null) {
            rootView.setItemAnimator(itemAnimator);
        }
    }

    public TreeViewOperatingV2(@NonNull TreeNode root, @NonNull Context context, @NonNull BaseNodeViewOperatingV2Factory baseNodeViewOperatingV2Factory) {
        this.root = root;
        this.context = context;
        this.baseNodeViewOperatingV2Factory = baseNodeViewOperatingV2Factory;
        if (baseNodeViewOperatingV2Factory == null) {
            throw new IllegalArgumentException("You must assign a BaseNodeViewFactory!");
        }
    }

    public View getView() {
        if (rootView == null) {
            this.rootView = buildRootView();
        }

        return rootView;
    }

    @NonNull
    private RecyclerView buildRootView() {
        RecyclerView recyclerView = new RecyclerView(context);
        /**
         * disable multi touch event to prevent terrible data set error when calculate list.
         */
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(itemAnimator != null ? itemAnimator : new TreeItemAnimator());
        SimpleItemAnimator itemAnimator = (SimpleItemAnimator) recyclerView.getItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TreeViewOperatingAdapterV2(context, root, baseNodeViewOperatingV2Factory);
        adapter.setTreeView(this);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }



    @Override
    public void selectNode(TreeNode treeNode) {
        if (treeNode != null) {
            adapter.selectNode(true, treeNode);
        }

    }

    @Override
    public void deselectNode(TreeNode treeNode) {
        if (treeNode != null) {
            adapter.selectNode(false, treeNode);
        }

    }

    @Override
    public void selectAll() {
        TreeHelper.selectNodeAndChild(root, true);

        refreshTreeView();


    }

    @Override
    public void deselectAll() {
        TreeHelper.selectNodeAndChild(root, false);

        refreshTreeView();

    }

    @Override
    public void settingViewTreeNode(TreeNode treeNode) {
        TreeHelper.filterNodeInner(root,false);
        TreeHelper.filterNodeInner(treeNode,true);
        refreshTreeView();

    }

    @Override
    public void selectMultilXLC(int typeXLC) {
        adapter.setTypeXLC(typeXLC);


    }

    @Override
    public int positionSelect() {
        return adapter.positiveXLC();
    }

    @Override
    public void updatePositionSelect(TreeNode treeNode) {

            adapter.updatePositiveXLC(treeNode);
    }

    @Override
    public List<TreeNode> getSelectedXLCNodes() {

            return TreeHelper.getSelectedNodes(root);
    }

    @Override
    public List<TreeNode> getSelectedPHNodes() {
        return TreeHelper.getSelectedPHNodes(root);
    }

    @Override
    public List<TreeNode> getSelectedXEMNodes() {
        return TreeHelper.getSelectedXEMNodes(root);
    }

    @Override
    public void expandAll() {
        if (root == null) {
            return;
        }
        TreeHelper.expandAll(root);

        refreshTreeView();

    }
    public void refreshTreeView() {
        if (rootView != null) {
            ((TreeViewOperatingAdapterV2) rootView.getAdapter()).refreshView();
        }
    }

    @Override
    public void expandNode(TreeNode treeNode) {
        adapter.expandNode(treeNode);

    }

    @Override
    public void expandLevel(int level) {
        TreeHelper.expandLevel(root, level);

        refreshTreeView();

    }

    @Override
    public void collapseAll() {
        if (root == null) {
            return;
        }
        TreeHelper.collapseAll(root);

        refreshTreeView();

    }

    @Override
    public void collapseNode(TreeNode treeNode) {
        adapter.collapseNode(treeNode);

    }

    @Override
    public void collapseLevel(int level) {
        TreeHelper.collapseLevel(root, level);

        refreshTreeView();


    }

    @Override
    public void toggleNode(TreeNode treeNode) {
        if (treeNode.isExpanded()) {
            collapseNode(treeNode);
        } else {
            expandNode(treeNode);
        }

    }

    @Override
    public void deleteNode(TreeNode node) {
        adapter.deleteNode(node);

    }

    @Override
    public void addNode(TreeNode parent, TreeNode treeNode) {
        parent.addChild(treeNode);

        refreshTreeView();

    }

    @Override
    public List<TreeNode> getAllNodes() {
        return TreeHelper.getAllNodes(root);
    }

    public boolean isItemSelectable() {
        return itemSelectable;
    }

    public void setItemSelectable(boolean itemSelectable) {
        this.itemSelectable = itemSelectable;
    }
}
