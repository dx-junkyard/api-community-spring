package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.EventSummary;
import com.dxjunkyard.community.domain.Event;
import com.dxjunkyard.community.domain.EventSummary;
import com.dxjunkyard.community.domain.dto.EventDto;
import com.dxjunkyard.community.domain.request.AddEventRequest;
import com.dxjunkyard.community.repository.dao.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class EventService {
    private Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    EventMapper eventMapper;

    public List<EventSummary> getEventList() {
        logger.info("getEvent List");
        try {
            List<EventSummary> eventList = eventMapper.getEventList();
            return eventList;
        } catch (Exception e) {
            logger.info("addEvent error");
            logger.info("addEvent error info : " + e.getMessage());
            return new ArrayList<EventSummary>();
        }
    }

    // 公開設定になっているイベントをキーワード検索する
    public List<EventSummary> searchEventByKeyword(String keyword) {
        logger.info("keyword search : event");
        try {
            // todo: keywordをサニタイズする
            // イベント名・概要・PR文をキーワードで検索する
            List<EventSummary> eventList = eventMapper.searchEvent(keyword);
            return eventList;
        } catch (Exception e) {
            logger.info("addEvent error");
            logger.info("addEvent error info : " + e.getMessage());
            return new ArrayList<EventSummary>();
        }
    }


    public Event getEvent(Long eventId) {
        logger.info("getEvent List");
        try {
            Event response = eventMapper.getEvent(eventId);
            return response;
        } catch (Exception e) {
            logger.info("addEvent error");
            logger.info("addEvent error info : " + e.getMessage());
            return null;
        }
    }

    public Event createEvent(AddEventRequest request) {
        logger.info("createEvent");
        try {
            // イベント新規登録
            Event event = EventDto.event(request);
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
