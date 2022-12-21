package com.pal2hmnk.example.shared.utils

fun <T, U, V> Function1<T, U>.compose(func: (U) -> V): (T) -> V = { func(this(it)) }
