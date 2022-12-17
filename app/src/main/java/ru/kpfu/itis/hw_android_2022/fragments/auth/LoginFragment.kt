package ru.kpfu.itis.hw_android_2022.fragments.auth

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
import ru.kpfu.itis.hw_android_2022.databinding.LoginFragmentBinding
import ru.kpfu.itis.hw_android_2022.db.DatabaseHandler
import ru.kpfu.itis.hw_android_2022.utils.showToast
import ru.kpfu.itis.hw_android_2022.utils.toMd5
import ru.kpfu.itis.hw_android_2022.utils.validateInput

class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }


    private fun initClickListeners() {
        with(binding) {
            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            btnLogin.setOnClickListener {
                val password = inputLayoutPassword.editText?.text.toString()
                val userName = inputLayoutLogin.editText?.text.toString()
                if (validateInput(userName, password)) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        if (DatabaseHandler.findUser(userName, password.toMd5()) != null) {
                            showToast("Logged in")
                        } else {
                            showToast("Wrong username or password. Try again")
                        }
                    }
                } else {
                    showToast("All fields must be filled in")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun createBundle(message: String) = bundleOf(
            ARG_TEXT to message
        )
    }
}