package com.example.drinkaway.database.model

class dare {
    var id: Int = 0
    var dareText: String? = null
    var amount: Int = 1
    var drinkAmount: Int = 1

    constructor(id: Int, dareText: String, amount: Int, drinkAmount: Int) {
        this.id = id
        this.dareText = dareText
        this.amount = amount
        this.drinkAmount = drinkAmount
    }

    constructor(dareText: String, amount: Int, drinkAmount: Int) {
        this.dareText = dareText
        this.amount = amount
        this.drinkAmount = drinkAmount
    }
}