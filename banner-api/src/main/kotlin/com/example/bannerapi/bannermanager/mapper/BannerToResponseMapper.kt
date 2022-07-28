package com.example.bannerapi.bannermanager.mapper

import com.example.bannerapi.bannermanager.Banner
import com.example.bannerapi.bannermanager.dto.BannerResponse
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter


@Mapper()
interface BannerToResponseMapper : Converter<Banner,BannerResponse>
