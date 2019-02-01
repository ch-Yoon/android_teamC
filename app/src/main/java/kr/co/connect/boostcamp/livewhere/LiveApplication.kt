package kr.co.connect.boostcamp.livewhere

import android.app.Application
import com.bumptech.glide.Glide
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kr.co.connect.boostcamp.livewhere.di.appModules
import org.koin.android.ext.android.startKoin
import com.facebook.stetho.Stetho





class LiveApplication : Application(){

    lateinit var glide : Glide

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin(applicationContext, appModules)
        glide = Glide.get(this)
        Fabric.with(this, Crashlytics())
    }

    override fun onLowMemory() {
        super.onLowMemory()
        glide.clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        glide.trimMemory(level)
    }
}