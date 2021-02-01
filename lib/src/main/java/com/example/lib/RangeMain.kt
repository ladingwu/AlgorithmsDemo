package club.jinmei.lib



const val MAX_NUM = 25
object RangeMain {
    @JvmStatic
     fun main(args:Array<String>){
        getSort().testSort(createArrays(),true)
//        RangeCompator().sortAll(arrayOf("choose","insert","shell","quick"))

//        var a = mutableListOf<Int>(5,3,6,4,1,4,7,6,8,5,2,45,23,34,54)
//        Collections.sort(a,object :Comparator<Int>{
//            override fun compare(p0: Int, p1: Int): Int {
//                if (p0>p1) {
//                    return -1  // 不调整顺序 ，保持p0>p1 即降序                }
//
//                }
//                return 0
//
//            }
//
//        })
//        a.forEach {
//            print(it.toString()+",")
//        }
    }
    fun getSort():BaseSort = QuickSort()
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
            "insert"->InsertSort()
            "choose"->ChooseSort()
            "shell"->ShellSort()
            "quick"->QuickSort()
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

/**
 * 快速排序
 * 每次找到一个数，把大于它的放在右边，小于它的放在左边
 */
class QuickSort:BaseSort(){
    override fun sort(intArray: IntArray) {
        sort(intArray,0,intArray.size-1)
    }

    fun sort(intArray: IntArray,start:Int,end:Int){
        if (start>=end){
            return
        }
        var i = start
        var j = end
        var target = start
        while (i <= j) {
            while (i <= j) {
                if (intArray[target]>intArray[j]){
                    exchange(intArray, target, j)
                    target = j
                    j--
                    break
                }
                j--
            }


            while (i <= j) {
                if (intArray[target]< intArray[i]){
                    exchange(intArray, target, i)
                    target = i
                    i++
                    break

                }
                i++
            }

        }
        sort(intArray,start,target-1)
        sort(intArray,target+1,end)
    }

    override fun rangeName(): String {
        return "quickSort "
    }

}
class ShellSort:BaseSort() {
    override fun sort(intArray: IntArray) {
        var size = intArray.size
        var n = size/2
        // 最后一个增量必须为1
        while (n>=1){
            for (i in n until size){
                var j = i
                while (j>=n && less(intArray[j],intArray[j-n])){
                    exchange(intArray,j,j-n)
                    j -=n
                }
            }
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
        return "shellSort "
    }

}
/**
 * 插入排序
 * 时间复杂度是O(n^2),但是利用了每次遍历得到的排序信息
 * 比选择排序更有效
 */
class InsertSort:BaseSort(){
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
        return "insertSort "
    }

}

/**
 * 选择排序，每次找到剩余数组中最小的，排在已排序的数组末尾
 * 交换n次，比较
 * 时间复杂度 O(n^2)
 */
class ChooseSort : BaseSort() {
    override fun sort(intArray: IntArray) {
        intArray.forEachIndexed { index, i ->
            var minIndex = index
            for (index2 in index until intArray.size){
                if (less(intArray[index2],intArray[minIndex])) minIndex = index2
            }
            exchange(intArray,index,minIndex)
        }
    }

    override fun rangeName() = "chooseSort "

}



abstract class BaseSort{
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
