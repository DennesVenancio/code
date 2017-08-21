package com.test.dennesvenancio.services;

import com.test.dennesvenancio.model.ListItem;
import com.test.dennesvenancio.util.Urls;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by dennes on 24/09/16.
 */
public class ListViewService {

    private ListServiceAPI listServiceAPI;

    public ListViewService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.LIST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        listServiceAPI = retrofit.create(ListServiceAPI.class);
    }

    public ListServiceAPI getListServiceAPI() {
        return listServiceAPI;
    }

    public interface ListServiceAPI {

        @GET(Urls.RECIPES)
        Observable<List<ListItem>> getList();

    }
}
