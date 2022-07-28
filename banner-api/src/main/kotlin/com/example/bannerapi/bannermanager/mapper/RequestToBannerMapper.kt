package com.example.bannerapi.bannermanager.mapper

import com.example.bannerapi.bannermanager.Banner
import com.example.bannerapi.bannermanager.dto.BannerRequest
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter


@Mapper()
interface RequestToBannerMapper : Converter<BannerRequest, Banner>
