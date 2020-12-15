package com.example.login.presenter;

import androidx.annotation.Nullable;

public class LoginServiceHolder {
    ILoginService myService = null;

    @Nullable
    public ILoginService get() {
        return myService;
    }

    public void set(ILoginService myService) {
        this.myService = myService;
    }
}
