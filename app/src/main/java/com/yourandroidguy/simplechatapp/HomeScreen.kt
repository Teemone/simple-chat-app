package com.yourandroidguy.simplechatapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel()
) {
    // NOTE: Use collectAsStateWithLifecycle() because it is lifecycle aware instead
    // of collectAsState() which isn't lifecycle aware

    val messages by viewModel.chatMessageList.collectAsState()
    val enableSendButton by viewModel.enableSendButton.collectAsState()

    Scaffold(
        modifier
            .statusBarsPadding()
            .imePadding()) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(messages){ message ->
                    when(message.sender){
                        Sender.USER -> {
                            PromptChatBubble(text = message.message)
                        }

                        Sender.BOT -> {
                            ResponseChatBubble(text = message.message)
                        }
                    }
                }
            }

            SendPromptRow(
                modifier = Modifier.padding(horizontal = 4.dp),
                enableSendButton = enableSendButton
            ){ text ->

                if (text.isNotBlank() && text.isNotEmpty()){
                    val message = ChatMessage(
                        messages.lastIndex,
                        text,
                        Sender.USER
                    )
                    viewModel.updateList(message)
                    viewModel.sendRequestToAi(message)

                }


            }
        }
    }


}