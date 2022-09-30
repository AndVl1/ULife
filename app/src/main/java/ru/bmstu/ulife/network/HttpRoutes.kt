package ru.bmstu.ulife.network

import ru.bmstu.ulife.BuildConfig

object HttpRoutes {
    const val BASE_ULIFE_URL = BuildConfig.API_URL
    const val IMAGES_API_URL = "https://api.imgbb.com/1/upload"
    const val LATLNG_TO_POS_API_URL = "http://api.positionstack.com/v1/reverse"
}
