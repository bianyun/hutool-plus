package plus.hutool.media.image;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.imgscalr.Scalr;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import plus.hutool.core.io.FileUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.content.type.MediaTypeUtils;
import plus.hutool.media.exception.ImageResizeFailureException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFile;

class ImageUtilsTest {

    @Test
    void testGetBufferedImage() {
        final File imageFile = resolveTestFile("test_550x734.jpg");
        assertThat(ImageUtils.getBufferedImage(imageFile)).isNotNull();

        final File imageFileNotExists = resolveTestFile("not_exists.png");
        assertThatThrownBy(() -> ImageUtils.getBufferedImage(imageFileNotExists))
                .isInstanceOf(IORuntimeException.class)
                .hasMessageStartingWith("FileNotFoundException: %s", imageFileNotExists.getAbsolutePath())
                .hasCauseExactlyInstanceOf(FileNotFoundException.class);

        try (MockedStatic<ImageIO> mockedStatic = Mockito.mockStatic(ImageIO.class)) {
            mockedStatic.when(() -> ImageIO.read(any(File.class)))
                    .thenThrow(new IOException("ImageIO 读文件内容到 BufferedImage 发生异常"));
            assertThatThrownBy(() -> ImageUtils.getBufferedImage(imageFile))
                    .isExactlyInstanceOf(IORuntimeException.class)
                    .hasMessage("IOException: ImageIO 读文件内容到 BufferedImage 发生异常")
                    .hasCauseExactlyInstanceOf(IOException.class);
        }
    }

    @Test
    void testGetImageWidth() {
        final File imageFile = resolveTestFile("test_550x734.jpg");
        assertThat(ImageUtils.getImageWidth(imageFile)).isEqualTo(550);
    }

    @Test
    void testGetImageHeight() {
        final File imageFile = resolveTestFile("test_550x734.jpg");
        assertThat(ImageUtils.getImageHeight(imageFile)).isEqualTo(734);
    }

    @Test
    void testGetMaxSizeOfWidthAndHeight() {
        final File imageFile = resolveTestFile("test_550x734.jpg");
        assertThat(ImageUtils.getMaxSizeOfWidthAndHeight(imageFile)).isEqualTo(734);
    }

    @Test
    void testGetMinSizeOfWidthAndHeight() {
        final File imageFile = resolveTestFile("test_550x734.jpg");
        assertThat(ImageUtils.getMinSizeOfWidthAndHeight(imageFile)).isEqualTo(550);
    }

    @Test
    void testResizeToSmallerImage() {
        final File originalFile = resolveTestFile("test_550x734.jpg");
        final File resizedImageFile = ImageUtils.resizeImage(originalFile, 123);

        assertThat(resizedImageFile).exists().hasExtension("jpg").isFile();
        assertThat(ImageUtils.getMaxSizeOfWidthAndHeight(resizedImageFile)).isEqualTo(123);
        FileUtil.del(resizedImageFile);
    }

    @Test
    void testResizeToBiggerImage() {
        final File originalFile = resolveTestFile("test_550x734.jpg");
        final File resizedImageFile = ImageUtils.resizeImage(originalFile, 1024);

        assertThat(resizedImageFile).exists().hasExtension("jpg").isFile();
        assertThat(ImageUtils.getMaxSizeOfWidthAndHeight(resizedImageFile)).isEqualTo(1024);
        FileUtil.del(resizedImageFile);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void testResizeImageThrowsException() {
        final File imageFile = resolveTestFile("test.tiff");
        MediaType mediaType = MediaTypeUtils.detectMediaType(imageFile);

        assertThatThrownBy(() -> ImageUtils.resizeImage(imageFile, 100))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("不支持该文件的媒体类型[%s]: %s", mediaType, imageFile.getAbsolutePath());

        final File jpegImageFile = resolveTestFile("test.jpeg");
        try (MockedStatic<Scalr> staticScalr = Mockito.mockStatic(Scalr.class)) {
            staticScalr.when(() -> Scalr.resize(any(BufferedImage.class), any(Scalr.Method.class),
                            any(Scalr.Mode.class), anyInt(), any(ConvolveOp.class)))
                    .thenThrow(new ImagingOpException("图片调整大小操作内部异常"));
            assertThatThrownBy(() -> ImageUtils.resizeImage(jpegImageFile, 100))
                    .isExactlyInstanceOf(ImageResizeFailureException.class)
                    .hasMessage("调整图片大小失败, 原因: 图片调整大小操作内部异常")
                    .hasCauseExactlyInstanceOf(ImagingOpException.class);
        }

        assertThatThrownBy(() -> ImageUtils.resizeImage(resolveTestFile("test.wbmp"), 100))
                .isExactlyInstanceOf(ImageResizeFailureException.class)
                .hasMessage("调整图片大小失败, 原因: 其输出的图片文件大小为 0")
                .hasNoCause();
    }

    @Test
    void testWriteImageToFile_throwsIOException() {
        BufferedImage bufferedImage = ImageUtils.getBufferedImage(resolveTestFile("test.jpg"));
        File outputFile = FileUtils.createRandomNamedTempFile("jpg");

        try (MockedStatic<ImageIO> mockedStatic = Mockito.mockStatic(ImageIO.class)) {
            mockedStatic.when(() -> ImageIO.write(any(BufferedImage.class), anyString(), any(File.class)))
                    .thenThrow(new IOException("ImageIO 写 BufferedImage 到文件发生异常"));
            assertThatThrownBy(() -> ImageUtils.writeImageToFile(bufferedImage, "jpg", outputFile))
                    .isExactlyInstanceOf(IORuntimeException.class)
                    .hasMessage("IOException: ImageIO 写 BufferedImage 到文件发生异常")
                    .hasCauseExactlyInstanceOf(IOException.class);
        }
    }
}
