package com.example.bannerapi.bannermanager

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface BannerRepository : CoroutineCrudRepository<Banner,UUID?> {

    @Query("select * from banners where banner_url= :bannerUrl and  advertiser_name = :advertiserName")
    suspend fun isBannerAlreadyExists(bannerUrl: String,advertiserName: String) : Banner?;

}