package com.example.bannerapi.bannermanager

import com.example.bannerapi.bannermanager.dto.BannerRequest
import com.example.bannerapi.bannermanager.dto.BannerResponse
import com.example.bannerapi.bannermanager.mapper.BannerToResponseMapper
import com.example.bannerapi.bannermanager.mapper.RequestToBannerMapper
import com.example.bannerapi.exception.BannerAlreadyExistException
import com.example.bannerapi.exception.BannerNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class BannerService(val bannerRepository: BannerRepository,
                    val requestToBannerMapper: RequestToBannerMapper,
                    val bannerToResponseMapper: BannerToResponseMapper)
{

    fun getAll() : Flow<BannerResponse> {
         return bannerRepository.findAll().map {
             bannerToResponseMapper.convert(it)!!
         }
    }

    suspend fun isBannerAlreadyExists(bannerUrl: String,advertiserName: String) : Banner? {
        return bannerRepository.isBannerAlreadyExists(bannerUrl,advertiserName)
    }

    suspend fun getById(bannerId : UUID) : BannerResponse {

        val banner : Banner? = bannerRepository.findById(bannerId)
        if (banner === null)
            throw BannerNotFoundException()

        return bannerToResponseMapper.convert(banner)!!
    }


    suspend fun addBanner(bannerRequest : BannerRequest) : BannerResponse {

        val targetBanner :Banner? = isBannerAlreadyExists(bannerRequest.bannerUrl!!,bannerRequest.advertiserName!!)
        if(targetBanner != null)
            throw BannerAlreadyExistException()

        return bannerToResponseMapper
                .convert(bannerRepository
                        .save(requestToBannerMapper.convert(bannerRequest)!!))!!
    }

}