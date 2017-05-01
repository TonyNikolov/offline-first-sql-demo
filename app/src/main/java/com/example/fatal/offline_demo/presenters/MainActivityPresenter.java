package com.example.fatal.offline_demo.presenters;

import android.util.Log;

import com.example.fatal.offline_demo.IPresenter;
import com.example.fatal.offline_demo.IView;
import com.example.fatal.offline_demo.data.AppRepository;
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

public class MainActivityPresenter implements IPresenter.MainActivityPresenter {

    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private Subscription mSubscription;
    private AppRepository mAppRepository;
    private IView.MainActivityFragmentView  mView;

    @Inject
    public MainActivityPresenter(AppRepository mAppRepository){
        this.mAppRepository = mAppRepository;
    }


    @Override
    public void loadPost() {
        mSubscription = mAppRepository.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Post>>() {
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
                    public void onNext(List<Post> posts) {
                        mView.showPosts(posts);
                    }
                });
    }

    @Override
    public void loadPostFromRemoteDatatore() {
        new AppRemoteRepository().getPosts().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        mView.showComplete();
                        loadPost();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        mView.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                    }
                });
    }


    @Override
    public void subscribe() {
        loadPost();
    }

    @Override
    public void unsubscribe() {
        //Unsubscribe Rx subscription
        if (mSubscription != null && mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void setView(IView.MainActivityFragmentView view) {
        this.mView = view;
    }

}
