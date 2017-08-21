package com.test.dennesvenancio.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.test.dennesvenancio.R;
import com.test.dennesvenancio.adapter.ListViewAdapter;
import com.test.dennesvenancio.databinding.ActivityListViewBinding;
import com.test.dennesvenancio.model.ListItem;
import com.test.dennesvenancio.viewmodel.ListViewModel;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements ListViewModel.ListViewChange, AppBarLayout.OnOffsetChangedListener{

    private ListViewModel listViewModel;
    private ActivityListViewBinding activityListViewBinding;
    private ListViewAdapter listViewAdapter;

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_view);


        listViewModel = new ListViewModel(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        AppBarLayout appbarLayout = activityListViewBinding.materialupAppbar;
        mProfileImage = activityListViewBinding.materialupProfileImage;

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        activityListViewBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listViewModel.loadList();

                if(listViewAdapter != null) {
                    listViewAdapter.cleanup();
                    activityListViewBinding.listItems.setAdapter(null);
                }
            }
        });

        activityListViewBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void onLoadItems(List<ListItem> listItems) {

        if(activityListViewBinding.listItems.getAdapter() == null){

            listViewAdapter = new ListViewAdapter(listItems, this);

            activityListViewBinding.listItems.setAdapter(listViewAdapter);
            activityListViewBinding.listItems.setLayoutManager(new GridLayoutManager(this, 2));
        }else{

            activityListViewBinding.listItems.getAdapter().notifyDataSetChanged();
        }

        activityListViewBinding.swipeContainer.setRefreshing(false);

       activityListViewBinding.countRecipes.setText(listItems.size() +" "+ this.getString(R.string.recipes_favorites));

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }
}
