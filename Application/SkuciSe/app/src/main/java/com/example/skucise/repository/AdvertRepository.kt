package com.example.skucise.repository

import android.net.Uri
import android.util.Log
import com.example.skucise.api.RetrofitInstance
import com.example.skucise.models.Advert
import com.example.skucise.models.Comment
import com.example.skucise.models.Filters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

import okhttp3.ResponseBody
import java.io.InputStream


class AdvertRepository {
    suspend fun getAdvertsForRent(): Response<List<Advert>> {
        return RetrofitInstance.advertApi.getAdvertsForRent();
    }

    suspend fun getAdvertsForSale(): Response<List<Advert>> {
        return RetrofitInstance.advertApi.getAdvertsForSale();
    }

	suspend fun createAdvert(images : MutableList<InputStream>, advert : Advert) : Response<Long>{

        var list : MutableList<MultipartBody.Part> = ArrayList()
        var i = 0
        for (path in images){
            list.add(prepareFilePart(path, i))
            i++
        }
        var gson = Gson()
        var advertGson = gson.toJson(advert)

        var advertBody: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), advertGson)


        return RetrofitInstance.advertApi.createAdvert(list, advertBody)
	}

    private fun prepareFilePart(path: InputStream, i : Int): MultipartBody.Part {

        var file : File = File.createTempFile("slika$i", ".jpg")
        path.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        var requestFile : RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(),
            file)

        return MultipartBody.Part.createFormData("files", file.name, requestFile)
    }

    suspend fun updateAdvert(advert : Advert) : Response<Boolean>{

        return RetrofitInstance.advertApi.updateAdvert(advert)
    }

    suspend fun deleteAdvert(advertId : Long) : Response<Boolean>{

        return RetrofitInstance.advertApi.deleteAdvert(advertId)
    }
	
    suspend fun getAdvertById(id: Long) : Response<Advert> {
        return RetrofitInstance.advertApi.getAdvertById(id);
    }

    suspend fun getAdvertsByName(searchInput: String): Response<List<Advert>> {
        return RetrofitInstance.advertApi.getAdvertsByName(searchInput);
    }

    suspend fun getAdvertsByRentProperty(rentPropertyId: Long): Response<List<Advert>> {
        return RetrofitInstance.advertApi.getAdvertsByRentProperty(rentPropertyId);
    }

    suspend fun getLikeIfExist(userId: Long, advertId: Long): Response<Boolean> {
        return RetrofitInstance.advertApi.getLikeIfExist(userId, advertId);
    }

    suspend fun postLike(userId: Long, advertId: Long): Response<Boolean> {
        return RetrofitInstance.advertApi.postLike(userId, advertId);
    }


    suspend fun getAdvertsByFilter(filters: Filters): Response<List<Advert>> {
        return RetrofitInstance.advertApi.getAdvertsByFilter(filters);
    }
    suspend fun getComments( advertId: Long): Response<List<Comment>> {
        return RetrofitInstance.advertApi.getComments(advertId);
    }
    suspend fun getMyLikes( id: Long): Response<ArrayList<Advert>> {
        return RetrofitInstance.advertApi.getMyLikes(id);
    }

    suspend fun getAdvertsByOwnerId(ownerId: Long): Response<List<Advert>> {
        return RetrofitInstance.advertApi.getAdvertsByOwnerId(ownerId);
    }

    suspend fun getReceivedComments(ownerId: Long): Response<List<Comment>> {
        return RetrofitInstance.advertApi.getReceivedComments(ownerId);
    }

    suspend fun getSentComments(userId: Long): Response<List<Comment>> {
        return RetrofitInstance.advertApi.getSentComments(userId);
    }
}