package com.example.skucise.api

import com.example.skucise.models.Advert
import com.example.skucise.models.Comment
import com.example.skucise.models.Filters
import com.example.skucise.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AdvertApi {
    @GET("Advert/GetAdvertsForRent")
    suspend fun getAdvertsForRent(): Response<List<Advert>>

    @GET("Advert/GetAdvertsForSale")
    suspend fun getAdvertsForSale(): Response<List<Advert>>

    @Multipart
    @POST("Advert/CreateAdvert")
    suspend fun createAdvert(
        @Part files : List<MultipartBody.Part>,
        @Part("advertJson") advert : RequestBody
    ): Response<Long>

    @PUT("Advert/UpdateAdvert")
    suspend fun updateAdvert(@Body advert: Advert): Response<Boolean>

    @GET("Advert/GetAdvertById/{advertId}")
    suspend fun getAdvertById(@Path("advertId") id: Long): Response<Advert>

    @GET("Advert/GetAdvertsByName/{searchInput}")
    suspend fun getAdvertsByName(@Path("searchInput") searchInput: String): Response<List<Advert>>

    @GET("Advert/GetAdvertsByRealtyType/{realtyId}")
    suspend fun getAdvertsByRentProperty(@Path("realtyId") rentPropertyId: Long): Response<List<Advert>>

    @GET("Like/CheckLike/{userId}/{advertId}")
    suspend fun getLikeIfExist(
        @Path("userId")userId: Long,
        @Path("advertId")advertId: Long):
        Response<Boolean>

    @POST("Like/Like/{userId}/{advertId}")
    suspend fun postLike(
        @Path("userId")userId: Long,
        @Path("advertId")advertId: Long):
            Response<Boolean>

    @POST("Advert/SearchAdvertsWithFilters")
    suspend fun getAdvertsByFilter(@Body filters: Filters): Response<List<Advert>>

    @GET("Advert/GetComments/{advertId}")
    suspend fun getComments(
        @Path("advertId")advertId: Long):
            Response<List<Comment>>
    @GET("Like/GetMyLikes/{id}")
    suspend fun getMyLikes(
        @Path("id")id: Long):
            Response<ArrayList<Advert>>

    @GET("Advert/getAdvertsByOwnerId/{ownerId}")
    suspend fun getAdvertsByOwnerId(@Path("ownerId") ownerId: Long): Response<List<Advert>>

    @GET("Advert/GetReceivedComments/{ownerId}")
    suspend fun getReceivedComments(@Path("ownerId")ownerId: Long): Response<List<Comment>>

    @GET("Advert/getSentComments/{userId}")
    suspend fun getSentComments(@Path("userId")userId: Long): Response<List<Comment>>

    @DELETE("Advert/{advertId}")
    suspend fun deleteAdvert(@Path("advertId")advertId: Long): Response<Boolean>

}