package ru.lionzxy.alarmonnotification

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Environment
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast
import java.io.File
import java.util.concurrent.TimeUnit

var lastActive = 0L

public class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val packageName = sbn?.packageName ?: return
        val extras = sbn.notification.extras ?: return
        var titleData = ""
        var textData = ""

        if (extras.getCharSequence("android.title") != null) {
            titleData = extras.getCharSequence("android.title")?.toString() ?: ""
        }
        if (extras.getCharSequence("android.text") != null) {
            textData = extras.getCharSequence("android.text")?.toString() ?: ""
        }

        Log.d("Package", packageName);
        Log.d("Title", titleData);
        Log.d("Text", textData);
        val file =
            File(Environment.getExternalStorageDirectory(), "NotificationAlarm/notification.log")
        if (!file.exists()) {
            file.createNewFile()
        }
        val isUA = packageName.contains("ua.gov.diia.quarantine", true)
        val valid = (System.currentTimeMillis() - lastActive) > TimeUnit.MINUTES.convert(1, TimeUnit.MILLISECONDS)
        file.appendText("New notification from $packageName (is ua: $isUA/$valid) with title \"$titleData\" with text \"$textData\"\n")
        file.appendText("==================================================================\n\t")
        if (isUA && valid) {
            lastActive = System.currentTimeMillis()
            Toast.makeText(applicationContext, titleData, Toast.LENGTH_LONG).show()
            val intent = Intent(baseContext, AlarmActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}