package com.grebennikovas.viewpoint;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseEntity entity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Date now = new Date();
        entity.setCreatedOn(now);
        entity.setUpdatedOn(now);
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        Date now = new Date();
        entity.setUpdatedOn(now);
    }
}
