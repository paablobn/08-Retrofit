package com.example.ejercicio1ex.a08_retroft.adapters;

import android.content.Context;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio1ex.a08_retroft.PhotosActivity;
import com.example.ejercicio1ex.a08_retroft.R;
import com.example.ejercicio1ex.a08_retroft.conexiones.ApiConexiones;
import com.example.ejercicio1ex.a08_retroft.conexiones.RetrofitObject;
import com.example.ejercicio1ex.a08_retroft.modelos.Album;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                bundle.putString("ID_ALBUM", albumId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitObject.getConexion();
                ApiConexiones api = retrofit.create(ApiConexiones.class);
                Call<Album> doDeleteAlbum = api.postAlbumDelete(String.valueOf(a.getId()));

                doDeleteAlbum.enqueue(new Callback<Album>() {
                    @Override
                    public void onResponse(Call<Album> call, Response<Album> response) {
                        if (response.code()== HttpURLConnection.HTTP_OK) {
                            objects.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyItemRangeChanged(holder.getAdapterPosition(), objects.size());
                            Toast.makeText(context, "ALBUM ELIMINADO", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Album> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class AlbumVH extends RecyclerView.ViewHolder {

        TextView lbTitle;
        ImageButton btnEliminar;

        public AlbumVH(@NonNull View itemView) {
            super(itemView);
            lbTitle = itemView.findViewById(R.id.lbTitleAlbumCard);
            btnEliminar = itemView.findViewById(R.id.btnEliminarAlbum);
        }
    }
}
