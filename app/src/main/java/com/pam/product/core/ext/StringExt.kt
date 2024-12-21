package com.pam.product.core.ext

fun String?.toDash() : String{
    if (this == null || this.isEmpty())
        return "-"

    return this
}