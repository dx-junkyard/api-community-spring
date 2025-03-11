package com.dxjunkyard.community.repository.dao.mapper;


import com.dxjunkyard.community.domain.EventSummary;
import com.dxjunkyard.community.domain.UpcommingEvent;
import com.dxjunkyard.community.domain.response.EventPage;
import org.apache.ibatis.annotations.Mapper;
import com.dxjunkyard.community.domain.Events;

import java.util.List;

@Mapper
public interface EventMapper {
    void addEvent(Events event);
    Events getEvent(Long eventId);
    List<Events> getEventList();
    List<Events> searchEvent(String keyword);
    List<UpcommingEvent> getUpCommingEventList(List<Long> communityIdList);
//    List<Event> getEventList(String keyword);
}
