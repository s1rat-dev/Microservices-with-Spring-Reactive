package com.example.bannerapi.bannermanager

import com.example.bannerapi.bannermanager.dto.BannerRequest
import com.example.bannerapi.bannermanager.dto.BannerResponse
import com.example.bannerapi.bannermanager.mapper.BannerToResponseMapper
import com.example.bannerapi.bannermanager.mapper.RequestToBannerMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import java.util.*

@Service
class BannerService(val bannerRepository: BannerRepository,
                    val requestToBannerMapper: RequestToBannerMapper,
                    val bannerToResponseMapper: BannerToResponseMapper)
{

    suspend fun getAll() : Flow<BannerResponse> {
         return bannerRepository.findAll().map {
             bannerToResponseMapper.convert(it)!!
         }
    }


    suspend fun getById(bannerId : UUID) : BannerResponse? {
        return bannerToResponseMapper.convert(bannerRepository.findById(bannerId)!!)
    }


    suspend fun addBanner(bannerRequest : BannerRequest) {
        bannerRepository.save(requestToBannerMapper.convert(bannerRequest)!!)
    }

}