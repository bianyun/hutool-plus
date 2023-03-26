package plus.hutool.media.converter.file.impl.pdf;

import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.converter.file.impl.AbstractFileConverter;

/**
 * 转换目标为 PDF文件 的文件转换器基类
 *
 * @author bianyun
 * @date 2023/2/15
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class AbstractToPdfConverter extends AbstractFileConverter {

    @Override
    public MediaType getDestMediaType() {
        return MediaType.APPLICATION_PDF;
    }
}
