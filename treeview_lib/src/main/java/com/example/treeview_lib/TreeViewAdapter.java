package com.example.treeview_lib;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treeview_lib.base.BaseNodeViewBinder;
import com.example.treeview_lib.base.BaseNodeViewFactory;
import com.example.treeview_lib.base.CheckableNodeViewBinder;
import com.example.treeview_lib.base.CheckableOperatingNodeViewBinder;
import com.example.treeview_lib.base.MoveDocNodeViewBinder;
import com.example.treeview_lib.helper.TreeHelper;

import java.util.ArrayList;
import java.util.List;

public class TreeViewAdapter extends RecyclerView.Adapter {
    private Context context;

    private TreeNode root;

    private List<TreeNode> expandedNodeList;

    private BaseNodeViewFactory baseNodeViewFactory;
    private View EMPTY_PARAMETER;

    private TreeView treeView;
    private TreeNode lastTreeNode = null;
    private int positiveFromList = -1;
    private int typeXLC = 1;

    public TreeViewAdapter(Context context, TreeNode root,
                           @NonNull BaseNodeViewFactory baseNodeViewFactory) {
        this.context = context;
        this.root = root;
        this.baseNodeViewFactory = baseNodeViewFactory;

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
        if (treeNode.isEnableShow()) {
            nodeList.add(treeNode);
        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(baseNodeViewFactory.
                getNodeViewBinder(EMPTY_PARAMETER, viewType).getLayoutId(), parent, false);
        BaseNodeViewBinder nodeViewBinder = baseNodeViewFactory.getNodeViewBinder(view, viewType);
        nodeViewBinder.setTreeView(treeView);
        return nodeViewBinder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final View nodeView = holder.itemView;
        final TreeNode treeNode = expandedNodeList.get(position);
        final BaseNodeViewBinder viewBinder = (BaseNodeViewBinder) holder;
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

        if (viewBinder instanceof CheckableNodeViewBinder) {
            final View view = nodeView
                    .findViewById(((CheckableNodeViewBinder) viewBinder).getCheckableViewId());

            if (view != null && view instanceof CheckBox) {
                final CheckBox checkableView = (CheckBox) view;
                checkableView.setChecked(treeNode.isSelectedXLC());

                checkableView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = checkableView.isChecked();
                        selectOnlyNode(checked, treeNode);

//                        selectNode(checked, treeNode);
                        ((CheckableNodeViewBinder) viewBinder).onNodeSelectedChanged(treeNode, checked);
                    }
                });
            } else {
                throw new ClassCastException("The getCheckableViewId() " +
                        "must return a CheckBox's id");
            }
        } else if (viewBinder instanceof CheckableOperatingNodeViewBinder) {
            final View view = nodeView
                    .findViewById(((CheckableOperatingNodeViewBinder) viewBinder).getCheckableViewId());

            if (view != null && view instanceof CheckBox) {
                final CheckBox checkableView = (CheckBox) view;
                checkableView.setChecked(treeNode.isSelectedXLC());

                checkableView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = checkableView.isChecked();
                        selectNode(checked, treeNode);
                        ((CheckableOperatingNodeViewBinder) viewBinder).onNodeSelectedChanged(treeNode, checked);
                    }
                });
            } else {
                throw new ClassCastException("The getCheckableViewId() " +
                        "must return a CheckBox's id");
            }
        } else if (viewBinder instanceof MoveDocNodeViewBinder) {
            final View viewXLC = nodeView
                    .findViewById(((MoveDocNodeViewBinder) viewBinder).getCheckXLCViewId());
            final View viewPH = nodeView
                    .findViewById(((MoveDocNodeViewBinder) viewBinder).getCheckPHViewId());
            final View viewXEM = nodeView
                    .findViewById(((MoveDocNodeViewBinder) viewBinder).getCheckXEMViewId());
            final View viewTextNode = nodeView
                    .findViewById(((MoveDocNodeViewBinder) viewBinder).getNodeNameViewId());
            ((TextView) viewTextNode).setTextColor(treeNode.isSelectedXLC() || treeNode.isSelectedXEM()
                    || treeNode.isSelectedPH() ? Color.RED : Color.BLACK);

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
                        ((MoveDocNodeViewBinder) viewBinder).onNodeSelectedChanged(treeNode, checked);
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
                        ((MoveDocNodeViewBinder) viewBinder).onNodeSelectedChanged(treeNode, checked);
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

        viewBinder.bindView(treeNode);

    }

    public void setTypeXLC(int typeXLC) {
        this.typeXLC = typeXLC;
    }

    public int positiveXLC() {
        return positiveFromList;
    }

    public void updatePositiveXLC(TreeNode treeNode) {
        positiveFromList = getPositiveXLC(treeNode);
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

    private void selectChildren(TreeNode treeNode, boolean checked) {
        List<TreeNode> impactedChildren = TreeHelper.selectNodeAndChild(treeNode, checked);
        int index = expandedNodeList.indexOf(treeNode);
        if (index != -1 && impactedChildren.size() > 0) {
            notifyItemRangeChanged(index, impactedChildren.size() + 1);
        }
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

    private void selectOtherCheck(TreeNode treeNode) {
        int index = expandedNodeList.indexOf(treeNode);
        if (index != -1) {
            notifyItemChanged(index);
        }
    }

    public void updateNodeXLC(TreeNode node) {
        node.setSelectedXLC(false);
        int index = expandedNodeList.indexOf(node);
        if (index != -1) {
            notifyItemChanged(index);
        }
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

    @Override
    public int getItemCount() {
        return expandedNodeList == null ? 0 : expandedNodeList.size();
    }
    public void setTreeView(TreeView treeView) {
        this.treeView = treeView;
    }
}
