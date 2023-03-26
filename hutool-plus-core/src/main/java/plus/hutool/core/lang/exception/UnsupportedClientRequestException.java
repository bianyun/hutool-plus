package plus.hutool.core.lang.exception;

import lombok.NoArgsConstructor;

/**
 * 不支持的客户端请求异常
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
@NoArgsConstructor
public class UnsupportedClientRequestException extends IllegalArgumentException {

    public UnsupportedClientRequestException(String message) {
        super(message);
    }

}
