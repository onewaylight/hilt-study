package kr.co.spesys.hilt.metest.repository

import dagger.hilt.android.scopes.ActivityScoped
import kr.co.spesys.hilt.metest.api.ApiServices
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)
    fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)
}