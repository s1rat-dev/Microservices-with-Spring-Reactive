package com.example.bannerapi.bannermanager

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface BannerRepository : CoroutineCrudRepository<Banner,UUID?> {
}