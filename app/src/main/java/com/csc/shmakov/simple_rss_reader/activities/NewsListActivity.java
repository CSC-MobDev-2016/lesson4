package com.csc.shmakov.simple_rss_reader.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csc.shmakov.simple_rss_reader.BuildConfig;
import com.csc.shmakov.simple_rss_reader.R;
import com.csc.shmakov.simple_rss_reader.apis.ApiLocator;
import com.csc.shmakov.simple_rss_reader.apis.common.ApiRequestCallback;
import com.csc.shmakov.simple_rss_reader.apis.rss.RssApi;
import com.csc.shmakov.simple_rss_reader.content_provider.ProviderException;
import com.csc.shmakov.simple_rss_reader.model.Channel;
import com.csc.shmakov.simple_rss_reader.model.Item;

import java.util.EnumMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsListActivity extends AppCompatActivity {
    private static Map<ProviderException.Type, Integer> errorMessages
            = new EnumMap<>(ProviderException.Type.class);
    static {
        errorMessages.put(ProviderException.Type.NO_CONNECTION, R.string.no_connection_error);
        errorMessages.put(ProviderException.Type.PARSE_ERROR, R.string.parse_error);
    }

    public static final String EXTRA_URL = "EXTRA_URL";

    private static final RssApi api = ApiLocator.RSS_SERVICE;

    private static final String TAG = "NewsListActivity";


    private String url;

    @Bind(R.id.recyclerview) RecyclerView recyclerView;
    @Bind(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAllCreations();
        if (savedInstanceState == null) {
            onFirstCreation();
        } else {
            onSubsequentCreations(savedInstanceState);
        }
    }

    private void onAllCreations() {
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra(EXTRA_URL);
        api.setCallback(apiRequestCallback);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onFirstCreation() {
        api.fetch(this, url);
    }

    private void onSubsequentCreations(Bundle savedInstanceState) {
        api.fetchCached(this, url);
    }

    private final ApiRequestCallback<Channel> apiRequestCallback =
            new ApiRequestCallback<Channel>() {
        @Override
        public void onSuccess(Channel result) {
            recyclerView.setAdapter(new Adapter(result));
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onError(Throwable error) {
            int messageResourse;
            if (error instanceof ProviderException) {
                ProviderException.Type type = ((ProviderException) error).type;
                messageResourse = errorMessages.get(type);
            } else {
                messageResourse = R.string.unknown_error;
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, error.getMessage());
                }
            }

            new AlertDialog.Builder(NewsListActivity.this)
                    .setTitle(R.string.error_title)
                    .setMessage(getString(messageResourse))
                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        api.setCallback(null);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title_textview) TextView titleTextView;
        @Bind(R.id.description_textview) TextView descriptionTextView;
        @Bind(R.id.date_textview) TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindToItem(Item item) {
            titleTextView.setText(item.title);
            descriptionTextView.setText(item.description);
            dateTextView.setText(item.date);
        }

    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private final Channel channel;

        public Adapter(Channel channel) {
            this.channel = channel;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindToItem(channel.items.get(position));
        }

        @Override
        public int getItemCount() {
            return channel.items.size();
        }
    }
}
