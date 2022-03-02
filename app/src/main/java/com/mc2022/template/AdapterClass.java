package com.mc2022.template;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {

    private List<ModelNews> newsData;
    private Context context;


    public AdapterClass(List<ModelNews> newsData, Context context) {
        this.newsData = newsData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelNews mn = newsData.get(position);

        holder.title.setText(mn.getmTextTitle());
        holder.body.setText(mn.getmTextBody());
        holder.sno.setText(mn.getmSno());

        Picasso.with(context)
                .load(mn.getmImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView body;
        public TextView sno;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.newsTitle);
            body = (TextView) itemView.findViewById(R.id.newsBody);
            sno = (TextView) itemView.findViewById(R.id.sNo);
            imageView = (ImageView) itemView.findViewById(R.id.newsImage);

        }
    }
}
