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
import ru.kpfu.itis.hw_android_2022.utils.PreferencesHandler
import ru.kpfu.itis.hw_android_2022.utils.showToast
import ru.kpfu.itis.hw_android_2022.utils.toMd5
import ru.kpfu.itis.hw_android_2022.utils.validateInput

class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding by lazy { _binding!! }
    private var preferencesHandler: PreferencesHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(layoutInflater)
        preferencesHandler = PreferencesHandler(binding.root.context)
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
                            context?.showToast(getString(R.string.login_success))
                            //сохраняем в SP
                            preferencesHandler?.saveUsername(userName)
                            findNavController().navigate(
                                R.id.action_loginFragment_to_profileFragment
                            )
                        } else {
                            context?.showToast(getString(R.string.login_error))
                        }
                    }
                } else {
                    context?.showToast(getString(R.string.empty_fields_error))
                }
            }
        }
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