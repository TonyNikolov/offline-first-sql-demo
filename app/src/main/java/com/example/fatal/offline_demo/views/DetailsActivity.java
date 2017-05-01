package com.example.fatal.offline_demo.views;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fatal.offline_demo.App;
import com.example.fatal.offline_demo.IView;
import com.example.fatal.offline_demo.R;
import com.example.fatal.offline_demo.data.AppRepository;
import com.example.fatal.offline_demo.data.local.models.Comment;
import com.example.fatal.offline_demo.presenters.DetailsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class DetailsActivity extends AppCompatActivity implements IView.DetailsActivityFragmentView, SwipeRefreshLayout.OnRefreshListener{

    private ListView listView;
    private ArrayList list;
    private ArrayAdapter adapter;
    PostWithCommentsAdapter commentsAdapter;
    int postId;

    @Inject
    AppRepository repository;

    @Inject
    DetailsPresenter mPresenter;

    RecyclerView rvComments;

    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        App.get().component().inject(this);
        mPresenter.setView(this);

        postId = getIntent().getIntExtra("PostId",0);

        //listView = (ListView) findViewById(R.id.my_list);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        list = new ArrayList<>();

        commentsAdapter = new PostWithCommentsAdapter(this);
        rvComments = (RecyclerView)findViewById(R.id.my_rv_list);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(commentsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onRefresh() {

        //Because we are using fake online REST API,
        // which requires a separete call to get Post's comments,
        //we have to do this

        //mPresenter.loadPostById(postId);
        mPresenter.loadCommentByPostIdFromRemoteDatatore(postId);
    }

    @Override
    public int getPostId() {
        return postId;
    }

    @Override
    public void showComments(List<Comment> comments) {
        //   for (int i = 0; i < comments.size(); i++) {
        //list.add(comments.get(i).getName());
        //    }
        //Create the array adapter and set it to list view
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(adapter);

        commentsAdapter.setData(comments);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading post", Toast.LENGTH_SHORT).show();
        if (swipeContainer != null)
            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
    }

    @Override
    public void showComplete() {
        Toast.makeText(this, "Completed loading", Toast.LENGTH_SHORT).show();

        if (swipeContainer != null)
            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
    }
}
