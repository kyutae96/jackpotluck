package com.kyutae.jackpotluck.objects

import android.util.Log
import android.view.View
import com.kyutae.jackpotluck.fragment.LogFragment
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import java.util.Calendar

object PearsonUtil {
    private val TAG = this::class.java.simpleName

    fun pearsonCorrelation(): ArrayList<String> {
        var answer = 0.0
        val epsilon = 1e-10  // 아주 작은 값을 정의하여 부동소수점 비교에 사용
        var x: DoubleArray = doubleArrayOf()

        var results  = arrayListOf<String>()


//        while (Math.abs(answer - 0.9678622145200673) > epsilon) {
        while (Math.abs(answer - 0.99) > 0.01) {
//        while (Math.abs(answer - correlation) > ep) {
            x = calValX()
            val y = doubleArrayOf(10.0, 12.0, 29.0, 31.0, 40.0, 44.0)

//            Log.i(TAG, "x : ${x.toList()}")
//            Log.i(TAG, "y : ${y.toList()}")
            // PearsonsCorrelation 객체 생성
            val pearsonsCorrelation = PearsonsCorrelation()

            // 피어슨 상관계수 계산
            answer = pearsonsCorrelation.correlation(x, y)
//            Log.i(TAG, "pearsons : ${answer}")
        }
        Log.i(TAG, "x : ${x.toList()}")
        Log.i(TAG, "pearsons : ${answer}")

        results.add(x.toList().toString())
        results.add(answer.toString())
//        LogFragment.context?.bind?.progress!!.visibility = View.GONE
        return results
    }
    private fun calValX(): DoubleArray {
        // 1부터 45까지의 숫자 리스트 생성
        val numbers = (1706177700453..1706180400453).toMutableList()

        // 리스트를 섞음 (무작위로 숫자 선택을 위해)
        numbers.shuffle()

        // 처음 6개의 숫자를 선택하고 정렬
        val selectedNumbers = numbers.take(6).sorted()
        return selectedNumbers.map { it.toDouble() }.toDoubleArray()
    }

    fun findPearsonDate(){
        // 현재 시간을 가져옴
        val currentTime = Calendar.getInstance()

        // 이번 주 토요일의 오후 8시 45분을 계산
        var daysUntilSaturday = Calendar.SATURDAY - currentTime.get(Calendar.DAY_OF_WEEK)
        if (daysUntilSaturday < 0) {
            daysUntilSaturday += 7 // 다음 주로 이동
        }
        val targetTimeThisWeek = currentTime.clone() as Calendar
        targetTimeThisWeek.add(Calendar.DAY_OF_MONTH, daysUntilSaturday)
        targetTimeThisWeek.set(Calendar.HOUR_OF_DAY, 20)
        targetTimeThisWeek.set(Calendar.MINUTE, 45)
        targetTimeThisWeek.set(Calendar.SECOND, 0)


        // 저번 주 토요일의 오후 8시 45분을 계산
        val targetTimeLastWeek = targetTimeThisWeek.clone() as Calendar
        targetTimeLastWeek.add(Calendar.DAY_OF_MONTH, -7)

        Log.e(TAG, "0120 8시 45분 : ${targetTimeLastWeek.timeInMillis}")

        val pearNum = 1705751100906.0
        // 주어진 데이터
        val X = doubleArrayOf(pearNum, pearNum + 9.0, pearNum + 7.0, pearNum + 7.0, pearNum + 5.0, pearNum + 8.0)
        val Y = doubleArrayOf(10.0, 12.0, 29.0, 31.0, 40.0, 44.0)

        // PearsonsCorrelation 객체 생성
        val pearsonsCorrelation = PearsonsCorrelation()

        // 피어슨 상관계수 계산
        val correlationCoefficient = pearsonsCorrelation.correlation(X, Y)

        Log.e(TAG, "피어슨 상관계수: $correlationCoefficient")
        // 0.9678622145200673
    }

}