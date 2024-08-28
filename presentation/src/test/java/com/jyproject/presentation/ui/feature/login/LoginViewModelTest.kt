package com.jyproject.presentation.ui.feature.login

import com.jyproject.domain.features.auth.usecase.CheckMemberUseCase
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
    private lateinit var checkMemberUseCase: CheckMemberUseCase
    private lateinit var userDataRepository: UserDataRepository
    private lateinit var viewModel: LoginViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        naverLoginUseCase = mockk()
        kakaoLoginUseCase = mockk()
        checkMemberUseCase = mockk()
        userDataRepository = mockk()
        viewModel = LoginViewModel(
            naverLoginUseCase, kakaoLoginUseCase,
            checkMemberUseCase, userDataRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `startKakaoLogin는 UseCase, checkMember, setUserData 호출해야함`() = runTest {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `startLogin을 할 때 기존유저면 LoginState는 EXIST`() = runTest {
        val userNum = "123"
        coEvery { checkMemberUseCase(userNum) } returns Result.success(true)

        viewModel.startLogin(userNum)

        advanceUntilIdle()

        coEvery { checkMemberUseCase(userNum) }
        assert(viewModel.viewState.value.loginState == LoginState.EXIST)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `startLogin을 할 때 첫 유저면 LoginState는 Register`() = runTest {
        val userNum = "123"
        coEvery { checkMemberUseCase(userNum) } returns Result.success(false)

        viewModel.startLogin(userNum)

        advanceUntilIdle()

        coEvery { checkMemberUseCase(userNum) }
        assert(viewModel.viewState.value.loginState == LoginState.REGISTER)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `startLogin을 할 때 Boolean타입이 아니면 LoginState는 ERROR`() = runTest {
        val userNum = "123"
        coEvery { checkMemberUseCase(userNum) } returns Result.success(null)

        viewModel.startLogin(userNum)

        advanceUntilIdle()

        coEvery { checkMemberUseCase(userNum) }
        assert(viewModel.viewState.value.loginState == LoginState.ERROR)
    }
}