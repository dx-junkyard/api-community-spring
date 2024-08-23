package com.dxjunkyard.community.service;

import com.dxjunkyard.community.repository.dao.mapper.CommunityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommunityService {
    private Logger logger = LoggerFactory.getLogger(CommunityService.class);

    @Autowired
    CommunityMapper communityMapper;

    public void addCommunity() {
        logger.info("addCommunity");
        try {
        } catch (Exception e) {
            logger.info("addCommunity error");
            logger.info("addCommunity error info : " + e.getMessage());
        }
    }
}
