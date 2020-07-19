package ru.lionzxy.alarmonnotification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ncorti.slidetoact.SlideToActView
import kotlinx.android.synthetic.main.activity_alarm.*
import ru.lionzxy.alarmonnotification.alarm.AlarmKlaxon


class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        AlarmKlaxon.start(this)

        slideToCancel.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                AlarmKlaxon.stop(this@AlarmActivity)
                val launchIntent =
                    packageManager.getLaunchIntentForPackage("ua.gov.diia.quarantine")
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        }
    }
}