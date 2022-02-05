package com.dojadragon.dolphins

class DolphinsSynchronizerFactory {
    fun create() = object : IDolphinsSynchronizer {
        override fun allow() {
            isAllowed = true
        }

        override fun disallow() {
            synchronizedAction { isAllowed = false }
        }

        override fun <T> ifAllowed(action: () -> T): T? {
            var result: T? = null

            if (isAllowed) {
                synchronizedAction {
                    if (isAllowed) {
                        result = action()
                    }
                }
            }
            return result
        }

        @Synchronized
        private fun synchronizedAction(action: () -> Unit) {
            action()
        }

        @Volatile
        private var isAllowed = false
    }
}
