package com.example.zeeshan.supermario.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.zeeshan.supermario.R;
import com.example.zeeshan.supermario.api.ApiClient;
import com.example.zeeshan.supermario.model.ResponseModel;
import com.example.zeeshan.supermario.news.fragment.NewsfeedFragment;
import com.example.zeeshan.supermario.news.service.NewsfeedService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private HashMap<Integer, String> urlsMap;
    private final Context mContext;
    private List<ResponseModel> transactionsList;
    private LayoutInflater mLayoutInflater;
    private NewsfeedFragment parentFrag;


    public NewsfeedAdapter(Context _mContext, List<ResponseModel> _transactionsList, NewsfeedFragment parentFrag) {
        this.mContext = _mContext;
        this.transactionsList = _transactionsList;
        urlsMap =  new HashMap<>();
        mLayoutInflater = LayoutInflater.from(mContext);
        this.parentFrag = parentFrag;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_newsfedd, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        final ResponseModel model = transactionsList.get(position);
        String transactionDate ="";

        viewHolder.title.setText(model.getTitle().getRendered());
        viewHolder.date.setText(model.getDate());

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentFrag.selectedFeed = model;
                parentFrag.onFeedClicked();
            }
        });

        String url = model.getLinks().getWpFeaturedmedia().get(0).getHref();
        url = url.substring(url.lastIndexOf('/') + 1).trim();
        viewHolder.container.setVisibility(View.VISIBLE);
        viewHolder.img.setVisibility(View.VISIBLE);
        if(model.getId() != null && !urlsMap.containsKey(model.getId()))
            getImageUrls(model.getId(),url,viewHolder.container,viewHolder.img);
        else if(model.getId() != null && urlsMap.containsKey(model.getId())){
//            Glide.with(mContext).load(urlsMap.get(model.getId())).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(viewHolder.img);
            Picasso.get().load(urlsMap.get(model.getId())).fit().into(viewHolder.img);
            /*Picasso.get().load(urlsMap.get(model.getId())).resize(200,100).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    viewHolder.container.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });*/
        }

    }

    @Override
    public int getItemCount() {
//        return count;
        if(transactionsList != null)
            return transactionsList.size();
        return 0;
    }



    public void getImageUrls(final Integer feed_id, String img_id, final LinearLayout container,final ImageView iv){
        NewsfeedService service =
                ApiClient.getClient().create(NewsfeedService.class);


        Call<ResponseModel> call = service.getImageUrl("media/"+img_id);
//        pd.show();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                pd.dismiss();
                if(null != response && null != response.body()) {
                    container.setVisibility(View.VISIBLE);
                    iv.setVisibility(View.VISIBLE);
//                    Glide.with(mContext).load(response.body().getMedia_details().getSizes().getThumbnail().getSourceUrl())
//                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(iv);
                    Picasso.get().load(response.body().getMedia_details().getSizes().getThumbnail().getSourceUrl()).fit().into(iv);
                   /* Picasso.get().load(response.body().getMedia_details().getSizes().getThumbnail().getSourceUrl()).resize(200,100).into(new com.squareup.picasso.Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            container.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });*/

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("failure", "failure");
//                pd.dismiss();

            }
        });
    }

    public List<ResponseModel> getItems() {
        return transactionsList;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        LinearLayout container;
        ImageView img;

        MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            img = (ImageView) itemView.findViewById(R.id.img);
            date = (TextView) itemView.findViewById(R.id.date);
            container = (LinearLayout) itemView.findViewById(R.id.container);


        }
    }
}
