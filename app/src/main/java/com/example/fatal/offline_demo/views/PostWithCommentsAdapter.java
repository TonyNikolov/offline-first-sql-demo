package com.example.fatal.offline_demo.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fatal.offline_demo.R;
import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.data.local.models.Post;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by fatal on 4/30/2017.
 */


public class PostWithCommentsAdapter extends RecyclerView.Adapter<PostWithCommentsAdapter.ViewHolder> {


    private final Context context;
    private List<Comment> comments;
    private Post post;

    PostWithCommentsAdapter(Context context){
        this.context = context;
    }

    public void setData(@NonNull Post post, @Nullable List<Comment> comments) {
        this.comments = comments;
        this.post = post;
        notifyDataSetChanged();
    }

    public void setData( @Nullable List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_post_with_comments_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Comment comment = comments.get(position);

        holder.authorCommentView.setText("@" + comment.getName());
        holder.commentBodyView.setText(comment.getBody());
        holder.commentIdView.setText(String.format("comment id: %s", comment.getId()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView authorCommentView;
        TextView commentBodyView;
        TextView commentIdView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            commentIdView = (TextView) itemView.findViewById(R.id.list_item_comment_id);
            authorCommentView = (TextView) itemView.findViewById(R.id.list_item_comment_author);
            commentBodyView = (TextView) itemView.findViewById(R.id.list_item_comment_body);
        }
    }
}
