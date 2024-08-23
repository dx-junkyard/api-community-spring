package com.dxjunkyard.community.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AddCommunityRequest {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String dateTime;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "timeFrom should be in 'yyyy-MM-dd HH:mm' format")
    private String timeFrom;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "timeTo should be in 'yyyy-MM-dd HH:mm' format")
    private String timeTo;

    @Size(min = 3, max = 120, message = "OwnerId must be between 3 and 50 characters")
    private String ownerId;
    private Integer placeId;
    private String comment;
    private Integer communityType;
    private List<Integer> sportCommunityIdList;
}
