package com.example.bannerapi.bannermanager.dto

import java.util.*

data class BannerResponse(
        val id : UUID,
        val bannerUrl : String,
        val advertiserName: String
)

