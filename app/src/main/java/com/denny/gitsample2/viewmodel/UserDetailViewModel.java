package com.denny.gitsample2.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denny.gitsample2.api.APIClient;
import com.denny.gitsample2.bean.UserDetailBean;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Denny
 * created on: 2022/3/8 下午 03:46
 */
public class UserDetailViewModel extends ViewModel {

    public MutableLiveData< UserDetailBean> bean=new MutableLiveData<>();

    public UserDetailViewModel() {

    }

    // Init ViewModel Data
    public void init(String login) {
        // Define Paging Source
//        APIClient.getAPIInterface().getUserDetail(login)
//                .subscribeOn(Schedulers.io())

                Call<UserDetailBean> call = APIClient.getAPIInterface().getUserDetail(login);
        call.enqueue(new Callback<UserDetailBean>() {
            @Override
            public void onResponse(Call<UserDetailBean> call, Response<UserDetailBean> response) {
                bean.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserDetailBean> call, Throwable t) {

            }
        });



    }
}
