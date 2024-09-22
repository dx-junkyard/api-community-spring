package com.dxjunkyard.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventSummary {
    private Long id; // イベントID
    private String name; // イベント名
    private Long placeId; // 場所ID
    private String pr;
    private String url;
    private String message;
}
