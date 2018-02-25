package io.esid.dev.githubcommit.presentation

import io.esid.dev.githubcommit.network.Injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

/**
 * Created by Aldi on 2/25/2018.
 */
class GithubCommitPresenter(val view: GithubCommitView) {

    val compositeDisposable = CompositeDisposable()
    val api = Injector.getApi()

    fun getCommits(user: String, repository: String, limit: Int) {

        compositeDisposable.add(
                api.getCommit(user, repository, limit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { view.showProgress() }
                        .doOnNext {
                            if (it.size > 0) view.renderCommits(it)
                            else view.onCommitsNoFound()
                        }
                        .doOnError {
                            if (it is IOException) view.noInternet()
                            else view.showError(it.message!!)
                        }
                        .doOnComplete { view.hideProgress() }
                        .subscribe()
        )
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}