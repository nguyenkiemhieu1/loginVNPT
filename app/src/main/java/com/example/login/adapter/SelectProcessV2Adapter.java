package com.example.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.model.PersonProcessInfo;

import java.util.List;

public class SelectProcessV2Adapter extends RecyclerView.Adapter<SelectProcessV2Adapter.ViewHolder> {
    private Context context;
    private int resource;
    private List<PersonProcessInfo> personProcessInfoList;
    private int positionCheckBocXlcOLD;

    public SelectProcessV2Adapter(Context context, int resource, List<PersonProcessInfo> personProcessInfoList) {
        this.context = context;
        this.resource = resource;
        this.personProcessInfoList = personProcessInfoList;
        this.positionCheckBocXlcOLD = -1;
    }


    @NonNull
    @Override
    public SelectProcessV2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.resource, parent, false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder   ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(personProcessInfoList.get(position).getFullName());
        if (personProcessInfoList.get(position).isXlc() || personProcessInfoList.get(position).isDxl()) {
            holder.txtName.setTextColor(ContextCompat.getColor(context, R.color.md_red_500));
        } else {
            holder.txtName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        holder.txtDonVi.setText(personProcessInfoList.get(position).getSecondUnitName());

        if (personProcessInfoList.get(position).isXlc()) {
            holder.checkboxXLC.setChecked(true);
        } else holder.checkboxXLC.setChecked(false);
        if (personProcessInfoList.get(position).isDxl()) {
            holder.checkboxDXL.setChecked(true);
        } else holder.checkboxDXL.setChecked(false);
        holder.checkboxXLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!personProcessInfoList.get(position).isXlc()) {
                    personProcessInfoList.get(position).setXlc(true);

                    if (personProcessInfoList.get(position).isDxl()) {
                        personProcessInfoList.get(position).setDxl(false);
                    }
                    if (positionCheckBocXlcOLD > -1 && positionCheckBocXlcOLD != position) {//kiểm tra xem đã click checkbox XLC lần nào chưa
                        personProcessInfoList.get(positionCheckBocXlcOLD).setXlc(false);
                        notifyItemChanged(positionCheckBocXlcOLD);
                    }
                    positionCheckBocXlcOLD = position;
                    notifyItemChanged(position);
                }

            }
        });
        holder.checkboxDXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personProcessInfoList.get(position).isDxl()) {
                    personProcessInfoList.get(position).setDxl(false);
                } else {
                    personProcessInfoList.get(position).setDxl(true);
                    personProcessInfoList.get(position).setXlc(false);
                }
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return personProcessInfoList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView txtName, txtDonVi;
        private RadioButton checkboxXLC;
        private CheckBox checkboxDXL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDonVi = itemView.findViewById(R.id.txtDonVi);
            checkboxXLC = itemView.findViewById(R.id.checkXLChinh);
            checkboxDXL = itemView.findViewById(R.id.checkDongXL);
        }
    }
}
