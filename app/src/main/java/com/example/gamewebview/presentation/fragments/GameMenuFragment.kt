package com.example.gamewebview.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gamewebview.R
import com.example.gamewebview.databinding.FragmentGameMenuBinding
import com.example.gamewebview.presentation.activity.WebViewActivity


class GameMenuFragment : Fragment() {
    lateinit var binding: FragmentGameMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openNewsFragment()
    }

    /**
     * Слушатели кликов и открытие по ним новых фрагментов
     */
    private fun openNewsFragment(){
        binding.btGame.setOnClickListener {
            findNavController().navigate(R.id.action_gameMenuFragment_to_gameFragment)
        }
        binding.btRecord.setOnClickListener {
            findNavController().navigate(R.id.action_gameMenuFragment_to_recordFragment)
        }
        binding.btSetting.setOnClickListener {
            findNavController().navigate(R.id.action_gameMenuFragment_to_settingFragment)
        }
        binding.btWebView.setOnClickListener {
            startActivity(Intent(requireActivity(), WebViewActivity::class.java))
        }
        binding.btInstructions.setOnClickListener {
            findNavController().navigate(R.id.action_gameMenuFragment_to_instructionsFragment)
        }
    }

}