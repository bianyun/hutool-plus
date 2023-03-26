package plus.hutool.media.exception;

import cn.hutool.core.util.StrUtil;
import lombok.NoArgsConstructor;

/**
 * 非法的媒体类型主类型异常
 *
 * @author bianyun
 * @date 2023/2/17
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
@NoArgsConstructor
public class IllegalMainMediaTypeException extends IllegalArgumentException {

    public IllegalMainMediaTypeException(String mainMediaType) {
        super(StrUtil.format("Not a legal main media type string: {}", mainMediaType));
    }

}
