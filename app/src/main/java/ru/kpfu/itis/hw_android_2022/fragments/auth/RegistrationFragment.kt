package ru.kpfu.itis.hw_android_2022.fragments.auth

import android.os.Bundle
import android.util.Log
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
import ru.kpfu.itis.hw_android_2022.databinding.RegistrationFragmentBinding
import ru.kpfu.itis.hw_android_2022.db.DatabaseHandler
import ru.kpfu.itis.hw_android_2022.models.UserModel
import ru.kpfu.itis.hw_android_2022.utils.showToast
import ru.kpfu.itis.hw_android_2022.utils.toMd5
import ru.kpfu.itis.hw_android_2022.utils.validateInput

class RegistrationFragment : Fragment() {
    private var _binding: RegistrationFragmentBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegistrationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners() {
        with(binding) {
            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
            btnRegister.setOnClickListener {
                val password = inputLayoutPassword.editText?.text.toString()
                val passwordConfirm = inputLayoutPasswordConfirm.editText?.text.toString()
                val userName = inputLayoutLogin.editText?.text.toString()
                if (validateInput(userName, password, passwordConfirm)) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        if (DatabaseHandler.findUser(userName, password.toMd5()) == null) {
                            DatabaseHandler.createUser(
                                UserModel(
                                    id = 0,
                                    userName = userName,
                                    userPassword = password.toMd5()
                                )
                            )
                            context?.showToast(getString(R.string.register_success))
                            // можно было и сразу в профиль на самом деле..
                            findNavController().navigate(
                                R.id.action_registrationFragment_to_loginFragment
                            )
                        } else {
                            context?.showToast(getString(R.string.register_error))
                            Log.d("DB_RESULT", DatabaseHandler.getUsers().toString())
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
    }

    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun createBundle(message: String) = bundleOf(
            ARG_TEXT to message
        )
    }
}