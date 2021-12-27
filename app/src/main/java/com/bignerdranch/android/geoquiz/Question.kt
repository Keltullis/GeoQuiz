package com.bignerdranch.android.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId:Int,val answer:Boolean){

}
//аннотация помогает встроенному инспектору
//кода проверить во время компиляции, что в конструкторе
//используется правильный строковый идентификатор ресурса