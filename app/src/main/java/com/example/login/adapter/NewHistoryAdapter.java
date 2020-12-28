package com.example.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.common.ConvertUtils;
import com.example.login.model.DetailDocumentWaiting.UnitLogInfo;
import com.example.login.model.LogInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<UnitLogInfo> unitLogInfos;
    private String status;
        @NonNull
        @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return new  MyViewHolder(inflater.inflate(R.layout.item_new_log, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).bindData(unitLogInfos.get(position));

    }

    @Override
    public int getItemCount() {
        return unitLogInfos != null ? unitLogInfos.size() : 0;
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_dv)
        TextView tvDv;
        @BindView(R.id.rcvDanhSach)
        RecyclerView rcvDanhSach;
        @BindView(R.id.layout_process)
        LinearLayout layoutProcess;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void bindData(final UnitLogInfo logInfo) {
            tvDv.setText(logInfo.getUnit());
            List<LogInfo> logInfos = ConvertUtils.fromJSONList(logInfo.getParameter(), LogInfo.class);
            if (logInfos != null && logInfos.size() > 0) {
                List<LogInfo> logs = new ArrayList<>();
                for (LogInfo log: logInfos) {
                    if (log.getStatus() != null && log.getStatus().equals(status)) {
                        logs.add(log);
                    }
                }
                NewHistoryDetailAdapter adapter = new NewHistoryDetailAdapter(context, logs, status);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                rcvDanhSach.setLayoutManager(mLayoutManager);
                rcvDanhSach.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
    public NewHistoryAdapter(Context mContext, List<UnitLogInfo> datalist, String status) {
        this.context = mContext;
        this.unitLogInfos = datalist;
        this.status = status;
    }
}
