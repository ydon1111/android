package com.yeongdon.composemovieapp.domain.use_case.get_movies

import com.yeongdon.composemovieapp.common.Resource
import com.yeongdon.composemovieapp.data.remote.model.MoviesDto
import com.yeongdon.composemovieapp.domain.repository.OmdbRepository
import com.yeongdon.composemovieapp.utils.ApiLogger

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



class GetMoviesUseCase @Inject constructor(
    private val repository: OmdbRepository
) {
    private val TAG = "GetMoviesUseCase"
    operator fun invoke(name: String, page: Int, apiKey: String): Flow<Resource<MoviesDto>> = flow {
        try {
            emit(Resource.Loading<MoviesDto>())
            val movies = repository.getMovies(name, page, apiKey)
            emit(Resource.Success<MoviesDto>(movies))
            ApiLogger.isSuccess(TAG, "$movies")
        } catch (e: HttpException) {
            emit(Resource.Error<MoviesDto>(e.localizedMessage ?: "예기치 않은 오류 발생"))
            ApiLogger.isUnSuccess(TAG, e.localizedMessage ?: "에기치 않은 오류 발생")
        } catch (e: IOException) {
            emit(Resource.Error<MoviesDto>("서버에 연결 할 수 없습니다.인터넷 상태를 확인 하세요."))
            ApiLogger.isFailure(TAG, "${e.message}")
        }
    }

}