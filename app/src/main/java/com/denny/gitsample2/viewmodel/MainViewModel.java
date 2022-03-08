package com.denny.gitsample2.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.denny.gitsample2.bean.UserBean;
import com.denny.gitsample2.paging.UserPagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

/**
 * author: Denny
 * created on: 2022/3/7 下午 04:40
 */
public class MainViewModel extends ViewModel {

    public Flowable<PagingData<UserBean>> PagingDataFlowable;
    public MainViewModel() {
        init();
    }

    // Init ViewModel Data
    private void init() {
        // Define Paging Source
        UserPagingSource moviePagingSource = new UserPagingSource();

        // Create new Pager
        Pager<Integer, UserBean> pager = new Pager(
                // Create new paging config
                new PagingConfig(20, //  Count of items in one page
                        20, //  Number of items to prefetch
                        false, // Enable placeholders for data which is not yet loaded
                        20, // initialLoadSize - Count of items to be loaded initially
                        20 * 499),// maxSize - Count of total items to be shown in recyclerview
                () -> moviePagingSource); // set paging source

        // inti Flowable
        PagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(PagingDataFlowable, coroutineScope);

    }
}
