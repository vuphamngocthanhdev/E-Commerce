package sh.roadmap.ecommerce.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * An abstract entity class that extends the functionality of TimestampEntity
 * by adding auditing fields for tracking the user who created and last updated the entity.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Getter and @Setter: Lombok annotations to automatically generate
 *       getter and setter methods for all fields.</li>
 *   <li>@TableField: MyBatis-Plus annotation to map the fields to database columns
 *       and specify their behavior during insert and update operations.</li>
 * </ul>
 *
 * <p>Extends:</p>
 * <ul>
 *   <li>TimestampEntity: Inherits timestamp fields for creation and update times.</li>
 * </ul>
 *
 * <p>Implements:</p>
 * <ul>
 *   <li>Serializable: Allows instances of this class to be serialized, enabling
 *       them to be transferred or stored as byte streams.</li>
 * </ul>
 */
@Getter
@Setter
public abstract class AuditableEntity extends TimestampEntity implements Serializable {

    /**
     * A unique identifier for the serialized class version.
     * This ensures compatibility during the deserialization process.
     */
    @Serial
    private static final long serialVersionUID = 2_310_398_035_878_195_648L;

    /**
     * The ID of the user who created the entity.
     * Mapped to the "created_by" database column and automatically filled during insert operations.
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy; // data scope will automatically fill this field with the current user's ID during insert operations

    /**
     * The ID of the user who last updated the entity.
     * Mapped to the "updated_by" database column and automatically filled during insert and update operations.
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
}