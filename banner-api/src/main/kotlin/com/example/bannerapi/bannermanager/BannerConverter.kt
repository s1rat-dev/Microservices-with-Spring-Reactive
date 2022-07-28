package com.example.bannerapi.bannermanager

import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter


@Mapper()
interface BannerConverter : Converter<CreateBannerRequest,Banner>
