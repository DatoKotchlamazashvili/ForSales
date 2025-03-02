package com.example.tbcexercises.utils.extension


fun String.getCompany(): String {
    if (this.contains(",")) {
        return this.split(",").get(0)
    }
    return this
}