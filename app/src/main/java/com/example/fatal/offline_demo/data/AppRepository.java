package com.example.fatal.offline_demo.data;

import android.util.Log;

import com.example.fatal.offline_demo.data.local.AppLocalRepository;
import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.data.local.models.Post;
import com.example.fatal.offline_demo.data.remote.AppRemoteRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by fatal on 4/30/2017.
 */


public class AppRepository implements IDataSource {
    private AppLocalRepository mAppLocalRepository;
    private AppRemoteRepository mAppRemoteRepository;

    @Inject
    public AppRepository(AppLocalRepository mAppLocalDataStore, AppRemoteRepository mAppRemoteDataStore) {
        this.mAppLocalRepository = mAppLocalDataStore;
        this.mAppRemoteRepository = mAppRemoteDataStore;
    }


    @Override
    public Observable<List<Post>> getPosts() {
        return Observable.concat(mAppLocalRepository.getPosts(), mAppRemoteRepository.getPosts())
                .first(new Func1<List<Post>, Boolean>() {
                    @Override
                    public Boolean call(List<Post> posts) {
                        //Commeted this log due Arrayindexoutofbound exception at first lauch of application
//                        Log.d("Repo", posts.get(0).getTitle());
                        return posts != null;
                    }
                });
    }

    @Override
    public Observable<List<Comment>> getCommentsByPostId(int id) {
        return Observable.concat(mAppLocalRepository.getCommentsByPostId(id), mAppRemoteRepository.getCommentsByPostId(id))
                .first(new Func1<List<Comment>, Boolean>() {
                    @Override
                    public Boolean call(List<Comment> comments) {
                        //Commeted this log due Arrayindexoutofbound exception at first lauch of application
//                        Log.d("Repo", posts.get(0).getTitle());
                        return comments != null;
                    }
                });
    }


    @Override
    public Observable<Post> getPostById(int id) {
            return mAppLocalRepository.getPostById(id);
    }
}
