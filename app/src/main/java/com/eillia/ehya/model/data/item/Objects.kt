package com.eillia.ehya.model.data.item

import com.eillia.ehya.model.data.entity.Sunnah

data class SwipeEvent(val swipeResult: SwipeResult = SwipeResult.NONE, val sunnah: Sunnah? = null)

enum class SwipeResult { TRY, PASS, NONE }
