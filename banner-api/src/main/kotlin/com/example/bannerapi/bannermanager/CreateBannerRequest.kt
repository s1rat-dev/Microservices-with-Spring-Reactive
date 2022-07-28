package com.example.bannerapi.bannermanager

import java.time.LocalDateTime


data class CreateBannerRequest(
  val bannerUrl : String?,
  val advertiserName: String?,
)