package com.tw.sportstest.retrofitClient

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    @get:GET("nzin01312019187360.json")
    val getData: Observable<ResponseBody>

}