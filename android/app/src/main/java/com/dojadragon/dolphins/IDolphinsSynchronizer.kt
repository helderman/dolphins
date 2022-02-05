package com.dojadragon.dolphins

import java.util.*

interface IDolphinsSynchronizer {
    fun allow()
    fun disallow()
    fun <T> ifAllowed(action: () -> T): T?
}
