package com.example.zeeshan.supermario.news.fragment;


import android.app.ProgressDialog;
import android.icu.text.Replaceable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshan.supermario.R;
import com.example.zeeshan.supermario.api.ApiClient;
import com.example.zeeshan.supermario.model.ResponseModel;
import com.example.zeeshan.supermario.model.Title;
import com.example.zeeshan.supermario.news.adapter.NewsfeedAdapter;
import com.example.zeeshan.supermario.news.service.NewsfeedService;
import com.example.zeeshan.supermario.utility.Utility;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsfeedFragment extends Fragment {
    private HashMap<Integer, String> urlsMap;
    private View contentView;
    private RecyclerView mRecyclerView;
    private NewsfeedAdapter adapter;
    ProgressDialog pd;
    private List<ResponseModel> responseList;

    public static NewsfeedFragment newInstance() {
        return new NewsfeedFragment();
    }

    public NewsfeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pd = ProgressDialog.show(getActivity(), null, "Processing");
        pd.setCanceledOnTouchOutside(false);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.my_recycler_view);
        urlsMap = new HashMap<>();
        getNewsfeed();
       /* List<ResponseModel> modelList = new ArrayList<>();
        ResponseModel dummy = new ResponseModel();
        modelList.add(dummy);
        dummy = new ResponseModel();
        modelList.add(dummy);
        dummy = new ResponseModel();
        modelList.add(dummy);
        dummy = new ResponseModel();
        modelList.add(dummy);
        dummy = new ResponseModel();
        modelList.add(dummy);
        dummy = new ResponseModel();
        modelList.add(dummy);
        dummy = new ResponseModel();
        modelList.add(dummy);
        dummy = new ResponseModel();
        modelList.add(dummy);
        adapter = new NewsfeedAdapter(getActivity(), modelList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(adapter);*/

    }

    public void getNewsfeed() {
        NewsfeedService service =
                ApiClient.getClient().create(NewsfeedService.class);


        Call<List<ResponseModel>> call = service.getNewsfeed("308827");
//        pd.show();
        if (null != pd && !pd.isShowing())
            pd = ProgressDialog.show(getActivity(), null, "Processing");
        pd.setCanceledOnTouchOutside(false);
        if (!Utility.isThereInternetConnection(getActivity())) {
            Toast.makeText(getActivity(), "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
            if (null != pd)
                pd.dismiss();
            return;
        }
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {

                if (response != null) {
                    responseList = response.body();
                    /*String url = "";
                    for(ResponseModel rm : responseList){
                        url = rm.getLinks().getWpFeaturedmedia().get(0).getHref();
                        url = url.substring(url.lastIndexOf(',') + 1).trim();
                        getImageUrls(rm.getId(),url);
                    }*/
                    pd.dismiss();
                    adapter = new NewsfeedAdapter(getActivity(), responseList);

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
//                    mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
                    mRecyclerView.setAdapter(adapter);

//                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    mRecyclerView.setHasFixedSize(true);
//                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                // Log error here since request failed
                Log.e("failure", "failure");
                pd.dismiss();

            }
        });
    }


}