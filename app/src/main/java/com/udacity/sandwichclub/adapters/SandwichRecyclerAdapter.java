package com.udacity.sandwichclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.interfaces.ListItemClickListener;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SandwichRecyclerAdapter extends RecyclerView.Adapter<SandwichRecyclerAdapter.SandwichViewHolder> {

    private String [] mSandwichData;
    private Context context;
    final private ListItemClickListener mOnClickListener;

    public SandwichRecyclerAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public SandwichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.item_sandwich;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new SandwichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichViewHolder holder, int position) {
        Sandwich sandwich = JsonUtils.parseSandwichJson(mSandwichData[position]);
        holder.mTitleTextView.setText(sandwich.getMainName());
        holder.mSubtitleTextView.setText(sandwich.getPlaceOfOrigin());

        final Transformation transformation = new CropCircleTransformation();
        Picasso.with(context)
                .load(sandwich.getImage())
                .transform(transformation)
                .into(holder.mThumbnailTextView);
    }

    @Override
    public int getItemCount() {
        if (mSandwichData == null) return 0;
        return mSandwichData.length;
    }

    public void setSandwichData(String[] sandwiches) {
        mSandwichData = sandwiches;
        notifyDataSetChanged();
    }

    public class SandwichViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mThumbnailTextView;
        TextView mTitleTextView, mSubtitleTextView;

        public SandwichViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumbnailTextView= (ImageView) itemView.findViewById(R.id.item_thumbnail_iv);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_title_tv);
            mSubtitleTextView = (TextView) itemView.findViewById(R.id.item_origin_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onSandwichItemClick(clickedPosition);
        }
    }
}
