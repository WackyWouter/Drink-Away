package com.example.drinkaway.database.model

class dare {
    var id: Int = 0
    var dareText: String? = null
    var amount: Int = 1
    var drinkAmount: Int = 1
    var drinkBool: Int = 1

    constructor(id: Int, dareText: String, amount: Int, drinkAmount: Int, drinkBool: Int) {
        this.id = id
        this.dareText = dareText
        this.amount = amount
        this.drinkAmount = drinkAmount
        this.drinkBool = drinkBool
    }

    constructor(dareText: String, amount: Int, drinkAmount: Int, drinkBool: Int) {
        this.dareText = dareText
        this.amount = amount
        this.drinkAmount = drinkAmount
        this.drinkBool = drinkBool
    }
}