/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.tivi.common.imageloading

import app.tivi.data.entities.ImageType
import app.tivi.data.entities.TmdbImageEntity
import app.tivi.tmdb.TmdbImageUrlProvider
import coil.map.MeasuredMapper
import coil.size.PixelSize
import coil.size.Size
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject
import javax.inject.Provider

class TmdbImageEntityCoilMapper @Inject constructor(
    private val tmdbImageUrlProvider: Provider<TmdbImageUrlProvider>
) : MeasuredMapper<TmdbImageEntity, HttpUrl> {

    override fun handles(data: TmdbImageEntity): Boolean = true

    override fun map(data: TmdbImageEntity, size: Size): HttpUrl {
        val width = if (size is PixelSize) size.width else 0

        return when (data.type) {
            ImageType.BACKDROP -> tmdbImageUrlProvider.get()
                    .getBackdropUrl(data.path, width).toHttpUrl()
            ImageType.POSTER -> tmdbImageUrlProvider.get()
                    .getPosterUrl(data.path, width).toHttpUrl()
        }
    }
}