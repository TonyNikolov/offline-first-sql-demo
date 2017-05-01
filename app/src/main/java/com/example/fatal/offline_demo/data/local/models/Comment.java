package com.example.fatal.offline_demo.data.local.models;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by fatal on 4/30/2017.
 */
@Entity
public class Comment {

    @Id
    @NotNull
    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private String name;

    @NonNull
    private String email;

    @NotNull
    private String body;

    @ToOne(joinProperty = "postId")
    private Post post;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1903578761)
    private transient CommentDao myDao;

    @Generated(hash = 167259329)
    public Comment(@NotNull Long id, @NotNull Long postId, @NotNull String name,
            @NotNull String email, @NotNull String body) {
        this.id = id;
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    @Generated(hash = 1669165771)
    public Comment() {
    }

    @Generated(hash = 1690682906)
    private transient Long post__resolvedKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1638466291)
    public Post getPost() {
        Long __key = this.postId;
        if (post__resolvedKey == null || !post__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PostDao targetDao = daoSession.getPostDao();
            Post postNew = targetDao.load(__key);
            synchronized (this) {
                post = postNew;
                post__resolvedKey = __key;
            }
        }
        return post;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 197523274)
    public void setPost(@NotNull Post post) {
        if (post == null) {
            throw new DaoException(
                    "To-one property 'postId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.post = post;
            postId = post.getId();
            post__resolvedKey = postId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2038262053)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommentDao() : null;
    }
}
