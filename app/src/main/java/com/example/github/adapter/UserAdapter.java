package com.example.github.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.models.Item;
import com.example.github.ui.UserReposActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<Item> mUsers;

    public UserAdapter(List<Item> mUsers) {
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        holder.bindUser(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.userAvatarImageView)
        CircleImageView mUserAvatar;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.userNameTextView)
        TextView mUserName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.reposButton)
        Button mRepoButton;

        private final Context mContext;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            ButterKnife.bind(this,itemView);
            mRepoButton.setOnClickListener(this);
        }

        public void bindUser(Item user){
//            Picasso.get().load(user.getAvatarUrl()).error(R.drawable.profile_icon).into(mUserAvatar);
            Picasso.with(mContext).load(user.getAvatarUrl()).into(mUserAvatar);
           

            mUserName.setText(user.getLogin());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, UserReposActivity.class);
            intent.putExtra("userName", mUsers.get(itemPosition).getLogin());
            mContext.startActivity(intent);
        }
    }
}
