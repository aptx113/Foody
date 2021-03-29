package com.danteyu.studio.foody.data

import com.danteyu.studio.foody.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Created by George Yu on 2021/3/29.
 */
@ActivityRetainedScoped
class Repository @Inject constructor(remoteDataSource: RemoteDataSource)
