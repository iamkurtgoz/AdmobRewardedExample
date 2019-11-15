package com.iamkurtgoz.examplerewardads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var credit: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)
    }

    fun rewardAd(view: View){
        var rewardedAd: RewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917") //Reklamı oluşturuyoruz
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() { //Reklam yüklenince çalışıyor
                Toast.makeText(this@MainActivity, "onRewardedAdLoaded", Toast.LENGTH_SHORT).show()
                showAd(rewardedAd) //Reklamı başlatıyoruz
            }

            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                //Reklam yükleme sırasında hata alınca çalışıyor
                Toast.makeText(this@MainActivity, "onRewardedAdFailedToLoad", Toast.LENGTH_SHORT).show()
            }
        }
        val adRequest: AdRequest
        if (BuildConfig.DEBUG) {//Eğer proje debug modunda ise reklamları test olarak başlatacağız.
            adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        } else {//Eğer release modunda ise normal başlatıyoruz.
            adRequest = AdRequest.Builder().build()
        }
        rewardedAd.loadAd(adRequest, adLoadCallback) //Reklam yüklemeyi başlatıyoruz
    }

    private fun showAd(rewardedAd: RewardedAd){
        val adCallback = object : RewardedAdCallback() {
            override fun onUserEarnedReward(p0: com.google.android.gms.ads.rewarded.RewardItem) {
                //Kredi alındı
                Toast.makeText(this@MainActivity, "onUserEarnedReward", Toast.LENGTH_SHORT).show()
                credit++
                textCredit.text = "Credit: $credit"
            }

            override fun onRewardedAdOpened() { //Reklam açıldı
                Toast.makeText(this@MainActivity, "onRewardedAdOpened", Toast.LENGTH_SHORT).show()
            }

            override fun onRewardedAdClosed() { //Reklam kapandı
                Toast.makeText(this@MainActivity, "onRewardedAdClosed", Toast.LENGTH_SHORT).show()
            }

            override fun onRewardedAdFailedToShow(errorCode: Int) { //Reklam açılırken hata aldı
                Toast.makeText(this@MainActivity, "onRewardedAdFailedToShow", Toast.LENGTH_SHORT).show()
            }
        }
        rewardedAd.show(this@MainActivity, adCallback) //reklamı başlat
    }
}
