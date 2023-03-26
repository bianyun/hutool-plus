package plus.hutool.media.exception;

import lombok.NoArgsConstructor;

/**
 * 调整图片大小失败异常
 *
 * @author bianyun
 * @date 2023/2/17
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
@NoArgsConstructor
public class ImageResizeFailureException extends IllegalArgumentException {

    public ImageResizeFailureException(String message) {
        super(message);
    }

    public ImageResizeFailureException(String message, Throwable t) {
        super(message, t);
    }

}
