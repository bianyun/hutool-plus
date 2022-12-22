package plus.hutool.media.image;

import cn.hutool.core.io.IORuntimeException;
import org.imgscalr.Scalr;
import plus.hutool.core.io.FileUtils;
import plus.hutool.core.lang.Asserts;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片工具
 *
 * @author bianyun
 * @date 2022/12/9
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ImageUtils {
    private ImageUtils() {}

    /**
     * 获取图片文件的像素宽度
     *
     * @param imageFile 图片文件
     * @return 图片文件的像素宽度
     */
    public static int getImageWidth(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            return image.getWidth();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 创建缩略图
     *
     * @param originalFile 原始图片文件
     * @param width        宽度
     * @return 缩略图文件
     */
    public static File createThumbnail(File originalFile, Integer width) {
        try {
            String filenameExtension = FileUtils.getFileExtension(originalFile.getName());
            Asserts.notBlank(filenameExtension, "图片文件的扩展名不能为空: {}", originalFile.getAbsolutePath());
            File outputFile = FileUtils.createRandomNamedTempFile(filenameExtension);

            BufferedImage img = ImageIO.read(originalFile);
            BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC,
                    width, Scalr.OP_ANTIALIAS);
            ImageIO.write(thumbImg, filenameExtension, outputFile);
            return outputFile;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
