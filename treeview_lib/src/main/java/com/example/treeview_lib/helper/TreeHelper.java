package com.example.treeview_lib.helper;

import com.example.treeview_lib.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TreeHelper {
    public static void expandAll(TreeNode node) {
        if (node == null) {
            return;
        }
        expandNode(node, true);
    }
    public static List<TreeNode> expandNode(TreeNode treeNode, boolean includeChild) {
        List<TreeNode> expandChildren = new ArrayList<>();

        if (treeNode == null) {
            return expandChildren;
        }

        treeNode.setExpanded(true);

        if (!treeNode.hasChild()) {
            return expandChildren;
        }

        for (TreeNode child : treeNode.getChildren()) {
            expandChildren.add(child);

            if (includeChild || child.isExpanded()) {
                expandChildren.addAll(expandNode(child, includeChild));
            }
        }

        return expandChildren;
    }

    public static void expandLevel(TreeNode root, int level) {
        if (root == null) {
            return;
        }

        for (TreeNode child : root.getChildren()) {
            if (child.getLevel() == level) {
                expandNode(child, false);
            } else {
                expandLevel(child, level);
            }

        }
    }

    public static void collapseAll(TreeNode node) {
        if (node == null) {
            return;
        }
        for (TreeNode child : node.getChildren()) {
            performCollapseNode(child, true);
        }
    }

    public static List<TreeNode> collapseNode(TreeNode node, boolean includeChild) {
        List<TreeNode> treeNodes = performCollapseNode(node, includeChild);
        node.setExpanded(false);
        return treeNodes;
    }

    private static List<TreeNode> performCollapseNode(TreeNode node, boolean includeChild) {
        List<TreeNode> collapseChildren = new ArrayList<>();

        if (node == null) {
            return collapseChildren;
        }
        if (includeChild) {
            node.setExpanded(false);
        }
        for (TreeNode child : node.getChildren()) {
            collapseChildren.add(child);

            if (child.isExpanded()) {
                collapseChildren.addAll(performCollapseNode(child, includeChild));
            } else if (includeChild) {
                performCollapseNodeInner(child);
            }
        }

        return collapseChildren;
    }
    private static void performCollapseNodeInner(TreeNode node) {
        if (node == null) {
            return;
        }
        node.setExpanded(false);
        for (TreeNode child : node.getChildren()) {
            performCollapseNodeInner(child);
        }
    }
    public static void collapseLevel(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        for (TreeNode child : root.getChildren()) {
            if (child.getLevel() == level) {
                collapseNode(child, false);
            } else {
                collapseLevel(child, level);
            }
        }
    }
    public static List<TreeNode> getAllNodes(TreeNode root) {
        List<TreeNode> allNodes = new ArrayList<>();

        fillNodeList(allNodes, root);
        allNodes.remove(root);

        return allNodes;
    }
    private static void fillNodeList(List<TreeNode> treeNodes, TreeNode treeNode) {
        treeNodes.add(treeNode);

        if (treeNode.hasChild()) {
            for (TreeNode child : treeNode.getChildren()) {
                fillNodeList(treeNodes, child);
            }
        }
    }

    public static List<TreeNode> selectNodeAndChild(TreeNode treeNode, boolean select) {
        List<TreeNode> expandChildren = new ArrayList<>();

        if (treeNode == null) {
            return expandChildren;
        }

        treeNode.setSelectedXLC(select);

        if (!treeNode.hasChild()) {
            return expandChildren;
        }

        if (treeNode.isExpanded()) {
            for (TreeNode child : treeNode.getChildren()) {
                expandChildren.add(child);

                if (child.isExpanded()) {
                    expandChildren.addAll(selectNodeAndChild(child, select));
                } else {
                    selectNodeInner(child, select);
                }
            }
        } else {
            selectNodeInner(treeNode, select);
        }
        return expandChildren;
    }
    private static void selectNodeInner(TreeNode treeNode, boolean select) {
        if (treeNode == null) {
            return;
        }
        treeNode.setSelectedXLC(select);
        if (treeNode.hasChild()) {
            for (TreeNode child : treeNode.getChildren()) {
                selectNodeInner(child, select);
            }
        }
    }

    private static void selectNodeInner(TreeNode treeNode, boolean select,int typeSelected) {
        if (treeNode == null) {
            return;
        }
        switch (typeSelected) {
            case 1:
                treeNode.setSelectedPH(select);
                if (select) {
                    treeNode.setSelectedXLC(!select);
                    treeNode.setSelectedXEM(!select);
                }
                break;
            case 2:
                treeNode.setSelectedXEM(select);
                if (select) {
                    treeNode.setSelectedXLC(!select);
                    treeNode.setSelectedPH(!select);
                }
                break;
        }
        if (treeNode.hasChild()) {
            for (TreeNode child : treeNode.getChildren()) {
                selectNodeInner(child, select,typeSelected);
            }
        }
    }

    public static List<TreeNode> selectOnlyNodeAndChild(TreeNode treeNode, boolean select, int typeSelected) {
        List<TreeNode> expandChildren = new ArrayList<>();

        if (treeNode == null) {
            return expandChildren;
        }
        switchTreeNote(treeNode,typeSelected,select);
        if (!treeNode.hasChild()) {
            return expandChildren;
        }
        for (TreeNode child : treeNode.getChildren()) {
            switchTreeNote(child,typeSelected,select);
            expandChildren.add(child);
            if (child.isExpanded()) {
                expandChildren.addAll(selectAllNodeAndChild(child));
            }
        }
        return expandChildren;
    }

    public static List<TreeNode> selectAllNodeAndChild(TreeNode treeNode) {
        List<TreeNode> expandChildren = new ArrayList<>();

        if (treeNode == null) {
            return expandChildren;
        }

        if (!treeNode.hasChild()) {
            return expandChildren;
        }

        if (treeNode.isExpanded()) {
            for (TreeNode child : treeNode.getChildren()) {
                expandChildren.add(child);

                if (child.isExpanded()) {
                    expandChildren.addAll(selectAllNodeAndChild(child));
                }
            }
        }
        return expandChildren;
    }


    public static List<TreeNode> selectParentIfNeedWhenNodeSelected(TreeNode treeNode, boolean select) {
        List<TreeNode> impactedParents = new ArrayList<>();
        if (treeNode == null) {
            return impactedParents;
        }

        //ensure that the node's level is bigger than 1(first level is 1)
        TreeNode parent = treeNode.getParent();
        if (parent == null || parent.getParent() == null) {
            return impactedParents;
        }

        List<TreeNode> brothers = parent.getChildren();
        int selectedBrotherCount = 0;
        for (TreeNode brother : brothers) {
            if (brother.isSelectedXLC()) selectedBrotherCount++;
        }

        if (select && selectedBrotherCount == brothers.size()) {
            parent.setSelectedXLC(true);
            impactedParents.add(parent);
            impactedParents.addAll(selectParentIfNeedWhenNodeSelected(parent, true));
        } else if (!select && selectedBrotherCount == brothers.size() - 1) {
            // only the condition that the size of selected's brothers
            // is one less than total count can trigger the deselect
            parent.setSelectedXLC(false);
            impactedParents.add(parent);
            impactedParents.addAll(selectParentIfNeedWhenNodeSelected(parent, false));
        }
        return impactedParents;
    }
    public static List<TreeNode> getSelectedNodes(TreeNode treeNode) {
        List<TreeNode> selectedNodes = new ArrayList<>();
        if (treeNode == null) {
            return selectedNodes;
        }

        if (treeNode.isSelectedXLC() && treeNode.getParent() != null) selectedNodes.add(treeNode);

        for (TreeNode child : treeNode.getChildren()) {
            selectedNodes.addAll(getSelectedNodes(child));
        }
        return selectedNodes;
    }
    public static List<TreeNode> getSelectedXEMNodes(TreeNode treeNode) {
        List<TreeNode> selectedNodes = new ArrayList<>();
        if (treeNode == null) {
            return selectedNodes;
        }

        if (treeNode.isSelectedXEM() && treeNode.getParent() != null) selectedNodes.add(treeNode);

        for (TreeNode child : treeNode.getChildren()) {
            selectedNodes.addAll(getSelectedXEMNodes(child));
        }
        return selectedNodes;
    }

    public static List<TreeNode> getSelectedPHNodes(TreeNode treeNode) {
        List<TreeNode> selectedNodes = new ArrayList<>();
        if (treeNode == null) {
            return selectedNodes;
        }

        if (treeNode.isSelectedPH() && treeNode.getParent() != null) selectedNodes.add(treeNode);

        for (TreeNode child : treeNode.getChildren()) {
            selectedNodes.addAll(getSelectedPHNodes(child));
        }
        return selectedNodes;
    }

    public static boolean hasOneSelectedNodeAtLeast(TreeNode treeNode) {
        if (treeNode == null || treeNode.getChildren().size() == 0) {
            return false;
        }
        List<TreeNode> children = treeNode.getChildren();
        for (TreeNode child : children) {
            if (child.isSelectedXLC() || hasOneSelectedNodeAtLeast(child)) {
                return true;
            }
        }
        return false;
    }

    private static void switchTreeNote(TreeNode child,int typeSelected,boolean select) {
        switch (typeSelected) {
            case 1:
                child.setSelectedPH(select);
                if (select) {
                    child.setSelectedXLC(!select);
                    child.setSelectedXEM(!select);
                }
                break;
            case 2:
                child.setSelectedXEM(select);
                if (select) {
                    child.setSelectedXLC(!select);
                    child.setSelectedPH(!select);
                }
                break;
            case 3:
                child.setSelectedXLC(select);
                if (select) {
                    child.setSelectedXEM(!select);
                    child.setSelectedPH(!select);
                }
                break;
        }
    }
    public static void filterNodeInner(TreeNode treeNode, boolean select) {
        if (treeNode == null) {
            return;
        }
        treeNode.setEnableShow(select);
        if (treeNode.hasChild()) {
            for (TreeNode child : treeNode.getChildren()) {
                filterNodeInner(child, select);
            }
        }
    }
}
