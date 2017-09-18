package com.mvp;

import java.util.ArrayList;

/**
 * Created by good on 9/14/2017.
 */

public interface MainView {

    public void onEmailInValid();

    public void onPasswordInvalid();

    public void setOnSucess(ArrayList<String> array);

    public void setOnFailure(String errorMsg);
}
