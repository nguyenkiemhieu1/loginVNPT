package com.example.login.view.viewevent;

import com.example.login.model.APIError;
import com.example.login.model.respone.JobPositionInfo;
import com.example.login.model.respone.UnitInfo;

import java.util.List;

public interface IFilterPersonView {
    void onSuccessJobPossitions(List<JobPositionInfo> jobPositionInfos);
    void onSuccessUnits(List<UnitInfo> unitInfos);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}