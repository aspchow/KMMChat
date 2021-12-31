package com.avinash.mykmmchat.android.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avinash.database.db.Message
import com.avinash.mykmmchat.android.AppViewModel
import com.avinash.mykmmchat.android.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import java.util.*

@Composable
fun LinkedInCompose() {

    val scope = rememberCoroutineScope()

    val viewmodel = get<AppViewModel>()

    val messages by viewmodel.messages.collectAsState(initial = emptyList())

    var newMessage by remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {

        UserInfo()

        LazyColumn(
            Modifier
                .weight(1f)
                .padding(10.dp)
        ) {
            items(messages) { message ->
                MessageView(message)
            }
        }

        MessageSentBar(newMessage = newMessage, onNewMessage = {
            newMessage = it
        }) {
            scope.launch(Dispatchers.IO) {
                Log.d("AVINASH", "NEW MESSAGE IS: $newMessage")
                viewmodel.sendNewMessage(newMessage)
                newMessage = ""
            }
        }
    }

}

@Preview
@Composable
fun MessageSentBar(
    newMessage: String = "Ios",
    onNewMessage: (String) -> Unit = {},
    onMessageSent: () -> Unit = {}
) {


    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = newMessage, onValueChange = onNewMessage, Modifier.weight(1f),
            textStyle = MaterialTheme.typography.h2
        )
        if (newMessage.isNotEmpty())
            Icon(modifier = Modifier.clickable {
                onMessageSent()
            }, painter = painterResource(id = R.drawable.send_green), contentDescription = null)
    }
}

@Preview
@Composable
fun MessageView(
    message: Message = Message(
        messageId = "123", userName = "Avinash Munnangi", "Hai Dude", 123, false
    )
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = if (message.userName == "android") R.drawable.androidp else R.drawable.ic_apple_13),
            contentDescription = null,
            tint = if (message.userName == "android") Color.Unspecified else Color.Black
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Row {
                Text(text = message.userName, style = MaterialTheme.typography.h2)

                val date = Date(message.creationTime)
                val time = " " + date.hours + ":" + date.minutes
                Text(text = time,style = TextStyle(fontWeight = FontWeight.W300))
            }
            Text(text = message.messageText, style = TextStyle(fontWeight = FontWeight.W500))
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
fun UserInfo(userName: String = "Ios") {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_apple_13),
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = userName, style = MaterialTheme.typography.h1, color = Color.White)
    }
}

