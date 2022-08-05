package com.example.bannerapi.bannermanager

import com.example.bannerapi.bannermanager.dto.BannerRequest
import com.example.bannerapi.bannermanager.dto.BannerResponse
import com.example.bannerapi.bannermanager.mapper.BannerToResponseMapper
import com.example.bannerapi.bannermanager.mapper.RequestToBannerMapper
import com.example.bannerapi.exception.BannerAlreadyExistException
import com.example.bannerapi.exception.BannerNotFoundException
import io.mockk.*
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

internal class BannerServiceTest {

    private val bannerRepository        : BannerRepository          = mockk<BannerRepository>()
    private val bannerToResponseMapper  : BannerToResponseMapper    = mockk<BannerToResponseMapper>()
    private val requestToBannerMapper   : RequestToBannerMapper     = mockk<RequestToBannerMapper>()
    private val bannerService           : BannerService             = BannerService(
                                                                            bannerRepository,
                                                                            requestToBannerMapper,
                                                                            bannerToResponseMapper)


    @Test
    fun `when BannerService#getAll is called when database has records`() = runBlocking {
        //Given
        val bannerId                = UUID.randomUUID()
        val bannerCreatedInstant    = LocalDateTime.now()
        val banner                  = Banner(bannerId, "banner-url", "Sırat", bannerCreatedInstant)
        val bannerResponse          = BannerResponse(bannerId, "banner-url", "Sırat")
        val bannerFlow              = flowOf(banner, banner, banner)
        val expected                = listOf<BannerResponse>(bannerResponse, bannerResponse, bannerResponse)

        coEvery {
            bannerRepository.findAll()
        } returns bannerFlow

        coEvery {
            bannerToResponseMapper.convert(banner)
        } returnsMany expected


        //When
        val actual = bannerService.getAll().toList()

        //Then
        assertEquals(expected,actual)
        coVerify {
            bannerRepository.findAll()
        }
        coVerify(exactly = 3) {
            bannerToResponseMapper.convert(banner)
        }

    }

    @Test
    fun `when BannerService#getAll is called when database has no records`() = runBlocking {
        //Given
        val expected = emptyList<BannerResponse>()

        coEvery {
            bannerRepository.findAll()
        } returns emptyFlow()


        //When
        val actual = bannerService.getAll().toList()

        //Then
        assertEquals(expected,actual)
        coVerify {
            bannerRepository.findAll()
        }


    }


    @Test
    fun `when BannerService#addBanner is called with valid request`() = runBlocking {
        //Given
        val bannerId        = UUID.randomUUID()
        val bannerRequest   = BannerRequest("banner-url", "Sırat")
        val banner          = Banner(bannerId, "banner-url", "Sırat", LocalDateTime.now())
        val expected        = BannerResponse(bannerId, "banner-url", "Sırat")

        coEvery {
            bannerService.isBannerAlreadyExists(bannerRequest.bannerUrl!!, bannerRequest.advertiserName!!)
        } returns null

        coEvery {
            requestToBannerMapper.convert(bannerRequest)
        } returns banner

        coEvery {
            bannerRepository.save(banner)
        } returns banner

        coEvery {
            bannerToResponseMapper.convert(banner)
        } returns expected

        //When
        val actual = bannerService.addBanner(bannerRequest)

        //Then
        assertEquals(expected, actual)
        coVerifySequence {
            bannerService.isBannerAlreadyExists(bannerRequest.bannerUrl!!, bannerRequest.advertiserName!!)
            requestToBannerMapper.convert(bannerRequest)
            bannerRepository.save(banner)
            bannerToResponseMapper.convert(banner)
        }

    }

    @Test
    fun `when BannerService#addBanner is called with already exist banners request`() = runBlocking {
        //Given
        val bannerRequest   = BannerRequest("banner-url", "Sırat")
        val expected        = Banner(UUID.randomUUID(), "banner-url", "Sırat", LocalDateTime.now())

        coEvery {
            bannerService.isBannerAlreadyExists(bannerRequest.bannerUrl!!, bannerRequest.advertiserName!!)
        } returns expected

        //When-Then
        assertThrows<BannerAlreadyExistException> {
            bannerService.addBanner(bannerRequest)
        }

        coVerify {
            bannerService.isBannerAlreadyExists(bannerRequest.bannerUrl!!, bannerRequest.advertiserName!!)
        }

    }

    @Test
    fun `when BannerService#getById is called with exist id`() = runBlocking {
        //Given
        val bannerId    = UUID.randomUUID()
        val banner      = Banner(bannerId, "banner-url", "Sırat", LocalDateTime.now())
        val expected    = BannerResponse(bannerId, "banner-url", "Sırat")

        coEvery {
            bannerRepository.findById(bannerId)
        } returns banner

        coEvery {
            bannerToResponseMapper.convert(banner)
        } returns expected

        //When
        val actual = bannerService.getById(bannerId)

        //Then
        assertEquals(expected, actual)
        coVerifySequence {
            bannerRepository.findById(bannerId)
            bannerToResponseMapper.convert(banner)
        }

    }


    @Test
    fun `when BannerService#getById is called with not found id`() = runBlocking {
        //Given
        val bannerId = UUID.randomUUID()
        val expected = null

        coEvery {
            bannerRepository.findById(bannerId)
        } returns expected

        //When-Then
        assertThrows<BannerNotFoundException> {
            bannerService.getById(bannerId)
        }

        coVerify {
            bannerRepository.findById(bannerId)
        }

    }

    @Test
    fun `when BannerService#isAlreadyException is called with exist arguments`() = runBlocking {
        //Given
        val expected = Banner(UUID.randomUUID(), "banner-url", "Sırat", LocalDateTime.now())

        coEvery {
            bannerRepository.isBannerAlreadyExists("banner-url", "Sırat")
        } returns expected

        //When
        val actual = bannerService.isBannerAlreadyExists("banner-url", "Sırat")

        //Then
        assertEquals(expected, actual)
        coVerify {
            bannerRepository.isBannerAlreadyExists("banner-url", "Sırat")
        }
    }

    @Test
    fun `when BannerService#isAlreadyException is called with not exist arguments`() = runBlocking {
        //Given
        val expected = null

        coEvery {
            bannerRepository.isBannerAlreadyExists("banner-url", "Sırat")
        } returns expected

        //When
        val actual = bannerService.isBannerAlreadyExists("banner-url", "Sırat")

        //Then
        assertEquals(expected, actual)
        coVerify {
            bannerRepository.isBannerAlreadyExists("banner-url", "Sırat")
        }
    }

}