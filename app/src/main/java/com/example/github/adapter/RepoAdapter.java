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
import com.example.github.models.Repository;
import com.example.github.ui.RepoIssuesActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private final List<Repository> mRepos;

    public RepoAdapter(List<Repository> mRepos) {
        this.mRepos = mRepos;
    }

    @NonNull
    @Override
    public RepoAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepoAdapter.RepoViewHolder holder, int position) {
        holder.bindRepo(mRepos.get(position));
    }


    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.repoDescTextView)
        TextView mRepoDesc;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.repoOwnerTextView)
        TextView mRepoOwner;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.repoNameTextView)
        TextView mRepoName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.issuesButton)
        Button mIssuesButton;

        private final Context mContext;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
            mIssuesButton.setOnClickListener(this);
        }

        public void bindRepo(Repository repo){
            mRepoDesc.setText(repo.getDescription());
            mRepoOwner.setText(repo.getOwner().getLogin());
            mRepoName.setText(repo.getName());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RepoIssuesActivity.class);
            intent.putExtra("repoName", mRepos.get(itemPosition).getName());
            intent.putExtra("userName", mRepos.get(itemPosition).getOwner().getLogin());
            mContext.startActivity(intent);
        }
    }


}
