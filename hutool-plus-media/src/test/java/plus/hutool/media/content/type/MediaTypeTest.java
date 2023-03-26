package plus.hutool.media.content.type;

import cn.hutool.core.util.ReflectUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.media.content.type.internal.MainMediaType;
import plus.hutool.media.content.type.internal.SubMediaType;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static plus.hutool.media.content.type.MediaType.APPLICATION_OOXML_DOCUMENT;
import static plus.hutool.media.content.type.MediaType.APPLICATION_PDF;
import static plus.hutool.media.content.type.MediaType.getOneByFileExtension;

class MediaTypeTest {

    @Test
    void testOf() {
        final MediaType result = MediaType.of(MainMediaType.EXAMPLE, "subtype", "ext1", "ext1", "ext2");
        assertThat(result.getMainType()).isEqualTo(MainMediaType.EXAMPLE);
        assertThat(result.getSubTypeStr()).isEqualTo("subtype");
        assertThat(result.toString()).isEqualTo("example/subtype");
        assertThat(result).isEqualTo(MediaType.of(MainMediaType.EXAMPLE, "subtype"));
        assertThat(result.hashCode()).isEqualTo(MediaType.of(MainMediaType.EXAMPLE, "subtype").hashCode());
        assertThat(result == MediaType.of(MainMediaType.EXAMPLE, "subtype")).isTrue();
        assertThat(result.getFileExtensionList()).hasSameElementsAs(Arrays.asList("ext1", "ext2"));
        assertThat(result.getDefaultFileExtension()).isEqualTo("ext1");
        assertThat(MediaType.of(MainMediaType.EXAMPLE, "anotherType").getDefaultFileExtension()).isNull();
    }

    @Test
    void testApplication() {
        final MediaType result = MediaType.application(SubMediaType.JSON);
        assertThat(result.getMainType()).isEqualTo(MainMediaType.APPLICATION);
        assertThat(result.getSubTypeStr()).isEqualTo(SubMediaType.JSON);
        assertThat(result.toString()).isEqualTo(MediaType.APPLICATION_JSON.strValue());
        assertThat(result).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(result.hashCode()).isEqualTo(MediaType.APPLICATION_JSON.hashCode());
        assertThat(result == MediaType.APPLICATION_JSON).isTrue();
        assertThat(result.getDefaultFileExtension()).isEqualTo("json");
    }

    @Test
    void testText() {
        final MediaType result = MediaType.text(SubMediaType.HTML);
        assertThat(result.getMainType()).isEqualTo(MainMediaType.TEXT);
        assertThat(result.getSubTypeStr()).isEqualTo(SubMediaType.HTML);
        assertThat(result.toString()).isEqualTo(MediaType.TEXT_HTML.strValue());
        assertThat(result).isEqualTo(MediaType.TEXT_HTML);
        assertThat(result.hashCode()).isEqualTo(MediaType.TEXT_HTML.hashCode());
        assertThat(result == MediaType.TEXT_HTML).isTrue();
    }

    @Test
    void testImage() {
        final MediaType result = MediaType.image(SubMediaType.PNG);
        assertThat(result.getMainType()).isEqualTo(MainMediaType.IMAGE);
        assertThat(result.getSubTypeStr()).isEqualTo(SubMediaType.PNG);
        assertThat(result.toString()).isEqualTo(MediaType.IMAGE_PNG.strValue());
        assertThat(result).isEqualTo(MediaType.IMAGE_PNG);
        assertThat(result.hashCode()).isEqualTo(MediaType.IMAGE_PNG.hashCode());
        assertThat(result == MediaType.IMAGE_PNG).isTrue();
    }

    @Test
    void testAudio() {
        final MediaType result = MediaType.audio(SubMediaType.MPEG);
        assertThat(result.getMainType()).isEqualTo(MainMediaType.AUDIO);
        assertThat(result.getSubTypeStr()).isEqualTo(SubMediaType.MPEG);
        assertThat(result.toString()).isEqualTo(MediaType.AUDIO_MPEG.strValue());
        assertThat(result).isEqualTo(MediaType.AUDIO_MPEG);
        assertThat(result.hashCode()).isEqualTo(MediaType.AUDIO_MPEG.hashCode());
        assertThat(result == MediaType.AUDIO_MPEG).isTrue();
    }

