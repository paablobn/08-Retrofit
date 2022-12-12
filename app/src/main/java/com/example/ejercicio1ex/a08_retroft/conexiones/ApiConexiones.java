package com.example.ejercicio1ex.a08_retroft.conexiones;

import com.example.ejercicio1ex.a08_retroft.configuraciones.Constantes;
import com.example.ejercicio1ex.a08_retroft.modelos.Album;
import com.example.ejercicio1ex.a08_retroft.modelos.Photo;

import java.net.InterfaceAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiConexiones {
    @GET("/albums")
    Call<ArrayList<Album>> getAlbums();

    @GET("/photos?")
    Call<ArrayList<Photo>> getPhotosAlbum(@Query("albumId") String albumId);
}
