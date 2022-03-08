package com.denny.gitsample2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.denny.gitsample2.R;
import com.denny.gitsample2.adapter.UserAdapter;
import com.denny.gitsample2.adapter.UserComparator;
import com.denny.gitsample2.adapter.UserLoadStateAdapter;
import com.denny.gitsample2.bean.UserBean;
import com.denny.gitsample2.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView user_rv;
    private MainViewModel mainActivityViewModel;
    private UserAdapter adapter;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        user_rv = findViewById(R.id.user_rv);
        user_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(new UserComparator(), requestManager);
        adapter.setOnClickListner((bean, pos) -> {
            Intent i =new Intent(MainActivity.this,UserDeatilActivity.class);
            i.putExtra("login",bean.login);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
//            Log.e("測試",bean.login);
        });
//        user_rv.setAdapter(
//                // This will show end user a progress bar while pages are being requested from server
//                adapter.withLoadStateFooter(
//                        // When we will scroll down and next page request will be sent
//                        // while we get response form server Progress bar will show to end user
//                        new UserLoadStateAdapter(v -> {
//                            adapter.retry();
//                        }))
//        );

        user_rv.setAdapter(
                adapter
        );
        mainActivityViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainActivityViewModel.PagingDataFlowable.subscribe(Data -> {
            // submit new data to recyclerview adapter
            adapter.submitData(getLifecycle(), Data);
        });
    }
}