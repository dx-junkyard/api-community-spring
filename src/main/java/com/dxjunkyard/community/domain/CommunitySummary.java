package com.dxjunkyard.community.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySummary {
    private Long id;
    private String name;
    private Long placeId;
    private String summaryPr;
    private String summaryImageUrl;
    private String summaryMessage;
}

