package com.example.beStudious.feed

class UserHelperClass {


   lateinit var userName: String
   lateinit var password: String

   constructor() {

   }

    constructor(username: String, password: String) {
        this.userName = username
        this.password = password
    }


}