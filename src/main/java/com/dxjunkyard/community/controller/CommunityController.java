package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.Community;
import com.dxjunkyard.community.domain.CommunitySummary;
import com.dxjunkyard.community.domain.request.AssignRoleRequest;
import com.dxjunkyard.community.domain.request.EditCommunityRequest;
import com.dxjunkyard.community.domain.request.NewCommunityRequest;
import com.dxjunkyard.community.domain.response.AdminResponse;
import com.dxjunkyard.community.domain.response.CommunityResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/api/communities")
@Slf4j
public class CommunityController {
    private Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private CommunityService communityService;

    // コミュニティリストの表示
    @GetMapping("/communitylist")
    public ResponseEntity<?> getCommunityList() {
        try {
            logger.info("community_list");
            List<CommunitySummary> communityList= communityService.getCommunityList();
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // 自分が所属しているコミュニティリストの表示
    @GetMapping("/mycommunitylist")
    public ResponseEntity<?> getMyCommunityList(
            @RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("my community list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            List<CommunitySummary> communityList= communityService.getMyCommunityList(myId);
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // community_idのコミュニティ（自分が所属しているものに限る）が連携しているコミュニティリストの表示
    @GetMapping("/{community_id}/ourcommunitylist")
    public ResponseEntity<?> getOurCommunityList(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
        try {
            logger.info("our community list community_id: " + communityId.toString());
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // todo: roleチェック（myIdにcommunity_id表示の権限があるか？）
            List<CommunitySummary> communityList= communityService.getOurCommunityList(communityId);
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティリストの表示
    @GetMapping("/keywordsearch")
    public ResponseEntity<?> searchCommunityByKeyword(@RequestParam String keyword) {
        try {
            logger.info("community_list");
            List<CommunitySummary> communityList= communityService.searchCommunityByKeyword(keyword);
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティ詳細の表示
    @GetMapping("/{community_id}")
    public ResponseEntity<?> getCommunity(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
        try {
            logger.info("community info");
            Community community = communityService.getCommunity(communityId);
            return ResponseEntity.ok(community);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティリストの表示
    @PostMapping("/community/{community_id}/edit")
    public ResponseEntity<?> getMyCommunityList(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId,
            @RequestBody EditCommunityRequest request) {
            try {
            logger.info("my community list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            CommunityResponse response = communityService.editCommunityInfo(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid community info");
        }
    }

    @PostMapping("/community/new")
    public ResponseEntity<?> newCommunity(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody NewCommunityRequest request) {
            try {
            logger.info("my community list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // owner_idの指定がない場合は、操作中のユーザーをオーナーに指定する
            if (request.getOwnerId() == null) {
                request.setOwnerId(myId);
            }
            Community community = communityService.createCommunityInfo(request);
            return ResponseEntity.ok(community);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid community info.");
        }
    }

    // 権限の付与/変更
    @PostMapping("/{community_id}/roles/assign")
    public ResponseEntity<String> assignRole(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId,
            @RequestBody AssignRoleRequest request) {
        logger.info("role assign API");
        try {
            logger.info("community_id: " + communityId.toString());
            // todo: roleチェック（myIdにcommunity_idにおける権限付与の権限があるか？）
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.badRequest().body("auth failed.");
            }
            return ResponseEntity.ok("Role " + request.getRole() + " has been successfully assigned.");
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: communityId is missing or invalid, role is missing or invalid");
        }
    }

    // 権限の確認
    @GetMapping("/{community_id}/roles/{user_id}")
    public ResponseEntity<String> getRole(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId,
            @PathVariable("user_id") Long userId) {
        // 権限の確認ロジック
        try {
            String role = "Member"; // ここでDBからuserIdに対応するroleを取得する
            logger.info("community_id: " + communityId.toString());
            // todo: roleチェック（myIdにuser_idの権限表示の権限があるか？）
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.badRequest().body("auth failed.");
            }
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // 権限の削除
    @DeleteMapping("/{community_id}/roles/{user_id}")
    public ResponseEntity<String> deleteRole(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId,
            @PathVariable("user_id") Long userId) {
        // 権限の削除ロジック
        // todo: roleチェック（myIdにuser_idの権限削除の権限があるか？）
        // userIdのロールを削除する処理
        try {
            String role = "Member"; // ここでDBからuserIdに対応するroleを取得する
            logger.info("community_id: " + communityId.toString());
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.badRequest().body("auth failed.");
            }
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            logger.debug("error : delete role" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティ管理者リストの取得
    @GetMapping("/{community_id}/admins")
    public ResponseEntity<?> getAdmins(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
        // 管理者リストを取得するロジック
        String myId = authService.checkAuthHeader(authHeader);
        // todo: roleチェック（myIdにコミュニティ管理者リスト表示権限があるか？）
        if (myId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
        }
        List<AdminResponse> admins = List.of(
                new AdminResponse(1L, "Admin1"),
                new AdminResponse(2L, "Admin2")
        );
        return ResponseEntity.ok(admins);
    }

    // メンバーの招待
    @PostMapping("/{community_id}/members/invite")
    public ResponseEntity<String> inviteMember(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId,
            @RequestBody InviteMemberRequest request) {
        // メンバーの招待ロジック
        String userId = authService.checkAuthHeader(authHeader);
        if (userId == null) {
            return ResponseEntity.badRequest().body("auth failed.");
        }
        return ResponseEntity.ok("succeed");
    }

    // メンバーの削除
    @DeleteMapping("/{community_id}/members/{user_id}")
    public ResponseEntity<String> removeMember(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId,
            @PathVariable("user_id") String userId) {
        // メンバーの削除ロジック
        String myId= authService.checkAuthHeader(authHeader);
        if (myId == null) {
            return ResponseEntity.badRequest().body("auth failed.");
        }
        return ResponseEntity.ok("succeed");
    }

    // メンバーリストの取得
    @GetMapping("/{community_id}/members")
    public ResponseEntity<?> getMembers(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
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
