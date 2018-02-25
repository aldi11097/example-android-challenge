package io.esid.dev.githubcommit.network

import io.esid.dev.githubcommit.model.GithubCommitResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Aldi on 2/25/2018.
 */

interface API {

    @GET("repos/{user}/{repos}/commits")
    fun getCommit(@Path("user") user: String,
                  @Path("repos") repos: String,
                  @Query("per_page") limit: Int): Flowable<MutableList<GithubCommitResponse>>
}