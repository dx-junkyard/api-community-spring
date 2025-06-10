package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.Events;
import com.dxjunkyard.community.domain.response.EventPage;
import com.dxjunkyard.community.repository.dao.mapper.EventMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    @Test
    void searchEventByKeyword_NullKeyword_ReturnsAllEvents() {
        Events event = new Events();
        when(eventMapper.getEventList()).thenReturn(List.of(event));

        List<EventPage> result = eventService.searchEventByKeyword(null);

        verify(eventMapper, times(1)).getEventList();
        verify(eventMapper, never()).searchEvent(any());
        assertEquals(1, result.size());
    }

    @Test
    void searchEventByKeyword_ValidKeyword_DelegatesToMapper() {
        Events event = new Events();
        when(eventMapper.searchEvent("test")).thenReturn(List.of(event));

        List<EventPage> result = eventService.searchEventByKeyword("test");

        verify(eventMapper).searchEvent("test");
        assertEquals(1, result.size());
    }
}
