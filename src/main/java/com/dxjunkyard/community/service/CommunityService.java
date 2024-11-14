package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.*;
import com.dxjunkyard.community.domain.request.EditCommunityRequest;
import com.dxjunkyard.community.domain.request.NewCommunityRequest;
import com.dxjunkyard.community.domain.response.CommunityPage;
import com.dxjunkyard.community.domain.response.CommunityResponse;
import com.dxjunkyard.community.domain.response.MyPage;
import com.dxjunkyard.community.repository.dao.mapper.CommunityMapper;
import com.dxjunkyard.community.repository.dao.mapper.CommunityMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static com.google.common.io.Files.getFileExtension;


@Service
public class CommunityService {
    private Logger logger = LoggerFactory.getLogger(CommunityService.class);

    @Value("${file.upload-dir}")
    private String upload_dir;

    @Value("${file.image-dir}")
    private String image_dir;

    @Autowired
    CommunityMapper communityMapper;

    @Autowired
    private CommunityMemberMapper communityMemberMapper;

    public List<CommunitySummary> getCommunityList(String myId) {
        logger.info("getCommunity List");
        try {
            List<CommunitySummary> communityList = communityMapper.getCommunityList(myId);
            return communityList;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySummary>();
        }
    }

    // 1コミュニティの詳細情報を取得する
    public Community getCommunity(Long communityId) {
        logger.info("getCommunity List");
        try {
            Community community = communityMapper.getCommunity(communityId);
            return community;
        } catch (Exception e) {
            logger.info("getCommunity error");
            logger.info("getCommunity error info : " + e.getMessage());
            return null;
        }
    }

