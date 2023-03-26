package plus.hutool.media.exception;

import cn.hutool.core.util.StrUtil;
import lombok.NoArgsConstructor;

/**
 * 非法的媒体类型字符串异常
 *
 * @author bianyun
 * @date 2023/2/17
 */
@SuppressWarnings("JavadocDeclaration")
@NoArgsConstructor
public class IllegalFullMediaTypeStrException extends IllegalArgumentException {

    public IllegalFullMediaTypeStrException(String fullMediaType) {
        super(StrUtil.format("Not a legal full media type string with format [mainType/subType]: {}", fullMediaType));
    }

}
