package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.sandwichclub.adapters.SandwichRecyclerAdapter;
import com.udacity.sandwichclub.interfaces.ListItemClickListener;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    private RecyclerView mRecyclerView;
    private SandwichRecyclerAdapter mSandwichAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.sandwiches_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mSandwichAdapter = new SandwichRecyclerAdapter(this);
        mRecyclerView.setAdapter(mSandwichAdapter);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        mSandwichAdapter.setSandwichData(sandwiches);
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

    @Override
    public void onSandwichItemClick(int clickedItemIndex) {
        launchDetailActivity(clickedItemIndex);
    }
}
