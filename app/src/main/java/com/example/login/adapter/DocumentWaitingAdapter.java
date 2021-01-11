package com.example.login.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.common.ConnectionDetector;
import com.example.login.common.Constants;
import com.example.login.configuration.Application;
import com.example.login.configuration.ApplicationSharedPreferences;
import com.example.login.model.APIError;
import com.example.login.model.DetailDocumentWaiting.DetailDocumentInfo;
import com.example.login.model.DocumentWaitingInfo;
import com.example.login.model.LoginInfo;
import com.example.login.model.TypeChangeDocumentRequest;
import com.example.login.presenter.DocumentWaitingDao.DocumentWaitingDao;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ExceptionRequest;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.IExceptionErrorView;
import com.example.login.presenter.loginDao.LoginDao;
import com.example.login.view.DetailDocumentWaitingActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DocumentWaitingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<DocumentWaitingInfo> documentWaitingInfoList;
    private Context context;
    public final int TYPE_NEW = 0;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_trang_thai)
        TextView tvTrangThai;
        @BindView(R.id.tv_kh)
        TextView tvKH;
        @BindView(R.id.tv_cqbh)
        TextView tvCQBH;
        @BindView(R.id.tv_ngay_vb)
        TextView tvNgayVB;
        @BindView(R.id.tv_do_khan)
        TextView tvDoKhan;
        @BindView(R.id.tv_vatro_xl)
        TextView tvVaiTro;
        @BindView(R.id.img_file_dinh_kem)
        ImageView imgFileDinhkem;
        @BindView(R.id.tv_trang_thai_label)
        TextView tvTrangThai_label;
        @BindView(R.id.tv_kh_label)
        TextView tvKH_label;
        @BindView(R.id.tv_cqbh_label)
        TextView tvCQBH_label;
        @BindView(R.id.tv_ngay_vb_label)
        TextView tvNgayVB_label;
        @BindView(R.id.tv_do_khan_label)
        TextView tvDoKhan_label;
        @BindView(R.id.tv_vaitro_xl_label)
        TextView tvVaiTro_XL_label;
        @BindView(R.id.tv_file_attach_label)
        TextView imgFileDinhkem_label;
        @BindView(R.id.linear)
        LinearLayout linear;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("NewApi")
        void bindData(int position) {
            DocumentWaitingInfo documentWaitingInfo = documentWaitingInfoList.get(position);
            try {
                if (documentWaitingInfo.getNgayNhan() != null) {
                    try {
                        String[] arr = documentWaitingInfo.getNgayNhan().split(" ");
                        tvTime.setText(arr[1]);
                        tvDate.setText(arr[0]);
                    } catch (Exception ex) {
                        tvTime.setText("");
                        tvDate.setText("");
                    }
                }
                if (documentWaitingInfo.getTrichYeu() != null) {
                    tvTitle.setText(documentWaitingInfo.getTrichYeu());
                }
                if (documentWaitingInfo.getIsRead() != null) {
                    if (documentWaitingInfo.getIsRead().equals(Constants.IS_READ)) {
                        tvTrangThai.setText(" " + context.getString(R.string.IS_READ));
                        tvTitle.setTextColor(context.getResources().getColor(R.color.md_black));
                    }
                    if (documentWaitingInfo.getIsRead().equals(Constants.IS_NOT_READ)) {
                        tvTrangThai.setText(" " + context.getString(R.string.IS_NOT_READ));
                        tvTitle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    }
                }
                if (documentWaitingInfo.getSoKihieu() != null) {
                    tvKH.setText(" " + documentWaitingInfo.getSoKihieu());
                }
                if (documentWaitingInfo.getCoQuanBanHanh() != null) {
                    tvCQBH.setText(documentWaitingInfo.getCoQuanBanHanh());
                }
                if (documentWaitingInfo.getNgayVanBan() != null) {
                    tvNgayVB.setText(" " + documentWaitingInfo.getNgayVanBan());
                }
                if (documentWaitingInfo.getDoKhan() != null) {
                    tvDoKhan.setText(" " + documentWaitingInfo.getDoKhan());
                    if (documentWaitingInfo.getDoKhan().equals(context.getString(R.string.str_thuong))) {
                        tvDoKhan.setTextColor(context.getResources().getColor(R.color.md_light_green_status));
                    } else {
                        tvDoKhan.setTextColor(context.getResources().getColor(R.color.colorTextRed));
                    }
                }
                if (documentWaitingInfo.getRole() != null) {
                    tvVaiTro_XL_label.setVisibility(View.VISIBLE);
                    tvVaiTro.setVisibility(View.VISIBLE);

                    tvVaiTro.setText(" " + documentWaitingInfo.getRole());
                } else {
                    tvVaiTro.setVisibility(View.GONE);
                    tvVaiTro_XL_label.setVisibility(View.GONE);
                }
                if (documentWaitingInfo.getHasFile() != null) {
                    if (documentWaitingInfo.getHasFile().equals(Constants.HAS_FILE)) {
                        imgFileDinhkem.setVisibility(View.VISIBLE);
                    }
                    if (documentWaitingInfo.getHasFile().equals(Constants.HAS_NOT_FILE)) {
                        imgFileDinhkem.setVisibility(View.GONE);
                    }
                } else {
                    imgFileDinhkem.setVisibility(View.VISIBLE);
                }
                linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().postSticky(documentWaitingInfo);
                        EventBus.getDefault().postSticky(new DetailDocumentInfo(documentWaitingInfo.getId(), Constants.DOCUMENT_WAITING, null));
                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(documentWaitingInfo.getId(), documentWaitingInfo.getProcessDefinitionId(), documentWaitingInfo.getCongVanDenDi()));
                        context.startActivity(new Intent(context, DetailDocumentWaitingActivity.class));
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public DocumentWaitingAdapter(Context context, List<DocumentWaitingInfo> documentWaitingInfoList) {
        this.context = context;
        this.documentWaitingInfoList = documentWaitingInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_documentwaiting, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.progressbar_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_NEW) {
            ((MyViewHolder) holder).bindData(position);
        }
    }

    @Override
    public int getItemCount() {
        return documentWaitingInfoList.size();
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
    */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void removeAll() {
        int size = this.documentWaitingInfoList.size();
        this.documentWaitingInfoList.clear();
        notifyItemRangeRemoved(0, size);
    }


}
