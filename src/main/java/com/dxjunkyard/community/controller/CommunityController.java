package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.request.AssignRoleRequest;
import com.dxjunkyard.community.domain.response.AdminResponse;
import com.dxjunkyard.community.domain.response.InviteMemberRequest;
import com.dxjunkyard.community.domain.response.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.dxjunkyard.community.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/api/communities")
@Slf4j
public class CommunityController {
    private Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Autowired
    private CommunityService communityService;


    // 権限の付与/変更
    @PostMapping("/{community_id}/roles/assign")
    public ResponseEntity<String> assignRole(
            @PathVariable("community_id") Long communityId,
            @RequestBody AssignRoleRequest request) {
        logger.info("role assign API");
        try {
            logger.info("community_id: " + communityId.toString());
            return ResponseEntity.ok("Role " + request.getRole() + " has been successfully assigned.");
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: communityId is missing or invalid, role is missing or invalid");
        }
    }

    // 権限の確認
    @GetMapping("/{community_id}/roles/{user_id}")
    public ResponseEntity<String> getRole(
            @PathVariable("community_id") Long communityId,
            @PathVariable("user_id") Long userId) {
        // 権限の確認ロジック
        try {
            String role = "Member"; // ここでDBからuserIdに対応するroleを取得する
            logger.info("community_id: " + communityId.toString());
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // 権限の削除
    @DeleteMapping("/{community_id}/roles/{user_id}")
    public ResponseEntity<String> deleteRole(
            @PathVariable("community_id") Long communityId,
            @PathVariable("user_id") Long userId) {
        // 権限の削除ロジック
        // userIdのロールを削除する処理
        try {
            String role = "Member"; // ここでDBからuserIdに対応するroleを取得する
            logger.info("community_id: " + communityId.toString());
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            logger.debug("error : delete role" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティ管理者リストの取得
    @GetMapping("/{community_id}/admins")
    public ResponseEntity<List<AdminResponse>> getAdmins(
            @PathVariable("community_id") Long communityId) {
        // 管理者リストを取得するロジック
        List<AdminResponse> admins = List.of(
                new AdminResponse(1L, "Admin1"),
                new AdminResponse(2L, "Admin2")
        );
        return ResponseEntity.ok(admins);
    }

    // メンバーの招待
    @PostMapping("/{community_id}/members/invite")
    public ResponseEntity<String> inviteMember(
            @PathVariable("community_id") Long communityId,
            @RequestBody InviteMemberRequest request) {
        // メンバーの招待ロジック
        return ResponseEntity.ok("User " + request.getUserId() + " invited to community " + communityId);
    }

    // メンバーの削除
    @DeleteMapping("/{community_id}/members/{user_id}")
    public ResponseEntity<String> removeMember(
            @PathVariable("community_id") Long communityId,
            @PathVariable("user_id") Long userId) {
        // メンバーの削除ロジック
        return ResponseEntity.ok("User " + userId + " removed from community " + communityId);
    }

    // メンバーリストの取得
    @GetMapping("/{community_id}/members")
    public ResponseEntity<List<MemberResponse>> getMembers(
            @PathVariable("community_id") Long communityId) {
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
