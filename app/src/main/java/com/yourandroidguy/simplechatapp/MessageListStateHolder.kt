package com.yourandroidguy.simplechatapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

class MessageListStateHolder(messageList: List<ChatMessage>){
    var value = messageList.toMutableStateList()

    fun update(message: ChatMessage){
        value = (value + message).toMutableStateList()
    }
}

@Composable
fun rememberMessageListState(
    messageList: SnapshotStateList<ChatMessage> = mutableStateListOf()
): MessageListStateHolder {
    return remember(messageList) {
        MessageListStateHolder(messageList)
    }
}