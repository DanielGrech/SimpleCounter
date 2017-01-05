package com.gtecklabs.simplecounter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gtecklabs.simplecounter.model.Count;

import java.util.ArrayList;
import java.util.List;

public class CountListAdapter extends RecyclerView.Adapter<CountListAdapter.CountViewHolder> {

  private final ArrayList<Count> mData = new ArrayList<>();

  public CountListAdapter() {
    setHasStableIds(true);
  }

  @Override
  public CountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    TextView tv = new TextView(parent.getContext());
    return new CountViewHolder(tv);
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

    public CountViewHolder(View itemView) {
      super(itemView);
    }

    void bind(Count count) {
      ((TextView) itemView).setText(count.toString());
    }
  }
}
