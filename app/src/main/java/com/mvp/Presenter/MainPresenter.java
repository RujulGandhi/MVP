package com.mvp.Presenter;

import com.mvp.MainView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by good on 9/14/2017.
 */

public class MainPresenter {
    MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }


    public boolean checkValidation(String username, String password) {
        // here is your validation
        if (username.length() > 2) {
            mainView.onEmailInValid();
            return false;
        }

        if (password.length() > 3) {
            mainView.onPasswordInvalid();
            return false;
        }

        return true;
    }

    public String getApiResponse(HashMap<String, String> i) {
        // here wil be api calling code
        if (i.get("username").equalsIgnoreCase("admin")) {
//  here you will get some model class object or some arrayList
            ArrayList<String> array = new ArrayList<>();

            mainView.setOnSucess(array);
        } else {
            String errorMsg = "";
            mainView.setOnFailure(errorMsg);
        }

        return "";
    }

}
