package com.kyutae.jackpotluck.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import com.kyutae.jackpotluck.databinding.FragmentSettingBinding
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.kyutae.jackpotluck.R
import java.lang.Math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class SettingFragment: Fragment() {

    lateinit var bind: FragmentSettingBinding
    private val TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentSettingBinding.inflate(inflater, container, false)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scatterChart = bind.scatterChart

        // 주어진 데이터
//        val pearNum = 1705751100906.0
        val pearNum = 06.0
        val Y = doubleArrayOf(
            pearNum,
            pearNum + 9.0,
            pearNum + 16.0,
            pearNum + 23.0,
            pearNum + 28.0,
            pearNum + 36.0
        )
        val X = doubleArrayOf(10.0, 12.0, 29.0, 31.0, 40.0, 44.0)

        Log.e(TAG, "코사인 유사도 : ${cosineSimilarity(X,Y)}")
        Log.e(TAG, "멘헤튼 거리 : ${manhattanDistance(X,Y)}")

        // 예시 데이터
        val Xx = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
        val Yy = doubleArrayOf(10.0, 12.0, 29.0, 31.0, 40.0, 44.0)
        // 데이터 포인트 생성
        val dataEntries = mutableListOf<Entry>()
        for (i in X.indices) {
            dataEntries.add(Entry(X[i].toFloat(), Y[i].toFloat()))
        }

        // ScatterDataSet 생성
        val dataSet = ScatterDataSet(dataEntries, "Scatter DataSet")

        // ScatterData 생성 및 DataSet 추가
        val scatterData = ScatterData(dataSet)

        // ScatterChart에 데이터 설정
        scatterChart.data = scatterData

        // 그래프 설명 추가 (선택 사항)
        val description = Description()
        description.text = "Pearson Correlation Scatter Plot"
        scatterChart.description = description

        // 그래프 스타일 및 속성 설정 (선택 사항)
        dataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.blue)
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
        dataSet.valueTextSize = 5f

        // 그래프 업데이트
        scatterChart.invalidate()
    }


    fun cosineSimilarity(vector1: DoubleArray, vector2: DoubleArray): Double {
        require(vector1.size == vector2.size) { "두 벡터는 같은 차원이어야 합니다." }

        var dotProduct = 0.0
        var norm1 = 0.0
        var norm2 = 0.0

        for (i in vector1.indices) {
            dotProduct += vector1[i] * vector2[i]
            norm1 += vector1[i].pow(2)
            norm2 += vector2[i].pow(2)
        }

        return dotProduct / (sqrt(norm1) * sqrt(norm2))
    }

    fun manhattanDistance(vector1: DoubleArray, vector2: DoubleArray): Double {
        require(vector1.size == vector2.size) { "두 벡터는 같은 차원이어야 합니다." }

        var sum = 0.0
        for (i in vector1.indices) {
            sum += abs(vector1[i] - vector2[i])
        }

        return sum
    }


}