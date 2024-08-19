package com.yourandroidguy.simplechatapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.vertexai.vertexAI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {

    private val generativeModel = com.google.firebase.Firebase.vertexAI.generativeModel("gemini-1.5-flash")
    private val _chatMessageList = MutableStateFlow(listOf<ChatMessage>())
    val chatMessageList
        get() =  _chatMessageList.asStateFlow()

    private var _enableSendButton = MutableStateFlow(true)
    val enableSendButton = _enableSendButton.asStateFlow()

    fun sendRequestToAi(chatMessage: ChatMessage){
        viewModelScope.launch(Dispatchers.IO) {
            val message = chatMessage.message

            try {
                _enableSendButton.update { false }

                val resp = async { generativeModel.generateContent(message) }
                val text = resp.await().text

                val cm = ChatMessage(
                    id = chatMessage.id.plus(1),
                    message = text!!,
                    sender = Sender.BOT
                )
                _enableSendButton.update { true }
                _chatMessageList.update { it + cm }
            }catch (e: Exception){
                _enableSendButton.update { true }
                e.printStackTrace()}
        }
    }


    fun updateList(chatMessage: ChatMessage){_chatMessageList.update { it + chatMessage }}
}