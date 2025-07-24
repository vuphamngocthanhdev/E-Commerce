package sh.roadmap.ecommerce.mybatis.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a base entity with soft delete functionality.
 * This class extends the AuditableEntity to include auditing fields
 * and adds a boolean field to track soft deletion status.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Getter and @Setter: Lombok annotations to automatically generate
 *       getter and setter methods for all fields.</li>
 *   <li>@TableField: MyBatis-Plus annotation to map the field to a database column.</li>
 * </ul>
 *
 * <p>Extends:</p>
 * <ul>
 *   <li>AuditableEntity: Inherits auditing fields for tracking creation and update metadata.</li>
 * </ul>
 *
 * <p>Implements:</p>
 * <ul>
 *   <li>Serializable: Allows instances of this class to be serialized, enabling
 *       them to be transferred or stored as byte streams.</li>
 * </ul>
 */
@Setter
@Getter
public class SoftDeleteEntity extends AuditableEntity implements Serializable {

    /**
     * A unique identifier for the serialized class version.
     * This ensures compatibility during the deserialization process.
     */
    @Serial
    private static final long serialVersionUID = 3_210_422_785_326_263_858L;

    /**
     * Indicates whether the entity is soft deleted.
     * Mapped to the "is_deleted" database column.
     * Defaults to false, meaning the entity is not deleted.
     */
    @TableField(value = "is_deleted")
    private boolean isDeleted = false;
}
