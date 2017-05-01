package com.example.fatal.offline_demo.data.remote;

import android.util.Log;

import com.example.fatal.offline_demo.App;
import com.example.fatal.offline_demo.data.IDataSource;
import com.example.fatal.offline_demo.data.local.AppLocalRepository;
import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.data.local.models.Post;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by fatal on 4/30/2017.
 */

public class AppRemoteRepository implements IRemoteDataSource {
    @Inject
    Retrofit retrofit;

    @Inject
    AppLocalRepository mAppLocalRepository;

    public AppRemoteRepository() {
        App.get().component().inject(this);
    }


    @Override
    public Observable<List<Post>> getPosts() {
        Log.d("REMOTE","Loaded from remote");

        return retrofit.create(PostService.class).getPostList().doOnNext(new Action1<List<Post>>() {
            @Override
            public void call(List<Post> posts) {
                mAppLocalRepository.savePostToDatabase(posts);
            }
        });
    }

    @Override
    public Observable<List<Comment>> getCommentsByPostId(int id) {
        Log.d("REMOTE","Loaded from remote");

        return retrofit.create(CommentService.class).getCommentsByPostId(id).doOnNext(new Action1<List<Comment>>() {
            @Override
            public void call(List<Comment> comments) {
                mAppLocalRepository.saveCommentsToDatabase(comments);
            }
        });
    }

    @Override
    public Observable<Post> getPostById(int id) {
        return null;
    }

    @Override
    public Boolean updatePostsCommentsById(int id) {
        return null;
    }


    private interface PostService {
        @GET("/posts")
        Observable<List<Post>> getPostList();
    }

    private interface CommentService {
        @GET("/posts/{id}/comments")
        Observable<List<Comment>> getCommentsByPostId(@Path("id") int id);

        @GET("/posts/{id}/comments")
        Observable<Boolean> updateCommentsByPostId(@Path("id") int id);
    }
}

