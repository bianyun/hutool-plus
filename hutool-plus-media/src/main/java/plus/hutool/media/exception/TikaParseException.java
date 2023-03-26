package plus.hutool.media.exception;

import lombok.NoArgsConstructor;

/**
 * Tika 解析异常
 *
 * @author bianyun
 * @date 2022/12/22
 */
@SuppressWarnings("JavadocDeclaration")
@NoArgsConstructor
public class TikaParseException extends RuntimeException {

    public TikaParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
