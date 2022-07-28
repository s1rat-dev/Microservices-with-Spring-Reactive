package com.example.bannerapi.bannermanager

import com.example.bannerapi.bannermanager.dto.BannerRequest
import com.example.bannerapi.bannermanager.dto.BannerResponse
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/banners")
class BannerController(val bannerService: BannerService) {

    @GetMapping()
    suspend fun getAll() : Flow<BannerResponse> = bannerService.getAll()


    @GetMapping("/{id}")
    suspend fun findWithId(@PathVariable("id") bannerId : UUID) : BannerResponse? = bannerService.getById(bannerId)


    @PostMapping()
    suspend fun addBanner(@RequestBody bannerRequest: BannerRequest) {
        bannerService.addBanner(bannerRequest)
    }

}