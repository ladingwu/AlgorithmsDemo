package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun longestCommonSubsequence2(text1: String, text2: String): Int {
        if (text1.length == 0 || text2.length == 0){
            return 0
        }
        var result = Array(text1.length){IntArray(text2.length)}
        var i = 0
        var j = 0
        for (c1 in text1) {
            for (c2 in text2) {
                var d = if (c1==c2) 1 else 0
                if (i==0 && j==0){
                    result[i][j] = d
                    continue
                }

                if (c1 == c2){
                    if (i==0 || j == 0){
                        result[i][j] = 1
                    }else{
                        result[i][j] = result[i-1][j-1]+1
                    }
                }else if (i==0){
                    result[i][j] = result[i][j-1]
                }else if (j==0){
                    result[i][j] = result[i-1][j]
                }else{
                    result[i][j] = Math.max(result[i-1][j],result[i][j-1])
                }
                j++
            }
            i++
        }
        return result[text1.length-1][text2.length-1]
    }
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        if (text1.length == 0 || text2.length == 0){
            return 0
        }
        var result = Array(text1.length){IntArray(text2.length)}
        var f = text1.length-1
        var s = text2.length-1
        return longestCommoSub(text1, text2, f, s, result)
    }
    fun longestCommoSub(text1: String, text2: String, f:Int, s:Int,result:Array<IntArray>): Int {
        if (f < 0 || s < 0){
            return 0
        }
        if (text1[f] == text2[s]){
            if (s == 0 || f == 0){
                return 1
            }
            result[f][s] =  longestCommoSub(text1, text2, f-1,s-1, result)+1
        }else{
            if (s == 0 || f == 0){
                return 0
            }
            result[f][s] = if (f==0){
                longestCommoSub(text1, text2, f, s-1, result)
            } else if (s == 0){
                longestCommoSub(text1, text2, f-1, s, result)
            }else{
                Math.max(longestCommoSub(text1, text2, f-1, s, result),longestCommoSub(text1, text2, f, s-1, result))
            }
        }
        return result[f][s]
    }
}
