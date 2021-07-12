package com.example.android.app.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.android.app.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val args by navArgs<UserFragmentArgs>()

    private var _binding: FragmentUserBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}