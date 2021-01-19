package com.example.treeview_lib;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
     private  int level;
     private  Object value;
     private TreeNode parent;
     private List<TreeNode> children;
     private  int index;
    private boolean expanded;

    private boolean selectedXLC;
    private boolean selectedPH;
    private boolean selectedXEM;
    private boolean isEnableShow = true;

    private boolean itemClickEnable = true;
    public TreeNode(Object value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
    public static TreeNode root() {
        TreeNode treeNode = new TreeNode(null);
        return treeNode;
    }

    public void addChild(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        children.add(treeNode);
        treeNode.setIndex(getChildren().size());
        treeNode.setParent(this);
    }


    public void removeChild(TreeNode treeNode) {
        if (treeNode == null || getChildren().size() < 1) {
            return;
        }
        if (getChildren().indexOf(treeNode) != -1) {
            getChildren().remove(treeNode);
        }
    }

    public boolean isLastChild() {
        if (parent == null) {
            return false;
        }
        List<TreeNode> children = parent.getChildren();
        return children.size() > 0 && children.indexOf(this) == children.size() - 1;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children;
    }
    public boolean hasChild() {
        return children.size() > 0;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public List<TreeNode> getSelectedChildren() {
        List<TreeNode> selectedChildren = new ArrayList<>();
        for (TreeNode child : getChildren()) {
            if (child.isSelectedXLC()) {
                selectedChildren.add(child);
            }
        }
        return selectedChildren;
    }

    public int getIndex() {
        return index;
    }
    public String getId() {
        return getLevel() + "," + getIndex();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isSelectedXLC() {
        return selectedXLC;
    }

    public void setSelectedXLC(boolean selectedXLC) {
        this.selectedXLC = selectedXLC;
    }

    public boolean isSelectedPH() {
        return selectedPH;
    }

    public void setSelectedPH(boolean selectedPH) {
        this.selectedPH = selectedPH;
    }

    public boolean isSelectedXEM() {
        return selectedXEM;
    }

    public void setSelectedXEM(boolean selectedXEM) {
        this.selectedXEM = selectedXEM;
    }

    public boolean isEnableShow() {
        return isEnableShow;
    }

    public void setEnableShow(boolean enableShow) {
        isEnableShow = enableShow;
    }

    public boolean isItemClickEnable() {
        return itemClickEnable;
    }

    public void setItemClickEnable(boolean itemClickEnable) {
        this.itemClickEnable = itemClickEnable;
    }
}
