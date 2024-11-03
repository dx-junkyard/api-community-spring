package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.Events;
import com.dxjunkyard.community.domain.EventSummary;
import com.dxjunkyard.community.domain.dto.EventDto;
import com.dxjunkyard.community.domain.request.AddEventRequest;
import com.dxjunkyard.community.domain.response.EventPage;
import com.dxjunkyard.community.domain.response.InviteMemberRequest;
import com.dxjunkyard.community.domain.response.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.dxjunkyard.community.service.*;
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

    // イベントリストの表示
    @GetMapping("/eventlist")
    public ResponseEntity<?> getEventList() {
        try {
            logger.info("event_list");
            List<EventSummary> eventList= eventService.getEventList();
            return ResponseEntity.ok(eventList);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    /*
    // 自分が登録しているイベントリストの表示
    @GetMapping("/myeventlist")
    public ResponseEntity<?> getMyEventList(
            @RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("my event list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            List<EventSummary> eventList= eventService.getMyEventList(myId);
            return ResponseEntity.ok(eventList);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // 自分が所属するコミュニティ(community_id)が主催・共同開催するイベントリストの表示
    @GetMapping("/{community_id}/oureventlist")
    public ResponseEntity<?> getOurEventList(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
        try {
            logger.info("our event list event_id: " + eventId.toString());
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // todo: roleチェック（myIdにevent_id表示の権限があるか？）
            List<EventSummary> eventList= eventService.getOurEventList(communityId);
            return ResponseEntity.ok(eventList);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }
     */

    // イベントリストの表示
    @GetMapping("/keywordsearch")
    public ResponseEntity<?> searchEventByKeyword(@RequestParam String keyword) {
        try {
            logger.info("event_list");
            List<EventSummary> eventList= eventService.searchEventByKeyword(keyword);
            return ResponseEntity.ok(eventList);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // イベント詳細の表示
    @GetMapping("/event/{event_id}")
    public ResponseEntity<?> getEvent(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("event_id") Long eventId) {
        try {
            logger.info("my event list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            Events response = eventService.getEvent(eventId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // イベントリストの表示
    /*
    @PostMapping("/event/{event_id}/edit")
    public ResponseEntity<?> getMyEventList(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("event_id") Long eventId,
            @RequestBody EditEventRequest request) {
            try {
            logger.info("my event list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            EventResponse response = eventService.editEventInfo(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.debug("event" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid event info");
        }
    }

    // イベント管理者リストの取得
    @GetMapping("/{event_id}/admins")
    public ResponseEntity<?> getAdmins(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("event_id") Long eventId) {
        // 管理者リストを取得するロジック
        String myId = authService.checkAuthHeader(authHeader);
        // todo: roleチェック（myIdにイベント管理者リスト表示権限があるか？）
        if (myId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
        }
        List<AdminResponse> admins = List.of(
                new AdminResponse(1L, "Admin1"),
                new AdminResponse(2L, "Admin2")
        );
        return ResponseEntity.ok(admins);
    }
     */

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
            // todo : コミュニティに対するイベント作成権限の有無を確認
            // owner_idの指定がない場合は、操作中のユーザーをオーナーに指定する
            if (request.getOwnerId() == null) {
                request.setOwnerId(myId);
            }
            Events event = eventService.createEvent(request);
            Events newEvent = eventService.getEvent(event.getId());
            EventPage eventPage = EventDto.eventPage(newEvent);
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
