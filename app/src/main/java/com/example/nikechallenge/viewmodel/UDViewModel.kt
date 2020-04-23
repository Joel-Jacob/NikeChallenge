package com.example.nikechallenge.viewmodel

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
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

    private val spinnerMutableLiveData = MutableLiveData<Boolean>()
    val spinnerLiveData: LiveData<Boolean>
        get() = spinnerMutableLiveData

    private val errorMutableLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = spinnerMutableLiveData

    private val thumbsMutableLiveData = MutableLiveData<Boolean>()
    val thumbsLiveData: LiveData<Boolean>
        get() = spinnerMutableLiveData

    fun getDefinitions(word: String) {
        spinnerMutableLiveData.value = true
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
                    spinnerMutableLiveData.value = false
                }, {
                    errorMutableLiveData.value = true
                    spinnerMutableLiveData.value = false
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
            thumbsMutableLiveData.value = true
            uDAdapter.notifyDataSetChanged()
        } else {
            definitions.sortByDescending {
                it.thumbs_down
            }
            uDAdapter.definitionList = definitions
            thumbsMutableLiveData.value = false
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