package plus.hutool.media.exception;

import lombok.NoArgsConstructor;

/**
 * 文件转换异常
 *
 * @author bianyun
 * @date 2022/6/28
 */
@SuppressWarnings("JavadocDeclaration")
@NoArgsConstructor
public class FileConversionException extends RuntimeException {

    public FileConversionException(String message, Throwable cause) {
        super(message, cause);
    }

}
