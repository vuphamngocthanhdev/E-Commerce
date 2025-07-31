package sh.roadmap.ecommerce.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class for retrieving localized messages from a MessageSource.
 * Provides static methods to fetch messages based on message codes and arguments.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Autowired: Injects the MessageSource bean into the constructor.</li>
 * </ul>
 */
@Component
public class MessageUtil {

    /**
     * Static reference to the MessageSource for retrieving messages.
     * This is initialized via the constructor.
     */
    private static MessageSource messageSource;

    /**
     * Constructor for MessageUtil.
     * Initializes the static MessageSource field with the provided bean.
     *
     * @param messageSource The MessageSource bean to be used for message retrieval.
     */
    public MessageUtil(@Autowired MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    /**
     * Retrieves a localized message based on an integer code.
     * Delegates to the overloaded method with no arguments.
     *
     * @param code The integer code of the message.
     * @return The localized message as a String.
     */
    public static String getMessage(int code) {
        return getMessage(code, new String[0]);
    }

    /**
     * Retrieves a localized message based on an integer code and arguments.
     * Converts the integer code to a String and fetches the message.
     *
     * @param code The integer code of the message.
     * @param args The arguments to be used in the message format.
     * @return The localized message as a String.
     */
    public static String getMessage(int code, String... args) {
        return messageSource.getMessage(code + "", args, LocaleContextHolder.getLocale());
    }

    /**
     * Retrieves a localized message based on a String code.
     * Delegates to the overloaded method with no arguments.
     *
     * @param code The String code of the message.
     * @return The localized message as a String.
     */
    public static String getMessage(String code) {
        return getMessage(code, new String[0]);
    }

    /**
     * Retrieves a localized message based on a String code and arguments.
     * Fetches the message from the MessageSource using the current locale.
     *
     * @param code The String code of the message.
     * @param args The arguments to be used in the message format.
     * @return The localized message as a String.
     */
    public static String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
