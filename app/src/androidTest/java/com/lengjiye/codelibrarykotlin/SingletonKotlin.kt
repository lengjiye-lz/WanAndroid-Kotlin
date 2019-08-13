package com.lengjiye.codelibrarykotlin


class SingletonKotlin private constructor() {
    companion object {
        var singleton = SingletonKotlin.instance
    }

    private object SingletonKotlin {
        val instance = SingletonKotlin()
    }
}

