package com.dendi.storyapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.dendi.storyapp.R
import com.dendi.storyapp.data.source.remote.auth.AuthBody
import com.dendi.storyapp.databinding.FragmentLoginBinding
import com.dendi.storyapp.ui.StoryActivity
import com.dendi.storyapp.ui.home.HomeActivity
import com.dendi.storyapp.utils.Status
import com.dendi.storyapp.utils.hideKeyboard
import com.dendi.storyapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var email: String
    private lateinit var password: String

    private val loginVM: LoginVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as StoryActivity?)?.hideLoading()
        (activity as StoryActivity?)?.hideAppBar()


        binding.textRegister.setOnClickListener {
            (activity as StoryActivity?)?.showLoading()
            NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        checkLogin()
        checkData()

    }


    private fun checkLogin(){
        loginVM.tokenUser.observe(viewLifecycleOwner){ checkLogin ->
            if (checkLogin.isNotEmpty()){
                showToast(requireContext(), getString(R.string.notice_checking_session))
                val mIntent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(mIntent)
                (activity as StoryActivity?)?.finish()
//                val moveToHome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//                NavHostFragment.findNavController(this).navigate(moveToHome)
            }
        }
    }


    private fun checkData() {
        binding.apply {
            btnLogin.setOnClickListener {
                email = edtLoginEmail.text.toString()
                password = edtLoginPassword.text.toString()

                if(edtLoginEmail.error == null && edtLoginEmail.error == null){
                    activity?.hideKeyboard()
                    val request = AuthBody(null, email, password)
                    validateUser(request)
                } else showToast(requireContext(), getString(R.string.error_check_not_valid))
            }
        }
    }

    private fun validateUser(addUser: AuthBody) {
        loginVM.loginUser(addUser).observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Status.LOADING -> {
                    (activity as StoryActivity?)?.showLoading()

                }
                Status.SUCCESS -> {
                    (activity as StoryActivity?)?.showLoading()
                    val mIntent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(mIntent)
                    (activity as StoryActivity?)?.finish()
                }
                Status.ERROR -> {
                    (activity as StoryActivity?)?.hideLoading()
                    binding.textWelcome.apply {
                        setTextColor(getColor(resources, R.color.merah, null))
                        text = getString(R.string.error_invalid_login)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}