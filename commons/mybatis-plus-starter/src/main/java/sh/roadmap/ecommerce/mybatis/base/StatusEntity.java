package sh.roadmap.ecommerce.mybatis.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an entity with a status field.
 * This class is serializable and integrates with MyBatis-Plus for database mapping.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Getter and @Setter: Lombok annotations to automatically generate
 *       getter and setter methods for all fields.</li>
 *   <li>@TableField: MyBatis-Plus annotation to map the field to a database column.</li>
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
public class StatusEntity extends SoftDeleteEntity implements Serializable {

    /**
     * A unique identifier for the serialized class version.
     * This ensures compatibility during the deserialization process.
     */
    @Serial
    private static final long serialVersionUID = 6_490_522_452_612_397_559L;

    /**
     * The status of the entity.
     * Mapped to the "status" database column.
     */
    @TableField(value = "status")
    private Status status;
}