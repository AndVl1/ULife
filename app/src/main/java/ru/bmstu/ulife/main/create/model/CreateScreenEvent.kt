package ru.bmstu.ulife.main.create.model

sealed class CreateScreenEvent {
    data class UploadClicked(
        val simpleUploadModel: SimpleUploadModel
    ) : CreateScreenEvent()
}

