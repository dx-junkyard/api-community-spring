package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.Community;
import com.dxjunkyard.community.domain.CommunitySummary;
import com.dxjunkyard.community.domain.request.AssignRoleRequest;
import com.dxjunkyard.community.domain.request.CommunityNetworking;
import com.dxjunkyard.community.domain.request.EditCommunityRequest;
import com.dxjunkyard.community.domain.request.FavoriteRequest;
import com.dxjunkyard.community.domain.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.dxjunkyard.community.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/api/communities")
@Slf4j
public class CommunityController {
    private Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private AuthService authService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommunityMemberService communityMemberService;


    // コミュニティリストの表示
    @GetMapping("/communitylist")
    public ResponseEntity<?> getCommunityList(
            @RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("community_list");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            List<CommunitySummary> communityList= communityService.getCommunityList(myId);
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }


    // 自分が所属しているコミュニティリストの表示
    @GetMapping("/myhome")
    public ResponseEntity<?> getMyPageInfo(
            @RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("my home page");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            MyPage mypage = communityService.getMyPage(myId);
            return ResponseEntity.ok(mypage);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティリストの表示
    @GetMapping("/keyword-search")
    public ResponseEntity<?> searchCommunityByKeyword(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            logger.info("community_list");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            List<CommunitySummary> communityList= communityService.searchCommunityByKeyword(myId, keyword);
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティ詳細の表示
    @GetMapping("/{community_id}/page")
    public ResponseEntity<?> getCommunityPage(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
        try {
            logger.info("community info");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // todo : check role
            CommunityPage communityPage = communityService.getCommunityPage(communityId);
            return ResponseEntity.ok(communityPage);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティ登録情報の取得
    @GetMapping("/{community_id}/registration")
    public ResponseEntity<?> getCommunityRegistration(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
        try {
            logger.info("community info");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // todo : check role
            Community community = communityService.getCommunity(communityId);
            return ResponseEntity.ok(community);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: userId is missing or invalid, role is missing or invalid");
        }
    }

    // コミュニティ名の取得
    @GetMapping("/{community_id}/community-name")
    public ResponseEntity<?> getCommunityName(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("community_id") Long communityId) {
        try {
            logger.info("get community name");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // todo check role
            String communityName = communityService.getCommunityName(communityId);
            return ResponseEntity.ok(communityName);
        } catch (Exception e) {
            logger.debug("get communityName : " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid fields: invalid communityId or invalid token.");
        }
    }

    @PostMapping("/favorite")
    public ResponseEntity<Integer> toggleFavorite(@RequestBody FavoriteRequest favoriteRequest,
                                                  @RequestHeader("Authorization") String authHeader) {

        // ここでデータベースの更新処理を行う（お気に入り状態の設定/解除）
        String myId = authService.checkAuthHeader(authHeader);
        if (myId == null) {
            return ResponseEntity.ok(0);
        }
        Integer updateSuccess = communityMemberService.updateFavoriteStatus(myId, 0, favoriteRequest);
        return ResponseEntity.ok(updateSuccess);
    }

    /*
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
     */

    @PostMapping("/community/edit")
    public ResponseEntity<?> postEditCommunity(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Community request) {
        try {
            logger.info("my community list ");
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            Community response = communityService.editCommunity(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.debug("community" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid community info");
        }
    }

    @PostMapping("/community/new")
    public ResponseEntity<?> newCommunity(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Community request) {
        logger.info("create new community");
        try {
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

    @PostMapping("/community/create-group")
    public ResponseEntity<?> createGroup(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CommunityNetworking request) {
        logger.info("create new community");
        try {
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // todo : 権限チェックを実装する
            Long parentCommunityId = communityService.createGroup(myId, request);
            return ResponseEntity.ok(parentCommunityId);
        } catch (Exception e) {
            logger.debug("new community group" + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid community info.");
        }
    }

    @PostMapping("/community/update")
    public ResponseEntity<?> updateCommunity(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Community request) {
        logger.info("create new community");
        try {
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // owner_idの指定がない場合は、操作中のユーザーをオーナーに指定する
            if (request.getOwnerId() == null) {
                request.setOwnerId(myId);
            }
            Community community = communityService.updateCommunityInfo(request);
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

    @PostMapping("/community/upload-image")
    public ResponseEntity<?> uploadPhoto(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("photo") MultipartFile photo
    ) {
        String myId= authService.checkAuthHeader(authHeader);
        if (myId == null) {
            return ResponseEntity.badRequest().body("auth failed.");
        }
        try {
            String photoPath = communityService.savePhoto(myId, photo);
            return ResponseEntity.ok(photoPath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ファイルの保存に失敗しました");
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                // ファイルのMIMEタイプを推定する
                String contentType = null;

                // 画像の拡張子に応じてContent-Typeを手動で設定
                if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                    contentType = MediaType.IMAGE_JPEG_VALUE;
                } else if (filename.endsWith(".png")) {
                    contentType = MediaType.IMAGE_PNG_VALUE;
                } else if (filename.endsWith(".gif")) {
                    contentType = MediaType.IMAGE_GIF_VALUE;
                } else {
                    // それ以外の場合はデフォルトでバイナリファイルと見なす
                    contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity checkin(){
        logger.info("疎通確認 URL");
        return ResponseEntity.ok("OK");
    }

}
