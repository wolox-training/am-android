package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class UserRepositoryTest {

    // lateinit var userRepository: UserRepository
    //
    // @Mock
    // lateinit var apiService: UserAPI
    //
    // @Before
    // fun setup() {
    //     MockitoAnnotations.initMocks(this)
    //     userRepository = UserRepository()
    //     apiService = ApiBuilder(BuildConfig.WOLOX_API_URL).create(UserAPI::class.java)
    // }
    //
    // @Test
    // fun `get User Info Response`() {
    //     runBlocking {
    //         val userAuth = UserAuth(
    //             "melvin.lambert15@example.com",
    //             "123456"
    //         )
    //         Mockito.`when`(apiService.getUserInfo(userAuth)).thenReturn(Response.success(USER_INFO))
    //         val response = userRepository.getUserInfo(userAuth)
    //         Assertions.assertThat(response).isEqualTo(USER_INFO)
    //     }
    // }
}
