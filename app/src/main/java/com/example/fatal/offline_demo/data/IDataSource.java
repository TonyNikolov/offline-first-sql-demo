package com.example.fatal.offline_demo.data;

import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.data.local.models.Post;

import java.util.List;

import rx.Observable;

/**
 * Created by fatal on 4/30/2017.
 */

public interface IDataSource {

    Observable<List<Post>> getPosts();
    Observable<List<Comment>> getCommentsByPostId(int id);
    Observable<Post> getPostById(int id);
}
