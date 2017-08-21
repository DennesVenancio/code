package com.test.dennesvenancio.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.common.eventbus.Subscribe;
import com.test.dennesvenancio.R;
import com.test.dennesvenancio.databinding.ActivityDetailsBinding;
import com.test.dennesvenancio.model.ListItem;
import com.test.dennesvenancio.viewmodel.ItemViewModel;

import de.greenrobot.event.EventBus;

public class DetailsActivity extends AppCompatActivity {


    private ActivityDetailsBinding activityDetailsBinding;
    EventBus eventBus = EventBus.getDefault();
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        setSupportActionBar(activityDetailsBinding.toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        EventBus.getDefault().registerSticky(this);

        if(itemViewModel != null){
            activityDetailsBinding.setItem(itemViewModel);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(ItemViewModel itemViewModel){
        this.itemViewModel = itemViewModel;

        if(activityDetailsBinding != null)
            activityDetailsBinding.setItem(itemViewModel);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }
}
