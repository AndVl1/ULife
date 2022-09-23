package ru.bmstu.ulife.main.create.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class ImageLoadingResponse(
    @SerialName("data")
    val `data`: Data,
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean
)

@Serializable
data class Data(
    @SerialName("delete_url")
    val deleteUrl: String,
    @SerialName("display_url")
    val displayUrl: String,
    @SerialName("expiration")
    val expiration: String,
    @SerialName("height")
    val height: String,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: Image? = null,
    @SerialName("medium")
    val medium: Medium? = null,
    @SerialName("size")
    val size: String,
    @SerialName("thumb")
    val thumb: Thumb? = null,
    @SerialName("time")
    val time: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("url_viewer")
    val urlViewer: String,
    @SerialName("width")
    val width: String
)

@Serializable
data class Image(
    @SerialName("extension")
    val extension: String,
    @SerialName("filename")
    val filename: String,
    @SerialName("mime")
    val mime: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)

@Serializable
data class Medium(
    @SerialName("extension")
    val extension: String,
    @SerialName("filename")
    val filename: String,
    @SerialName("mime")
    val mime: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)

@Serializable
data class Thumb(
    @SerialName("extension")
    val extension: String,
    @SerialName("filename")
    val filename: String,
    @SerialName("mime")
    val mime: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)