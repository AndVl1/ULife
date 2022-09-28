package ru.bmstu.ulife.utils

import java.util.Base64

inline fun <T1 : Any, T2 : Any, R : Any> multiLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun ByteArray.toBase64(): String = String(Base64.getEncoder().encode(this))

inline fun <T> T.applyIf(condition : Boolean, block : T.() -> Unit) : T = apply {
    if(condition) block(this)
}
