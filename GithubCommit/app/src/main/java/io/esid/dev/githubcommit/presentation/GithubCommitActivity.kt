package io.esid.dev.githubcommit.presentation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import io.esid.dev.githubcommit.R
import io.esid.dev.githubcommit.model.GithubCommitResponse
import kotlinx.android.synthetic.main.activity_github_commit.*
import kotlinx.android.synthetic.main.dialog_seeker_layout.view.*


class GithubCommitActivity : AppCompatActivity(), GithubCommitView {

    lateinit var snackbar: Snackbar
    lateinit var presenter: GithubCommitPresenter
    lateinit var adapter: GithubCommitAdapter

    val user: String = "rails"
    val repository: String = "rails"
    var limit: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_commit)

        initSnackbar()
        initSwipeRefresh()
        initRecyclerView()
        getGithubCommit()
    }

    fun initSnackbar() {
        snackbar = Snackbar.make(coordinatorLayout, getString(R.string.message_no_internet_connection), Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", { })
    }

    fun initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener { presenter.getCommits(user, repository, limit) }
    }

    fun initRecyclerView() {
        commitsDetailRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        commitsDetailRecyclerView.setHasFixedSize(true)

        adapter = GithubCommitAdapter(applicationContext, mutableListOf())
        commitsDetailRecyclerView.adapter = adapter
    }

    fun getGithubCommit() {
        presenter = GithubCommitPresenter(this)

        presenter.getCommits(user, repository, limit)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_limit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_limit -> {
                showLimitDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showLimitDialog() {


        val view = layoutInflater.inflate(R.layout.dialog_seeker_layout, null)
        val seekBar = view.seekBar
        var seekValue = view.seekerValue
        seekValue.text = limit.toString()
        seekBar.progress = limit
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                seekValue.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AppDialog))
        builder.setView(view)
        builder.setTitle("Limit")
        builder.setPositiveButton("Change", { dialogInterface, i ->
            if (seekBar.progress != limit) {
                limit = seekBar.progress
                presenter.getCommits(user, repository, limit)
            }
            dialogInterface.dismiss()
        })

        builder.create().show()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showProgress() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefreshLayout.isRefreshing = false
        snackbar.dismiss()
    }

    override fun showError(message: String) {
        showToast(message)
    }

    override fun noInternet() {
        snackbar.show()
    }

    override fun renderCommits(commits: MutableList<GithubCommitResponse>) {
        adapter.updateItem(commits)
    }

    override fun onCommitsNoFound() {
        showToast(getString(R.string.message_no_item_found))
    }

    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
