package sh.roadmap.ecommerce.mybatis.base;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract base class for auditable entities with a unique identifier.
 * Extends the StatusEntity class to include a status field and implements Serializable
 * for object serialization.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Getter and @Setter: Lombok annotations to automatically generate
 *       getter and setter methods for all fields.</li>
 *   <li>@TableId: MyBatis-Plus annotation to map the "id" field to a database primary key column.</li>
 * </ul>
 *
 * <p>Extends:</p>
 * <ul>
 *   <li>StatusEntity: Inherits the status field and related functionality.</li>
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
public abstract class AbstractLockAuditableEntity extends StatusEntity implements Serializable {

    /**
     * A unique identifier for the serialized class version.
     * This ensures compatibility during the deserialization process.
     */
    @Serial
    private static final long serialVersionUID = 3_591_713_109_058_445_361L;

    /**
     * The unique identifier for the entity.
     * Mapped to the "id" database primary key column.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * Compares this entity to another object for equality.
     * Two entities are considered equal if they are of the same class
     * and have the same "id" value.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLockAuditableEntity that = (AbstractLockAuditableEntity) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Computes the hash code for this entity based on its "id" field.
     *
     * @return The hash code of the entity.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the entity.
     * Includes the class name and the "id" field value.
     *
     * @return A string representation of the entity.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                "} " + super.toString();
    }
}
