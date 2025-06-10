package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.Events;
import com.dxjunkyard.community.domain.response.EventPage;
import com.dxjunkyard.community.domain.response.InviteMemberRequest;
import com.dxjunkyard.community.domain.response.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.dxjunkyard.community.service.*;
import com.dxjunkyard.community.domain.PermissionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/api/events")
@Slf4j
public class EventController {
    private Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AccessControlService accessControlService;

    // イベントリストの表示
    @GetMapping("/eventlist")
    public ResponseEntity<?> getEventList() {
        try {
            logger.info("event_list");
            List<EventPage> eventList= eventService.getEventList();
            return ResponseEntity.ok(eventList);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }


    // コミュニティリストの表示
    @GetMapping("/keyword-search")
    public ResponseEntity<?> searchCommunityByKeyword(@RequestParam(value = "keyword", required = false) String keyword) {
        try {
            logger.info("community_list");
            List<EventPage> eventList= eventService.searchEventByKeyword(keyword);
            return ResponseEntity.ok(eventList);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }
    // イベントリストの表示
    @GetMapping("/keywordsearch")
    public ResponseEntity<?> searchEventByKeyword(@RequestParam String keyword) {
        try {
            logger.info("event_list");
            List<EventPage> eventList= eventService.searchEventByKeyword(keyword);
            return ResponseEntity.ok(eventList);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // イベント詳細の表示
    @GetMapping("/event/{event_id}/display")
    public ResponseEntity<?> getEvent(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("event_id") Long eventId) {
        try {
            logger.info("my event list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            EventPage response = eventService.getEvent(eventId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    @PostMapping("/event/new")
    public ResponseEntity<?> newEvent(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EventPage request) {
            try {
            logger.info("my event list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            if (!accessControlService.hasPermission(myId, request.getCommunityId(), PermissionType.PLAN_EVENT)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
            }
            // owner_idの指定がない場合は、操作中のユーザーをオーナーに指定する
            if (request.getOwnerId() == null) {
                request.setOwnerId(myId);
            }
            Events event = eventService.createEvent(request);
            EventPage eventPage = eventService.getEvent(event.getId());
            return ResponseEntity.ok(eventPage);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid event info.");
        }
    }


    // メンバーの招待
    @PostMapping("/{event_id}/members/invite")
    public ResponseEntity<String> inviteMember(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("event_id") Long eventId,
            @RequestBody InviteMemberRequest request) {
        // メンバーの招待ロジック
        String userId = authService.checkAuthHeader(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().body("auth failed.");
        }
        return ResponseEntity.ok("succeed");
    }

    // メンバーの削除
    @DeleteMapping("/{event_id}/members/{user_id}")
    public ResponseEntity<String> removeMember(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("event_id") Long eventId,
            @PathVariable("user_id") String userId) {
        // メンバーの削除ロジック
        String myId= authService.checkAuthHeader(authHeader);
        if (myId == null) {
            return ResponseEntity.badRequest().body("auth failed.");
        }
        return ResponseEntity.ok("succeed");
    }

    // メンバーリストの取得
    @GetMapping("/{event_id}/members")
    public ResponseEntity<?> getMembers(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("event_id") Long eventId) {
        String myId= authService.checkAuthHeader(authHeader);
        if (myId == null) {
            return ResponseEntity.badRequest().body("auth failed.");
        }
        // メンバーリストを取得するロジック
        List<MemberResponse> members = List.of(
                new MemberResponse(1L, "Member1", "Member"),
                new MemberResponse(2L, "Member2", "Admin")
        );
        return ResponseEntity.ok(members);
    }

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity checkin(){
        logger.info("疎通確認 URL");
        return ResponseEntity.ok("OK");
    }

}
