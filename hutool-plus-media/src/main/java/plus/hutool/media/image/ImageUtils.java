package plus.hutool.media.image;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import plus.hutool.core.io.FileUtils;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.lang.ExceptionUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.content.type.MediaTypeUtils;
import plus.hutool.media.exception.ImageResizeFailureException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import static plus.hutool.media.content.type.MediaType.IMAGE_BMP;
import static plus.hutool.media.content.type.MediaType.IMAGE_GIF;
import static plus.hutool.media.content.type.MediaType.IMAGE_JPEG;
import static plus.hutool.media.content.type.MediaType.IMAGE_PNG;
import static plus.hutool.media.content.type.MediaType.IMAGE_WBMP;

/**
 * 图片工具
 *
 * @author bianyun
 * @date 2022/12/9
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class ImageUtils {
    private ImageUtils() {}

    /**
     * 获取图片文件的 {@link BufferedImage} 对象
     *
     * @param imageFile 图片文件
     * @return {@link BufferedImage} 对象
     */
    public static BufferedImage getBufferedImage(File imageFile) {
        MediaType mediaType = MediaTypeUtils.detectMediaType(imageFile);
        if (mediaType.notAnyOf(IMAGE_BMP, IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF, IMAGE_WBMP)) {
            throw ExceptionUtils.illegalArgumentException(
                    "不支持该文件的媒体类型[{}]: {}", mediaType, imageFile.getAbsolutePath());
        }

        try {
            BufferedImage result = ImageIO.read(imageFile);
            Asserts.notNull(result, "从图像文件获取的 BufferedImage 对象为 null: {}", imageFile.getAbsoluteFile());
            return result;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获取图片文件的宽度（像素）
     *
     * @param imageFile 图片文件
     * @return 图片文件的宽度（像素）
     */
    public static int getImageWidth(File imageFile) {
        return getBufferedImage(imageFile).getWidth();
    }

    /**
     * 获取图片文件的高度（像素）
     *
     * @param imageFile 图片文件
     * @return 图片文件的高度（像素）
     */
    public static int getImageHeight(File imageFile) {
        return getBufferedImage(imageFile).getHeight();
    }

    /**
     * 获取图片文件的宽度和高度的最大值（像素）
     *
     * @param imageFile 图片文件
     * @return 图片文件的宽度和高度的最大值（像素）
     */
    public static int getMaxSizeOfWidthAndHeight(File imageFile) {
        BufferedImage image = getBufferedImage(imageFile);
        return Math.max(image.getWidth(), image.getHeight());
    }

    /**
     * 获取图片文件的宽度和高度的最小值（像素）
     *
     * @param imageFile 图片文件
     * @return 图片文件的宽度和高度的最小值（像素）
     */
    public static int getMinSizeOfWidthAndHeight(File imageFile) {
        BufferedImage image = getBufferedImage(imageFile);
        return Math.min(image.getWidth(), image.getHeight());
    }

    /**
     * 将 {@link RenderedImage} 对象写入到文件
     *
     * @param image             {@link RenderedImage} 对象
     * @param filenameExtension 文件扩展名（如： png、jpg 等）
     * @param outputFile        输出文件
     */
    public static void writeImageToFile(RenderedImage image, String filenameExtension, File outputFile) {
        try {
            ImageIO.write(image, filenameExtension, outputFile);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 读取图片文件内容，调整大小后输出到新的图片文件（后缀名不变，且不修改原始图片）
     *
     * @param imageFile  图片文件
     * @param targetSize 调整的目标大小（新的图片的宽和高的最大值）
     * @return 调整大小后的图片文件
     */
    public static File resizeImage(File imageFile, Integer targetSize) {
        String filenameExtension = FileUtils.getFileExtension(imageFile.getName());
        Asserts.notBlank(filenameExtension, "图片文件的扩展名不能为空: {}", imageFile.getAbsolutePath());

        File outputFile = FileUtils.createRandomNamedTempFile(filenameExtension);

        BufferedImage img = getBufferedImage(imageFile);

        BufferedImage resizedImage;
        try {
            resizedImage = Scalr.resize(img, Method.AUTOMATIC, Mode.AUTOMATIC, targetSize, Scalr.OP_ANTIALIAS);
        } catch (Exception e) {
            throw new ImageResizeFailureException(StrUtil.format("调整图片大小失败, 原因: {}", e.getMessage()), e);
        }

        writeImageToFile(resizedImage, filenameExtension, outputFile);

        if (outputFile.length() == 0) {
            throw new ImageResizeFailureException("调整图片大小失败, 原因: 其输出的图片文件大小为 0");
        }

        return outputFile;
    }

}
