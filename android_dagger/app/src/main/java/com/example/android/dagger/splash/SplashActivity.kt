package com.example.android.dagger.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.android.dagger.MyApplication
import com.example.android.dagger.R
import com.example.android.dagger.login.LoginActivity
import com.example.android.dagger.main.MainActivity
import com.example.android.dagger.registration.RegistrationActivity
import javax.inject.Inject

const val LOGIN = 1
const val REGISTRATION = 2
const val MAIN = 3
val idlingResource = CountingIdlingResource(SplashActivity::class.java.canonicalName)

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.splashComponent().create().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        idlingResource.increment()
        Handler().postDelayed({
            when (splashViewModel.getRedirectCode()) {
                MAIN -> {
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                        finishAndDecrementCounter()
                    }
                }
                REGISTRATION -> {
                    Intent(this, RegistrationActivity::class.java).also {
                        startActivity(it)
                        finishAndDecrementCounter()
                    }
                }
                LOGIN -> {
                    Intent(this, LoginActivity::class.java).also {
                        startActivity(it)
                        finishAndDecrementCounter()
                    }
                }
            }
        }, 3000)
    }

    private fun finishAndDecrementCounter() {
        finish()
        idlingResource.decrement()
    }
}
