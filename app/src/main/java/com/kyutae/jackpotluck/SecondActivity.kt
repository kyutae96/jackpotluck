package com.kyutae.jackpotluck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyutae.jackpotluck.databinding.ActivitySecondBinding
import com.kyutae.jackpotluck.objects.Utils
import kotlinx.coroutines.*

class SecondActivity : AppCompatActivity() {
    lateinit var bind: ActivitySecondBinding
    private val updateInterval = 1000L // 업데이트 간격: 1초
    private lateinit var job: Job
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(bind.root)


        println("현재 시간: ${System.currentTimeMillis()}")


        // 코루틴을 사용하여 주기적으로 시간 갱신
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                updateDisplay()
                delay(updateInterval)
            }
        }
        val numberOfLists = 5
        val lottoLists = Utils.generateMultipleLottoLists(numberOfLists)

        // 결과 출력
        for ((index, lottoList) in lottoLists.withIndex()) {
            println("리스트 ${index + 1}: $lottoList")
        }


        val recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NumberAdapter(lottoLists)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()



    }

    private fun updateDisplay() {
        runOnUiThread {
            bind.timeleftTxt.text = Utils.timeLeft()
        }
    }
    override fun onDestroy() {
        // 액티비티가 종료되면 코루틴도 종료
        job.cancel()
        super.onDestroy()
    }


}