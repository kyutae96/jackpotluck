package com.kyutae.jackpotluck.objects

import android.util.Log
import android.view.View
import com.kyutae.jackpotluck.fragment.LogFragment
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import java.util.Calendar


object Utils {
    private val TAG = this::class.java.simpleName

    private const val PEARSONNUM = 0.9678622145200673
    fun timeLeft(): String {
        // 현재 시간을 가져옴
        val currentTime = Calendar.getInstance()

        // 다음 토요일의 오후 8시 45분을 계산
        var daysUntilSaturday = Calendar.SATURDAY - currentTime.get(Calendar.DAY_OF_WEEK)
        if (daysUntilSaturday < 0) {
            daysUntilSaturday += 7 // 다음 주로 이동
        }
        val targetTime = currentTime.clone() as Calendar
        targetTime.add(Calendar.DAY_OF_MONTH, daysUntilSaturday)
        targetTime.set(Calendar.HOUR_OF_DAY, 20)
        targetTime.set(Calendar.MINUTE, 45)
        targetTime.set(Calendar.SECOND, 0)

        // 현재 시간과 목표 시간 사이의 시간 계산
        val millisToTarget = targetTime.timeInMillis - currentTime.timeInMillis

//        Log.e(TAG, "목표 시간 : ${targetTime.timeInMillis}")

        val itemPlaySeconds = millisToTarget.toInt() / 1000
        val hours = itemPlaySeconds / 3600
        val minutes = (itemPlaySeconds % 3600) / 60
        val seconds = itemPlaySeconds % 60
        return simpleDateFormatter(hours, minutes, seconds)
    }

    fun simpleDateFormatter(hour: Int, min: Int, sec: Int): String {
        val formattedPlayTime = StringBuilder()

        if (hour != 0) {
            formattedPlayTime.append("${hour}시간 ")
        }

        if (min != 0) {
            formattedPlayTime.append("${min}분 ")
        }

        if (sec != 0) {
            formattedPlayTime.append("${sec}초")
        }

        return formattedPlayTime.toString().trim()
    }

    fun generateMultipleLottoLists(numberOfLists: Int): List<List<Int>> {
        val allLottoLists = mutableListOf<List<Int>>()

        repeat(numberOfLists) {
//            val lottoList = generateLottoNumbers()
            val lottoList = setList(generateLottoNumbers())
            allLottoLists.add(lottoList)
        }

        return allLottoLists
    }

    fun generateLottoNumbers(): List<Int> {
        // 1부터 45까지의 숫자 리스트 생성
        val numbers = (1..45).toMutableList()

        // 리스트를 섞음 (무작위로 숫자 선택을 위해)
        numbers.shuffle()

        // 처음 6개의 숫자를 선택하고 정렬
        val selectedNumbers = numbers.take(6).sorted()

        return selectedNumbers
    }

    fun setList(initList: List<Int>): List<Int> {
        // 1부터 45까지의 숫자 리스트 생성
        val numbers = (1..45).toMutableList()
        val finalList = (numbers + initList).distinct() - (numbers intersect initList)
        // 리스트를 섞음 (무작위로 숫자 선택을 위해)
        val finalmutable = finalList.toMutableList()
        finalmutable.shuffle()
        // 처음 6개의 숫자를 선택하고 정렬
        val selectedNumbers = finalmutable.take(6).sorted()
        return selectedNumbers
    }

    private fun calValY(): DoubleArray {
        // 1부터 45까지의 숫자 리스트 생성
        val numbers = (1..45).toMutableList()

        // 리스트를 섞음 (무작위로 숫자 선택을 위해)
        numbers.shuffle()

        // 처음 6개의 숫자를 선택하고 정렬
        val selectedNumbers = numbers.take(6).sorted()
        return selectedNumbers.map { it.toDouble() }.toDoubleArray()
    }

