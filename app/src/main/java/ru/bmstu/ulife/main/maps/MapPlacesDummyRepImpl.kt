package ru.bmstu.ulife.main.maps

import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.main.maps.model.EventsLoadingState
import kotlin.random.Random

class MapPlacesDummyRepImpl : MapPlacesRepository{

    override fun getEvents(): EventsLoadingState {
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
        return EventsLoadingState.Loaded(points)
    }
}