package com.denny.gitsample2.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.denny.gitsample2.R;
import com.denny.gitsample2.bean.UserDetailBean;
import com.denny.gitsample2.viewmodel.MainViewModel;
import com.denny.gitsample2.viewmodel.UserDetailViewModel;

public class UserDeatilActivity extends AppCompatActivity {
    ImageView img_user;
    TextView tv_username;
    TextView tv_person_name;
    TextView tv_location;
    TextView tv_link;

    private String login;
    private UserDetailViewModel  viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatil);
        if (savedInstanceState != null) {
            login = savedInstanceState.getString("login");
        } else {
            login = getIntent().getStringExtra("login");
        }

        if (login == null || login.isEmpty()) {
            finish();
        }
        init();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("login", login);
    }

    private void init() {
        img_user = findViewById(R.id.img_user);
        tv_username = findViewById(R.id.tv_username);
        tv_person_name = findViewById(R.id.tv_person_name);
        tv_location = findViewById(R.id.tv_location);
        tv_link = findViewById(R.id.tv_link);


        viewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);
        viewModel.bean.observe(this, new Observer<UserDetailBean>() {
            @Override
            public void onChanged(UserDetailBean user) {
                Glide
                        .with(UserDeatilActivity.this)
                        .load(user.avatar_url)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(img_user);

                tv_username.setText(user.name);
                tv_person_name.setText(user.login);
                tv_location.setText(user.location);
                tv_link.setText(user.blog);
            }
        });
        viewModel.init(login);
    }
}