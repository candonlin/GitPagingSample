package com.denny.gitsample2.api;

import com.denny.gitsample2.bean.UserBean;
import com.denny.gitsample2.bean.UserDetailBean;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class APIClient {
    // Define APIInterface
    static APIInterface apiInterface;

    // create retrofit instance
    public static APIInterface getAPIInterface() {
        if (apiInterface == null) {
            // Create OkHttp Client
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            // Add interceptor to add API key as query string parameter to each request
//            client.addInterceptor(chain -> {
//                Request original = chain.request();
//                HttpUrl originalHttpUrl = original.url();
//                HttpUrl url = originalHttpUrl.newBuilder()
//                        // Add API Key as query string parameter
//                        .addQueryParameter("api_key", API_KEY)
//                        .build();
//                Request.Builder requestBuilder = original.newBuilder()
//                        .url(url);
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            });
            // Create retrofit instance
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .client(client.build())
                    // Add Gson converter
                    .addConverterFactory(GsonConverterFactory.create())
                    // Add RxJava spport for Retrofit
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            // Init APIInterface
            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }

    //API service interface
    public interface APIInterface {
        // Define Get request with query string parameter as page number
        @GET("users")
        Single<ArrayList<UserBean>> getUserByPage(@Query("since") int page, @Query("per_page") int per_page);

        @GET("users/{user}")
        Call<UserDetailBean> getUserDetail(@Path("user") String user);
    }
}
