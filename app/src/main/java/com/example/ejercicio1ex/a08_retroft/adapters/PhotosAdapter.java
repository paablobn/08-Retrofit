package com.example.ejercicio1ex.a08_retroft.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio1ex.a08_retroft.R;
import com.example.ejercicio1ex.a08_retroft.modelos.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoVH> {

    private List<Photo> objects;
    private int resource;
    private Context context;

    public PhotosAdapter(List<Photo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoVH(LayoutInflater.from(context).inflate(resource,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoVH holder, int position) {
        Photo p = objects.get(position);
        holder.lbTitulo.setText(p.getTitle());
        Picasso.get()
                .load(p.getThumbnailUrl())// url para la descarga de la imagen
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class PhotoVH extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView lbTitulo;

        public PhotoVH(@NonNull View itemView){
            super(itemView);

            imgPhoto= itemView.findViewById(R.id.imgImagenPhoto);
            lbTitulo = itemView.findViewById(R.id.lbTituloPhoto);
        }
    }
}
