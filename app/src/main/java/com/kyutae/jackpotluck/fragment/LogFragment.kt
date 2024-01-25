package com.kyutae.jackpotluck.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kyutae.jackpotluck.databinding.FragmentLogBinding
import com.kyutae.jackpotluck.objects.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LogFragment : Fragment() {

    lateinit var bind: FragmentLogBinding

    var job: Job? = null
    companion object {
        var context: LogFragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogFragment.context = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentLogBinding.inflate(inflater, container, false)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.startButton.setOnClickListener {
            when {
                bind.correlationEdt.text.isNullOrEmpty() -> {
                    Toast.makeText(context, "correlation Empty", Toast.LENGTH_SHORT).show()
                }

                bind.epsilonEdt.text.isNullOrEmpty() -> {
                    Toast.makeText(context, "epsilon Empty", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    bind.progress.visibility = View.VISIBLE

                    val corr = bind.correlationEdt.text.toString().toDouble()
                    val ep = bind.epsilonEdt.text.toString().toDouble()
                    bind.rangeTxt.text = "${corr - ep} ~ ${corr + ep}"
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000L)
                        val result = Utils.pearsonCorrelation(corr, ep)
                        bind.result.text = result.toList()[0]
                        bind.pearson.text = result.toList()[1]

                    }
                }
            }
        }


    }

}