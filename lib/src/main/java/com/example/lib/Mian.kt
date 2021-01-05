package com.example.lib

import java.util.*
import kotlin.Comparator
import kotlin.contracts.contract
import kotlin.math.max

object Mian {
    @JvmStatic
    fun main(args: Array<String>) {
        var word1 = "abcde"
        var word2 = "ace"
//        print(longestPalindrome("aacabdkacaa"))
        print(isPalindrome(1000021))
//        print(maxEnvelopes2(arrayOf(intArrayOf(5, 4), intArrayOf(6, 4), intArrayOf(6, 7), intArrayOf(2, 3))))
    }

    fun isPalindrome(x: Int): Boolean {
        if (x<0){
            return false
        }
        if (x<10){
            return true
        }
        if (x/10 == 0){
            return false
        }
        var preValue = 0
        x.toString().apply {
            var end = this.length-1
            forEachIndexed { index, c ->
                var startIndex = index
                var endIndex = end-index
                var startN = this[startIndex].toInt()
                var endN = this[endIndex].toInt()
                if (startIndex>endIndex){
                    return@forEachIndexed
                }
                if (startIndex == endIndex){
                    return preValue == 0
                }
                preValue = startN.xor(endN)
                if (preValue>0){
                    return false
                }
            }
        }
        return preValue == 0
    }
    fun longestPalindrome(realStr: String): String {

        if (realStr.length <= 1) {
            return realStr
        }
        var s = realStr
        var sb = StringBuffer()
        sb.append("#")
        for (c in s) {
            sb.append(c).append("#")
        }
        s = sb.toString()

        var indexEnd = s.length - 1
        var maxLen = 1
        var startIndex = 0
        var endIndex = 0
        for (indexCenter in indexEnd downTo 0) {
            var c = s[indexCenter]
            var preLen = 1
            for (d in 0..(indexEnd - indexCenter)) {

                var indexLeft = indexCenter - d
                var indexRight = indexCenter + d
                if (indexLeft < 0 || indexRight > indexEnd) {
                    break
                }
                var cR = s[indexRight]
                var cL = s[indexLeft]
                if (indexLeft == indexRight) {
                    preLen = 1
                    if (1 > maxLen) {
                        startIndex = indexLeft
                        endIndex = indexRight
                    }
                    maxLen = Math.max(maxLen, 1)
                    continue
                }
                if (cR == cL) {
                    var len = if (indexLeft + 1 >= indexRight) {
                        indexRight - indexLeft + 1
                    } else {
                         preLen + 2
                    }
                    if (len > maxLen) {
                        startIndex = indexLeft
                        endIndex = indexRight
                    }
                    maxLen = Math.max(maxLen, len)
                    preLen = len

                } else {
                    break
                }
            }
        }
        return s.substring(startIndex, endIndex + 1).replace("#", "")
    }

    fun maxEnvelopes2(envelopes: Array<IntArray>): Int {
        if (envelopes.size <= 1) {
            return envelopes.size
        }
        Arrays.sort(envelopes, object : Comparator<IntArray> {
            override fun compare(p0: IntArray, p1: IntArray): Int {
                var a = if (p0[0] == p1[0]) (p1[1] - p0[1]) else (p0[0] - p1[0])
                return a
            }

        })
        var result = IntArray(envelopes.size)
        var max = 0
        Arrays.fill(result, 1)
        envelopes.forEachIndexed { index, ints ->
            for (j in 0 until index) {
                if (envelopes[index][1] > envelopes[j][1]) {
                    result[index] = Math.max(result[j] + 1, result[index])
                }
            }

        }
        result.forEach {
            max = Math.max(max, it)
        }
        return max
    }

