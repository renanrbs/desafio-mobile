package com.stone.desafiomobile.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.stone.desafiomobile.database.ProductDAO
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.network.RestRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class ProductsListVm() : ViewModel() {

    @Inject
    lateinit var restRepository: RestRepository

    @Inject
    lateinit var productDAO: ProductDAO

    val products: LiveData<List<Product>>
        get() {
            return productDAO.listAll()
        }

    fun loadProducts() {
        restRepository.getProducts({
            Log.d(this::class.simpleName, "SUCESSO REQUISICAO " + it.toString())
            if (it != null) {
                launch(CommonPool){
                    productDAO.insertBatch(it)
                }
            }
        }, {
            Log.d(this::class.simpleName, "ERRO REQUISICAO " + it.toString())
        })
    }
}