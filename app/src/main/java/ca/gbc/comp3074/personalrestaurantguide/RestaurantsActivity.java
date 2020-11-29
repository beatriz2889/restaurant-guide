package ca.gbc.comp3074.personalrestaurantguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RestaurantsActivity extends AppCompatActivity {

    private static final String TAG = RestaurantsActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;
    private ArrayList<Entries>allEntries;
    private EntryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.frame_layout);
        RecyclerView entryView=(RecyclerView)findViewById(R.id.entryView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        entryView.setLayoutManager(linearLayoutManager);
        entryView.setHasFixedSize(true);
        allEntries=new ArrayList<>();
        mDatabase=new SqliteDatabase(this);
        allEntries=mDatabase.listEntries();

        if(allEntries.size() > 0){
            entryView.setVisibility(View.VISIBLE);
            mAdapter = new EntryAdapter(this, allEntries);
            entryView.setAdapter(mAdapter);
        }else {
            entryView.setVisibility(View.GONE);
            Toast.makeText(this, "There are currently no entries to show, please add an entry through the menu", Toast.LENGTH_LONG).show();
        }
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.addRestaurant:
                    Intent intent = new Intent(this, AddRestaurantActivity.class);
                    this.startActivity(intent);
                    break;
            }
            switch (item.getItemId()) {
                case R.id.about:
                    Intent intent = new Intent(this, AboutActivity.class);
                    this.startActivity(intent);
                    break;
            }
            switch (item.getItemId()) {
                case R.id.restaurants:
                    Intent intent = new Intent(this, RestaurantsActivity.class);
                    this.startActivity(intent);
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
            return true;
        }
}
