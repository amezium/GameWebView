package com.example.gamewebview.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamewebview.R
import com.example.gamewebview.databinding.FragmentGameBinding
import com.example.gamewebview.di.DaggerApplicationsComponent
import com.example.gamewebview.presentation.GameAdapter
import com.example.gamewebview.presentation.GameViewModel
import com.example.gamewebview.presentation.GameViewModelFactory
import javax.inject.Inject


class GameFragment : Fragment(), GameAdapter.ClickItem {
    lateinit var binding: FragmentGameBinding
    lateinit var adapter: GameAdapter
    @Inject
    lateinit var viewModelFactory: GameViewModelFactory
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[GameViewModel::class.java]
    }


    private var startGameHardLevel = listOf(
        R.drawable.question, R.drawable.question, R.drawable.question,
        R.drawable.question, R.drawable.question, R.drawable.question
    )
    private var startGameNormalLevel = listOf(
        R.drawable.question, R.drawable.question, R.drawable.question,
        R.drawable.question
    )
    private var winPosition = 0
    private var countClick = 3
    private var countRound = 1
    private var countCoinResult = 0
    private var countCoinNow = 0
    private var gameContinues = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerApplicationsComponent.create().inject(this)
        startAdapter()
        winPosition = if (viewModel.hardLevel) {
            (0..5).random()
        } else {
            (0..3).random()
        }
    }

    /**
     * Запускаю адаптер
     */
    private fun startAdapter() {
        adapter = GameAdapter(this)
        binding.rcView.adapter = adapter
        binding.rcView.layoutManager = GridLayoutManager(requireActivity(), 2)
        if (viewModel.hardLevel){
            adapter.listGame.addAll(startGameHardLevel)
        } else adapter.listGame.addAll(startGameNormalLevel)
    }

    /**
     * Реализация интерфейса, для кликов по ячейкам в recyclerView
     */
    override fun onClickItem(position: Int) {
        if (countClick >= 1 && gameContinues) {
            if (position == winPosition) {
                countCoin() //увеличиваю балы
                countClick--
                binding.tvAttempts.text = countClick.toString()
                adapter.listGame.removeAt(position)
                adapter.notifyItemChanged(position)
                adapter.listGame.add(position, R.drawable.sunduk)
                gameContinues = false
                if (countRound == 3) gameOver() //В конце игры предлагаю начать заново или вернуться в меню
                nextRound() //показываю текст о следующем раунде
            } else {
                countClick--
                binding.tvAttempts.text = countClick.toString()
                adapter.listGame.removeAt(position)
                adapter.listGame.add(position, R.drawable.smail)
                adapter.notifyItemChanged(position)
            }
        }
        if (countClick == 0) {
            if (countRound == 3) gameOver() //В конце игры предлагаю начать заново или вернуться в меню
            nextRound() //показываю текст о следующем раунде
            gameContinues = false
        }
    }


    /**
     * Слушатели нажатий на кнопки
     */
    private fun clickButton() = with(binding) {
        btExit.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_gameMenuFragment)
        }
        btRepeat.setOnClickListener {
            visibilityViewGone()
            gameContinues = true
            countCoinResult = 0
            countCoinNow = 0
            countRound = 1
            tvRound.text = countRound.toString()
            countClick = 3
            tvAttempts.text = "3"
            tvCoins.text = "0"
            adapter.listGame.clear()
            if (viewModel.hardLevel){
                winPosition = (0..5).random()
                adapter.listGame.addAll(startGameHardLevel)
            } else {
                winPosition = (0..3).random()
                adapter.listGame.addAll(startGameNormalLevel)
            }
            adapter.notifyItemRangeChanged(0, 6)
        }
    }

    private fun visibilityViewGone() = with(binding) {
        btExit.visibility = View.GONE
        btRepeat.visibility = View.GONE
        cardViewId.visibility = View.GONE
        tvResult.visibility = View.GONE
    }

    /**
     * Счетчик балов
     */
    private fun countCoin() {
        when (countClick) {
            3 -> {
                countCoinResult += 100
                countCoinNow = 100
            }
            2 -> {
                countCoinResult += 70
                countCoinNow = 70
            }
            1 -> {
                countCoinResult += 50
                countCoinNow = 50
            }
        }
        binding.tvCoins.text = countCoinResult.toString()
    }

    /**
     * Показываю текст с окончанием раунда и делаю видимыми кнопки
     */
    private fun nextRound() = with(binding) {
        if (countRound < 3) {
            cardViewId.visibility = View.VISIBLE
            tvNextRound.visibility = View.VISIBLE
            btNextRound.visibility = View.VISIBLE
            tvNextRound.text =
                resources.getString(
                    R.string.number_round,
                    countRound.toString(),
                    countCoinNow.toString()
                )
            btNextRound.setOnClickListener {
                countRound++
                tvRound.text = countRound.toString()
                countCoinNow = 0
                binding.tvAttempts.text = "3"
                countClick = 3
                gameContinues = true
                cardViewId.visibility = View.GONE
                tvNextRound.visibility = View.GONE
                btNextRound.visibility = View.GONE
                adapter.listGame.clear()
                if (viewModel.hardLevel){
                    winPosition = (0..5).random()
                    adapter.listGame.addAll(startGameHardLevel)
                } else {
                    winPosition = (0..3).random()
                    adapter.listGame.addAll(startGameNormalLevel)
                }
                adapter.notifyItemRangeChanged(0, 6)
            }
        }
    }

    /**
     * В конце игры предлагаю начать заново или вернуться в меню
     */
    private fun gameOver() = with(binding) {
        cardViewId.visibility = View.VISIBLE
        tvResult.visibility = View.VISIBLE
        tvResult.text = resources.getString(R.string.result, countCoinResult.toString())
        binding.btRepeat.visibility = View.VISIBLE
        binding.btExit.visibility = View.VISIBLE
        clickButton() //кнопки для повтора игры или выхода в меню
        setRecord()
    }

    /**
     * Устанавливаю во ViewModel значение для таблицы рекордов
     */
    private fun setRecord() = with(viewModel){
        if (countCoinResult > firstPlace){
            thirdPlace = secondPlace
            secondPlace = firstPlace
            firstPlace = countCoinResult
        }
        if (countCoinResult in (secondPlace + 1) until firstPlace){
            thirdPlace = secondPlace
            secondPlace = countCoinResult
        }
        if (countCoinResult in (thirdPlace + 1) until secondPlace && countCoinResult < firstPlace){
            thirdPlace = countCoinResult
        }
    }
}