package com.example.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.common.Constants;
import com.example.login.model.DocumentWaitingInfo;

import java.util.List;


public class DocumentWaitingAdapter extends BaseAdapter {
    private List<DocumentWaitingInfo> documentWaitingInfos;
    private LayoutInflater layoutInflater;
    private Context context;
    OnLoadMoreListener loadMoreListener;
     public  DocumentWaitingAdapter(Context context, List<DocumentWaitingInfo> documentWaitingInfos){
         this.context = context;
         this.documentWaitingInfos = documentWaitingInfos;
         layoutInflater = LayoutInflater.from(context);
     }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


    @Override
    public int getCount() {
        return documentWaitingInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return documentWaitingInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         DocumentWaitingInfo newItem = this.documentWaitingInfos.get(i);
            view = layoutInflater.inflate(R.layout.custom_listview, null);
        TextView tv_time = view.findViewById(R.id.tv_time);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_kh = view.findViewById(R.id.tv_kh);
        TextView tv_cqbh = view.findViewById(R.id.tv_cqbh);
        TextView tv_ngay_vb = view.findViewById(R.id.tv_ngay_vb);
        TextView tv_do_khan = view.findViewById(R.id.tv_do_khan);
        ImageView img_file_dinh_kem = view.findViewById(R.id.img_file_dinh_kem);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvTrangThai  = view.findViewById(R.id.tv_trang_thai);
        TextView tv_vaitro_xl_label = view.findViewById(R.id.tv_vaitro_xl_label);
        TextView tv_vatro_xl = view.findViewById(R.id.tv_vatro_xl);


        try{
            if(newItem.getNgayNhan() != null){
                try{
                    String[] arr = newItem.getNgayNhan().split("");
                    tv_time.setText(arr[1]);
                    tv_date.setText(arr[0]);
                }catch (Exception ex){
                    tv_time.setText("");
                    tv_date.setText("");
                }
            }
            if (newItem.getTrichYeu() != null) {
                tvTitle.setText(newItem.getTrichYeu());
            }
            if (newItem.getIsRead() != null) {
                if (newItem.getIsRead().equals(Constants.IS_READ)) {
                    tvTrangThai.setText(" " + context.getString(R.string.IS_READ));
                    tvTitle.setTextColor(context.getResources().getColor(R.color.md_black));
                }
                if (newItem.getIsRead().equals(Constants.IS_NOT_READ)) {
                    tvTrangThai.setText(" " + context.getString(R.string.IS_NOT_READ));
                    tvTitle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
            }
            if (newItem.getSoKihieu() != null) {
                tv_kh.setText(" " + newItem.getSoKihieu());
            }
            if (newItem.getCoQuanBanHanh() != null) {
                tv_cqbh.setText(newItem.getCoQuanBanHanh());
            }
            if (newItem.getNgayVanBan() != null) {
                tv_ngay_vb.setText(" " + newItem.getNgayVanBan());
            }
            if (newItem.getDoKhan() != null) {
                tv_do_khan.setText(" " + newItem.getDoKhan());
                if (newItem.getDoKhan().equals(context.getString(R.string.str_thuong))) {
                    tv_do_khan.setTextColor(context.getResources().getColor(R.color.md_light_green_status));
                } else {
                    tv_do_khan.setTextColor(context.getResources().getColor(R.color.colorTextRed));
                }
            }
            if (newItem.getRole() != null) {
                tv_vaitro_xl_label.setVisibility(View.VISIBLE);
                tv_vatro_xl.setVisibility(View.VISIBLE);

                tv_vatro_xl.setText(" " + newItem.getRole());
            } else {
                tv_vatro_xl.setVisibility(View.GONE);
                tv_vaitro_xl_label.setVisibility(View.GONE);
            }
            if (newItem.getHasFile() != null) {
                if (newItem.getHasFile().equals(Constants.HAS_FILE)) {
                    img_file_dinh_kem.setVisibility(View.VISIBLE);
                }
                if (newItem.getHasFile().equals(Constants.HAS_NOT_FILE)) {
                    img_file_dinh_kem.setVisibility(View.GONE);
                }
            } else {
                img_file_dinh_kem.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

      return  view;

    }
}
