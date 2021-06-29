package com.example.come

class Example_Item {
    private var photoURL: String? = null
    private var title: String? = null
    private var amount: String? = null

    public fun Example_Item() {

    }

    //constructors
    public fun Example_Item(ImageResource: String?, Text1: String?, Text2:String) {
        photoURL = ImageResource
        title = Text1
        amount = Text2
    }

    public fun getphotoURL(): String? {
        return photoURL
    }

    public fun gettitle(): String? {
        return title
    }
    public fun getamount(): String? {
        return amount
    }

}