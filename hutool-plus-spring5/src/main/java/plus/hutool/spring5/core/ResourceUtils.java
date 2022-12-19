package plus.hutool.spring5.core;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Spring {@link Resource} 工具类
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ResourceUtils {
    private ResourceUtils() {}

    /**
     * 将类路径下的文件加载为 Spring {@link Resource}
     *
     * @param classpathFilePath 文件的类路径
     * @return Spring {@link Resource} 对象
     */
    public static Resource loadClassPathFileAsSpringResource(String classpathFilePath) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        if (!StrUtil.startWith(classpathFilePath, ResourceLoader.CLASSPATH_URL_PREFIX)) {
            classpathFilePath = ResourceLoader.CLASSPATH_URL_PREFIX + classpathFilePath;
        }
        return resourceLoader.getResource(classpathFilePath);
    }

    /**
     * 将文件加载为 Spring {@link Resource}
     *
     * @param file 文件
     * @return Spring {@link Resource} 对象
     */
    public static Resource loadFileAsSpringResource(File file) {
        try {
            return new UrlResource(file.toPath().toUri());
        } catch (MalformedURLException ex) {
            throw new IORuntimeException("shouldn't reach here");
        }
    }
}
