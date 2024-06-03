package goryachkovskiy.danil.mtuci.kmm.data_remote

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging


actual fun getToken(): String {
    val savedValue = FirebaseMessaging.getInstance().getToken()
    println("TOKEN" +    Firebase.firestore.collection("fcmTokens").document("myuserid").get())
    Firebase.firestore.collection("fcmTokens").document("myuserid").get()
    return savedValue.getResult()
}
