package com.example.fatal.offline_demo;

import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.data.local.models.Post;

import java.util.List;

/**
 * Created by fatal on 4/30/2017.
 */

public interface IView {

    interface MainActivityFragmentView {

        void showPosts(List<Post> posts);

        void showError(String message);

        void showComplete();

    }

    interface DetailsActivityFragmentView {
        int getPostId();

        void showComments(List<Comment> comments);

        void showError(String message);

        void showComplete();
    }
}
