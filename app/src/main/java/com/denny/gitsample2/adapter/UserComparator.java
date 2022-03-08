package com.denny.gitsample2.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.denny.gitsample2.bean.UserBean;

/*
    Comparator for comparing Movie object to avoid duplicates
 */
public class UserComparator extends DiffUtil.ItemCallback<UserBean> {
    @Override
    public boolean areItemsTheSame(@NonNull UserBean oldItem, @NonNull UserBean newItem) {
        return oldItem.avatar_url.equals(newItem.avatar_url);
    }

    @Override
    public boolean areContentsTheSame(@NonNull UserBean oldItem, @NonNull UserBean newItem) {
        return oldItem.avatar_url.equals(newItem.avatar_url);
    }
}
