package com.yeongdon.composemovieapp.domain.use_case.get_movie


import com.yeongdon.composemovieapp.common.Resource
import com.yeongdon.composemovieapp.data.remote.model.MovieDto
import com.yeongdon.composemovieapp.domain.repository.OmdbRepository
import com.yeongdon.composemovieapp.utils.ApiLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: OmdbRepository
) {
    private val TAG = "GetMovieUseCase"
    operator fun invoke(id: String, apiKey: String): Flow<Resource<MovieDto>> = flow {
        try {
            emit(Resource.Loading<MovieDto>())
            val movie = repository.getMovie(id, apiKey)
            emit(Resource.Success<MovieDto>(movie))
            ApiLogger.isSuccess(TAG, movie)
        } catch (e: HttpException) {
            emit(Resource.Error<MovieDto>(e.localizedMessage ?: "예기치 않은 오류 발생"))
            ApiLogger.isUnSuccess(TAG, e.localizedMessage ?: "에기치 않은 오류 발생")
        } catch (e: IOException) {
            emit(Resource.Error<MovieDto>("서버에 연결 할 수 없습니다.인터넷 상태를 확인 하세요."))
            ApiLogger.isFailure(TAG, "${e.message}")
        }
    }

}