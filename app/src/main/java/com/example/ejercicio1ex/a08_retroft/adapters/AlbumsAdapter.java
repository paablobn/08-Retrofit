package com.example.ejercicio1ex.a08_retroft.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio1ex.a08_retroft.PhotosActivity;
import com.example.ejercicio1ex.a08_retroft.R;
import com.example.ejercicio1ex.a08_retroft.modelos.Album;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumVH> {

    private List<Album> objects;
    private int resource;
    private Context context;

    public AlbumsAdapter(List<Album> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View albumView = LayoutInflater.from(context).inflate(resource, null);
        albumView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new AlbumVH(albumView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumVH holder, int position) {
        Album a = objects.get(position);
        holder.lbTitle.setText(a.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotosActivity.class);
                String albumId = String.valueOf(a.getId());
                Bundle bundle = new Bundle();
                bundle.putString("ID_ALBUM",albumId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class AlbumVH extends RecyclerView.ViewHolder {

        TextView lbTitle;

        public AlbumVH(@NonNull View itemView) {
            super(itemView);
            lbTitle = itemView.findViewById(R.id.lbTitleAlbumCard);
        }
    }
}
