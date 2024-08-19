package com.yourandroidguy.simplechatapp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourandroidguy.simplechatapp.ui.theme.SimpleChatAppTheme

@Composable
fun PromptChatBubble(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ){
        Surface(
            modifier = modifier.widthIn(min = 100.dp, max = 300.dp),
            shape = MaterialTheme.shapes.large,
            color = Color.LightGray
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                text = text, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun ResponseChatBubble(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ){
        Surface(
            modifier = modifier.widthIn(min = 100.dp, max = 300.dp),
            shape = MaterialTheme.shapes.large,
            color = Color.Gray
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                text = text, color = Color.White)
        }
    }

}

@Composable
fun SendPromptRow(
    modifier: Modifier = Modifier,
    enableSendButton: Boolean = true,
    onSendClicked: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(modifier = Modifier.weight(1f), value = text, onValueChange ={text = it} )

        Spacer(modifier = Modifier.width(4.dp))

        AnimatedContent(targetState = enableSendButton) {
            when(it){
                true -> {
                    TextButton(
                        onClick = {
                            onSendClicked(text)
                            text = "" })
                    {
                        Text(text = "Send")
                    }
                }
                else -> {
                    Box(modifier = Modifier){
                        CircularProgressIndicator()
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun MessageBubblePrev() {
    SimpleChatAppTheme {
        SendPromptRow()
    }
}