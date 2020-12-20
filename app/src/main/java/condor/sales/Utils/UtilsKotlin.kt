@file: JvmName("UtilsKotlin")
package condor.sales.Utils

import condor.sales.Models.Incentive
import condor.sales.Models.Reception
import java.util.*
import kotlin.collections.ArrayList

fun getFilterdRecHis (commanddetails : ArrayList<Reception> , dateStart : Date ,dateEnd : Date ):Map<String,Int>{

    val list = commanddetails.filter { dateStart <= it.date_rec && it.date_rec <= dateEnd}.groupingBy { it.commecial_name }.eachCount()

   return list
}


fun getFilterdVenHis (commanddetails : ArrayList<Reception> , dateStart : Date ,dateEnd : Date ):Map<String,Int>{

    val list = commanddetails.filter { dateStart <= it.date_vente && it.date_vente <= dateEnd}.groupingBy { it.commecial_name }.eachCount()

    return list
}






//    val list = ArrayList<Reception>()
//    val date = Calendar.getInstance().time
//    val reception = Reception("","fuck it working","",100,true,date,date,100.toFloat())
//    list.add(reception)

//    /.filter { it.nickname == "bob" || it.nickname == "emily" }