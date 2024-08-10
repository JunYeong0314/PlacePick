package com.jyproject.presentation.ui.feature.home

import com.jyproject.domain.features.db.usecase.PlaceDeleteUseCase
import com.jyproject.domain.features.db.usecase.PlaceReadUseCase
import com.jyproject.domain.models.Place
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var placeReadUseCase: PlaceReadUseCase
    private lateinit var placeDeleteUseCase: PlaceDeleteUseCase
    private lateinit var viewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)

        placeReadUseCase = mockk()
        placeDeleteUseCase = mockk()
        viewModel = HomeViewModel(placeReadUseCase, placeDeleteUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getPlaceData() 호출 시 에러가 발생하면 PlaceState 상태는 ERROR`() = runTest {
        // Given
        val exception = RuntimeException("Test Exception")
        coEvery { placeReadUseCase() } returns flow { throw exception }

        // When
        viewModel.getPlaceData()
        advanceUntilIdle()

        // Then
        coVerify { placeReadUseCase() }
        assert(viewModel.viewState.value.placeState == PlaceState.ERROR)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getPlaceData() 호출 시 PlaceState 상태는 SUCCESS`() = runTest {
        // Given
        val placeList: List<Place> = listOf(Place(no = 1, place = "test", placeArea = "testArea"))
        coEvery { placeReadUseCase() } returns flow { emit(placeList) }

        // When
        viewModel.getPlaceData()
        advanceUntilIdle()

        // Then
        coVerify { placeReadUseCase() }
        assert(viewModel.viewState.value.placeState == PlaceState.SUCCESS)
    }
}