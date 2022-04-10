package com.example.gamewebview.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gamewebview.R
import com.example.gamewebview.databinding.FragmentSettingBinding
import com.example.gamewebview.di.DaggerApplicationsComponent
import com.example.gamewebview.presentation.GameViewModel
import com.example.gamewebview.presentation.GameViewModelFactory
import javax.inject.Inject


class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    @Inject
    lateinit var viewModelFactory: GameViewModelFactory
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[GameViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerApplicationsComponent.create().inject(this)
        statusRadioButton()
        binding.btExit.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_gameMenuFragment)
            setLevelGame()
        }
        onBackPressed()
    }

    /**
     * Задаю во ViewModel значение уровня сложности
     */
    private fun setLevelGame(){
        viewModel.hardLevel = !binding.rdNormal.isChecked
    }

    /**
     * Задаю начальное значение radioButton и то которое было выбрано прежде
     */
    private fun statusRadioButton(){
        if (viewModel.hardLevel){
            binding.rdHard.isChecked = true
        } else {
            binding.rdNormal.isChecked = true
        }
    }

    /**
     * Переопределяю метод onBackPressed, что бы сохраняло во viewModel выбор на radioButton
     */
    private fun onBackPressed(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setLevelGame()
                findNavController().navigate(R.id.action_settingFragment_to_gameMenuFragment)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }
}