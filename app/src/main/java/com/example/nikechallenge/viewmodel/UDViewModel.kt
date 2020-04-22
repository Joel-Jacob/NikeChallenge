package com.example.nikechallenge.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nikechallenge.adapter.UDAdapter
import com.example.nikechallenge.model.X
import com.example.nikechallenge.network.UDRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UDViewModel(application: Application) : AndroidViewModel(application) {
    val uDRetrofit = UDRetrofit()
    val uDAdapter = UDAdapter()
    var upDownBoolean = true
    val compositeDisposable = CompositeDisposable()
    val spinner : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    var definitions = ArrayList<X>()

    fun getDefinitions(word: String){
        compositeDisposable.add(
            uDRetrofit
                .getDefinitionResponse(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
                    for (item in response.list){
                        item.definition = item.definition.replace("[","")
                        item.definition = item.definition.replace("]","")
                    }
                    uDAdapter.definitionList = ArrayList(response.list)
                    definitions = uDAdapter.definitionList
                    uDAdapter.notifyDataSetChanged()
                    spinner.value = true
                },{
                    Toast.makeText(getApplication(),"There was an error loading definitions", Toast.LENGTH_LONG).show()
                    spinner.value = true
                    Log.d("TAG_X", "Error: ${it.message}")
                })
        )
    }

    fun sortList(){
        //up: true
        //down: false
        if(upDownBoolean){
            definitions = ArrayList(definitions.sortedByDescending { it.thumbs_up })
            uDAdapter.definitionList = definitions
            Toast.makeText(getApplication(),"Sorted by thumbs up", Toast.LENGTH_LONG).show()
            uDAdapter.notifyDataSetChanged()
        }
        else{
            definitions = ArrayList(definitions.sortedByDescending { it.thumbs_down })
            uDAdapter.definitionList = definitions
            Toast.makeText(getApplication(),"Sorted by thumbs down", Toast.LENGTH_LONG).show()

            uDAdapter.notifyDataSetChanged()
        }
        upDownBoolean = !upDownBoolean
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}