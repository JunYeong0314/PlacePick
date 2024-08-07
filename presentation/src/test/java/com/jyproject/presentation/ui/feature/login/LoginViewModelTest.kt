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
    fun `회원가입 할 때 토큰을 저장하고 LoginState가 EXIST로 변경`() = runTest {
        // Given
        val userNum = "123"
        val expectToken = "fakeToken"
        viewModel.userNumber = userNum

        coEvery { signUpUseCase(userNum) } returns Result.success(expectToken)
        coEvery { userDataRepository.setUserData("token", expectToken) } just Runs

        // When
        viewModel.signUp()

        // setUserData와 상태 변경이 완료될때까지 대기
        advanceUntilIdle()

        // Then
        coVerify { signUpUseCase(userNum) }
        coVerify { userDataRepository.setUserData("token", expectToken) }
        assert(viewModel.viewState.value.loginState == LoginState.EXIST)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `회원가입 실패 시 LoginState를 ERROR로 변경`() = runTest {
        // Given
        val userNum = "123"
        viewModel.userNumber = userNum
        coEvery { signUpUseCase(userNum) } returns Result.failure(Exception("Sign up failed"))

        // When
        viewModel.signUp()

        advanceUntilIdle()

        // Then
        coVerify { signUpUseCase(userNum) }
        assert(viewModel.viewState.value.loginState == LoginState.ERROR)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `isMember에서 기존유저면 LoginState를 EXIST로 변경`() = runTest {
        //Given
        val userNum = "123"
        val isMemberResult = true
        coEvery { checkMemberUseCase(userNum) } returns Result.success(isMemberResult)

        // When
        viewModel.isMember(userNum)

        advanceUntilIdle()

        //Then
        coVerify { checkMemberUseCase(userNum) }
        assert(viewModel.viewState.value.loginState == LoginState.EXIST)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `isMember에서 첫유저면 LoginState를 INIT으로 변경`() = runTest {
        //Given
        val userNum = "123"
        val isMemberResult = false
        coEvery { checkMemberUseCase(userNum) } returns Result.success(isMemberResult)

        // When
        viewModel.isMember(userNum)

        advanceUntilIdle()

        //Then
        coVerify { checkMemberUseCase(userNum) }
        assert(viewModel.viewState.value.loginState == LoginState.INIT)
    }
}