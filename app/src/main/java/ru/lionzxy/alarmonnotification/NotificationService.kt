package ru.lionzxy.alarmonnotification

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast


public class NotificationService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val packageName = sbn?.packageName ?: return
        val extras = sbn.notification.extras ?: return
        var titleData = ""
        var textData = ""

        if (extras.getString("android.title") != null) {
            titleData = extras.getString("android.title") ?: ""
        }
        if (extras.getCharSequence("android.text") != null) {
            textData = extras.getCharSequence("android.text").toString();
        }

        Log.d("Package", packageName);
        Log.d("Title", titleData);
        Log.d("Text", textData);
        if (packageName.contains("ua.gov.diia.quarantine", true)) {
            Toast.makeText(applicationContext, titleData, Toast.LENGTH_LONG).show()
            startActivity(Intent(applicationContext, AlarmActivity::class.java))
        }
    }
}