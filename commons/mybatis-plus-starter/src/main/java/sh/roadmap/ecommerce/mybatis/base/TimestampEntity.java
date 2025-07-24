package sh.roadmap.ecommerce.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A base entity class that includes timestamp fields for creation and update times.
 * This class is intended to be extended by other entity classes to inherit
 * the timestamp functionality.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Getter and @Setter: Lombok annotations to automatically generate
 *       getter and setter methods for all fields.</li>
 *   <li>@TableField: MyBatis-Plus annotation to map the fields to database columns.</li>
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
public class TimestampEntity implements Serializable {

    /**
     * A unique identifier for the serialized class version.
     * This ensures compatibility during the deserialization process.
     */
    @Serial
    private static final long serialVersionUID = 2_097_093_734_366_449_431L;

    /**
     * The timestamp indicating when the entity was created.
     * Mapped to a database column using @TableField.
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt; //recommended use Instant instead of LocalDateTime

    /**
     * The timestamp indicating when the entity was last updated.
     * Mapped to a database column using @TableField.
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt; //recommended use Instant instead of LocalDateTime
}