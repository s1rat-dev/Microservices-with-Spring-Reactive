package com.example.bannerapi.bannermanager

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.query.Param
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
    suspend fun getAll() : Flow<Banner> = bannerService.getAll()


    @GetMapping("/{id}")
    suspend fun findWithId(@PathVariable("id") bannerId : UUID) : Banner? = bannerService.getById(bannerId)


    @PostMapping()
    suspend fun addBanner(@RequestBody bannerRequest: CreateBannerRequest) {
        bannerService.addBanner(bannerRequest)
    }

}