    @Test
    void testVideo() {
        final MediaType result = MediaType.video(SubMediaType.MPEG);
        assertThat(result.getMainType()).isEqualTo(MainMediaType.VIDEO);
        assertThat(result.getSubTypeStr()).isEqualTo(SubMediaType.MPEG);
        assertThat(result.toString()).isEqualTo(MediaType.VIDEO_MPEG.strValue());
        assertThat(result).isEqualTo(MediaType.VIDEO_MPEG);
        assertThat(result.hashCode()).isEqualTo(MediaType.VIDEO_MPEG.hashCode());
        assertThat(result == MediaType.VIDEO_MPEG).isTrue();
    }

    @Test
    void testFont() {
        final MediaType result = MediaType.font(SubMediaType.WOFF);
        assertThat(result.getMainType()).isEqualTo(MainMediaType.FONT);
        assertThat(result.getSubTypeStr()).isEqualTo(SubMediaType.WOFF);
        assertThat(result.toString()).isEqualTo(MediaType.FONT_WOFF.strValue());
        assertThat(result).isEqualTo(MediaType.FONT_WOFF);
        assertThat(result.hashCode()).isEqualTo(MediaType.FONT_WOFF.hashCode());
        assertThat(result == MediaType.FONT_WOFF).isTrue();
    }

    @Test
    void testGetOneByFileExtension() {
        assertThat(getOneByFileExtension("aaa")).isNull();
        assertThat(getOneByFileExtension("pdf")).isEqualTo(APPLICATION_PDF);
        assertThat(getOneByFileExtension("DOCX")).isEqualTo(APPLICATION_OOXML_DOCUMENT);

        MediaType type1 = MediaType.of(MainMediaType.EXAMPLE, "type1", "type1_ext1", "type1_ext2");
        MediaType type2 = MediaType.of(MainMediaType.EXAMPLE, "type2", "type1_ext1", "type2_ext");

        assertThat(type1.equals(type2)).isFalse();
        assertThat(getOneByFileExtension("type1_ext1")).isNull();
        assertThat(getOneByFileExtension("type1_ext2")).isEqualTo(type1);
        assertThat(getOneByFileExtension("type2_ext")).isEqualTo(type2);
    }

    @Test
    void testEquals() {
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(MediaType.of(MainMediaType.EXAMPLE, "aaa")).isNotEqualTo("ccc");
        assertThat(MediaType.of(MainMediaType.EXAMPLE, "aaa")).isNotEqualTo(MediaType.of(MainMediaType.EXAMPLE, "bbb"));
        assertThat(MediaType.of(MainMediaType.EXAMPLE, "aaa")).isNotEqualTo(MediaType.of(MainMediaType.TEXT, "aaa"));
        assertThat(MediaType.of(MainMediaType.EXAMPLE, "aaa")).isNotEqualTo(MediaType.of(MainMediaType.TEXT, "bbb"));

        MediaType mediaType = ReflectUtil.newInstance(MediaType.class, MainMediaType.EXAMPLE, "aaa", Collections.emptyList());
        assertThat(MediaType.of(MainMediaType.EXAMPLE, "aaa")).isEqualTo(mediaType);
    }

    @Test
    void testContainsFileExtension() {
        assertThat(MediaType.TEXT_PLAIN.containsFileExtension("txt")).isTrue();
        assertThat(MediaType.TEXT_PLAIN.containsFileExtension("conf")).isTrue();
        assertThat(MediaType.TEXT_PLAIN.containsFileExtension("zip")).isFalse();
    }

    @Test
    void testIsAnyOf() {
        assertThat(MediaType.TEXT_PLAIN.isAnyOf(MediaType.TEXT_CSS)).isFalse();
        assertThat(MediaType.TEXT_PLAIN.isAnyOf(MediaType.TEXT_CSS, MediaType.TEXT_HTML)).isFalse();
        assertThat(MediaType.TEXT_PLAIN.isAnyOf(MediaType.TEXT_CSS, MediaType.TEXT_PLAIN)).isTrue();
        assertThat(MediaType.TEXT_CSS.isAnyOf(MediaType.TEXT_CSS, MediaType.TEXT_PLAIN, MediaType.TEXT_CSV)).isTrue();
    }

    @Test
    void testNotAnyOf() {
        assertThat(MediaType.TEXT_PLAIN.notAnyOf(MediaType.TEXT_CSS)).isTrue();
        assertThat(MediaType.TEXT_PLAIN.notAnyOf(MediaType.TEXT_CSS, MediaType.TEXT_HTML)).isTrue();
        assertThat(MediaType.TEXT_PLAIN.notAnyOf(MediaType.TEXT_CSS, MediaType.TEXT_PLAIN)).isFalse();
        assertThat(MediaType.TEXT_CSS.notAnyOf(MediaType.TEXT_CSS, MediaType.TEXT_PLAIN, MediaType.TEXT_CSV)).isFalse();
    }
}
