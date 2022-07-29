package com.example.bannerapi.exception
class BannerAlreadyExistException(message : String? = "This banner already exists.") : RuntimeException(message) {}