package com.example.bannerapi.bannermanager

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("banners")
data class Banner(
        @Id
        val id : UUID?,
        @Column("banner_url")
        val bannerUrl : String,
        @Column("advertiser_name")
        val advertiserName : String,
        @Column("creation_date")
        @CreatedDate
        val creationDate : LocalDateTime?
) {}