package com.example.bannerapi.bannermanager.dto

import javax.validation.constraints.NotEmpty


data class BannerRequest(
        @field:NotEmpty(message = "Banner url can not be empty.")
        val bannerUrl : String?,
        @field:NotEmpty(message = "Advertiser name can not be empty.")
        val advertiserName: String?,
)