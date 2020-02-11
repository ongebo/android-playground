package com.example.android.dagger.splash

import com.example.android.dagger.user.UserManager
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val userManager: UserManager) {
    fun getRedirectCode() = when {
        !userManager.isUserLoggedIn() -> if (userManager.isUserRegistered()) LOGIN else REGISTRATION
        else -> MAIN
    }
}
