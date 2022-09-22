package ru.bmstu.ulife.main.maps.model

enum class SnackbarType(val text: String) {
    LOCATION_UNAVAILABLE("Location unavailable. Access location in settings"),
    NETWORK_ERROR("Loading error"),
    LOCATION_LOADING("Location loading"),
}
