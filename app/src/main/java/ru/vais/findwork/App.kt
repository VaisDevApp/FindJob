package ru.vais.findwork

import android.app.Application
import ru.vais.core.di.BaseComponentHolder
import ru.vais.findwork.di.AppComponent
import ru.vais.findwork.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        BaseComponentHolder.init(appComponent)
    }
}