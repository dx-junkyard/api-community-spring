package com.dxjunkyard.community.domain.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClosureScheduleDTO {
    // 曜日（0-6）
    private int day;

    // 日付（"YYYY-MM-DD"形式）
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date;

    // 開始時刻（"HH:mm"形式）
    @JsonFormat(pattern = "HH:mm")
    private String startTime;

    // 終了時刻（"HH:mm"形式）
    @JsonFormat(pattern = "HH:mm")
    private String endTime;

    // イベントテキスト
    private String eventText;
}
