package com.grebennikovas.viewpoint;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Date;

public class BaseEntityListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
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
