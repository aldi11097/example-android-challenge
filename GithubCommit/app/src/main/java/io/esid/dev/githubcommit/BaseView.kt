package io.esid.dev.githubcommit

/**
 * Created by Aldi on 2/25/2018.
 */
interface BaseView{
    fun showProgress()
    fun hideProgress()
    fun showError(message: String)
    fun noInternet()
}