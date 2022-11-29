package com.example.ejercicio1ex.a08_retroft.conexiones;

import com.example.ejercicio1ex.a08_retroft.configuraciones.Constantes;
import com.example.ejercicio1ex.a08_retroft.modelos.Album;

import java.net.InterfaceAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiConexiones {
    @GET("/albums")
    Call<ArrayList<Album>> getAlbums();
}
