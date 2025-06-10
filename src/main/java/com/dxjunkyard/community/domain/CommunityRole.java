package com.dxjunkyard.community.domain;

public enum CommunityRole {
    VIEWER(1),
    PARTICIPANT(2),
    PLANNER(3),
    RECRUITER(4),
    ADMIN(5);

    private final int id;

    CommunityRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CommunityRole fromId(Integer id) {
        if (id == null) {
            return VIEWER;
        }
        for (CommunityRole r : values()) {
            if (r.getId() == id) {
                return r;
            }
        }
        return VIEWER;
    }
}
