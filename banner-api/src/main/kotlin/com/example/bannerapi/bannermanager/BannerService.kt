package com.example.bannerapi.bannermanager

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.util.*

@Service
class BannerService(val bannerRepository: BannerRepository,
                    val bannerConverter: BannerConverter)
{

    suspend fun getAll() : Flow<Banner> {
        return bannerRepository.findAll()
    }


    suspend fun getById(bannerId : UUID) : Banner? {
        return bannerRepository.findById(bannerId)
    }


    suspend fun addBanner(bannerRequest : CreateBannerRequest) {
        bannerRepository.save(bannerConverter.convert(bannerRequest)!!)
    }

}