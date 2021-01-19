package com.example.treeview_lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treeview_lib.base.BaseNodeViewOperatingV2Binder;
import com.example.treeview_lib.base.BaseNodeViewOperatingV2Factory;
import com.example.treeview_lib.base.CheckableOperatingV2NodeViewBinder;
import com.example.treeview_lib.base.MoveDocNodeViewOperatingV2Binder;
import com.example.treeview_lib.helper.TreeHelper;


import java.util.ArrayList;
import java.util.List;

import static com.example.treeview_lib.helper.TreeHelper.selectAllNodeAndChild;
import static com.example.treeview_lib.helper.TreeHelper.switchTreeNote;

public class TreeViewOperatingAdapterV2 extends RecyclerView.Adapter {
    private Context context;
    private TreeNode root;
    private List<TreeNode> expandedNodeList;
    private BaseNodeViewOperatingV2Factory baseNodeViewOperatingV2Factory;
    private View EMPTY_PARAMETER;

    private TreeViewOperatingV2 treeViewOperatingV2;
    private TreeNode lastTreeNode = null;
    private int positiveFromList = -1;
    private int typeXLC = 1;

    public TreeViewOperatingAdapterV2(Context context, TreeNode root,
                                      @NonNull BaseNodeViewOperatingV2Factory baseNodeViewOperatingV2Factory) {
        this.context = context;
        this.root = root;
        this.baseNodeViewOperatingV2Factory = baseNodeViewOperatingV2Factory;

        this.EMPTY_PARAMETER = new View(context);
        this.expandedNodeList = new ArrayList<>();

        buildExpandedNodeList();
    }

    private void buildExpandedNodeList() {
        expandedNodeList.clear();

        for (TreeNode child : root.getChildren()) {
            insertNode(expandedNodeList, child);
        }
    }

