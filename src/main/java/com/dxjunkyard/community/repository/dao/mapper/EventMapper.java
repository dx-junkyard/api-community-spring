package com.dxjunkyard.community.repository.dao.mapper;


import com.dxjunkyard.community.domain.EventSummary;
import org.apache.ibatis.annotations.Mapper;
import com.dxjunkyard.community.domain.Events;

import java.util.List;

@Mapper
public interface EventMapper {
    void addEvent(Events event);
    Events getEvent(Long eventId);
    List<EventSummary> getEventList();
    List<EventSummary> searchEvent(String keyword);
//    List<Event> getEventList(String keyword);
}
