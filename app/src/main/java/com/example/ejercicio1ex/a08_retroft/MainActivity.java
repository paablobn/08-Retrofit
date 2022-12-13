package com.example.ejercicio1ex.a08_retroft;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ejercicio1ex.a08_retroft.adapters.AlbumsAdapter;
import com.example.ejercicio1ex.a08_retroft.conexiones.ApiConexiones;
import com.example.ejercicio1ex.a08_retroft.conexiones.RetrofitObject;
import com.example.ejercicio1ex.a08_retroft.modelos.Album;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.example.ejercicio1ex.a08_retroft.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<Album> albumList;
    private AlbumsAdapter adapter;
    private RecyclerView.LayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(albumList, R.layout.album_view_holder, this);
        lm = new LinearLayoutManager(this);

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(lm);

        doGetAlbum();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlbumDialog().show();
            }
        });
    }

    private void doGetAlbum() {
        //Creo los objetos para poder hacer la conexion
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones conexiones = retrofit.create(ApiConexiones.class);

        //Preparo la llamada con el retorno de los datos
        Call<ArrayList<Album>> albums = conexiones.getAlbums();

        albums.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    ArrayList<Album> albums1 = response.body();
                    albumList.addAll(albums1);
                    adapter.notifyItemRangeChanged(0, albums1.size());
                    for (Album a : albums1) {
                        Log.d("ALBUM", "onResponse; " + a.toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR, TIENES INTERNET????", Toast.LENGTH_SHORT).show();
                Log.e("FAILURE", t.getLocalizedMessage());
            }
        });
    }

    private AlertDialog createAlbumDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Album");
        builder.setCancelable(false);
        EditText txtTitulo = new EditText(this);
        builder.setView(txtTitulo);
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtTitulo.getText().toString().isEmpty()) {
                    Album a = new Album();
                    a.setTitle(txtTitulo.getText().toString());
                    a.setUserId(1);
                    guardarAlbumAPIForm(a);
                }
            }
        });
        return builder.create();
    }


    private void guardarAlbumAPI(Album a) {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);
        Call<Album> doCreateAlbum = api.postAlbumCreate(a);
        doCreateAlbum.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.code() == HttpURLConnection.HTTP_CREATED) {
                    albumList.add(0, response.body());
                    adapter.notifyItemInserted(0);
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarAlbumAPIForm(Album a) {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);
        Call<Album> doCreateAlbum = api.postAlbumCreateForm(a.getUserId(), a.getTitle());
        doCreateAlbum.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.code() == HttpURLConnection.HTTP_CREATED) {
                    albumList.add(0, response.body());
                    adapter.notifyItemInserted(0);
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}