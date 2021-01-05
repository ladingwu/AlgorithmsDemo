package club.jinmei.lib

const val MAX_NUM = 10_000
object RangeMain {
    @JvmStatic
     fun main(args:Array<String>){
//        var range = getRange()
//        range.testSort(createArrays())
        RangeCompator().sortAll(arrayOf("choose","insert"))

    }
    fun getRange():BaseRange = InsertRange()
    fun createArrays():IntArray{
        var array = IntArray(MAX_NUM+1)
        for (i in 0.. MAX_NUM){
            array[i] = (Math.random()* MAX_NUM).toInt()
        }
        return array
    }
}
class RangeCompator(){
    fun sortAll(ranges:Array<String>){
        ranges.forEach {
            sort(it)
        }
    }
    private fun sort(rangeStr:String){
        var range = when(rangeStr){
            "insert"->InsertRange()
            "choose"->ChooseRange()
            else->null
        }
        range?.testSort(createArrays())
    }

    fun createArrays():IntArray{
        var array = IntArray(MAX_NUM+1)
        for (i in 0.. MAX_NUM){
            array[i] = (Math.random()* MAX_NUM).toInt()
        }
        return array
    }
}

/**
 * 插入排序
 * 时间复杂度是O(n^2)
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

    fun testSort(intArray: IntArray):Long{
        intArray.printSelf()
        var start = System.currentTimeMillis()
        sort(intArray)
        println("time for ${rangeName()}: ${System.currentTimeMillis()-start}ms")
        intArray.printSelf()
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

fun  IntArray.printSelf(){
//    println("start ======")
//    forEach {
//        print("$it ,")
//    }
    println()
//    println("done ======")
}
