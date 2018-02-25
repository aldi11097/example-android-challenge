package io.esid.dev.githubcommit.presentation

import io.esid.dev.githubcommit.BaseView
import io.esid.dev.githubcommit.model.GithubCommitResponse

/**
 * Created by Aldi on 2/25/2018.
 */
interface GithubCommitView: BaseView{
    fun renderCommits(commits: MutableList<GithubCommitResponse>)

    fun onCommitsNoFound()
}