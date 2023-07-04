package kr.co.spesys.hilt.metest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.spesys.hilt.metest.R
import kr.co.spesys.hilt.metest.adapter.MoviesAdapter
import kr.co.spesys.hilt.metest.databinding.FragmentMoviesBinding
import kr.co.spesys.hilt.metest.repository.ApiRepository
import kr.co.spesys.hilt.metest.response.MoviesListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    val TAG ="MoviesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            prgBarMovies.visibility = View.VISIBLE
            apiRepository.getPopularMoviesList(page = 1).enqueue( object :
                retrofit2.Callback<MoviesListResponse> {

                override fun onResponse(call: Call<MoviesListResponse>, response: Response<MoviesListResponse>) {
                    prgBarMovies.visibility=View.VISIBLE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            prgBarMovies.visibility=View.GONE
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        moviesAdapter.differ.submitList(itData)
                                        //Recycler
                                        rlMovies.apply {
                                            layoutManager = LinearLayoutManager(requireContext())
                                            adapter = moviesAdapter
                                        }
                                        moviesAdapter.setOnItemClickListener {
                                            val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
                                            findNavController().navigate(direction)
                                        }
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    prgBarMovies.visibility=View.GONE
                    Log.d(TAG,t.message.toString())

                }
            })
        }
    }

}