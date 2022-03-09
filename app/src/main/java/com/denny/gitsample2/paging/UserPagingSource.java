package com.denny.gitsample2.paging;

import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.denny.gitsample2.api.APIClient;
import com.denny.gitsample2.bean.UserBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserPagingSource extends RxPagingSource<Integer, UserBean> {
    private HashMap<Integer, Integer> map = new HashMap();

    @NotNull
    @Override
    public Single<LoadResult<Integer, UserBean>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        try {
            // If page number is already there then init page variable with it otherwise we are loading fist page
            int page = loadParams.getKey() != null ? loadParams.getKey() : 0;
//            Thread.sleep(2000);
            // Send request to server with page number
            return APIClient.getAPIInterface()
                    .getUserByPage(page, 20)
                    // Subscribe the result
                    .subscribeOn(Schedulers.io())
                    .delaySubscription(2, TimeUnit.SECONDS)
                    // Map result top List of movies
//                    .map(MovieResponse::getResults)
//                    .map(ArrayBean::getArrayList)
//                    .map()
                    // Map result to LoadResult Object
                    .map(it -> toLoadResult(it, page))
//                    .map(item -> toLoadResult())
                    // when error is there return error
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            // Request ran into error return error
            return Single.just(new LoadResult.Error(e));
        }
    }


    // Method to map Movies to LoadResult object
    private LoadResult<Integer, UserBean> toLoadResult(List<UserBean> movies, int beforeKey) {

        int before;
        int after;

        if (beforeKey == 0) {
            before = 0;
            after = movies.get(movies.size() - 1).id;
            map.put(before, after);
        } else {
            int key = getKeyByValue(map, beforeKey);
            before = beforeKey;
            after = movies.get(movies.size() - 1).id;
            map.put(before, after);
            before = key;
//            map.put(before, after);
        }

        return new LoadResult.Page(movies, before == 0 ? null : before, after);
//        return new LoadResult.Page(movies, beforeKey == 1 ? null : beforeKey - 1, beforeKey + 1);

    }

    public static Integer getKeyByValue(HashMap<Integer, Integer> map, int value) {
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, UserBean> pagingState) {
        return null;
    }


}
