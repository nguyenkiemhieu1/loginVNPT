package com.example.login.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login.R;
import com.example.login.model.FeedBackEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DialogCommentFinish extends Dialog {
    @BindView(R.id.txtFeedBack)
    EditText txtFeedBack;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnYes)
    Button btnYes;

    public DialogCommentFinish(@NonNull Context context) {
        super(context);
        getWindow().setDimAmount(0.5f);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public DialogCommentFinish(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogCommentFinish(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment_finish);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.btnYes, R.id.btnCancel})
    public void OnClickView(View view)
    {
        switch (view.getId()) {
            case R.id.btnYes:
                EventBus.getDefault().postSticky(new FeedBackEvent(txtFeedBack.getText().toString().trim()));
                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }

}
