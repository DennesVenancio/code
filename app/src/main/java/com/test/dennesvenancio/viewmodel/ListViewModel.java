package com.test.dennesvenancio.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.test.dennesvenancio.BR;
import com.test.dennesvenancio.generic.ViewModel;
import com.test.dennesvenancio.model.ListItem;
import com.test.dennesvenancio.services.ListViewService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dennes on 24/09/16.
 */
public class ListViewModel extends BaseObservable implements ViewModel{

    private ListViewChange listViewChange;
    private ListViewService listViewService;

    public String countRecipes;

    public ListViewModel(ListViewChange listViewChange) {
        this.listViewChange = listViewChange;
        listViewService = new ListViewService();

        loadList();
    }

    public void loadList(){

        listViewService.getListServiceAPI().getList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ListItem>>() {
                    @Override
                    public void call(List<ListItem> listItems) {
                        listViewChange.onLoadItems(listItems);
                        setCountRecipes(""+listItems.size());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.getStackTrace();
                    }
                });
    }

    public interface ListViewChange {

        void onLoadItems(List<ListItem> listItems);

    }

    @Bindable
    public String getCountRecipes() {
        return countRecipes;
    }

    public void setCountRecipes(String countRecipes) {
        this.countRecipes = countRecipes;
        notifyPropertyChanged(BR.countRecipes);
    }

    @Override
    public void destroy() {

    }
}
