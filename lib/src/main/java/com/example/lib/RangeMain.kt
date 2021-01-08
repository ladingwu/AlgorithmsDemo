package club.jinmei.lib


const val MAX_NUM = 23
object RangeMain {
    @JvmStatic
     fun main(args:Array<String>){
       getRange().testSort(createArrays(),true)
//        RangeCompator().sortAll(arrayOf("choose","insert","shell"))

    }
    fun getRange():BaseRange = ShellRange()
    fun createArrays():IntArray{
        var array = IntArray(MAX_NUM+1)
        for (i in 0.. MAX_NUM){
            array[i] = (Math.random()* MAX_NUM).toInt()
        }
        return array
    }
}
class RangeCompator(){
    companion object{
        private const val  MAX_NUM_TEST = 100_000
    }
    fun sortAll(ranges:Array<String>){
        ranges.forEach {
            sort(it)
        }
    }
    private fun sort(rangeStr:String){
        var range = when(rangeStr.toLowerCase()){
            "insert"->InsertRange()
            "choose"->ChooseRange()
            "shell"->ShellRange()
            else->null
        }
        range?.testSort(createArrays())
    }

    fun createArrays():IntArray{
        var array = IntArray(MAX_NUM_TEST+1)
        for (i in 0.. MAX_NUM_TEST){
            array[i] = (Math.random()* MAX_NUM_TEST).toInt()
        }
        return array
    }
}

class ShellRange:BaseRange() {
    override fun sort(intArray: IntArray) {
        var size = intArray.size
        var n = size/2
        while (n>=1){
            println(n.toString() +" ----")
            for (i in n until size){
                var j = i
                while (j>=n && less(intArray[j],intArray[j-n])){
                    exchange(intArray,j,j-n)
                    j -=n
                }
            }
            intArray.printSelf(true)
            n /= 2
        }
}
//    override fun sort(intArray: IntArray) {
//        // 对 arr 进行拷贝，不改变参数内容
//        val arr: IntArray =intArray
//
//        var gap = 1
//        while (gap < arr.size / 3) {
//            gap = gap * 3 + 1
//        }
//
//        while (gap > 0) {
//            for (i in gap until arr.size) {
//                val tmp = arr[i]
//                var j = i - gap
//                while (j >= 0 && arr[j] > tmp) {
//                    arr[j + gap] = arr[j]
//                    j -= gap
//                }
//                arr[j + gap] = tmp
//            }
//            gap = Math.floor(gap / 3.toDouble()).toInt()
//        }
//
//
//    }

    override fun rangeName(): String {
        return "shellRange "
    }

}
/**
 * 插入排序
 * 时间复杂度是O(n^2),但是利用了每次遍历得到的排序信息
 * 比选择排序更有效
 */
class InsertRange:BaseRange(){
    override fun sort(intArray: IntArray) {
        for (i in 1 until intArray.size){
            for (j in i downTo 0){
                if (j == 0){
                    continue
                }
                  if (less(intArray[j],intArray[j-1])){ // 找到已排序好的数组部分，插入其中
                      exchange(intArray,j-1,j)
                  }else{
                      break
                  }
            }
        }
    }

    override fun rangeName(): String {
        return "insertRange "
    }

}

/**
 * 选择排序，每次找到剩余数组中最小的，排在已排序的数组末尾
 * 交换n次，比较
 * 时间复杂度 O(n^2)
 */
class ChooseRange : BaseRange() {
    override fun sort(intArray: IntArray) {
        intArray.forEachIndexed { index, i ->
            var minIndex = index
            for (index2 in index until intArray.size){
                if (less(intArray[index2],intArray[minIndex])) minIndex = index2
            }
            exchange(intArray,index,minIndex)
        }
    }

    override fun rangeName() = "chooseRange "

}



abstract class BaseRange{
    fun less(a:Int,b:Int) = a<b

    fun testSort(intArray: IntArray,print: Boolean = false):Long{
        intArray.printSelf(print)
        var start = System.currentTimeMillis()
        sort(intArray)
        println("time for ${rangeName()}: ${System.currentTimeMillis()-start}ms")
        intArray.printSelf(print)
        return System.currentTimeMillis()-start
    }
    fun exchange(intArray: IntArray,a:Int,b:Int){
        var c = intArray[a]
        intArray[a] = intArray[b]
        intArray[b] = c
    }
    abstract fun sort(intArray: IntArray)
    abstract fun rangeName():String
}

fun  IntArray.printSelf(print:Boolean  = false){
    if (!print){
        return
    }
    println("start ======")
    forEach {
        print("$it ,")
    }
    println()
    println("done ======")
}
