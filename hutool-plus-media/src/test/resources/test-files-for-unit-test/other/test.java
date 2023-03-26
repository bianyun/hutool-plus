package plus.hutool.media.content.type.internal;

import plus.hutool.core.lang.Asserts;
import plus.hutool.media.exception.IllegalMainMediaTypeException;

/**
 * 媒体类型的主类型
 *
 * @author bianyun
 * @date 2023/2/17
 */
public enum MainMediaType {
    TEXT, IMAGE, AUDIO, VIDEO, APPLICATION, FONT, MULTIPART, MESSAGE, MODEL, EXAMPLE;

    /**
     * 获取媒体类型主类型的字符串表示
     *
     * @return 媒体类型主类型的字符串表示
     */
    public String strValue() {
        return name().toLowerCase();
    }

    /**
     * 将字符串解析为 {@link MainMediaType} 枚举对象
     *
     * @param mainMediaType 字符串格式的媒体类型主类型
     * @return {@link MainMediaType} 枚举对象
     */
    public static MainMediaType of(String mainMediaType) {
        Asserts.notBlank(mainMediaType, "main type cannot be null or empty");

        for (MainMediaType type : values()) {
            if (type.strValue().equalsIgnoreCase(mainMediaType)) {
                return type;
            }
        }

        throw new IllegalMainMediaTypeException(mainMediaType);
    }
}
