package com.example.github.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.models.GithubIssue;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {
    private List<GithubIssue> mIssueList;

    public IssueAdapter(List<GithubIssue> issuesList) {

        mIssueList = issuesList;
    }
    @NonNull
    @Override
    public IssueAdapter.IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issues_row, parent, false);
        return new IssueViewHolder(view);
    }
    @Override
    public void onBindViewHolder(IssueAdapter.IssueViewHolder holder, int position) {
        holder.bindAll(mIssueList.get(position));
    }
    @Override
    public int getItemCount() {
        return mIssueList.size();
    }

    public void updateList(List<GithubIssue> repoIssues) {
        mIssueList = repoIssues;
        notifyDataSetChanged();
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvCreatedat) TextView mCreateDate;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvTitle) TextView mTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvRepo) TextView mRepo;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvOwner) TextView mOwner;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvComments) TextView mComments;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_Url) TextView mUrl;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivAvatar) CircleImageView mAvatar;
        private final Context mContext;
        public IssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
        @Override
        public void onClick(View v) {

        }
        @SuppressLint("SetTextI18n")
        public void bindAll(GithubIssue githubIssue) {

            Picasso.with(mContext).load(githubIssue.getUser().getAvatarUrl()).into(mAvatar);
            mTitle.setText(githubIssue.getTitle());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat input = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat( "dd MMMM yyyy");
            Date d = null;
            try{
                d = input.parse(githubIssue.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mCreateDate.setText("Created: " + output.format(d));
            mRepo.setText(githubIssue.getRepository().getName());
            mOwner.setText(githubIssue.getUser().getLogin());
            mComments.setText(String.valueOf(githubIssue.getComments()));
            //mUrl.setText(githubIssue.getHtmlUrl());
        }
    }


}
