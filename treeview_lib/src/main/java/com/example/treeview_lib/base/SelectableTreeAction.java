package com.example.treeview_lib.base;

import com.example.treeview_lib.TreeNode;

import java.util.List;

public interface SelectableTreeAction extends BaseTreeAction{
    void selectNode(TreeNode treeNode);

    void deselectNode(TreeNode treeNode);

    void selectAll();

    void deselectAll();
    void settingViewTreeNode(TreeNode treeNode);
    void selectMultilXLC(int typeXLC);
    int positionSelect();
    void updatePositionSelect(TreeNode treeNode);

    List<TreeNode> getSelectedXLCNodes();
    List<TreeNode> getSelectedPHNodes();
    List<TreeNode> getSelectedXEMNodes();

}
