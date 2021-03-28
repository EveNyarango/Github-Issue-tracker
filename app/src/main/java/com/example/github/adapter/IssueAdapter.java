package com.example.github.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private List<GithubIssue> mIssuesList;

    public IssueAdapter(List<GithubIssue> issues){
        mIssuesList  = issues;
    }


    @NonNull
    @Override
    public IssueAdapter.IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issues_row, parent, false);
        IssueViewHolder viewHolder = new IssueViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull IssueAdapter.IssueViewHolder holder, int position) {
        holder.bindAll(mIssuesList.get(position));
//        Picasso.with(context).load(mIssuesList.get(position).getUser().getAvatarUrl()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mIssuesList.size();
    }

    public void newList(List<GithubIssue> issueList){
        mIssuesList = issueList;
        notifyDataSetChanged();
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.createdat) TextView mCreateDate;
        @BindView(R.id.title) TextView mTitle;
        @BindView(R.id.owner) TextView mOwner;
        @BindView(R.id.comments) TextView mComments;
        @BindView(R.id.url) Button mUrl;
//        @BindView(R.id.avatar) CircleImageView mAvatar;
        public IssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindAll(GithubIssue githubIssue) {
            SimpleDateFormat input = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat output = new SimpleDateFormat( "dd MMMM yyyy");
            Date d = null;
            try{
                d = input.parse(githubIssue.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mCreateDate.setText(output.format(d));
            mTitle.setText(githubIssue.getTitle());
            mOwner.setText(githubIssue.getUser().getLogin());
            mComments.setText(String.valueOf(githubIssue.getComments()));
            mUrl.setText(githubIssue.getState());

        }
    }
}
