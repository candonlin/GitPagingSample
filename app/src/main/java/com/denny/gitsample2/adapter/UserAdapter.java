package com.denny.gitsample2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.denny.gitsample2.R;
import com.denny.gitsample2.bean.UserBean;

import org.jetbrains.annotations.NotNull;

/**
 * author: Denny
 * created on: 2022/3/7 下午 04:10
 */
public class UserAdapter extends PagingDataAdapter<UserBean, UserAdapter.MyViewHolder> {
    // Define Loading ViewType
    public static final int LOADING_ITEM = 0;
    // Define Movie ViewType
    public static final int MOVIE_ITEM = 1;
    RequestManager glide;

    private OnClickListner onClickListner;

    public UserAdapter(@NotNull DiffUtil.ItemCallback<UserBean> diffCallback, RequestManager glide) {
        super(diffCallback);
        this.glide = glide;
    }

    public void setOnClickListner(OnClickListner onClickListner) {
        this.onClickListner = onClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Return MovieViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Get current movie
        UserBean user = getItem(position);
        // Check for null
        if (user != null) {
            Glide
                    .with(holder.itemView)
                    .load(user.avatar_url)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(holder.img_user);
            holder.tv_name.setText(user.login);
//
        }
    }

    @Override
    public int getItemViewType(int position) {
        // set ViewType
        return position == getItemCount() ? MOVIE_ITEM : LOADING_ITEM;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_user;
        TextView tv_name;

        public MyViewHolder(@NonNull View view) {
            super(view);
            // init binding
            this.img_user = view.findViewById(R.id.img_user);
            this.tv_name = view.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListner.onClick(getItem(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }

   public interface OnClickListner {
        void onClick(UserBean bean, int pos);
    }
}
