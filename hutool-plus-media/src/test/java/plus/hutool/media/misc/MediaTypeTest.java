package plus.hutool.media.misc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static plus.hutool.media.misc.MediaType.*;
import static plus.hutool.media.misc.MediaType.Value.*;

class MediaTypeTest {

    @Test
    void testOf() {
        final MediaType result = MediaType.of("type", "subtype", "ext1", "ext1", "ext2");
        assertThat(result.getType()).isEqualTo("type");
        assertThat(result.getSubtype()).isEqualTo("subtype");
        assertThat(result.toString()).isEqualTo("type/subtype");
        assertThat(result).isEqualTo(MediaType.of("type", "subtype"));
        assertThat(result.hashCode()).isEqualTo(MediaType.of("type", "subtype").hashCode());
        assertThat(result == MediaType.of("type", "subtype")).isTrue();
        assertThat(result.getFileExtensionList()).hasSameElementsAs(Arrays.asList("ext1", "ext2"));
        assertThat(result.getDefaultFileExtension()).isEqualTo("ext1");
    }

    @Test
    void testApplication() {
        final MediaType result = MediaType.application(JSON);
        assertThat(result.getType()).isEqualTo(MEDIA_TYPE_APPLICATION);
        assertThat(result.getSubtype()).isEqualTo(JSON);
        assertThat(result.toString()).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(result).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(result.hashCode()).isEqualTo(MediaType.APPLICATION_JSON.hashCode());
        assertThat(result == MediaType.APPLICATION_JSON).isTrue();
        assertThat(result.getDefaultFileExtension()).isEqualTo(JSON);
    }

    @Test
    void testText() {
        final MediaType result = MediaType.text(HTML);
        assertThat(result.getType()).isEqualTo(MEDIA_TYPE_TEXT);
        assertThat(result.getSubtype()).isEqualTo(HTML);
        assertThat(result.toString()).isEqualTo(TEXT_HTML_VALUE);
        assertThat(result).isEqualTo(MediaType.TEXT_HTML);
        assertThat(result.hashCode()).isEqualTo(MediaType.TEXT_HTML.hashCode());
        assertThat(result == MediaType.TEXT_HTML).isTrue();
    }

    @Test
    void testImage() {
        final MediaType result = MediaType.image(PNG);
        assertThat(result.getType()).isEqualTo(MEDIA_TYPE_IMAGE);
        assertThat(result.getSubtype()).isEqualTo(PNG);
        assertThat(result.toString()).isEqualTo(IMAGE_PNG_VALUE);
        assertThat(result).isEqualTo(MediaType.IMAGE_PNG);
        assertThat(result.hashCode()).isEqualTo(MediaType.IMAGE_PNG.hashCode());
        assertThat(result == MediaType.IMAGE_PNG).isTrue();
    }

    @Test
    void testAudio() {
        final MediaType result = MediaType.audio(MPEG);
        assertThat(result.getType()).isEqualTo(MEDIA_TYPE_AUDIO);
        assertThat(result.getSubtype()).isEqualTo(MPEG);
        assertThat(result.toString()).isEqualTo(AUDIO_MPEG_VALUE);
        assertThat(result).isEqualTo(MediaType.AUDIO_MPEG);
        assertThat(result.hashCode()).isEqualTo(MediaType.AUDIO_MPEG.hashCode());
        assertThat(result == MediaType.AUDIO_MPEG).isTrue();
    }

    @Test
    void testVideo() {
        final MediaType result = MediaType.video(MPEG);
        assertThat(result.getType()).isEqualTo(MEDIA_TYPE_VIDEO);
        assertThat(result.getSubtype()).isEqualTo(MPEG);
        assertThat(result.toString()).isEqualTo(VIDEO_MPEG_VALUE);
        assertThat(result).isEqualTo(MediaType.VIDEO_MPEG);
        assertThat(result.hashCode()).isEqualTo(MediaType.VIDEO_MPEG.hashCode());
        assertThat(result == MediaType.VIDEO_MPEG).isTrue();
    }

    @Test
    void testFont() {
        final MediaType result = MediaType.font(WOFF);
        assertThat(result.getType()).isEqualTo(MEDIA_TYPE_FONT);
        assertThat(result.getSubtype()).isEqualTo(WOFF);
        assertThat(result.toString()).isEqualTo(FONT_WOFF_VALUE);
        assertThat(result).isEqualTo(MediaType.FONT_WOFF);
        assertThat(result.hashCode()).isEqualTo(MediaType.FONT_WOFF.hashCode());
        assertThat(result == MediaType.FONT_WOFF).isTrue();
    }

    @Test
    void testGetOneByFileExtension() {
        assertThat(getOneByFileExtension("aaa")).isNull();
        assertThat(getOneByFileExtension("pdf")).isEqualTo(APPLICATION_PDF);
        assertThat(getOneByFileExtension("DOCX")).isEqualTo(APPLICATION_OOXML_DOCUMENT);
    }

    @Test
    void testEquals() {
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(MediaType.of("aaa", "bbb")).isNotEqualTo("ccc");
        assertThat(MediaType.of("aaa", "bbb")).isNotEqualTo(MediaType.of("bbb", "ccc"));
    }
}
