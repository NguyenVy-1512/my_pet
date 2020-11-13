package com.nhat.boiterplate.injection.module

import com.nhat.boiterplate.BuildConfig
import com.nhat.data.repository.IMovieRemote
import com.nhat.remote.MovieRemoteImpl
import com.nhat.remote.MovieService
import com.nhat.remote.MovieServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Module that provides all dependencies from the repository package/layer.
 */
@Module
abstract class RemoteModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideMovieService(): MovieService {
            return MovieServiceFactory.makeService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindMovieRemote(iMovieRemote: MovieRemoteImpl): IMovieRemote
}