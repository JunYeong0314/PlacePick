package com.jyproject.data.features.db

import com.jyproject.data.db.PlaceDAO
import com.jyproject.data.db.entitiy.PlaceEntity
import com.jyproject.data.mappers.RoomDataMapper
import com.jyproject.domain.features.db.repository.PlaceDataRepository
import com.jyproject.domain.models.Place
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PlaceDataRepositoryImplTest {
    private lateinit var placeDAO: PlaceDAO
    private lateinit var mapper: RoomDataMapper
    private lateinit var placeDataRepository: PlaceDataRepository

    @Before
    fun setUp() {
        placeDAO = mockk()
        mapper = mockk()
        placeDataRepository = PlaceDataRepositoryImpl(placeDAO, mapper)
    }

    @Test
    fun `readPlace는 제네릭 타입이 Place인 List 반환`() = runBlocking {
        // Given
        val placeEntities = listOf(PlaceEntity(no = 0, place = "test place", placeArea = "test area"))
        val places = listOf(Place(no = 0, place = "test place", placeArea = "test area"))

        coEvery { placeDAO.readPlace() } returns flowOf(placeEntities)
        coEvery { mapper.mapPlaceEntity(placeEntities) } returns places

        // When
        val result = placeDataRepository.readPlace()

        // Then
        result.collect { list->
            assert(list == places)
        }

    }
}