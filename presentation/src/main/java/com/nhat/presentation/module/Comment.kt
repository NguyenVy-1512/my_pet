package com.nhat.presentation.module

class Comment {
        var name: String = ""
        var rate: Double = 0.0
        var comment: String = ""
        var image : String = ""

        //FIXME. this funtion is only used to test. Delete it when getting data from the google API
        constructor() {}
        constructor(name: String, rate: Double, comment: String, image: String) {
            this.name = name
            this.rate = rate
            this.comment = comment
            this.image = image
        }
}