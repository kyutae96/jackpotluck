package com.kyutae.jackpotluck.fragment


import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyutae.jackpotluck.AppClass
import com.kyutae.jackpotluck.NumberAdapter
import com.kyutae.jackpotluck.databinding.FragmentMainBinding
import com.kyutae.jackpotluck.interfaces.CheckBoxClickListener
import com.kyutae.jackpotluck.objects.Animations
import com.kyutae.jackpotluck.objects.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    lateinit var bind: FragmentMainBinding
    private val updateInterval = 1000L // 업데이트 간격: 1초
    private lateinit var job: Job
    var savedData = ""

    private val TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedData = AppClass.prefs.getString("first", "")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentMainBinding.inflate(inflater, container, false)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 코루틴을 사용하여 주기적으로 시간 갱신
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                updateDisplay()
                delay(updateInterval)
            }
        }


//        CoroutineScope(Dispatchers.Default).launch {
//            val resultY = PearsonUtil.pearsonCorrelation()
//
//            Log.e(TAG, "최종 X 값: ${resultY.joinToString(", ")}")
//
//        }


        bind.startBtn.setOnClickListener {
            val numberOfLists = 5
            val lottoLists = Utils.generateMultipleLottoLists(numberOfLists)

            // 결과 출력
            for ((index, lottoList) in lottoLists.withIndex()) {
                println("리스트 ${index + 1}: $lottoList")
            }

            val recyclerView = bind.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = NumberAdapter(lottoLists)

            adapter.setOnCheckBoxClickListener(object : CheckBoxClickListener {
                override fun onClickCheckBox(flag: Int, pos: Int) {
                    Log.d("__checkboxClick__", "$flag / $pos")
                }
            })

            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            startClick()
        }

        bind.retryBtn.setOnClickListener {
            Animations.fadeIn(bind.startBtn)
            Animations.fadeOut(bind.retryBtn)
            Animations.fadeOut(bind.recyclerView)
        }

//        AppClass.prefs.setString("first", "hi")
        Log.e(TAG, "savedData : ${savedData}")

    }

    fun startClick(){
        Animations.fadeOut(bind.startBtn)
        Animations.fadeIn(bind.canonAni)
        bind.canonAni.playAnimation()
        bind.canonAni.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }
            override fun onAnimationEnd(animation: Animator) {
                Animations.fadeIn(bind.finishAni)
                bind.finishAni.playAnimation()
            }
            override fun onAnimationCancel(animation: Animator) {
            }
            override fun onAnimationRepeat(animation: Animator) {
            }
        })
        bind.finishAni.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }
            override fun onAnimationEnd(animation: Animator) {
                Animations.fadeOut(bind.canonAni)
                Animations.fadeOut(bind.finishAni)
                Animations.fadeIn(bind.recyclerView)
                Animations.fadeIn(bind.retryBtn)
            }
            override fun onAnimationCancel(animation: Animator) {
            }
            override fun onAnimationRepeat(animation: Animator) {
            }
        })

    }

    private fun updateDisplay() {
        requireActivity().runOnUiThread {
            bind.timeleftTxt.text = Utils.timeLeft()
        }
    }
    override fun onDestroy() {
        // 액티비티가 종료되면 코루틴도 종료
        job.cancel()
        super.onDestroy()
    }



}