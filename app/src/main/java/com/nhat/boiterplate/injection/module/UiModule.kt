package com.nhat.boiterplate.injection.module

import com.nhat.boiterplate.MainActivity
import com.nhat.boiterplate.UiThread
import com.nhat.boiterplate.injection.scopes.PerActivity
import com.nhat.boiterplate.injection.scopes.PerFragment
import com.nhat.boiterplate.ui.main.MainFragment
import com.nhat.boiterplate.ui.movie.MovieDetailsFragment
import com.nhat.boiterplate.ui.movies.MoviesListFragment
import com.nhat.boiterplate.ui.shop.InfoShopFragment
import com.nhat.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module that provides all dependencies from the mobile-ui package/layer and injects all activities.
 */
@Module
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMoviesListFragment(): MoviesListFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainFragment(): MainFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeInfoShopFragment(): InfoShopFragment
}

@Module
abstract class FragmentModule {
    @Binds
    internal abstract fun bindMoviesListFragment(fragment: MoviesListFragment): MoviesListFragment

    @Binds
    internal abstract fun bindMovieDetailsFragment(fragment: MovieDetailsFragment): MovieDetailsFragment

    @Binds
    internal abstract fun bindMainFragment(fragment: MainFragment): MainFragment

    @Binds
    internal abstract fun bindInfoShopFragment(fragment: InfoShopFragment): InfoShopFragment
}