package sh.roadmap.ecommerce.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A handler for automatically populating auditing fields during insert and update operations.
 * This class implements the MetaObjectHandler interface provided by MyBatis-Plus.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Slf4j: Lombok annotation to enable logging.</li>
 *   <li>@Component: Marks this class as a Spring-managed component.</li>
 * </ul>
 *
 * <p>Implements:</p>
 * <ul>
 *   <li>MetaObjectHandler: Provides methods for handling automatic field filling.</li>
 * </ul>
 */
@Slf4j
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {

    /**
     * Automatically fills auditing fields during insert operations.
     *
     * @param metaObject The meta-object representing the entity being inserted.
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill start");
        LocalDateTime now = LocalDateTime.now();
        strictInsertFill(metaObject, "created_at", LocalDateTime.class, now);
        strictInsertFill(metaObject, "updated_at", LocalDateTime.class, now);
        strictInsertFill(metaObject, "created_by", String.class, getCurrentUsername());
        strictInsertFill(metaObject, "updated_by", String.class, getCurrentUsername());
        log.info("end insert fill");
    }

    /**
     * Automatically updates auditing fields during update operations.
     *
     * @param metaObject The meta-object representing the entity being updated.
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill start");
        LocalDateTime now = LocalDateTime.now();
        strictUpdateFill(metaObject, "updated_at", LocalDateTime.class, now);
        strictUpdateFill(metaObject, "updated_by", String.class, getCurrentUsername());
        log.info("end update fill");
    }

    /**
     * Retrieves the username of the currently authenticated user.
     * If no user is authenticated, returns "system".
     *
     * @return The username of the current user or "system" if no user is authenticated.
     */
    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(auth).map(Authentication::getName).orElse("system");
    }
}
