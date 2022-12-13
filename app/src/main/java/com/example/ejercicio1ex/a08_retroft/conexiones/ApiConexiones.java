package com.example.ejercicio1ex.a08_retroft.conexiones;

import com.example.ejercicio1ex.a08_retroft.configuraciones.Constantes;
import com.example.ejercicio1ex.a08_retroft.modelos.Album;
import com.example.ejercicio1ex.a08_retroft.modelos.Photo;

import java.net.InterfaceAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiConexiones {
    @GET("/albums")
    Call<ArrayList<Album>> getAlbums();

    @GET("/photos?")
    Call<ArrayList<Photo>> getPhotosAlbum(@Query("albumId") String albumId);

    // Parametros dentro del PATH
    // /albums/{2}/photos
    @GET("/albums/{albumId}/photos")
    Call<ArrayList<Photo>> getPhotosAlbumPath(@Path("albumId") String albumId);

    /**
     * POST PARA CREAR UN NUEVO ALBUM -> Crear la informacion envio la info
     * - Body de la peticion (JSON)
     * - Formulario, campo
     */

    @POST("/albums")
    Call<Album> postAlbumCreate(@Body Album album);

    @FormUrlEncoded
    @POST("/albums")
    Call<Album> postAlbumCreateForm(@Field("userId") int userId, @Field("title") String titulo);

    @DELETE("/albums/{albumId}")
    Call<Album> postAlbumDelete(@Path("albumId") String albumId);
}
