package com.android.tanslatorapp.ui;

import android.content.Context;
import android.icu.number.CompactNotation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tanslatorapp.R;
import com.android.tanslatorapp.db.SavedEntity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context context;
    private List<SavedEntity> savedEntityList;

    public ListAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<SavedEntity> savedEntityList) {
        this.savedEntityList = savedEntityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position) {
        holder.from.setText(this.savedEntityList.get(position).textFrom);
        holder.to.setText(this.savedEntityList.get(position).textTo);
    }

    @Override
    public int getItemCount() {
        return this.savedEntityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView from,to;
        public MyViewHolder(View view) {
            super(view);
            from = view.findViewById(R.id.fromText);
            to = view.findViewById(R.id.toText);
        }
    }

    public void removeItem(int position) {
        savedEntityList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(SavedEntity item, int position) {
        savedEntityList.add(position,  item);
        notifyItemInserted(position);
    }

    public List<SavedEntity> getData() {
        return savedEntityList;
    }

}