    fun maxEnvelopes(envelopes: Array<IntArray>): Int {
        if (envelopes.size <= 1) {
            return envelopes.size
        }
        var maxF = 0
        var maxS = 0
        // 找到二维数组最大的两个数据
        envelopes.forEach { it ->
            maxF = max(maxF, it[0])
            maxS = max(maxS, it[1])
        }
        var result = Array(envelopes.size + 1) { IntArray(envelopes.size + 1) }
        envelopes.forEach { it ->
            result[it[0]][it[1]] = 1
        }
        var maxValue = 0
        for (index1 in 0..(maxF)) {
            for (index2 in 0..maxS) {
                if (index1 == 0 && index2 == 0) {
                    result[index1][index2] = result[index1][index2]
                    maxValue = max(maxValue, result[index1][index2])
                    continue
                }
                if (index1 == 0) {
                    result[index1][index2] = result[index1][index2 - 1]
                    maxValue = max(maxValue, result[index1][index2])
                    continue
                }
                if (index2 == 0) {
                    result[index1][index2] = result[index1 - 1][index2]
                    maxValue = max(maxValue, result[index1][index2])
                    continue
                }

                if (result[index1][index2] > 0) {
                    result[index1][index2] = result[index1 - 1][index2 - 1] + 1
                    maxValue = max(maxValue, result[index1][index2])
                } else {
                    result[index1][index2] = max(result[index1 - 1][index2], result[index1][index2 - 1])
                }
            }
        }
        return maxValue
    }

    /**
     * 最短编辑距离
     */
    fun changeStr(word1: String, word2: String): Int {
        if (word1.length == 0) {
            return word2.length
        }
        if (word2.length == 0) {
            return word1.length
        }
        var result = Array(word1.length) { IntArray(word2.length) }
        var i = 0
        var j = 0
        for (c1 in word1) {
            for (c2 in word2) {
                if (i == 0 && j == 0) {
                    result[i][j] = if (c1 == c2) 0 else 1
                    j++
                    continue
                }
                if (c1 == c2) {
                    result[i][j] = if (i == 0) {
                        j
                    } else if (j == 0) {
                        i
                    } else {
                        result[i - 1][j - 1] + 1
                    }
                } else {
                    result[i][j] = if (i == 0) {
                        result[i][j - 1] + 1
                    } else if (j == 0) {
                        result[i - 1][j] + 1
                    } else {
                        Math.min(Math.min(result[i - 1][j] + 1, result[i][j - 1] + 1), result[i - 1][j - 1] + 1)
                    }
                }

                j++
            }
            i++
            j = 0
        }
        return result[word1.length - 1][word2.length - 1]
    }

    /**
     * 最长共同子序列
     */
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        if (text1.length == 0 || text2.length == 0) {
            return 0
        }
        var result = Array(text1.length) { IntArray(text2.length) }
        var i = 0
        var j = 0
        for (c1 in text1) {
            for (c2 in text2) {
                var d = if (c1 == c2) 1 else 0
                if (i == 0 && j == 0) { // 如果字符串长度为0，且两个字符串相当，最长共同子串位1，否则位0
                    // result[i][j] 是字符串S(0-i) 和S(0-j)的公共子序列
                    result[i][j] = d
                    j++
                    continue
                }

                if (c1 == c2) {
                    if (i == 0 || j == 0) {
                        result[i][j] = 1 //i或者j为0 ，表示其中一个字符串长度位1，且两个字符相等，最长公共子串为1
                    } else {
                        result[i][j] = result[i - 1][j - 1] + 1
                    }
                } else if (i == 0) { // 字符不等，此时第一串字符长度为1，第二串长度大于1，则把第二串尾巴减一
                    result[i][j] = result[i][j - 1]
                } else if (j == 0) { // 字符不等，此时第二串字符长度为1，第一串长度大于1，则把第一串尾巴减一
                    result[i][j] = result[i - 1][j]
                } else { // 字符不等，子串的共同子序列最大值
                    result[i][j] = Math.max(result[i - 1][j], result[i][j - 1])
                }
                j++
            }
            i++
            j = 0
        }
        return result[text1.length - 1][text2.length - 1]
    }
}