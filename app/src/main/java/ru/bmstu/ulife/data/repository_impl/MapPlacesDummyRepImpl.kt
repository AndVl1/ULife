package ru.bmstu.ulife.data.repository_impl

import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.data.repository.MapPlacesRepository
import ru.bmstu.ulife.data.states.LoadingState
import kotlin.random.Random

class MapPlacesDummyRepImpl : MapPlacesRepository {

    override fun getEvents(): LoadingState {
        val points = arrayListOf<EventModel>()
        for (i in 0..100) {
            val event = EventModel(
                eventId = i,
                authorId = 0,
                title = "test $i",
                categoryTitle = "",
                eventAvatar = "",
                address = "",
                description = "",
                timeStart = "",
                timeEnd = "",
                latitude = Random.nextDouble(-90.0, 90.0).toFloat(),
                longitude = Random.nextDouble(-180.0, 180.0).toFloat(),
            )
            points.add(event)
        }
        return LoadingState.Loaded(points)
    }
}