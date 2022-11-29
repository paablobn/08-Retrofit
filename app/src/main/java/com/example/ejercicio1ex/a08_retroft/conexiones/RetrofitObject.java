package com.example.ejercicio1ex.a08_retroft.conexiones;

import com.example.ejercicio1ex.a08_retroft.configuraciones.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject {
    public static Retrofit getConexion() {
        return new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
