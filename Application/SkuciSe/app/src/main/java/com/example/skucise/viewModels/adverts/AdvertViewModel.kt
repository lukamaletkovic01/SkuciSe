package com.example.skucise.viewModels.adverts

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skucise.models.Advert
import com.example.skucise.models.Comment
import com.example.skucise.models.Filters
import com.example.skucise.models.User
import com.example.skucise.repository.AdvertRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.InputStream

class AdvertViewModel(private val advertRepository: AdvertRepository) : ViewModel() {
    val newAdvertResponse : MutableLiveData<Response<Long>> = MutableLiveData()
    val updateAdvertResponse : MutableLiveData<Response<Boolean>> = MutableLiveData()
    val deleteAdvertResponse : MutableLiveData<Response<Boolean>> = MutableLiveData()
    val adverts: MutableLiveData<Response<List<Advert>>> = MutableLiveData()
    val advert: MutableLiveData<Response<Advert>> = MutableLiveData();
    val likes: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val createLikesResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val getCommentsResponse: MutableLiveData<Response<List<Comment>>> = MutableLiveData()
    val getMyLikesResponse: MutableLiveData<Response<ArrayList<Advert>>> = MutableLiveData()

    fun getAdverts(type: Int) {
        viewModelScope.launch {
            if (type == 1) {
                val response: Response<List<Advert>> = advertRepository.getAdvertsForRent()
                adverts.value = response
            } else if (type == 2) {
                val response: Response<List<Advert>> = advertRepository.getAdvertsForSale()
                adverts.value = response
            }
        }
    }

    fun createAdvert(images :  MutableList<InputStream>, advert : Advert){
        viewModelScope.launch {
            val response : Response<Long> = advertRepository.createAdvert(images, advert)
            newAdvertResponse.value = response
		}
	}

    fun deleteAdvert(advertId: Long){
        viewModelScope.launch {
            val response : Response<Boolean> = advertRepository.deleteAdvert(advertId)
            deleteAdvertResponse.value = response
        }
    }

    fun updateAdvert(advert : Advert){
        viewModelScope.launch {
            val response : Response<Boolean> = advertRepository.updateAdvert(advert)
            updateAdvertResponse.value = response
        }
    }
	
	fun getAdvertById(id: Long){
        viewModelScope.launch {
            val response: Response<Advert> = advertRepository.getAdvertById(id);
            advert.value = response;
        }
    }

    fun getAdvertsByName(searchInput: String) {
        viewModelScope.launch {
            val response: Response<List<Advert>> = advertRepository.getAdvertsByName(searchInput)
            adverts.value = response
        }
    }

    fun getAdvertsByFilter(filters: Filters) {
        viewModelScope.launch {
            val response: Response<List<Advert>> = advertRepository.getAdvertsByFilter(filters)
            adverts.value = response
        }
    }

    fun getAdvertsByRentProperty(rentPropertyId: Long) {
        viewModelScope.launch {
            val response: Response<List<Advert>> = advertRepository.getAdvertsByRentProperty(rentPropertyId)
            adverts.value = response
        }
    }

    fun getLikeIfExist(userId: Long, advertId: Long) {
        viewModelScope.launch {
            val response: Response<Boolean> = advertRepository.getLikeIfExist(userId, advertId)
            likes.value = response
        }
    }

    fun postLike(userId: Long, advertId: Long) {
        viewModelScope.launch {
            val response: Response<Boolean> = advertRepository.postLike(userId, advertId)
            createLikesResponse.value = response
        }
    }

    fun getAdvertsByOwnerId(ownerId: Long) {
        viewModelScope.launch {
            val response: Response<List<Advert>> = advertRepository.getAdvertsByOwnerId(ownerId)
            adverts.value = response
        }
    }

    fun getComments(advertId: Long) {
        viewModelScope.launch {
            val response: Response<List<Comment>> = advertRepository.getComments(advertId)
            getCommentsResponse.value = response
        }
    }

    fun getReceivedComments(ownerId: Long) {
        viewModelScope.launch {
            val response: Response<List<Comment>> = advertRepository.getReceivedComments(ownerId)
            getCommentsResponse.value = response
        }
    }

    fun getSentComments(userId: Long) {
        viewModelScope.launch {
            val response: Response<List<Comment>> = advertRepository.getSentComments(userId)
            getCommentsResponse.value = response
        }
    }

    fun getMyLikes(id: Long) {
        viewModelScope.launch {
            val response: Response<ArrayList<Advert>> = advertRepository.getMyLikes(id)
            getMyLikesResponse.value = response
        }
    }
}