package com.test.dennesvenancio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.dennesvenancio.R;
import com.test.dennesvenancio.databinding.ItemListBinding;
import com.test.dennesvenancio.model.ListItem;
import com.test.dennesvenancio.view.DetailsActivity;
import com.test.dennesvenancio.viewmodel.ItemViewModel;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ItemViewHolder> {

    public List<ListItem> listItems;
    public Activity activity;

    public ListViewAdapter() {
        this.listItems = Collections.emptyList();
    }

    public ListViewAdapter(List<ListItem> listItems, Activity activity) {
        this.listItems = listItems;
        this.activity = activity;
    }

    public void setRepositories(List<ListItem> retailers) {
        this.listItems = retailers;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_list,
                parent,
                false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindProduct(listItems.get(position));
    }

    public void cleanup() {
        listItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemViewModel.ItemChange{
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        private ItemListBinding binding;


        public ItemViewHolder(ItemListBinding itemProductBinding) {
            super(itemProductBinding.getRoot());

            this.binding = itemProductBinding;
            setIsRecyclable(false);
        }

        public void bindProduct(ListItem item) {
            if (binding.getItem() == null) {
            //    binding.cardView.setBackgroundColor(itemView.getResources().getColor(R.color.background_card));
                binding.setItem(new ItemViewModel(itemView.getContext(), item, this));
            } else {
                binding.getItem().setItem(item);
            }

            binding.setHandler(binding.getItem().getHandlers());
        }

        @Override
        public void openItem() {
            Intent intent = new Intent(itemView.getContext(), DetailsActivity.class);

            Pair<View, String> p2 = Pair.create((View)binding.image, "head");
            Pair<View, String> p3 = Pair.create((View)binding.name, "name");
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(activity, p2, p3);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                itemView.getContext().startActivity(intent, options.toBundle());
            }else{
                itemView.getContext().startActivity(intent);
            }

            EventBus.getDefault().postSticky(binding.getItem());
        }
    }

}