    private fun targetTime(): Long {
        // 현재 시간을 가져옴
        val currentTime = Calendar.getInstance()

        // 다음 토요일의 오후 8시 45분을 계산
        var daysUntilSaturday = Calendar.SATURDAY - currentTime.get(Calendar.DAY_OF_WEEK)
        if (daysUntilSaturday < 0) {
            daysUntilSaturday += 7 // 다음 주로 이동
        }
        val targetTime = currentTime.clone() as Calendar
        targetTime.add(Calendar.DAY_OF_MONTH, daysUntilSaturday)
        targetTime.set(Calendar.HOUR_OF_DAY, 20)
        targetTime.set(Calendar.MINUTE, 45)
        targetTime.set(Calendar.SECOND, 0)

  /*      // 저번 주 토요일의 오후 8시 45분을 계산
        val targetTimeLastWeek = targetTime.clone() as Calendar
        targetTimeLastWeek.add(Calendar.DAY_OF_MONTH, -7)

        return targetTimeLastWeek.timeInMillis*/
        return targetTime.timeInMillis
    }

    fun pearsonCorrelation(correlation: Double, ep: Double): ArrayList<String> {
        var answer = 0.0
        val epsilon = 1e-10  // 아주 작은 값을 정의하여 부동소수점 비교에 사용
        var y: DoubleArray = doubleArrayOf()

        var results = arrayListOf<String>()


//        while (Math.abs(answer - 0.9678622145200673) > epsilon) {
//        while (Math.abs(answer - 0.98) > 0.02) {
        while (Math.abs(answer - correlation) > ep) {
            val x = doubleArrayOf(
                (targetTime() - 2614246).toDouble(),
                (targetTime() - 2564401).toDouble(),
                (targetTime() - 1878188).toDouble(),
                (targetTime() - 1585380).toDouble(),
                (targetTime() - 965616).toDouble(),
                (targetTime() - 843439).toDouble()
            )
            y = calValY()

//            Log.i(TAG, "x : ${x.toList()}")
//            Log.i(TAG, "y : ${y.toList()}")
            // PearsonsCorrelation 객체 생성
            val pearsonsCorrelation = PearsonsCorrelation()

            // 피어슨 상관계수 계산
            answer = pearsonsCorrelation.correlation(x, y)
//            Log.i(TAG, "pearsons : ${answer}")
            Log.i(TAG, "x : ${x.toList()}")
        }
        Log.i(TAG, "y : ${y.toList()}")
        Log.i(TAG, "pearsons : ${answer}")

        results.add(y.toList().toString())
        results.add(answer.toString())
        LogFragment.context?.bind?.progress!!.visibility = View.GONE
        return results
    }

//   fun MultiplePearson(numberOfLists: Int, corr: Double, ep: Double ): List<List<Double>> {
//        val allLottoLists = mutableListOf<List<Double>>()
//
//        repeat(numberOfLists) {
//            val lottoList = pearsonCorrelation(corr, ep)
//            Log.e(TAG, "@@@@@@@@@@@ : ${lottoList.toList()}")
//            allLottoLists.add(lottoList.toList())
//        }
//
//        return allLottoLists
//    }

    fun pearson0120() {
        // 주어진 데이터
        val X = doubleArrayOf(
            1706353908017.0,
            1706353908026.0,
            1706353908033.0,
            1706353908040.0,
            1706353908045.0,
            1706353908053.0
        )
        val Y = doubleArrayOf(10.0, 12.0, 29.0, 31.0, 40.0, 44.0)

        // PearsonsCorrelation 객체 생성
        val pearsonsCorrelation = PearsonsCorrelation()

        // 피어슨 상관계수 계산
        val correlationCoefficient = pearsonsCorrelation.correlation(X, Y)

        Log.e(TAG, "피어슨 상관계수: $correlationCoefficient")
        // 0.9678622145200673
    }

    fun pearson0113() {
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

        Log.e(TAG, "0120 : ${targetTimeLastWeek.timeInMillis}")

        val pearNum = 1705751100906.0
        // 주어진 데이터
        val X = doubleArrayOf(
            pearNum,
            pearNum + 9.0,
            pearNum + 7.0,
            pearNum + 7.0,
            pearNum + 5.0,
            pearNum + 8.0
        )
        val Y = doubleArrayOf(10.0, 12.0, 29.0, 31.0, 40.0, 44.0)

        // PearsonsCorrelation 객체 생성
        val pearsonsCorrelation = PearsonsCorrelation()

        // 피어슨 상관계수 계산
        val correlationCoefficient = pearsonsCorrelation.correlation(X, Y)

        Log.e(TAG, "피어슨 상관계수: $correlationCoefficient")
        // 0.9678622145200673
    }

}