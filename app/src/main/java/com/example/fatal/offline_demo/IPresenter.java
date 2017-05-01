package com.example.fatal.offline_demo;

import com.example.fatal.offline_demo.presenters.BasePresenter;

/**
 * Created by fatal on 4/30/2017.
 */

public interface IPresenter {
    interface MainActivityPresenter extends BasePresenter<IView.MainActivityFragmentView> {
        void loadPost();

        void loadPostFromRemoteDatatore();
    }

    interface DetailsPresenter extends BasePresenter<IView.DetailsActivityFragmentView> {
        void loadDetailedPost(int id);

        void loadCommentByPostIdFromRemoteDatatore(int id);

        void loadPostById(int postId);
    }
}
