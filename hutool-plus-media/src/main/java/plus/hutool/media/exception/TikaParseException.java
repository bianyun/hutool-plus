package plus.hutool.media.exception;

import lombok.NoArgsConstructor;

/**
 * Tika 解析异常
 *
 * @author bianyun
 * @date 2022/12/22
 */
@NoArgsConstructor
@SuppressWarnings({"JavadocDeclaration"})
public class TikaParseException extends RuntimeException {

    public TikaParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
