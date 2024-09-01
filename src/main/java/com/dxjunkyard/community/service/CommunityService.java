package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.Community;
import com.dxjunkyard.community.domain.CommunitySummary;
import com.dxjunkyard.community.domain.request.EditCommunityRequest;
import com.dxjunkyard.community.domain.request.NewCommunityRequest;
import com.dxjunkyard.community.domain.response.CommunityResponse;
import com.dxjunkyard.community.repository.dao.mapper.CommunityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class CommunityService {
    private Logger logger = LoggerFactory.getLogger(CommunityService.class);

    @Autowired
    CommunityMapper communityMapper;

    public List<CommunitySummary> getCommunityList() {
        logger.info("getCommunity List");
        try {
            List<CommunitySummary> communityList = communityMapper.getCommunityList();
            return communityList;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySummary>();
        }
    }

    // 公開設定になっているコミュニティをキーワード検索する
    public List<CommunitySummary> searchCommunityByKeyword(String keyword) {
        logger.info("keyword search : community");
        try {
            // todo: keywordをサニタイズする
            List<CommunitySummary> communityList_a = communityMapper.searchCommunityName(keyword);
            List<CommunitySummary> communityList_b = communityMapper.searchCommunityDescription(keyword);
            List<CommunitySummary> communityList_c = communityMapper.searchCommunityPR(keyword);
            List<CommunitySummary> communityList = new ArrayList<>();
            communityList.addAll(communityList_a);
            communityList.addAll(communityList_b);
            communityList.addAll(communityList_c);
            Set<Long> seenIds = new LinkedHashSet<>(); // 重複なしでIDを保持
            List<CommunitySummary> uniqueList = new ArrayList<>();
            for (CommunitySummary community : communityList) {
                // 重複していないIDのみを新しいリストに追加
                if (seenIds.add(community.getId())) {
                    uniqueList.add(community);
                }
            }
            return uniqueList;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySummary>();
        }
    }

    public List<CommunitySummary> getMyCommunityList(String myId) {
        logger.info("getMyCommunity List");
        try {
            List<CommunitySummary> communityList= communityMapper.getMyCommunityList(myId);
            return communityList;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySummary>();
        }
    }

    public List<CommunitySummary> getOurCommunityList(Long communityId) {
        logger.info("getOurCommunity List");
        try {
            List<CommunitySummary> communityList= List.of(
                    new CommunitySummary(1L,"community name", 1L,"thumbnailA_image_url","thumbnailA_message", "thumbnailA_pr"),
                    new CommunitySummary(2L,"community name", 2L,"thumbnailB_image_url","thumbnailB_message", "thumbnailB_pr")
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

    public Community createCommunityInfo(NewCommunityRequest request) {
        logger.info("createCommunity");
        try {
            // コミュニティ新規登録
            Community community = Community.builder()
                    .ownerId(request.getOwnerId())
                    .placeId(request.getPlaceId())
                    .name(request.getName())
                    .thumbnailImageUrl(request.getThumbnailImageUrl())
                    .thumbnailMessage(request.getThumbnailMessage())
                    .thumbnailPr(request.getThumbnailPr())
                    .description(request.getDescription())
                    .profileImageUrl(request.getProfileImageUrl())
                    .memberCount(request.getMemberCount())
                    .visibility(request.getVisibility())
                    .build();
            communityMapper.addCommunity(community);
            // owner_idを作成したコミュニティに追加
            communityMapper.addCommunityMember(community.getId(),community.getOwnerId(),100,1,1);
            return community;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return null;
        }
    }
}
