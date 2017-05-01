package com.example.fatal.offline_demo.data.remote;

import com.example.fatal.offline_demo.data.IDataSource;

/**
 * Created by fatal on 5/1/2017.
 */

public interface IRemoteDataSource extends IDataSource{
    Boolean updatePostsCommentsById(int id);
}
