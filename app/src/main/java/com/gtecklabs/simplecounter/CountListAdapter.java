package com.gtecklabs.simplecounter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.view.CountItemView;

import java.util.ArrayList;
import java.util.List;

public class CountListAdapter extends RecyclerView.Adapter<CountListAdapter.CountViewHolder> {

  private final ArrayList<Count> mData = new ArrayList<>();

  public CountListAdapter() {
    setHasStableIds(true);
  }

  @Override
  public CountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    CountItemView itemView = CountItemView.inflate(parent);
    return new CountViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(CountViewHolder holder, int position) {
    holder.bind(mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  @Override
  public long getItemId(int position) {
    return mData.get(position).id();
  }

  public void bind(@Nullable List<Count> data) {
    mData.clear();
    if (data != null) {
      mData.addAll(data);
    }

    notifyDataSetChanged();
  }

  static class CountViewHolder extends RecyclerView.ViewHolder {

    CountViewHolder(View itemView) {
      super(itemView);
    }

    void bind(Count count) {
      ((CountItemView) itemView).bind(count);
    }
  }
}
