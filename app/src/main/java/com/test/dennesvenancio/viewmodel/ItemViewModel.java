package com.test.dennesvenancio.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.test.dennesvenancio.generic.ViewModel;
import com.test.dennesvenancio.model.ListItem;

import java.io.Serializable;

/**
 * Created by dennes on 25/09/16.
 */
public class ItemViewModel implements ViewModel{


    private ListItem item;
    private Context context;
    private Handlers handlers;
    private ItemChange itemChange;

    public ItemViewModel(Context context, ListItem item, ItemChange itemChange) {
        this.context = context;
        this.item = item;
        this.itemChange = itemChange;

        handlers = new Handlers();
    }

    @Override
    public void destroy() {

    }

    public void setItem(ListItem item) {
        this.item = item;
    }

    public ListItem getItem() {
        return item;
    }

    public Handlers getHandlers() {
        return handlers;
    }

    public String getName(){
        if(item != null){
            return item.getName();
        }

        return "";
    }

    public String getTime(){
        if(item != null){
            return item.getTime();
        }

        return "";
    }

    public String getFavorites(){
        if(item != null){
            return ""+item.getBookmarks().size();
        }

        return "";
    }

    public String getImage(){
        if(item != null){
            return item.getImage();
        }

        return "";
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .into(view);
    }

    public interface ItemChange {

        void openItem();

    }

    public class Handlers {

        public void openItem(View view){
            itemChange.openItem();
        }

    }
}
