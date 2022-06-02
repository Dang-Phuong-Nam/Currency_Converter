package com.example.currencyconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class KeyboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> keys = Arrays.asList("9", "8", "7", "6", "5", "4", "3", "2", "1", "0", ".", "BACK", "CLEAR");
    Consumer<View> onClick;
    static class KeyViewHolder extends  RecyclerView.ViewHolder {

        TextView key;

        public KeyViewHolder(@NonNull View itemView, Consumer<View> onClickConsumer) {
            super(itemView);
            key = itemView.findViewById(R.id.key);
            key.setOnClickListener(onClickConsumer::accept);
        }
    }

    public KeyboardAdapter(Consumer<View> onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key, parent, false);
        return new KeyViewHolder(view, this.onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String item = keys.get(position);
        ((KeyViewHolder)holder).key.setText(item);
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }
}
