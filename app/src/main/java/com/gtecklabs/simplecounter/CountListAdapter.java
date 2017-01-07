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

  public interface Listener {

    void onCountClicked(Count count);

    void onIncrementClicked(Count count);

    void onDecrementClicked(Count count);
  }

  private final ArrayList<Count> mData = new ArrayList<>();

  private @Nullable Listener mListener;

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

  public void setListener(@Nullable Listener listener) {
    mListener = listener;
  }

  public void bind(@Nullable List<Count> data) {
    mData.clear();
    if (data != null) {
      mData.addAll(data);
    }

    notifyDataSetChanged();
  }

  class CountViewHolder extends RecyclerView.ViewHolder implements CountItemView.Listener, View.OnClickListener {

    CountViewHolder(CountItemView itemView) {
      super(itemView);
    }

    void bind(Count count) {
      CountItemView countItemView = (CountItemView) itemView;

      countItemView.setOnClickListener(this);
      countItemView.setListener(this);
      countItemView.bind(count);
    }

    @Override
    public void onClick(View view) {
      if (mListener != null) {
        mListener.onCountClicked(getCount());
      }
    }

    @Override
    public void onIncrementClicked(CountItemView view) {
      if (mListener != null) {
        mListener.onIncrementClicked(getCount());
      }
    }

    @Override
    public void onDecrementClicked(CountItemView view) {
      if (mListener != null) {
        mListener.onDecrementClicked(getCount());
      }
    }

    private Count getCount() {
      return mData.get(getAdapterPosition());
    }
  }
}
