package com.jyproject.domain.features.db.usecase

import com.jyproject.domain.features.db.repository.PlaceDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PlaceAddUseCaseTest {
    private lateinit var placeDataRepository: PlaceDataRepository
    private lateinit var placeAddUseCase: PlaceAddUseCase

    @Before
    fun setUp() {
        placeDataRepository = mockk()
        placeAddUseCase = PlaceAddUseCase(placeDataRepository)
    }

    @Test
    fun `invoke는 PlaceDataRepository의 addPlace를 호출`() = runBlocking {
        val place = "Test Place"
        val placeArea = "Test Area"

        coEvery { placeDataRepository.addPlace(place, placeArea) } returns Unit

        placeAddUseCase.invoke(place, placeArea)

        coVerify { placeDataRepository.addPlace(place, placeArea) }
    }
}