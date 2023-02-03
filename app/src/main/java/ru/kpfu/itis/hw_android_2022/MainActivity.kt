package ru.kpfu.itis.hw_android_2022

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ru.kpfu.itis.hw_android_2022.fragments.MainFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            add<MainFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
            addToBackStack(MainFragment.FRAGMENT_NAME)
        }
    }
}