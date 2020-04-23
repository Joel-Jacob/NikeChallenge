package com.example.nikechallenge.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nikechallenge.adapter.UDAdapter
import com.example.nikechallenge.model.Definition
import com.example.nikechallenge.network.UDRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UDViewModel(application: Application) : AndroidViewModel(application) {
    val uDRetrofit = UDRetrofit()
    val uDAdapter = UDAdapter()
    var upDownBoolean = true
    val compositeDisposable = CompositeDisposable()
    var definitions = ArrayList<Definition>()

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
        compositeDisposable.add(
            uDRetrofit
                .getDefinitionResponse(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    for (item in response.list) {
                        item.definition = item.definition.replace("[", "")
                        item.definition = item.definition.replace("]", "")
                    }
                    uDAdapter.definitionList = ArrayList(response.list)
                    definitions = uDAdapter.definitionList
                    uDAdapter.notifyDataSetChanged()
                    spinner.value = true
                }, {
                    error.value = true
                    spinner.value = true
                    Log.d("TAG_X", "Error: ${it.message}")
                })
        )
    }

    fun sortList() {
        //up: true
        //down: false
        if (upDownBoolean) {
            definitions = ArrayList(definitions.sortedByDescending { it.thumbs_up })
            uDAdapter.definitionList = definitions
            thumbs.value = true
            uDAdapter.notifyDataSetChanged()
        } else {
            definitions = ArrayList(definitions.sortedByDescending { it.thumbs_down })
            uDAdapter.definitionList = definitions
            thumbs.value = false
            uDAdapter.notifyDataSetChanged()
        }
        upDownBoolean = !upDownBoolean
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}