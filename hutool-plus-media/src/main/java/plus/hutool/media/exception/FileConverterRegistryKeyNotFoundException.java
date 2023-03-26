package plus.hutool.media.exception;

import cn.hutool.core.util.StrUtil;
import lombok.NoArgsConstructor;
import plus.hutool.media.content.type.MediaType;

/**
 * 不存在文件转换器注册表 Key 的异常
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
@NoArgsConstructor
public class FileConverterRegistryKeyNotFoundException extends RuntimeException {
    private static final String MSG_TEMPLATE = "文件转换器注册表中找不到相应的转换器: [{} => {}]";

    public FileConverterRegistryKeyNotFoundException(MediaType srcMediaType, MediaType destMediaType) {
        super(StrUtil.format(MSG_TEMPLATE, srcMediaType, destMediaType));
    }

}
