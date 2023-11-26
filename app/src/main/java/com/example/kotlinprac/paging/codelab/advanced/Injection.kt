package com.example.kotlinprac.paging.codelab.advanced

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.kotlinprac.paging.codelab.advanced.api.GithubService
import com.example.kotlinprac.paging.codelab.advanced.data.GithubRepository
import com.example.kotlinprac.paging.codelab.advanced.db.RepoDatabase

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), RepoDatabase.getInstance(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
//    fun provideViewModelFactory(context: Context, owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
//        return ViewModelFactory(owner, provideGithubRepository(context))
//    }
}