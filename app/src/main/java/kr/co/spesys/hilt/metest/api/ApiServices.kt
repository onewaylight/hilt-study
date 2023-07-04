package kr.co.spesys.hilt.metest.api

import kr.co.spesys.hilt.metest.response.MovieDetailsResponse
import kr.co.spesys.hilt.metest.response.MoviesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    //    https://api.themoviedb.org/3/movie/550?api_key=***
    //    https://api.themoviedb.org/3/movie/popular?api_key=***
    //    https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMoviesList(@Query("page") page: Int): Call<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Call<MovieDetailsResponse>

}