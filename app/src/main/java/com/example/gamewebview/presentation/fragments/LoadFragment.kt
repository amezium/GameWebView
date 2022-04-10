package com.example.gamewebview.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appsflyer.AppsFlyerLib
import com.example.gamewebview.R
import com.example.gamewebview.data.api.dto.AppsFlyerDataDto
import com.example.gamewebview.databinding.FragmentLoadBinding
import com.example.gamewebview.di.DaggerApplicationsComponent
import com.example.gamewebview.domain.SendDataUseCase
import com.example.gamewebview.presentation.GameViewModel
import com.example.gamewebview.presentation.GameViewModelFactory
import com.example.gamewebview.presentation.MyApplication
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.fullyInitialize
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoadFragment : Fragment() {
    lateinit var binding: FragmentLoadBinding
    @Inject
    lateinit var viewModelFactory: GameViewModelFactory
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[GameViewModel::class.java]
    }
    private var deeplink: String? = null
    private var installTime: String? = null
    private var afStatus: String? = null
    private var afMessage: String? = null
    private var isFirstLaunch: String? = null
    private var afIdKey: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerApplicationsComponent.create().inject(this)
        lifecycleScope.launch(Dispatchers.IO) {
            delay(2000) //задержка для красоты, что бы progressBar отобразился(не зря же делал его)
            startInitialFb()
            withContext(Dispatchers.Main) {
                getAppsFlyerParams()
                collectFullLink() //отправляю данные на сервер
                findNavController().navigate(R.id.action_loadFragment_to_gameMenuFragment)
            }
        }
    }

    /**
     * Инициализирую фейсбук
     */
    private fun startInitialFb() {
        FacebookSdk.setAutoInitEnabled(true)
        fullyInitialize()
        AppLinkData.fetchDeferredAppLinkData(
            activity
        ) {
            deeplink = it?.targetUri.toString()
        }
    }

    /**
     * Получаю параметры с AppsFlyer
     */
    private fun getAppsFlyerParams() {
        afIdKey = AppsFlyerLib.getInstance().getAppsFlyerUID(requireActivity())
        MyApplication.liveDataAppsFlyer.observe(requireActivity()){
            for (inform in it) {
                when(inform.key){
                    "install_time" -> installTime = inform.value.toString()
                    "af_status" -> afStatus = inform.value.toString()
                    "af_message" -> afMessage = inform.value.toString()
                    "is_first_launch" -> isFirstLaunch = inform.value.toString()
                }
            }
        }
    }

    /**
     * Собираю данныес appsFlyer и отправляю на сервер
     */
    private fun collectFullLink(){
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.sendData(AppsFlyerDataDto(installTime, afStatus, afMessage, isFirstLaunch, afIdKey))
        }
    }
}
