package com.example.zeeshan.supermario.news.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.icu.text.Replaceable;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.zeeshan.supermario.view.EndlessRecyclerView;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsfeedFragment extends Fragment implements EndlessRecyclerView.Pager{
    private HashMap<Integer, String> urlsMap;
    private View contentView;
    private RecyclerView mRecyclerView;
    private NewsfeedAdapter adapter;
    ProgressDialog pd;
    private List<ResponseModel> responseList;
    public  ResponseModel selectedFeed;
    private static final int ITEMS_ON_PAGE = 10;
    private static int TOTAL_PAGES = 1;
    private static final long DELAY = 1000L;
    public int total_transactions;
    private final Handler handler = new Handler();
    private EndlessRecyclerView list;
    private boolean loading = false;
    List<ResponseModel> items1 = new ArrayList<>();
    public int getPageNo() {
        return pageNo;
    }
    public boolean firstCall = true;
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    private NewsfeedFragment.OnFeedFragmentInteractionListener mListener;

    public int pageNo = 1;

    public static NewsfeedFragment newInstance() {
        return new NewsfeedFragment();
    }

    public NewsfeedFragment() {
        // Required empty public constructor
    }

    public void onFeedClicked() {
        if (mListener != null) {
            mListener.onFeedFragmentInteraction(selectedFeed);
        }
    }

    public void onFeedFetched(ResponseModel firstFeed) {
        if (mListener != null) {
            mListener.onFeedFetched(firstFeed);
        }
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
//        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.my_recycler_view);
        list = (EndlessRecyclerView) contentView.findViewById(R.id.my_recycler_view);
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



        Call<List<ResponseModel>> call = service.getNewsfeed("308827", Integer.toString(pageNo));
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
                if (null != items1) items1.clear();
                if (response != null) {
                    responseList = new ArrayList<>();
                    responseList = response.body();
                    total_transactions = responseList.size();
                    /*String url = "";
                    for(ResponseModel rm : responseList){
                        url = rm.getLinks().getWpFeaturedmedia().get(0).getHref();
                        url = url.substring(url.lastIndexOf(',') + 1).trim();
                        getImageUrls(rm.getId(),url);
                    }*/
                    pd.dismiss();

                    addItems();
                    adapter = new NewsfeedAdapter(getActivity(), items1, NewsfeedFragment.this);

                    /*mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
//                    mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
                    mRecyclerView.setAdapter(adapter);*/


//                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    mRecyclerView.setHasFixedSize(true);
//                    mRecyclerView.setAdapter(adapter);




                    list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    list.setHasFixedSize(true);
                    list.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                    list.setProgressView(R.layout.item_progress);
                    list.setAdapter(adapter);
                    list.setPager(NewsfeedFragment.this);
                    firstCall = false;
                    setPageNo(getPageNo() + 1);

                    onFeedFetched(items1.get(0));

                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                // Log error here since request failed
                Log.e("failure", "failure");
                pd.dismiss();

//                if (items1 != null && items1.size() == 0) showEmptyView();
//                else {
                    if (list != null && list.getAdapter() != null && items1 != null) {
                        items1.clear();
                        responseList = null;
                        list.getAdapter().notifyDataSetChanged();
                    }
//                }
            }
        });
    }


    @Override
    public boolean shouldLoad() {
        if (items1 != null && responseList != null && responseList.size() > 0 /*!(responseList.size() < ITEMS_ON_PAGE)*/)
            return true;
        else return false;
    }

    @Override
    public void loadNextPage() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (responseList != null && responseList.size() > 0 /*!(responseList.size() < ITEMS_ON_PAGE)*/) {
                    fetchMoreWrapper();
                } else {
                    loading = false;
                    list.setRefreshing(false);
                }
            }
        }, DELAY);
    }


    private void addItems() {
        int counter = 0;
        if (responseList.size() > 0) {

            for (int i = 0; i < responseList.size(); i++) {
                items1.add(responseList.get(i));
                counter++;
            }
        }
    }

    public void fetchMoreWrapper() {
        if (!(responseList.size() < ITEMS_ON_PAGE)) {
            String cardRefNo = "";

           fetchMoreTransactions(cardRefNo, "", "", "", Integer.toString(pageNo));

        } else {
            loading = false;
            list.setRefreshing(false);
        }
    }

    public void fetchMoreTransactions(final String cardRefNo, final String transactionType, final String dateFrom, final String dateTo, String pageNo) {

        if (!loading) {

            loading = true;

            NewsfeedService service =
                    ApiClient.getClient().create(NewsfeedService.class);

            ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<>();
            paramsMap.put("tags", "308827");
            paramsMap.put("page", pageNo);
            Call<List<ResponseModel>> call = service.getNewsfeed("308827", pageNo);


             call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {

                    responseList = response.body();
                    total_transactions = responseList.size();
                    addItems();
                    adapter = new NewsfeedAdapter(getActivity(), items1, NewsfeedFragment.this);
                    list.getAdapter().notifyDataSetChanged();
                    setPageNo(getPageNo() + 1);
                    loading = false;
                    list.setRefreshing(false);


                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    loading = false;
                    list.setRefreshing(false);
                    responseList = null;

                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NewsfeedFragment.OnFeedFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFeedFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFeedFragmentInteraction( ResponseModel selectedFeed);
        void onFeedFetched( ResponseModel firstFeed);
    }



}