    private void insertNode(List<TreeNode> nodeList, TreeNode treeNode) {
        nodeList.add(treeNode);

        if (!treeNode.hasChild()) {
            return;
        }
        if (treeNode.isExpanded()) {
            for (TreeNode child : treeNode.getChildren()) {
                insertNode(nodeList, child);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return expandedNodeList.get(position).getLevel();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int level) {
        View view = LayoutInflater.from(context).inflate(baseNodeViewOperatingV2Factory
                .getNodeViewOperatingBinder(EMPTY_PARAMETER, level).getLayoutId(), parent, false);

        BaseNodeViewOperatingV2Binder nodeViewBinder = baseNodeViewOperatingV2Factory.getNodeViewOperatingBinder(view, level);
        nodeViewBinder.setTreeView(treeViewOperatingV2);
        return nodeViewBinder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final View nodeView = holder.itemView;
        final TreeNode treeNode = expandedNodeList.get(position);
        final BaseNodeViewOperatingV2Binder viewBinder = (BaseNodeViewOperatingV2Binder) holder;
        // doi mau cho don vi
        if (position % 2 != 0) {
            nodeView.setBackgroundResource(R.color.md_white);
        } else {
            nodeView.setBackgroundResource(R.color.md_grey_200);
        }

        if (viewBinder.getToggleTriggerViewId() != 0) {
            View triggerToggleView = nodeView.findViewById(viewBinder.getToggleTriggerViewId());

            if (triggerToggleView != null) {
                triggerToggleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNodeToggled(treeNode);
                        viewBinder.onNodeToggled(treeNode, treeNode.isExpanded());
                    }
                });
            }
        } else if (treeNode.isItemClickEnable()) {
            nodeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNodeToggled(treeNode);
                    viewBinder.onNodeToggled(treeNode, treeNode.isExpanded());
                }
            });
        }

        if (viewBinder instanceof CheckableOperatingV2NodeViewBinder) {
            final View view = nodeView
                    .findViewById(((CheckableOperatingV2NodeViewBinder) viewBinder).getCheckableViewId());

            if (view != null && view instanceof CheckBox) {
                final CheckBox checkableView = (CheckBox) view;
                checkableView.setChecked(treeNode.isSelectedXLC());

                checkableView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = checkableView.isChecked();
                        selectNode(checked, treeNode);
                        ((CheckableOperatingV2NodeViewBinder) viewBinder).onNodeSelectedChanged(treeNode, checked);
                    }
                });
            } else {
                throw new ClassCastException("The getCheckableViewId() " +
                        "must return a CheckBox's id");
            }
        } else if (viewBinder instanceof MoveDocNodeViewOperatingV2Binder) {
            final View viewXLC = nodeView
                    .findViewById(((MoveDocNodeViewOperatingV2Binder) viewBinder).getCheckXLCViewId());
            final View viewPH = nodeView
                    .findViewById(((MoveDocNodeViewOperatingV2Binder) viewBinder).getCheckPHViewId());
            final View viewXEM = nodeView
                    .findViewById(((MoveDocNodeViewOperatingV2Binder) viewBinder).getCheckXEMViewId());

            if (viewXLC != null && viewXLC instanceof RadioButton) {
                final RadioButton checkableView = (RadioButton) viewXLC;
                checkableView.setChecked(treeNode.isSelectedXLC());

                checkableView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = checkableView.isChecked();
                        if (typeXLC == 1) {
                            selectOnlyNode(checked, treeNode);
                        } else {
                            selectSelectCheckBox(checked, treeNode, 1);
                        }
//                        selectNode(checked, treeNode);
                        ((MoveDocNodeViewOperatingV2Binder) viewBinder).onNodeSelectedChanged(treeNode, checked);
                    }
                });
                checkableView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (typeXLC == 1) {
                            return false;
                        }
                        boolean checked = !checkableView.isChecked();
                        checkableView.setChecked(checked);
                        selectNode(checked, treeNode, 3);
                        return true;
                    }
                });
            } else {
                throw new ClassCastException("The getCheckXLCViewId() " +
                        "must return a RadioButton's id");
            }
            if (viewPH != null && viewPH instanceof CheckBox) {
                final CheckBox checkableViewPH = (CheckBox) viewPH;
                checkableViewPH.setChecked(treeNode.isSelectedPH());

                checkableViewPH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = checkableViewPH.isChecked();

                        selectSelectCheckBox(checked, treeNode, 2);

//                        selectNode(checked, treeNode);
//                        ((CheckableNodeViewBinder) viewBinder).onNodeSelectedChanged(treeNode, checked);
                    }
                });
                checkableViewPH.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        boolean checked = !checkableViewPH.isChecked();
                        checkableViewPH.setChecked(checked);
                        selectNode(checked, treeNode, 1);
                        return true;
                    }
                });
            } else {
                throw new ClassCastException("The getCheckPHViewId() " +
                        "must return a CheckBox's id");
            }
            if (viewXEM != null && viewXEM instanceof CheckBox) {
                final CheckBox checkableViewXEM = (CheckBox) viewXEM;
                checkableViewXEM.setChecked(treeNode.isSelectedXEM());

                checkableViewXEM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = checkableViewXEM.isChecked();

                        selectSelectCheckBox(checked, treeNode, 3);

//                        selectNode(checked, treeNode);
                        ((MoveDocNodeViewOperatingV2Binder) viewBinder).onNodeSelectedChanged(treeNode, checked);
                    }
                });
                checkableViewXEM.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        boolean checked = !checkableViewXEM.isChecked();
                        selectNode(checked, treeNode, 2);
                        return true;
                    }
                });
            } else {
                throw new ClassCastException("The getCheckXEMViewId() " +
                        "must return a CheckBox's id");
            }
        }

        viewBinder.bindView(treeNode, context);

    }


    public void selectNode(boolean checked, TreeNode treeNode) {
        treeNode.setSelectedXLC(checked);

        selectChildren(treeNode, checked);
        selectParentIfNeed(treeNode, checked);
    }
    public void selectNode(boolean checked, TreeNode treeNode, int typeSelected) {
        switch (typeSelected) {
            case 1:
                treeNode.setSelectedPH(checked);
                if (checked) {
                    treeNode.setSelectedXLC(!checked);
                    treeNode.setSelectedXEM(!checked);
                }
                break;
            case 2:
                treeNode.setSelectedXEM(checked);
                if (checked) {
                    treeNode.setSelectedXLC(!checked);
                    treeNode.setSelectedPH(!checked);
                }
                break;
            case 3:
                treeNode.setSelectedXLC(checked);
                if (checked) {
                    treeNode.setSelectedXEM(!checked);
                    treeNode.setSelectedPH(!checked);
                }
                break;
        }

        selectChildren(treeNode, checked, typeSelected);
//        selectParentIfNeed(treeNode, checked);
    }



    private void selectChildren(TreeNode treeNode, boolean checked, int typeSelected) {
        List<TreeNode> impactedChildren = TreeHelper.selectOnlyNodeAndChild(treeNode, checked, typeSelected);
        int index = expandedNodeList.indexOf(treeNode);
        if (index != -1 && impactedChildren.size() > 0) {
            notifyItemRangeChanged(index, impactedChildren.size() + 1);
        } else if (index != -1) {
            notifyItemChanged(index);
        }
    }

    private void selectChildren(TreeNode treeNode, boolean checked) {
        List<TreeNode> impactedChildren = TreeHelper.selectNodeAndChild(treeNode, checked);
        int index = expandedNodeList.indexOf(treeNode);
        if (index != -1 && impactedChildren.size() > 0) {
            notifyItemRangeChanged(index, impactedChildren.size() + 1);
        }
    }


    private void selectParentIfNeed(TreeNode treeNode, boolean checked) {
        List<TreeNode> impactedParents = TreeHelper.selectParentIfNeedWhenNodeSelected(treeNode, checked);
        if (impactedParents.size() > 0) {
            for (TreeNode parent : impactedParents) {
                int position = expandedNodeList.indexOf(parent);
                if (position != -1) notifyItemChanged(position);
            }
        }
    }

    private void onNodeToggled(TreeNode treeNode) {
        treeNode.setExpanded(!treeNode.isExpanded());

        if (treeNode.isExpanded()) {
            expandNode(treeNode);
        } else {
            collapseNode(treeNode);
        }
    }

    public void expandNode(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        List<TreeNode> additionNodes = TreeHelper.expandNode(treeNode, false);
        int index = expandedNodeList.indexOf(treeNode);

        insertNodesAtIndex(index, additionNodes);
    }

    private void insertNodesAtIndex(int index, List<TreeNode> additionNodes) {
        if (index < 0 || index > expandedNodeList.size() - 1 || additionNodes == null) {
            return;
        }
        expandedNodeList.addAll(index + 1, additionNodes);
        notifyItemRangeInserted(index + 1, additionNodes.size());
    }

    public void collapseNode(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        List<TreeNode> removedNodes = TreeHelper.collapseNode(treeNode, false);
        int index = expandedNodeList.indexOf(treeNode);

        removeNodesAtIndex(index, removedNodes);
    }

    private void removeNodesAtIndex(int index, List<TreeNode> removedNodes) {
        if (index < 0 || index > expandedNodeList.size() - 1 || removedNodes == null) {
            return;
        }
        expandedNodeList.removeAll(removedNodes);
        notifyItemRangeRemoved(index + 1, removedNodes.size());
    }

    public void selectSelectCheckBox(boolean checked, TreeNode treeNode, int typeCheck) {
        switch (typeCheck) {
            case 1:
                treeNode.setSelectedXLC(checked);
                if (checked) {
                    treeNode.setSelectedPH(!checked);
                    treeNode.setSelectedXEM(!checked);
                }
                break;
            case 2:
                treeNode.setSelectedPH(checked);
                if (checked) {
                    treeNode.setSelectedXLC(!checked);
                    treeNode.setSelectedXEM(!checked);
                }
                break;
            case 3:
                treeNode.setSelectedXEM(checked);
                if (checked) {
                    treeNode.setSelectedXLC(!checked);
                    treeNode.setSelectedPH(!checked);
                }
                break;
        }

        selectOtherCheck(treeNode);
    }

    public void selectOnlyNode(boolean checked, TreeNode treeNode) {
//        TreeHelper.selectNodeAndChild(root, false);
//        buildExpandedNodeList();
        if (lastTreeNode != null) {
            updateNodeXLC(lastTreeNode);
        } else if (positiveFromList != -1) {
            updateNodeXLC(getTreeNoteByPositiveXLC(positiveFromList));
        }

        positiveFromList = getPositiveXLC(treeNode);
        int positive = expandedNodeList.indexOf(treeNode);
        treeNode.setSelectedXLC(checked);
        if (checked) {
            treeNode.setSelectedPH(!checked);
            treeNode.setSelectedXEM(!checked);
        }
        if (positive != -1) {
            notifyItemChanged(positive);
        }
        lastTreeNode = treeNode;
    }

    private int getPositiveXLC(TreeNode node) {
        if (node == null) {
            return -1;
        }
        List<TreeNode> allNodes = TreeHelper.getAllNodes(root);
        if (allNodes.indexOf(node) != -1) {
            return allNodes.indexOf(node);
        }

        //remove children form list before delete
        return -1;
    }

    public void updateNodeXLC(TreeNode node) {
        node.setSelectedXLC(false);
        int index = expandedNodeList.indexOf(node);
        if (index != -1) {
            notifyItemChanged(index);
        }
    }

    private void selectOtherCheck(TreeNode treeNode) {
        int index = expandedNodeList.indexOf(treeNode);
        if (index != -1) {
            notifyItemChanged(index);
        }
    }

    private TreeNode getTreeNoteByPositiveXLC(int positive) {
        if (positive == -1) {
            return null;
        }
        List<TreeNode> allNodes = TreeHelper.getAllNodes(root);
        if (allNodes.get(positive) != null) {
            return allNodes.get(positive);
        }

        //remove children form list before delete
        return null;
    }

    @Override
    public int getItemCount() {
        return expandedNodeList == null ? 0 : expandedNodeList.size();
    }

    public void refreshView() {
        buildExpandedNodeList();
        notifyDataSetChanged();
    }

    public void deleteNode(TreeNode node) {
        if (node == null || node.getParent() == null) {
            return;
        }
        List<TreeNode> allNodes = TreeHelper.getAllNodes(root);
        if (allNodes.indexOf(node) != -1) {
            node.getParent().removeChild(node);
        }

        //remove children form list before delete
        collapseNode(node);

        int index = expandedNodeList.indexOf(node);
        if (index != -1) {
            expandedNodeList.remove(node);
        }
        notifyItemRemoved(index);
    }
    public void setTreeView(TreeViewOperatingV2 treeViewOperatingV2) {
        this.treeViewOperatingV2 = treeViewOperatingV2;
    }
    public int positiveXLC() {
        return positiveFromList;
    }
    public void setTypeXLC(int typeXLC) {
        this.typeXLC = typeXLC;
    }
    public void updatePositiveXLC(TreeNode treeNode) {
        positiveFromList = getPositiveXLC(treeNode);
    }

}
