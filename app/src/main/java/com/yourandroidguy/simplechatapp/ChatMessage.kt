package com.yourandroidguy.simplechatapp

data class ChatMessage(
    val id: Int,
    val message: String,
    val sender: Sender,
)

enum class Sender{
    USER, BOT
}