package com.info.uitc.freeandr.api

import com.info.uitc.freeandr.api.model.*
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface FreeAndrApi {

    @POST("api.php")
    fun echo(@Body body: EchoRequest) : Flowable<EchoResponse>

    @POST("api.php")
    fun validate(@Body body: ValidateRequest) : Flowable<ValidateResponse>

    @POST("api.php")
    fun create(@Body body: CreateRequest) : Flowable<CreateResponse>

    @POST("api.php")
    fun listservers(@Body body: ListServersRequest) : Flowable<ListServersResponse>

    @POST("api.php")
    fun getconfig(@Body body: GetConfigRequest) : Flowable<GetConfigResponse>

    companion object {
        val BASE_URL = "https://www.freeandr.com/"

        fun create() : FreeAndrApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient().build())
                .build()
            return retrofit.create(FreeAndrApi::class.java)
        }

        val instance : FreeAndrApi by lazy { create() }
    }
}