package be.appwise.sample_compose.data.mock

import be.appwise.sample_compose.data.entity.Event
import be.appwise.sample_compose.data.entity.Type
import java.time.LocalDate

val Event.Companion.MOCK_EVENTS: List<Event>
    get() = listOf(
        Event(
            LocalDate.now(),
            Type.MOCK_TYPES[0]
        ),
        Event(
            LocalDate.now().minusDays(2),
            Type.MOCK_TYPES[0]
        ),
        Event(
            LocalDate.now().minusDays(2),
            Type.MOCK_TYPES[1]
        ),
        Event(
            LocalDate.now().plusDays(5),
            Type.MOCK_TYPES[2]
        ),
        Event(
            LocalDate.now().plusDays(9),
            Type.MOCK_TYPES[1]
        ),
        Event(
            LocalDate.now().plusDays(5),
            Type.MOCK_TYPES[0]
        )
    )