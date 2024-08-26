package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.CommunitySummary;
import com.dxjunkyard.community.domain.request.CommunityEditRequest;
import com.dxjunkyard.community.domain.response.CommunityResponse;
import com.dxjunkyard.community.repository.dao.mapper.CommunityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommunityService {
    private Logger logger = LoggerFactory.getLogger(CommunityService.class);

    @Autowired
    CommunityMapper communityMapper;

    public List<CommunitySummary> getCommunityList() {
        logger.info("getCommunity List");
        try {
            List<CommunitySummary> communityList= List.of(
                    new CommunitySummary(1L, "ownerid A","owner_name A", "community profile"),
                    new CommunitySummary(2L, "ownerid B","owner_name B", "community profile")
            );
            return communityList;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return new ArrayList<CommunitySummary>();
        }
    }

    public List<CommunitySummary> getMyCommunityList(String myId) {
        logger.info("getMyCommunity List");
        try {
            List<CommunitySummary> communityList= List.of(
                    new CommunitySummary(1L, "ownerid A","owner_name A", "community profile"),
                    new CommunitySummary(2L, "ownerid B","owner_name B", "community profile")
            );
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
                    new CommunitySummary(1L, "ownerid A","owner_name A", "community profile"),
                    new CommunitySummary(2L, "ownerid B","owner_name B", "community profile")
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

    public CommunityResponse editCommunityInfo(CommunityEditRequest request) {
        logger.info("getCommunity List");
        try {
            return null;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return null;
        }
    }

    public CommunityResponse newCommunityInfo(CommunityEditRequest request) {
        logger.info("getCommunity List");
        try {
            return null;
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
            return null;
        }
    }
}
