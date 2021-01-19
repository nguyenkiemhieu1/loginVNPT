package com.example.treeview_lib.base;

import android.view.View;

public abstract  class BaseNodeViewTaoCVFactory {

    public abstract BaseNodeViewTaoCVBinder getNodeViewBinder(View view, int level);
}
