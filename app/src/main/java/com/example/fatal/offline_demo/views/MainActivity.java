package com.example.fatal.offline_demo.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fatal.offline_demo.App;
import com.example.fatal.offline_demo.IView;
import com.example.fatal.offline_demo.R;
import com.example.fatal.offline_demo.data.AppRepository;
import com.example.fatal.offline_demo.data.local.models.Post;
import com.example.fatal.offline_demo.presenters.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements IView.MainActivityFragmentView, SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private ArrayList list;
    private ArrayAdapter adapter;

    @Inject
    AppRepository repository;

    @Inject
    MainActivityPresenter mPresenter;

    SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.get().component().inject(this);
        mPresenter.setView(this);

        listView = (ListView) findViewById(R.id.my_list);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        list = new ArrayList<>();

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                // WARNING, THIS IS INCORRECT DO NOT REPLICATE!!!
                intent.putExtra("PostId",position);
                // END OF WARNING
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        };

        listView.setOnItemClickListener(listener);

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("Are you sure you want to exit?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public void showPosts(List<Post> posts) {
        for (int i = 0; i < posts.size(); i++) {
            list.add(posts.get(i).getTitle());
        }
        //Create the array adapter and set it to list view
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
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
        mPresenter.loadPostFromRemoteDatatore();
    }
}