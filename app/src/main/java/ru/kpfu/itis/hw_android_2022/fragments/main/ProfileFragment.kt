package ru.kpfu.itis.hw_android_2022.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.databinding.ProfileFragmentBinding
import ru.kpfu.itis.hw_android_2022.db.DatabaseHandler
import ru.kpfu.itis.hw_android_2022.utils.PreferencesHandler
import ru.kpfu.itis.hw_android_2022.utils.showSnackbar
import ru.kpfu.itis.hw_android_2022.utils.showToast

class ProfileFragment: Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding by lazy { _binding!! }
    private var preferencesHandler: PreferencesHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(layoutInflater)
        preferencesHandler = PreferencesHandler(binding.root.context)
        setUpOnClickListeners()
        return binding.root
    }

    private fun setUpOnClickListeners() {
        with(binding) {
            btnLogout.setOnClickListener {
                view?.showSnackbar(
                    msg = getString(R.string.logout_message),
                    actionMessage = getString(R.string.ok),
                    action = ::logout
                )
            }
            btnEdit.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    val userId = preferencesHandler?.getUsername()
                        ?.let { it1 -> DatabaseHandler.findUserId(it1) }
                    if (userId != null) {
                        val newUserName = inputLayoutLogin.editText?.text.toString()
                        DatabaseHandler.updateUser(username = newUserName, userId)
                        //также надо обновить sp
                        preferencesHandler?.saveUsername(newUserName)
                        context?.showToast("Updated successfully")
                    } else {
                        context?.showToast("Couldn't find such a user")
                    }
                }
            }
        }
    }


    //навигируемся в себя же, тем самым трегиррим проверку сессии пользователя на уровне активити
    //возможно, не самое лучшее решение, но зато простое и понятное
    private fun logout() {
        preferencesHandler?.removeUsername()
        findNavController().navigate(
            R.id.action_profileFragment_self
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        preferencesHandler = null
    }


    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun createBundle(message: String) = bundleOf(
            ARG_TEXT to message
        )
    }
}