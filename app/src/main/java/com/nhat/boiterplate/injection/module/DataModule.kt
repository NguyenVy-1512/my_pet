package com.nhat.boiterplate.injection.module

import com.nhat.data.MovieDataRepository
import com.nhat.data.executor.JobExecutor
import com.nhat.domain.executor.ThreadExecutor
import com.nhat.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindMovieRepository(movieDataRepository: MovieDataRepository): MovieRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}