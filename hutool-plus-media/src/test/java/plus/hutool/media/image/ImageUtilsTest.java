package plus.hutool.media.image;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageUtilsTest {

    @Test
    void testGetImageWidth() {
        final File imageFile = FileUtil.file("classpath:testFiles/images/test.png");
        assertThat(ImageUtils.getImageWidth(imageFile)).isEqualTo(1920);

        final File imageFileNotExists = FileUtil.file("classpath:testFiles/images/not_exists.png");
        assertThatThrownBy(() -> ImageUtils.getImageWidth(imageFileNotExists))
                .isInstanceOf(IORuntimeException.class)
                .hasMessage("IIOException: Can't read input file!")
                .hasCauseExactlyInstanceOf(IIOException.class)
                .hasRootCauseMessage("Can't read input file!");
    }

    @Test
    void testCreateThumbnail() {
        final File originalFile = FileUtil.file("classpath:testFiles/images/test.png");
        final File thumbnail = ImageUtils.createThumbnail(originalFile, 123);

        assertThat(thumbnail).exists().hasExtension("png").isFile();
        assertThat(ImageUtils.getImageWidth(thumbnail)).isEqualTo(123);

        FileUtil.del(thumbnail);


        final File imageFileNotExists = FileUtil.file("classpath:testFiles/images/not_exists.png");
        assertThatThrownBy(() -> ImageUtils.createThumbnail(imageFileNotExists, 123))
                .isInstanceOf(IORuntimeException.class)
                .hasMessage("IIOException: Can't read input file!")
                .hasCauseExactlyInstanceOf(IIOException.class)
                .hasRootCauseMessage("Can't read input file!");
    }

}
