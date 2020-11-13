package com.nhat.presentation.module

class Shop {
    var name: String = ""
    var image: String = ""
    var rate : Double = 0.0
    constructor() {}
    constructor(name: String, image: String, rate: Double){
        this.name = name
        this.image = image
        this.rate = rate
    }
}