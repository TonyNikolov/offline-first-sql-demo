package com.example.fatal.offline_demo.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fatal.offline_demo.data.IDataSource;
import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.data.local.models.CommentDao;
import com.example.fatal.offline_demo.data.local.models.DaoMaster;
import com.example.fatal.offline_demo.data.local.models.DaoSession;
import com.example.fatal.offline_demo.data.local.models.Post;
import com.example.fatal.offline_demo.data.local.models.PostDao;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by fatal on 4/30/2017.
 */


public class AppLocalRepository implements IDataSource {
    private Context context;
    private DaoSession daoSession;
    public static final boolean ENCRYPTED = true;

    @Inject
    public AppLocalRepository(@NonNull Context context) {
        this.context = context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    @Override
    public Observable<List<Post>> getPosts() {
        Log.d("LOCAL", "Loaded from local");

        return daoSession
                .getPostDao()
                .rx()
                .loadAll();
    }

    @Override
    public Observable<List<Comment>> getCommentsByPostId(int id) {
        Log.d("LOCAL", "Loaded from local");
        return daoSession
                .getCommentDao()
                .queryBuilder()
                .where(CommentDao.Properties.PostId.eq(id))
                .orderAsc(CommentDao.Properties.Id)
                .rx()
                .list();
    }

    @Override
    public Observable<Post> getPostById(int id) {
        Log.d("LOCAL", "Loaded from local");
        return daoSession
                .getPostDao()
                .queryBuilder()
                .where(PostDao.Properties.Id.eq(id))
                .rx()
                .unique();
    }


    public void savePostToDatabase(List<Post> posts) {
        daoSession.getPostDao().insertOrReplaceInTx(posts);
    }

    public void saveCommentsToDatabase(List<Comment> comments) {
        daoSession.getCommentDao().insertOrReplaceInTx(comments);
    }
}