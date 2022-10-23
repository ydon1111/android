package fastcampus.aop.part3.chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
//        initData()
    }


    private fun initViews() {
        viewPager.adapter = QuotesPagerAdapter(
            listOf(
                Quote(
                    "나는 생각한다.고로 나난 존재한다",
                    "데카르트"
                )
            )
        )
    }
}


//    private fun initData(){
//        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.setConfigSettingAsye
//
//    }
//}