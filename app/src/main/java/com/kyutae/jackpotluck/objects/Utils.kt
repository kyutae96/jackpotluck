package com.kyutae.jackpotluck.objects

import java.util.Calendar


object Utils {
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

}