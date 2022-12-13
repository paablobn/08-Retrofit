package com.example.ejercicio1ex.a08_retroft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ejercicio1ex.a08_retroft.adapters.PhotosAdapter;
import com.example.ejercicio1ex.a08_retroft.conexiones.ApiConexiones;
import com.example.ejercicio1ex.a08_retroft.conexiones.RetrofitObject;
import com.example.ejercicio1ex.a08_retroft.modelos.Photo;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotosActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<Photo> photoList;
    private PhotosAdapter adapter;
    private RecyclerView.LayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photoList = new ArrayList<>();
        recycler = findViewById(R.id.contenedorPhotos);
        adapter = new PhotosAdapter(photoList,R.layout.photo_view_holder,this);
        lm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(lm);
        recycler.setAdapter(adapter);

        if (getIntent().getExtras().getString("ID_ALBUM")!= null){
            doGetPhotos(getIntent().getExtras().getString("ID_ALBUM"));
        }

    }

    private void doGetPhotos(String idAlbum) {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);

        Call<ArrayList<Photo>>getPhotos = api.getPhotosAlbumPath(idAlbum);

        getPhotos.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK){
                    ArrayList<Photo> temp = response.body();
                    photoList.addAll(temp);
                    adapter.notifyItemRangeChanged(0,temp.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

            }
        });
    }
}