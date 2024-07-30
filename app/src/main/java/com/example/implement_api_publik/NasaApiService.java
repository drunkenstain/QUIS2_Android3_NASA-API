package com.example.implement_api_publik;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApiService {
    @GET("planetary/apod")
    Call<NasaApodResponse> getApod(@Query("api_key") String apiKey);
}
