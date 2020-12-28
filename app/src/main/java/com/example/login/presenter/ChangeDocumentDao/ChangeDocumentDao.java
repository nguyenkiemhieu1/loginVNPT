package com.example.login.presenter.ChangeDocumentDao;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.model.BaseDao;
import com.example.login.model.BaseService;
import com.example.login.model.TypeChangeDocRequest;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.TypeChangeDocumentRespone;
import com.example.login.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class ChangeDocumentDao extends BaseDao implements IChangeDocumentDao {
    private IChangeDocumentService changeDocumentService;
    @Override
    public void onSendGetTypeChangeDocumentDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener) {

        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<TypeChangeDocumentRespone> call = changeDocumentService.getTypeChange(typeChangeDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
