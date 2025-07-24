package sh.roadmap.ecommerce.mybatis.base;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * Enum representing the status of an entity.
 * Implements the IEnum interface to integrate with MyBatis-Plus.
 *
 * <p>Enum Constants:</p>
 * <ul>
 *   <li>INACTIVE: Represents an inactive status with a value of 0.</li>
 *   <li>ACTIVE: Represents an active status with a value of 1.</li>
 *   <li>PENDING: Represents a pending status with a value of 2.</li>
 *   <li>CANCELLED: Represents a cancelled status with a value of 3.</li>
 *   <li>DELETED: Represents a deleted status with a value of 4.</li>
 * </ul>
 */
public enum Status implements IEnum<Byte> {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1),
    PENDING((byte) 2),
    CANCELLED((byte) 3),
    DELETED((byte) 4);

    /**
     * The byte value associated with the status.
     */
    private final byte value;

    /**
     * Constructor to initialize the enum with its corresponding byte value.
     *
     * @param value The byte value representing the status.
     */
    Status(byte value) {
        this.value = value;
    }

    /**
     * Retrieves the byte value of the status.
     *
     * @return The byte value of the status.
     */
    @Override
    public Byte getValue() {
        return value;
    }
}