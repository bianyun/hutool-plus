package plus.hutool.media.exception;

import cn.hutool.core.util.StrUtil;
import lombok.NoArgsConstructor;
import plus.hutool.media.content.type.MediaType;

/**
 * 不支持的文件转换来源媒体类型异常
 *
 * @author bianyun
 * @date 2023/2/15
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
@NoArgsConstructor
public class UnsupportedFileConversionSrcMediaTypeException extends RuntimeException {
    private static final String MSG_TEMPLATE = "不支持如下格式的文件转换: [{} => {}]";

    public UnsupportedFileConversionSrcMediaTypeException(MediaType srcMediaType, MediaType destMediaType) {
        super(StrUtil.format(MSG_TEMPLATE, srcMediaType, destMediaType));
    }

}
