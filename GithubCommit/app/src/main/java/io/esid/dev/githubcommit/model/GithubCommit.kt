package io.esid.dev.githubcommit.model

/**
 * Created by Aldi on 2/25/2018.
 */
class GithubCommit(val author: GithubAuthor,
                   val message: String,
                   val url: String,
                   val html_url: String)