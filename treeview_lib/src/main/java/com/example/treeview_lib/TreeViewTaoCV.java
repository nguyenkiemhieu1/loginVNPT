package com.example.treeview_lib;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.treeview_lib.animator.TreeItemAnimator;
import com.example.treeview_lib.base.BaseNodeViewTaoCVFactory;
import com.example.treeview_lib.base.SelectableTreeAction;

import java.util.List;

public class TreeViewTaoCV implements SelectableTreeAction {
    private TreeNode root;

    private Context context;

    private BaseNodeViewTaoCVFactory baseNodeViewFactory;

    private RecyclerView rootView;

    private TreeViewTaoCVAdapter adapter;

    private boolean itemSelectable = true;

    private RecyclerView.ItemAnimator itemAnimator;

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        if (rootView != null && itemAnimator != null) {
            rootView.setItemAnimator(itemAnimator);
        }
    }

    public TreeViewTaoCV(@NonNull TreeNode root, @NonNull Context context, @NonNull BaseNodeViewTaoCVFactory baseNodeViewFactory) {
        this.root = root;
        this.context = context;
        this.baseNodeViewFactory = baseNodeViewFactory;
        if (baseNodeViewFactory == null) {
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
        adapter = new TreeViewTaoCVAdapter(context, root, baseNodeViewFactory);
        adapter.setTreeView(this);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void selectNode(TreeNode treeNode) {

    }

    @Override
    public void deselectNode(TreeNode treeNode) {

    }

    @Override
    public void selectAll() {

    }

    @Override
    public void deselectAll() {

    }

    @Override
    public void settingViewTreeNode(TreeNode treeNode) {

    }

    @Override
    public void selectMultilXLC(int typeXLC) {

    }

    @Override
    public int positionSelect() {
        return 0;
    }

    @Override
    public void updatePositionSelect(TreeNode treeNode) {

    }

    @Override
    public List<TreeNode> getSelectedXLCNodes() {
        return null;
    }

    @Override
    public List<TreeNode> getSelectedPHNodes() {
        return null;
    }

    @Override
    public List<TreeNode> getSelectedXEMNodes() {
        return null;
    }

    @Override
    public void expandAll() {

    }

    @Override
    public void expandNode(TreeNode treeNode) {

    }

    @Override
    public void expandLevel(int level) {

    }

    @Override
    public void collapseAll() {

    }

    @Override
    public void collapseNode(TreeNode treeNode) {

    }

    @Override
    public void collapseLevel(int level) {

    }

    @Override
    public void toggleNode(TreeNode treeNode) {

    }

    @Override
    public void deleteNode(TreeNode node) {

    }

    @Override
    public void addNode(TreeNode parent, TreeNode treeNode) {

    }

    @Override
    public List<TreeNode> getAllNodes() {
        return null;
    }
}
