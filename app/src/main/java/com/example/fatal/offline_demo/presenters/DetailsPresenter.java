package com.example.fatal.offline_demo.presenters;

import android.util.Log;

import com.example.fatal.offline_demo.IPresenter;
import com.example.fatal.offline_demo.IView;
import com.example.fatal.offline_demo.data.AppRepository;
import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.data.local.models.Post;
import com.example.fatal.offline_demo.data.remote.AppRemoteRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fatal on 4/30/2017.
 */


public class DetailsPresenter implements IPresenter.DetailsPresenter {
    private static final String TAG = DetailsPresenter.class.getSimpleName();
    private Subscription mSubscription;
    private AppRepository mAppRepository;
    private IView.DetailsActivityFragmentView  mView;

    @Inject
    public DetailsPresenter(AppRepository mAppRepository){
        this.mAppRepository = mAppRepository;
    }

    @Override
    public void subscribe() {
        loadDetailedPost(mView.getPostId());
    }

    @Override
    public void unsubscribe() {
        //Unsubscribe Rx subscription
        if (mSubscription != null && mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void setView(IView.DetailsActivityFragmentView view) {
        this.mView = view;
    }

    @Override
    public void loadDetailedPost(int id) {
        mSubscription = mAppRepository.getPostById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        mView.showError(e.toString());
                    }

                    @Override
                    public void onNext(Post post) {
                        post.resetComments();
                        mView.showComments(post.getComments());
                    }
                });
    }

    @Override
    public void loadCommentByPostIdFromRemoteDatatore(final int id) {
        new AppRemoteRepository().getCommentsByPostId(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        mView.showComplete();
                        loadDetailedPost(id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        mView.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                    }
                });
    }

    @Override
    public void loadPostById(int postId) {
        mAppRepository.getPostById(postId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        mView.showError(e.toString());
                    }

                    @Override
                    public void onNext(Post post) {
                        mView.showComments(post.getComments());
                    }
                });
    }
}
