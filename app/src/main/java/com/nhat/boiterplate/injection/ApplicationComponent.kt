package com.nhat.boiterplate.injection

import android.app.Application
import com.nhat.boiterplate.TikiExerciseApplication
import com.nhat.boiterplate.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        DataModule::class,
        CacheModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        RemoteModule::class,
        UiModule::class
    )
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: TikiExerciseApplication)

}
