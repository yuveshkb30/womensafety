package com.yuvesh.womensafety

import kotlin.random.Random

data class StudentModel(
    var id:Int=getAutoId(),
    var PHONE:String=""

) {
 companion object {
     fun getAutoId(): Int {
      var random=java.util.Random()
      return random.nextInt(1000)
     }
 }
}


