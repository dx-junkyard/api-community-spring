package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.EventSummary;
import com.dxjunkyard.community.domain.Events;
import com.dxjunkyard.community.domain.dto.EventDto;
import com.dxjunkyard.community.domain.request.AddEventRequest;
import com.dxjunkyard.community.domain.response.EventPage;
import com.dxjunkyard.community.repository.dao.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventService {
    private Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    EventMapper eventMapper;

    public List<EventPage> getEventList() {
        logger.info("getEvent List");
        try {
            List<Events> eventList = eventMapper.getEventList();
            List<EventPage> eventPageList = eventList.stream()
                    .map(EventDto::eventPage)
                    .collect(Collectors.toList());
            return eventPageList;
        } catch (Exception e) {
            logger.info("addEvent error");
            logger.info("addEvent error info : " + e.getMessage());
            return new ArrayList<EventPage>();
        }
    }

    // 公開設定になっているイベントをキーワード検索する
    public List<EventPage> searchEventByKeyword(String keyword) {
        logger.info("keyword search : event");
        try {
            // todo: keywordをサニタイズする
            // イベント名・概要・PR文をキーワードで検索する
            String decodedKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8.name());
            List<Events> eventList = eventMapper.searchEvent(decodedKeyword);
            List<EventPage> eventPageList = eventList.stream()
                    .map(EventDto::eventPage)
                    .collect(Collectors.toList());
            return eventPageList;
        } catch (Exception e) {
            logger.info("addEvent error");
            logger.info("addEvent error info : " + e.getMessage());
            return new ArrayList<EventPage>();
        }
    }


    public EventPage getEvent(Long eventId) {
        logger.info("getEvent List");
        try {
            Events event = eventMapper.getEvent(eventId);
            return EventDto.eventPage(event);
        } catch (Exception e) {
            logger.info("addEvent error");
            logger.info("addEvent error info : " + e.getMessage());
            return null;
        }
    }

    public Events createEvent(EventPage request) {
        logger.info("createEvent");
        try {
            // イベント新規登録
            Events event = EventDto.Page2event(request);
            eventMapper.addEvent(event);
            // todo: owner_idを作成したイベントに追加
            // eventMapper.addEventMember(event.getEventId(),event.getCommunityId(),100,1,1);
            return event;
        } catch (Exception e) {
            logger.info("addEvent error");
            logger.info("addEvent error info : " + e.getMessage());
            return null;
        }
    }
}
