package com.example.zeeshan.supermario.news.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.zeeshan.supermario.R;
import com.example.zeeshan.supermario.model.ResponseModel;
import com.github.florent37.materialviewpager.MaterialViewPager;

import org.w3c.dom.Text;

public class FeedDetailActivity extends AppCompatActivity{

    public ResponseModel getSelectedFeed() {
        return selectedFeed;
    }

    public void setSelectedFeed(ResponseModel selectedFeed) {
        this.selectedFeed = selectedFeed;
    }

    private ResponseModel selectedFeed;
    private boolean isExpanded = false;
    private FrameLayout collapsed_container, expanded_container, expanded_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        selectedFeed = (ResponseModel) getIntent().getSerializableExtra("selectedFeed");

        TextView longDesc = findViewById(R.id.longDesc);
        collapsed_container = findViewById(R.id.collapsed_container);
        expanded_container = findViewById(R.id.expanded_container);
        expanded_arrow = findViewById(R.id.expanded_arrow);


        collapsed_container.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isExpanded = true;
                collapsed_container.setVisibility(View.GONE);
                expanded_container.setVisibility(View.VISIBLE);
            }
        });

        expanded_arrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isExpanded = false;
                collapsed_container.setVisibility(View.VISIBLE);
                expanded_container.setVisibility(View.GONE);
            }
        });

        if(isExpanded) {
            collapsed_container.setVisibility(View.GONE);
            expanded_container.setVisibility(View.VISIBLE);
        }else{
            collapsed_container.setVisibility(View.VISIBLE);
            expanded_container.setVisibility(View.GONE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            longDesc.setText(Html.fromHtml(selectedFeed.getContent().getRendered(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            longDesc.setText(Html.fromHtml(selectedFeed.getContent().getRendered()));
        }
    }
}
