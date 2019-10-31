package com.lengjiye.code


class SingletonKotlin private constructor() {
    companion object {
        var singleton = SingletonKotlin.instance
    }

    private object SingletonKotlin {
        val instance = SingletonKotlin()
    }
}

