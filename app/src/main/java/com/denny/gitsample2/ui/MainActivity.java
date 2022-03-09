package com.denny.gitsample2.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.denny.gitsample2.R;
import com.denny.gitsample2.adapter.UserAdapter;
import com.denny.gitsample2.adapter.UserComparator;
import com.denny.gitsample2.adapter.UserLoadStateAdapter;
import com.denny.gitsample2.databinding.ActivityMainBinding;
import com.denny.gitsample2.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainActivityViewModel;
    private UserAdapter adapter;
    private RequestManager requestManager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {

        binding.userRv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(new UserComparator(), requestManager);
        adapter.setOnClickListner((bean, pos) -> {
            Intent i = new Intent(MainActivity.this, UserDeatilActivity.class);
            i.putExtra("login", bean.login);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        binding.userRv.setAdapter(
                // This will show end user a progress bar while pages are being requested from server
                adapter.withLoadStateFooter(
                        // When we will scroll down and next page request will be sent
                        // while we get response form server Progress bar will show to end user
                        new UserLoadStateAdapter(v -> {
                            adapter.retry();
                        }))
        );

        mainActivityViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainActivityViewModel.PagingDataFlowable.subscribe(Data -> {
            // submit new data to recyclerview adapter
            adapter.submitData(getLifecycle(), Data);
        });
    }
}