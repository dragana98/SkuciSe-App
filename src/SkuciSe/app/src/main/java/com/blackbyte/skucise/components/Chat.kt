package com.blackbyte.skucise.components


class Chat (
    var senderId: Int = 0,
    var sender: String = "",
    var lastText: InboxMessage,
    var newMsg: Boolean = false,
    var url: String = ""
){
}