    // 公開設定になっているコミュニティをキーワード検索する
    public List<CommunitySummary> searchCommunityByKeyword(String myId, String keyword) {
        logger.info("keyword search : community");
        try {
            // todo: keywordをサニタイズする
            // コミュニティ名・概要・PR文をキーワードで検索する
            String decodedKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8.name());
            List<CommunitySummary> communityList = communityMapper.searchCommunity(myId, decodedKeyword);
            return communityList;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySummary>();
        }
    }

    public List<CommunitySelector> getMyCommunitySelector(String myId) {
        logger.info("getMyCommunity List");
        try {
            return communityMapper.getMyCommunitySelector(myId);
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySelector>();
        }
    }

    public List<CommunitySummary> getOurCommunityList(Long communityId) {
        logger.info("getOurCommunity List");
        try {
            List<CommunitySummary> communityList= List.of(
                    new CommunitySummary(1L,"community name", 1L,"summaryA_image_url","summaryA_message", "summaryA_pr",1),
                    new CommunitySummary(2L,"community name", 2L,"summaryB_image_url","summaryB_message", "summaryB_pr",1)
            );
            return communityList;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySummary>();
        }
    }

    public CommunityResponse getCommunityInfo(Long communityId) {
        logger.info("getCommunity List");
        try {
            CommunityResponse response = new CommunityResponse(1L, "community name", "email", "profile xxxxxx", "system message");
            return response;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return null;
        }
    }

    public CommunityResponse editCommunityInfo(EditCommunityRequest request) {
        logger.info("getCommunity List");
        try {
            return null;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return null;
        }
    }

    public Community createCommunityInfo(Community request) {
        logger.info("createCommunity");
        try {
            // コミュニティ新規登録
            Community community = Community.builder()
                    .ownerId(request.getOwnerId())
                    .placeId(request.getPlaceId())
                    .name(request.getName())
                    .summaryImageUrl(request.getSummaryImageUrl())
                    .summaryMessage(request.getSummaryMessage())
                    .summaryPr(request.getSummaryPr())
                    .description(request.getDescription())
                    .profileImageUrl(request.getProfileImageUrl())
                    .memberCount(request.getMemberCount())
                    .visibility(request.getVisibility())
                    .build();
            communityMapper.addCommunity(community);
            communityMemberMapper.addCommunityMember(community.getId(),community.getOwnerId(),100,1,1);
            // String imagePath = image_dir + renamePhoto(community.getOwnerId(), community.getId());
            // communityMapper.updatePhotoPath(community.getId(), imagePath);
            return community;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return null;
        }
    }

    public Community updateCommunityInfo(Community request) {
        logger.info("createCommunity");
        try {
            // コミュニティ新規登録
            communityMapper.updateCommunity(request);
            return request;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return null;
        }
    }

    private Optional<File> findFileWithId(String myId) {
        File directory = new File(upload_dir);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.startsWith(myId + "."));
            if (files != null && files.length > 0) {
                return Optional.of(files[0]);
            }
        }
        return Optional.empty();
    }

    public String renamePhoto(String myId, Long communityId) {
        try {
            // ディレクトリ内で myId に一致するファイルを探す
            Optional<File> originalFileOptional = findFileWithId(myId);

            if (originalFileOptional.isPresent()) {
                File originalFile = originalFileOptional.get();
                String fileExt = getFileExtension(originalFile.getName());

                String newFileName = communityId + "." + fileExt;
                String newFilePath = upload_dir + "/" + newFileName;
                File renamedFile = new File(newFilePath);

                if (originalFile.renameTo(renamedFile)) {
                    System.out.println("File renamed successfully to " + renamedFile.getName());
                    return newFileName;
                } else {
                    System.out.println("Failed to rename file");
                }
            } else {
                System.out.println("No file found with id: " + myId);
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String savePhoto(String myId, MultipartFile photo) {
        try {
            // ファイルを保存する
            String fileName = photo.getOriginalFilename();
            String fileExt = ""; // ファイルの拡張子
            if (fileName != null && fileName.contains(".")) {
                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            } else {
                return ""; // 拡張子がない場合
            }
            String saveFileName =  myId + "." + fileExt;
            String savePath = upload_dir + saveFileName;
            File saveFile = new File(savePath);
            photo.transferTo(saveFile);
            return saveFileName;
        } catch (IOException e) {
            logger.info("savePhoto error");
            return "";
        }
    }

    private List<CommunitySelector> mockCommunitySelectorList() {
        CommunitySelector selector_a = CommunitySelector.builder()
                .communityId(1L)
                .communityName("西東京ブローガンマスターズ")
                .build();
        CommunitySelector selector_b = CommunitySelector.builder()
                .communityId(2L)
                .communityName("ヨガ教室")
                .build();
        CommunitySelector selector_c = CommunitySelector.builder()
                .communityId(3L)
                .communityName("西東京市サッカークラブ")
                .build();
        List<CommunitySelector> communitySelectorList = new ArrayList<>();
        communitySelectorList.add(selector_a);
        communitySelectorList.add(selector_b);
        communitySelectorList.add(selector_c);
        return communitySelectorList;
    }

    private List<UpcommingEvent> mockUpcommingEventList() {
        UpcommingEvent event_a = UpcommingEvent.builder()
                .eventId(1L)
                .dateTime(LocalDateTime.of(2024, 11, 1, 15, 0))
                .eventName("吹き矢大会")
                .communityName("忍び")
                .location("某道場")
                .invitationCode("1000")
                .build();
        UpcommingEvent event_b = UpcommingEvent.builder()
                .eventId(2L)
                .dateTime(LocalDateTime.of(2024, 11, 8, 15, 1))
                .eventName("テニス大会")
                .communityName("ファイヤーボール")
                .location("テニスコートA")
                .invitationCode("1001")
                .build();
        List<UpcommingEvent> upcommingEventList = new ArrayList<>();
        upcommingEventList.add(event_a);
        upcommingEventList.add(event_b);
        return upcommingEventList;
    }

    private List<EventInvitation> mockEventInvitationList() {
        EventInvitation event_a = EventInvitation.builder()
                .dateTime(LocalDateTime.of(2024, 11, 15, 15, 2))
                .eventName("吹き矢大会")
                .communityName("忍び")
                .location("@某道場")
                .invitationCode("1000")
                .build();
        EventInvitation event_b = EventInvitation.builder()
                .dateTime(LocalDateTime.of(2024, 11, 8, 15, 3))
                .eventName("テニス")
                .communityName("ファイヤーボール")
                .location("@テニスコートA")
                .invitationCode("2000")
                .build();
        List<EventInvitation> eventInvitationList = new ArrayList<>();
        eventInvitationList.add(event_a);
        eventInvitationList.add(event_b);
        return eventInvitationList;
    }

    private List<FavoriteCommunity> mockFavoriteCommunityList() {
        FavoriteCommunity community_a = FavoriteCommunity.builder()
                .communityName("忍び")
                .communityId(1L)
                .build();
        FavoriteCommunity community_b = FavoriteCommunity.builder()
                .communityName("ファイヤーボール")
                .communityId(2L)
                .build();
        List<FavoriteCommunity> favoriteCommunityList = new ArrayList<>();
        favoriteCommunityList.add(community_a);
        favoriteCommunityList.add(community_b);
        return favoriteCommunityList;
    }


    public MyPage getMyPage(String myId) {
        MyPage myPage = MyPage.builder()
                .communitySelectorList(getMyCommunitySelector(myId))
                .upcommingEventList(mockUpcommingEventList())
                .eventInvitationList(mockEventInvitationList())
                .favoriteCommunityList(mockFavoriteCommunityList())
                .build();
        return myPage;
    }

    private List<EventSchedule> getMockScheduleList() {
        EventSchedule event_a = EventSchedule.builder()
                .eventId(2L)
                .dateTime(LocalDateTime.of(2024, 11, 15, 15, 0))
                .eventName("テニス大会A")
                .location("テニスコート")
                .favN(2L)
                .build();
        EventSchedule event_b = EventSchedule.builder()
                .eventId(2L)
                .dateTime(LocalDateTime.of(2024, 11, 22, 15, 0))
                .eventName("テニス大会B")
                .location("テニスコートA")
                .favN(2L)
                .build();
        EventSchedule event_c = EventSchedule.builder()
                .eventId(2L)
                .dateTime(LocalDateTime.of(2024, 11, 23, 15, 0))
                .eventName("テニス大会C")
                .location("テニスコートA")
                .favN(2L)
                .build();
        List<EventSchedule> eventScheduleList = new ArrayList<>();
        eventScheduleList.add(event_a);
        eventScheduleList.add(event_b);
        eventScheduleList.add(event_c);
        return eventScheduleList;
    }

    private List<ActivityHistory> getMockActivityHistoryList() {
        ActivityHistory history_a = ActivityHistory.builder()
                .eventId(2L)
                .dateTime(LocalDateTime.of(2024, 11, 15, 15, 0))
                .eventName("大会A (test data)")
                .location("location A")
                .build();
        ActivityHistory history_b = ActivityHistory.builder()
                .eventId(2L)
                .dateTime(LocalDateTime.of(2024, 11, 22, 15, 0))
                .eventName("大会B (test data)")
                .location("location B")
                .build();
        ActivityHistory history_c = ActivityHistory.builder()
                .eventId(2L)
                .dateTime(LocalDateTime.of(2024, 11, 23, 15, 0))
                .eventName("大会C (test data)")
                .location("location C")
                .build();
        List<ActivityHistory> historyScheduleList = new ArrayList<>();
        historyScheduleList.add(history_a);
        historyScheduleList.add(history_b);
        historyScheduleList.add(history_c);
        return historyScheduleList;
    }
    private String getLocationInfo(Long placeId) {
        return  "（位置情報なし）";
    }

    public CommunityPage getCommunityPage(Long communityId) {
        logger.info("getCommunityPage");
        try {
            Community com = getCommunity(communityId);
            CommunityPage response = CommunityPage.builder()
                    .id(com.getId())
                    .description(com.getDescription())
                    .name(com.getName())
                    .location(getLocationInfo(com.getPlaceId()))
                    .summaryImageUrl(com.getSummaryImageUrl())
                    .visibility(com.getVisibility())
                    .activityHistoryList(getMockActivityHistoryList())
                    .eventScheduleList(getMockScheduleList())
                    .build();
            return response;
        } catch (Exception e) {
            logger.info("getCommunity error");
            logger.info("getCommunity error info : " + e.getMessage());
            return null;
        }
    }
}
