package com.csc.malinovsky239.RSSReader;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class TitlesList extends ActionBarActivity {

    private ArrayList<ListItem> items = new ArrayList<>();
    private RSSProvider provider = new RSSProvider(this);
    private ListItemAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("URL");
        provider.addRssChannel(url);

        setContentView(R.layout.titles_list);

        itemsAdapter = new ListItemAdapter(this, items);
        ListView listView = (ListView) findViewById(R.id.listView);
        if (listView != null) {
            listView.setAdapter(itemsAdapter);
        }
    }

    public void update() {
        MatrixCursor cursor = provider.getTopics();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String pubDate = cursor.getString(cursor.getColumnIndex("pubDate"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                items.add(new ListItem(title, pubDate, description));
            } while (cursor.moveToNext());
        }
        itemsAdapter.notifyDataSetChanged();
        provider.getTopics().close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_titles_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
