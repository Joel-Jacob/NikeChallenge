package com.example.nikechallenge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.nikechallenge.adapter.UDAdapter
import com.example.nikechallenge.model.X
import com.example.nikechallenge.network.UDRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UDViewModel(application: Application) : AndroidViewModel(application) {
    val uDRetrofit = UDRetrofit()
    val uDAdapter = UDAdapter()
    val compositeDisposable = CompositeDisposable() //TODO:Dispose of this

    fun getDefinitions(word: String){
        compositeDisposable.add(
            uDRetrofit
                .getDefinitionResponse(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
//                    response.list = response.list.sortedByDescending { it.thumbs_down }
                    for (item in response.list){
                        item.definition = item.definition.replace("[","")
                        item.definition = item.definition.replace("]","")
                        item.definition = item.definition.replace("/n","")
                        item.definition = item.definition.replace("/r","")

                        Log.d("TAG_X",item.thumbs_up.toString())
                    }
                    uDAdapter.definitionList = ArrayList(response.list)
                    uDAdapter.notifyDataSetChanged()
                },{
                    //TODO:handle error
                    Log.d("TAG_X", "Error: ${it.cause}")
                })
        )
    }
}