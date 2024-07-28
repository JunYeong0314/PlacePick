package com.jyproject.presentation.ui.feature.login

import com.jyproject.domain.features.auth.usecase.CheckMemberUseCase
import com.jyproject.domain.features.auth.usecase.SignUpUseCase
import com.jyproject.domain.features.db.repository.UserDataRepository
import com.jyproject.domain.features.login.usecase.KakaoLoginUseCase
import com.jyproject.domain.features.login.usecase.NaverLoginUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var naverLoginUseCase: NaverLoginUseCase
    private lateinit var kakaoLoginUseCase: KakaoLoginUseCase
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var checkMemberUseCase: CheckMemberUseCase
    private lateinit var userDataRepository: UserDataRepository
    private lateinit var viewModel: LoginViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        naverLoginUseCase = mockk()
        kakaoLoginUseCase = mockk()
        signUpUseCase = mockk()
        checkMemberUseCase = mockk()
        userDataRepository = mockk()
        viewModel = LoginViewModel(
            naverLoginUseCase, kakaoLoginUseCase,
            signUpUseCase, checkMemberUseCase, userDataRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `startKakaoLogin state 업데이트와 use case 호출`() = runTest {
        // Given
        val userNum = "123"
        coEvery { kakaoLoginUseCase(any(), any()) } answers {
            secondArg<(String?) -> Unit>().invoke(userNum)
        }
        coEvery { userDataRepository.setUserData("num", userNum) } just Runs
        coEvery { checkMemberUseCase(userNum) } returns Result.success(true)

        // When
        viewModel.startKakaoLogin()

        // setUserData(코루틴 작업)이 끝날 때까지 대기
        advanceUntilIdle()

        // Then
        coVerify { kakaoLoginUseCase(any(), any()) }
        coVerify { userDataRepository.setUserData("num", userNum) }
        coVerify { checkMemberUseCase(userNum) }
    }
}