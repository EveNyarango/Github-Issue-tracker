package com.example.github.adapter;

import android.annotation.SuppressLint;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.models.GithubIssue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;


public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {
    private final List<GithubIssue> mIssueList;

    public IssueAdapter(List<GithubIssue> issuesList) {
        mIssueList = issuesList;
    }

    @NonNull
    @Override
    public IssueAdapter.IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_item, parent, false);
        return new IssueViewHolder(view);
    }
    @Override
    public void onBindViewHolder(IssueAdapter.IssueViewHolder holder, int position) {
        holder.bindIssue(mIssueList.get(position));
    }
    @Override
    public int getItemCount() {
        return mIssueList.size();
    }

    public static class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.dateOpenedTextView) TextView mOpenedAt;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.issueDescTextView) TextView mDesc;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.issueOwnerTextView) TextView mOwner;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.commentsTextView) TextView mComments;

        public IssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Context mContext = itemView.getContext();
        }
        @Override
        public void onClick(View v) {

        }
        @SuppressLint("SetTextI18n")
        public void bindIssue(GithubIssue githubIssue) {
            mDesc.setText(githubIssue.getBody() + " #" + githubIssue.getId());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat input = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat( "dd MMMM yyyy");
            Date d = null;
            try{
                d = input.parse(githubIssue.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mOpenedAt.setText("Opened " + output.format(Objects.requireNonNull(d)));
            mOwner.setText(" " + githubIssue.getUser().getLogin());
            mComments.setText(" " + githubIssue.getComments() + " comments");
        }
    }
}
