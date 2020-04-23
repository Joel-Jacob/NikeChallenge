package com.example.nikechallenge.viewmodel

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikechallenge.adapter.UDAdapter
import com.example.nikechallenge.model.Definition
import com.example.nikechallenge.network.UDRepository
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import io.reactivex.disposables.CompositeDisposable
import rx.Notification
import java.util.concurrent.TimeUnit

class UDViewModel constructor(private val udRepository: UDRepository): ViewModel() {
    val uDAdapter = UDAdapter()
    var upDownBoolean = true
    private val compositeDisposable = CompositeDisposable()
    var definitions: MutableList<Definition> = mutableListOf()

    val spinner: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val error: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val thumbs: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getDefinitions(word: String) {
        spinner.value = true
        compositeDisposable.add(
            udRepository
                .getDefinitionsList(word)
                .subscribe({ response ->
                    for (item in response.list) {
                        item.definition = item.definition.replace("[", "")
                        item.definition = item.definition.replace("]", "")
                    }
                    uDAdapter.definitionList = response.list
                    definitions = response.list
                    uDAdapter.notifyDataSetChanged()
                    spinner.value = false
                }, {
                    error.value = true
                    spinner.value = false
                    Log.d("TAG_X", "Error: ${it.message}")
                })
        )
    }

    fun sortList() {
        //up: true
        //down: false
        if (upDownBoolean) {
            definitions.sortByDescending {
                it.thumbs_up
            }
            uDAdapter.definitionList = definitions
            thumbs.value = true
            uDAdapter.notifyDataSetChanged()
        } else {
            definitions.sortByDescending {
                it.thumbs_down
            }
            uDAdapter.definitionList = definitions
            thumbs.value = false
            uDAdapter.notifyDataSetChanged()
        }
        upDownBoolean = !upDownBoolean
    }

    fun searchDefinitions(searchView: SearchView) {
        RxSearchView.queryTextChanges(searchView)
            .doOnEach { notification: Notification<in CharSequence?> ->
                val query = notification.value as CharSequence?
                if (query != null && query.length > 2) {
                    getDefinitions(query.toString())
                }
            }
            .debounce(
                300,
                TimeUnit.MILLISECONDS
            ) // to skip intermediate letters
            .retry(3)
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}