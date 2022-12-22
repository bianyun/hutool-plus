package plus.hutool.media.misc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.lang.Nullable;
import plus.hutool.core.iterable.collection.CollUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static plus.hutool.media.misc.MediaType.Value.*;


/**
 * 媒体类型常量
 *
 * @author bianyun
 * @date 2022/12/9
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "checkstyle:LineLength"})
@Data
public class MediaType {

    // =======================================================================
    //  Media Type constants (String value representation)
    // =======================================================================

    public static final String TEXT_PLAIN_VALUE = TEXT_TYPE_PREFIX + PLAIN;
    public static final String TEXT_HTML_VALUE = TEXT_TYPE_PREFIX + HTML;
    public static final String TEXT_CSS_VALUE = TEXT_TYPE_PREFIX + CSS;
    public static final String TEXT_CSV_VALUE = TEXT_TYPE_PREFIX + CSV;
    public static final String TEXT_JAVASCRIPT_VALUE = TEXT_TYPE_PREFIX + JAVASCRIPT;
    public static final String TEXT_MARKDOWN_VALUE = TEXT_TYPE_PREFIX + MARKDOWN;
    public static final String TEXT_VCARD_VALUE = TEXT_TYPE_PREFIX + VCARD;
    public static final String TEXT_CALENDAR_VALUE = TEXT_TYPE_PREFIX + CALENDAR;

    public static final String IMAGE_BMP_VALUE = IMAGE_TYPE_PREFIX + BMP;
    public static final String IMAGE_GIF_VALUE = IMAGE_TYPE_PREFIX + GIF;
    public static final String IMAGE_ICO_VALUE = IMAGE_TYPE_PREFIX + ICO;
    public static final String IMAGE_JPEG_VALUE = IMAGE_TYPE_PREFIX + JPEG;
    public static final String IMAGE_PNG_VALUE = IMAGE_TYPE_PREFIX + PNG;
    public static final String IMAGE_APNG_VALUE = IMAGE_TYPE_PREFIX + APNG;
    public static final String IMAGE_PSD_VALUE = IMAGE_TYPE_PREFIX + PSD;
    public static final String IMAGE_SVG_XML_VALUE = IMAGE_TYPE_PREFIX + SVG_XML;
    public static final String IMAGE_TIFF_VALUE = IMAGE_TYPE_PREFIX + TIFF;
    public static final String IMAGE_WEBP_VALUE = IMAGE_TYPE_PREFIX + WEBP;
    public static final String IMAGE_HEIF_VALUE = IMAGE_TYPE_PREFIX + HEIF;
    public static final String IMAGE_HEIC_VALUE = IMAGE_TYPE_PREFIX + HEIC;
    public static final String IMAGE_JP2K_VALUE = IMAGE_TYPE_PREFIX + JP2K;
    public static final String IMAGE_DWG_VALUE = IMAGE_TYPE_PREFIX + DWG;
    public static final String IMAGE_DXF_VALUE = IMAGE_TYPE_PREFIX + DXF;

    public static final String AUDIO_MP4_VALUE = AUDIO_TYPE_PREFIX + MP4;
    public static final String AUDIO_MPEG_VALUE = AUDIO_TYPE_PREFIX + MPEG;
    public static final String AUDIO_OGG_VALUE = AUDIO_TYPE_PREFIX + OGG;
    public static final String AUDIO_OPUS_VALUE = AUDIO_TYPE_PREFIX + OPUS;
    public static final String AUDIO_WEBM_VALUE = AUDIO_TYPE_PREFIX + WEBM;
    public static final String AUDIO_BASIC_VALUE = AUDIO_TYPE_PREFIX + BASIC;
    public static final String AUDIO_WMA_VALUE = AUDIO_TYPE_PREFIX + WMA;
    public static final String AUDIO_WAV_VALUE = AUDIO_TYPE_PREFIX + WAV;
    public static final String AUDIO_AAC_VALUE = AUDIO_TYPE_PREFIX + AAC;
    public static final String AUDIO_VORBIS_VALUE = AUDIO_TYPE_PREFIX + VORBIS;
    public static final String AUDIO_VND_WAVE_VALUE = AUDIO_TYPE_PREFIX + VND_WAVE;
    public static final String AUDIO_VND_REAL_AUDIO_VALUE = AUDIO_TYPE_PREFIX + VND_REAL_AUDIO;
    public static final String AUDIO_WAX_VALUE = AUDIO_TYPE_PREFIX + WAX;

    public static final String VIDEO_MP4_VALUE = VIDEO_TYPE_PREFIX + MP4;
    public static final String VIDEO_MPEG_VALUE = VIDEO_TYPE_PREFIX + MPEG;
    public static final String VIDEO_OGG_VALUE = VIDEO_TYPE_PREFIX + OGG;
    public static final String VIDEO_WEBM_VALUE = VIDEO_TYPE_PREFIX + WEBM;
    public static final String VIDEO_QUICKTIME_VALUE = VIDEO_TYPE_PREFIX + QUICKTIME;
    public static final String VIDEO_WMV_VALUE = VIDEO_TYPE_PREFIX + WMV;
    public static final String VIDEO_AVI_VALUE = VIDEO_TYPE_PREFIX + AVI;
    public static final String VIDEO_TS_VALUE = VIDEO_TYPE_PREFIX + TS;
    public static final String VIDEO_FLV_VALUE = VIDEO_TYPE_PREFIX + FLV;
    public static final String VIDEO_THREE_GPP_VALUE = VIDEO_TYPE_PREFIX + THREE_GPP;
    public static final String VIDEO_THREE_GPP2_VALUE = VIDEO_TYPE_PREFIX + THREE_GPP2;

    public static final String FONT_COLLECTION_VALUE = FONT_TYPE_PREFIX + COLLECTION;
    public static final String FONT_OTF_VALUE = FONT_TYPE_PREFIX + OTF;
    public static final String FONT_SFNT_VALUE = FONT_TYPE_PREFIX + SFNT;
    public static final String FONT_TTF_VALUE = FONT_TYPE_PREFIX + TTF;
    public static final String FONT_WOFF_VALUE = FONT_TYPE_PREFIX + WOFF;
    public static final String FONT_WOFF2_VALUE = FONT_TYPE_PREFIX + WOFF2;

    public static final String APPLICATION_FORM_DATA_VALUE = APPLICATION_TYPE_PREFIX + FORM_DATA;

    public static final String APPLICATION_OCTET_STREAM_VALUE = APPLICATION_TYPE_PREFIX + OCTET_STREAM;
    public static final String APPLICATION_XML_VALUE = APPLICATION_TYPE_PREFIX + XML;
    public static final String APPLICATION_XUL_VALUE = APPLICATION_TYPE_PREFIX + XUL;
    public static final String APPLICATION_PHP_VALUE = APPLICATION_TYPE_PREFIX + PHP;
    public static final String APPLICATION_PDF_VALUE = APPLICATION_TYPE_PREFIX + PDF;
    public static final String APPLICATION_ZIP_VALUE = APPLICATION_TYPE_PREFIX + ZIP;
    public static final String APPLICATION_7Z_VALUE = APPLICATION_TYPE_PREFIX + SEVEN_ZIP;
    public static final String APPLICATION_RAR_VALUE = APPLICATION_TYPE_PREFIX + RAR;
    public static final String APPLICATION_OGG_VALUE = APPLICATION_TYPE_PREFIX + OGG;
    public static final String APPLICATION_JSON_VALUE = APPLICATION_TYPE_PREFIX + JSON;
    public static final String APPLICATION_JSON_LD_VALUE = APPLICATION_TYPE_PREFIX + JSON_LD;
    public static final String APPLICATION_POSTSCRIPT_VALUE = APPLICATION_TYPE_PREFIX + POSTSCRIPT;
    public static final String APPLICATION_PROTOBUF_VALUE = APPLICATION_TYPE_PREFIX + PROTOBUF;
    public static final String APPLICATION_JWT_VALUE = APPLICATION_TYPE_PREFIX + JWT;
    public static final String APPLICATION_MBOX_VALUE = APPLICATION_TYPE_PREFIX + MBOX;
    public static final String APPLICATION_RTF_VALUE = APPLICATION_TYPE_PREFIX + RTF;
    public static final String APPLICATION_WASM_VALUE = APPLICATION_TYPE_PREFIX + WASM;
    public static final String APPLICATION_DART_VALUE = APPLICATION_TYPE_PREFIX + DART;

    public static final String APPLICATION_XHTML_VALUE = APPLICATION_TYPE_PREFIX + XHTML;
    public static final String APPLICATION_MHTML_VALUE = APPLICATION_TYPE_PREFIX + MHTML;

    public static final String APPLICATION_RDF_XML_VALUE = APPLICATION_TYPE_PREFIX + RDF_XML;
    public static final String APPLICATION_SOAP_XML_VALUE = APPLICATION_TYPE_PREFIX + SOAP_XML;
    public static final String APPLICATION_ATOM_XML_VALUE = APPLICATION_TYPE_PREFIX + ATOM_XML;
    public static final String APPLICATION_DASH_XML_VALUE = APPLICATION_TYPE_PREFIX + DASH_XML;

    public static final String APPLICATION_GEO_JSON_VALUE = APPLICATION_TYPE_PREFIX + GEO_JSON;
    public static final String APPLICATION_HAL_JSON_VALUE = APPLICATION_TYPE_PREFIX + HAL_JSON;
    public static final String APPLICATION_MANIFEST_JSON_VALUE = APPLICATION_TYPE_PREFIX + MANIFEST_JSON;

    public static final String APPLICATION_BZIP_VALUE = APPLICATION_TYPE_PREFIX + BZIP;
    public static final String APPLICATION_BZIP2_VALUE = APPLICATION_TYPE_PREFIX + BZIP2;
    public static final String APPLICATION_GZIP_VALUE = APPLICATION_TYPE_PREFIX + GZIP;
    public static final String APPLICATION_TAR_VALUE = APPLICATION_TYPE_PREFIX + TAR;
    public static final String APPLICATION_CSH_VALUE = APPLICATION_TYPE_PREFIX + CSH;
    public static final String APPLICATION_SH_VALUE = APPLICATION_TYPE_PREFIX + SH;
    public static final String APPLICATION_JAR_VALUE = APPLICATION_TYPE_PREFIX + JAR;
    public static final String APPLICATION_SHOCKWAVE_FLASH_VALUE = APPLICATION_TYPE_PREFIX + SHOCKWAVE_FLASH;

    public static final String APPLICATION_EPUB_VALUE = APPLICATION_TYPE_PREFIX + EPUB;
    public static final String APPLICATION_KEY_ARCHIVE_VALUE = APPLICATION_TYPE_PREFIX + KEY_ARCHIVE;
    public static final String APPLICATION_EOT_VALUE = APPLICATION_TYPE_PREFIX + EOT;
    public static final String APPLICATION_GOOGLE_EARTH_KML_VALUE = APPLICATION_TYPE_PREFIX + GOOGLE_EARTH_KML;
    public static final String APPLICATION_GOOGLE_EARTH_KMZ_VALUE = APPLICATION_TYPE_PREFIX + GOOGLE_EARTH_KMZ;
    public static final String APPLICATION_AMAZON_EBOOK_VALUE = APPLICATION_TYPE_PREFIX + AMAZON_EBOOK;
    public static final String APPLICATION_APPLE_INSTALLER_VALUE = APPLICATION_TYPE_PREFIX + APPLE_INSTALLER;
    public static final String APPLICATION_APPLE_PKPASS_VALUE = APPLICATION_TYPE_PREFIX + APPLE_PKPASS;
    public static final String APPLICATION_APPLE_KEYNOTE_VALUE = APPLICATION_TYPE_PREFIX + APPLE_KEYNOTE;
    public static final String APPLICATION_APPLE_MPEGURL_VALUE = APPLICATION_TYPE_PREFIX + APPLE_MPEGURL;
    public static final String APPLICATION_APPLE_NUMBERS_VALUE = APPLICATION_TYPE_PREFIX + APPLE_NUMBERS;
    public static final String APPLICATION_APPLE_PAGES_VALUE = APPLICATION_TYPE_PREFIX + APPLE_PAGES;

    public static final String APPLICATION_MS_WORD_VALUE = APPLICATION_TYPE_PREFIX + MS_WORD;
    public static final String APPLICATION_MS_EXCEL_VALUE = APPLICATION_TYPE_PREFIX + MS_EXCEL;
    public static final String APPLICATION_MS_POWERPOINT_VALUE = APPLICATION_TYPE_PREFIX + MS_POWERPOINT;
    public static final String APPLICATION_MS_ACCESS_VALUE = APPLICATION_TYPE_PREFIX + MS_ACCESS;
    public static final String APPLICATION_MS_VISIO_VALUE = APPLICATION_TYPE_PREFIX + MS_VISIO;
    public static final String APPLICATION_MS_OUTLOOK_VALUE = APPLICATION_TYPE_PREFIX + MS_OUTLOOK;

    public static final String APPLICATION_OOXML_DOCUMENT_VALUE = APPLICATION_TYPE_PREFIX + OOXML_DOCUMENT;
    public static final String APPLICATION_OOXML_DOCUMENT_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OOXML_DOCUMENT_TEMPLATE;
    public static final String APPLICATION_MS_WORD_MACRO_ENABLED_DOCUMENT_VALUE = APPLICATION_TYPE_PREFIX + MS_WORD_MACRO_ENABLED_DOCUMENT;
    public static final String APPLICATION_MS_WORD_MACRO_ENABLED_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + MS_WORD_MACRO_ENABLED_TEMPLATE;

    public static final String APPLICATION_OOXML_SHEET_VALUE = APPLICATION_TYPE_PREFIX + OOXML_SHEET;
    public static final String APPLICATION_OOXML_SHEET_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OOXML_SHEET_TEMPLATE;
    public static final String APPLICATION_MS_EXCEL_MACRO_ENABLED_SHEET_VALUE = APPLICATION_TYPE_PREFIX + MS_EXCEL_MACRO_ENABLED_SHEET;
    public static final String APPLICATION_MS_EXCEL_MACRO_ENABLED_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + MS_EXCEL_MACRO_ENABLED_TEMPLATE;
    public static final String APPLICATION_MS_EXCEL_MACRO_ENABLED_ADDIN_VALUE = APPLICATION_TYPE_PREFIX + MS_EXCEL_MACRO_ENABLED_ADDIN;
    public static final String APPLICATION_MS_EXCEL_MACRO_ENABLED_SHEET_BINARY_VALUE = APPLICATION_TYPE_PREFIX + MS_EXCEL_MACRO_ENABLED_SHEET_BINARY;


    public static final String APPLICATION_OOXML_PRESENTATION_VALUE = APPLICATION_TYPE_PREFIX + OOXML_PRESENTATION;
    public static final String APPLICATION_OOXML_PRESENTATION_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OOXML_PRESENTATION_TEMPLATE;
    public static final String APPLICATION_OOXML_PRESENTATION_SLIDESHOW_VALUE = APPLICATION_TYPE_PREFIX + OOXML_PRESENTATION_SLIDESHOW;
    public static final String APPLICATION_MS_POWERPOINT_MACRO_ENABLED_PRESENTATION_VALUE = APPLICATION_TYPE_PREFIX + MS_POWERPOINT_MACRO_ENABLED_PRESENTATION;
    public static final String APPLICATION_MS_POWERPOINT_MACRO_ENABLED_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + MS_POWERPOINT_MACRO_ENABLED_TEMPLATE;
    public static final String APPLICATION_MS_POWERPOINT_MACRO_ENABLED_ADDIN_VALUE = APPLICATION_TYPE_PREFIX + MS_POWERPOINT_MACRO_ENABLED_ADDIN;
    public static final String APPLICATION_MS_POWERPOINT_MACRO_ENABLED_SLIDESHOW_VALUE = APPLICATION_TYPE_PREFIX + MS_POWERPOINT_MACRO_ENABLED_SLIDESHOW;

    public static final String APPLICATION_OPENDOCUMENT_TEXT_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_TEXT;
    public static final String APPLICATION_OPENDOCUMENT_TEXT_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_TEXT_TEMPLATE;
    public static final String APPLICATION_OPENDOCUMENT_TEXT_WEB_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_TEXT_WEB;
    public static final String APPLICATION_OPENDOCUMENT_TEXT_MASTER_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_TEXT_MASTER;

    public static final String APPLICATION_OPENDOCUMENT_SPREADSHEET_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_SPREADSHEET;
    public static final String APPLICATION_OPENDOCUMENT_SPREADSHEET_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_SPREADSHEET_TEMPLATE;

    public static final String APPLICATION_OPENDOCUMENT_PRESENTATION_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_PRESENTATION;
    public static final String APPLICATION_OPENDOCUMENT_PRESENTATION_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_PRESENTATION_TEMPLATE;

    public static final String APPLICATION_OPENDOCUMENT_GRAPHICS_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_GRAPHICS;
    public static final String APPLICATION_OPENDOCUMENT_GRAPHICS_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_GRAPHICS_TEMPLATE;

    public static final String APPLICATION_OPENDOCUMENT_IMAGE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_IMAGE;
    public static final String APPLICATION_OPENDOCUMENT_IMAGE_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_IMAGE_TEMPLATE;

    public static final String APPLICATION_OPENDOCUMENT_FORMULA_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_FORMULA;
    public static final String APPLICATION_OPENDOCUMENT_FORMULA_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_FORMULA_TEMPLATE;

    public static final String APPLICATION_OPENDOCUMENT_CHART_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_CHART;
    public static final String APPLICATION_OPENDOCUMENT_CHART_TEMPLATE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_CHART_TEMPLATE;

    public static final String APPLICATION_OPENDOCUMENT_BASE_VALUE = APPLICATION_TYPE_PREFIX + OPENDOCUMENT_BASE;


    /**
     * internal cache
     */
    private static final Map<String, MediaType> LOCAL_CACHE = new LinkedHashMap<>();
    /**
     * Partial one-to-one mapping of file extension to {@link MediaType}
     */
    private static final Map<String, Set<MediaType>> FILE_EXTENSION_TO_MEDIA_TYPES_MAP = new HashMap<>();

    // =======================================================================
    //  Media Type constants
    // =======================================================================

    public static final MediaType TEXT_PLAIN = text(PLAIN, "txt", "text", "conf", "cnf", "in", "ini", "def", "list", "log");
    public static final MediaType TEXT_HTML = text(HTML, "html", "htm", "shtml");
    public static final MediaType TEXT_XML = text(XML, "xml");
    public static final MediaType TEXT_CSS = text(CSS, "css");
    public static final MediaType TEXT_CSV = text(CSV, "csv");
    public static final MediaType TEXT_JAVASCRIPT = text(JAVASCRIPT, "js", "mjs");
    public static final MediaType TEXT_MARKDOWN = text(MARKDOWN, "md", "markdown");
    public static final MediaType TEXT_VCARD = text(VCARD, "vcf", "vcard");
    public static final MediaType TEXT_CALENDAR = text(CALENDAR, "ics", "ifb");

    public static final MediaType IMAGE_BMP = image(BMP, "bmp", "dib");
    public static final MediaType IMAGE_JPEG = image(JPEG, "jpg", "jpeg");
    public static final MediaType IMAGE_GIF = image(GIF, "gif");
    public static final MediaType IMAGE_ICO = image(ICO, "ico");
    public static final MediaType IMAGE_PNG = image(PNG, "png");
    public static final MediaType IMAGE_APNG = image(APNG, "apng");
    public static final MediaType IMAGE_PSD = image(PSD, "psd");
    public static final MediaType IMAGE_SVG_XML = image(SVG_XML, "svg");
    public static final MediaType IMAGE_TIFF = image(TIFF, "tif");
    public static final MediaType IMAGE_WEBP = image(WEBP, "webp");
    public static final MediaType IMAGE_HEIF = image(HEIF, "heif");
    public static final MediaType IMAGE_HEIC = image(HEIC, "heic");
    public static final MediaType IMAGE_JP2K = image(JP2K, "jp2");
    public static final MediaType IMAGE_DWG = image(DWG, "dwg");
    public static final MediaType IMAGE_DXF = image(DXF, "dxf");

    public static final MediaType AUDIO_3GPP = audio(THREE_GPP, "3gp", "3gpp");
    public static final MediaType AUDIO_3GPP2 = audio(THREE_GPP2, "3g2", "3gpp2");
    public static final MediaType AUDIO_AAC = audio(AAC, "aac");
    public static final MediaType AUDIO_MP4 = audio(MP4, "m4a", "mp4");
    public static final MediaType AUDIO_MPEG = audio(MPEG, "mp3", "mp1", "mp2");
    public static final MediaType AUDIO_BASIC = audio(BASIC);
    public static final MediaType AUDIO_VORBIS = audio(VORBIS);
    public static final MediaType AUDIO_OGG = audio(OGG, "oga");
    public static final MediaType AUDIO_OPUS = audio(OPUS, "opus");
    public static final MediaType AUDIO_WEBM = audio(WEBM, "weba");
    public static final MediaType AUDIO_WMA = audio(WMA, "wma");
    public static final MediaType AUDIO_WAV = audio(WAV, "wav");
    public static final MediaType AUDIO_VND_WAVE = audio(VND_WAVE, "wav");
    public static final MediaType AUDIO_VND_REAL_AUDIO = audio(VND_REAL_AUDIO, "ra");
    public static final MediaType AUDIO_WAX = audio(WAX);

    public static final MediaType VIDEO_MP4 = video(MP4, "mp4", "mpg4");
    public static final MediaType VIDEO_MPEG = video(MPEG, "mpeg");
    public static final MediaType VIDEO_OGG = video(OGG, "ogv");
    public static final MediaType VIDEO_WEBM = video(WEBM, "webm");
    public static final MediaType VIDEO_QUICKTIME = video(QUICKTIME, "mov");
    public static final MediaType VIDEO_WMV = video(WMV, "wmv");
    public static final MediaType VIDEO_AVI = video(AVI, "avi");
    public static final MediaType VIDEO_TS = video(TS, "ts");
    public static final MediaType VIDEO_FLV = video(FLV, "flv");
    public static final MediaType VIDEO_THREE_GPP = video(THREE_GPP, "3gp");
    public static final MediaType VIDEO_THREE_GPP2 = video(THREE_GPP2, "3g2");

    public static final MediaType FONT_COLLECTION = font(COLLECTION, "ttc");
    public static final MediaType FONT_OTF = font(OTF, "otf");
    public static final MediaType FONT_SFNT = font(SFNT);
    public static final MediaType FONT_TTF = font(TTF, "ttf");
    public static final MediaType FONT_WOFF = font(WOFF, "woff");
    public static final MediaType FONT_WOFF2 = font(WOFF2, "woff2");

    public static final MediaType APPLICATION_FORM_DATA = application(FORM_DATA);

    public static final MediaType APPLICATION_OCTET_STREAM = application(OCTET_STREAM, "bin", "exe", "deb", "dll", "dmg", "iso", "so", "msi");
    public static final MediaType APPLICATION_XML = application(XML, "xml");
    public static final MediaType APPLICATION_XUL = application(XUL, "xul");
    public static final MediaType APPLICATION_PHP = application(PHP, "php");
    public static final MediaType APPLICATION_PDF = application(PDF, "pdf");
    public static final MediaType APPLICATION_ZIP = application(ZIP, "zip");
    public static final MediaType APPLICATION_7Z = application(SEVEN_ZIP, "7z");
    public static final MediaType APPLICATION_RAR = application(RAR, "rar");
    public static final MediaType APPLICATION_OGG = application(OGG, "ogx");
    public static final MediaType APPLICATION_JSON = application(JSON, "json");
    public static final MediaType APPLICATION_JSON_LD = application(JSON_LD, "jsonld");
    public static final MediaType APPLICATION_POSTSCRIPT = application(POSTSCRIPT, "ps");
    public static final MediaType APPLICATION_PROTOBUF = application(PROTOBUF);
    public static final MediaType APPLICATION_JWT = application(JWT);
    public static final MediaType APPLICATION_MBOX = application(MBOX, "mbox");
    public static final MediaType APPLICATION_RTF = application(RTF, "rtf");
    public static final MediaType APPLICATION_WASM = application(WASM, "wasm");
    public static final MediaType APPLICATION_DART = application(DART, "dart");

    public static final MediaType APPLICATION_XHTML = application(XHTML, "xhtml", "xht");
    public static final MediaType APPLICATION_MHTML = application(MHTML, "mhtml");

    public static final MediaType APPLICATION_RDF_XML = application(RDF_XML, "rdf", "owl");
    public static final MediaType APPLICATION_SOAP_XML = application(SOAP_XML);
    public static final MediaType APPLICATION_ATOM_XML = application(ATOM_XML, "atom");
    public static final MediaType APPLICATION_DASH_XML = application(DASH_XML, "mpd");

    public static final MediaType APPLICATION_GEO_JSON = application(GEO_JSON, "geojson");
    public static final MediaType APPLICATION_HAL_JSON = application(HAL_JSON, "json");
    public static final MediaType APPLICATION_MANIFEST_JSON = application(MANIFEST_JSON, "webmanifest");

    public static final MediaType APPLICATION_BZIP = application(BZIP, "bz");
    public static final MediaType APPLICATION_BZIP2 = application(BZIP2, "bz2", "boz");
    public static final MediaType APPLICATION_GZIP = application(GZIP, "gz");
    public static final MediaType APPLICATION_TAR = application(TAR, "tar");
    public static final MediaType APPLICATION_CSH = application(CSH, "csh");
    public static final MediaType APPLICATION_SH = application(SH, "sh");
    public static final MediaType APPLICATION_JAR = application(JAR, "jar");
    public static final MediaType APPLICATION_SHOCKWAVE_FLASH = application(SHOCKWAVE_FLASH, "swf");

    public static final MediaType APPLICATION_EPUB = application(EPUB, "epub");
    public static final MediaType APPLICATION_KEY_ARCHIVE = application(KEY_ARCHIVE, "p12", "pfx");
    public static final MediaType APPLICATION_EOT = application(EOT, "eot");
    public static final MediaType APPLICATION_GOOGLE_EARTH_KML = application(GOOGLE_EARTH_KML, "kml");
    public static final MediaType APPLICATION_GOOGLE_EARTH_KMZ = application(GOOGLE_EARTH_KMZ, "kmz");
    public static final MediaType APPLICATION_AMAZON_EBOOK = application(AMAZON_EBOOK, "azw");
    public static final MediaType APPLICATION_APPLE_INSTALLER = application(APPLE_INSTALLER, "mpkg");
    public static final MediaType APPLICATION_APPLE_PKPASS = application(APPLE_PKPASS, "pkpass");
    public static final MediaType APPLICATION_APPLE_KEYNOTE = application(APPLE_KEYNOTE, "key");
    public static final MediaType APPLICATION_APPLE_MPEGURL = application(APPLE_MPEGURL, "m3u8");
    public static final MediaType APPLICATION_APPLE_NUMBERS = application(APPLE_NUMBERS, "numbers");
    public static final MediaType APPLICATION_APPLE_PAGES = application(APPLE_PAGES, "pages");

    public static final MediaType APPLICATION_MS_WORD = application(MS_WORD, "doc", "dot");
    public static final MediaType APPLICATION_MS_EXCEL = application(MS_EXCEL, "xls", "xlt", "xla");
    public static final MediaType APPLICATION_MS_POWERPOINT = application(MS_POWERPOINT, "ppt", "pot", "pps", "ppa");
    public static final MediaType APPLICATION_MS_ACCESS = application(MS_ACCESS, "mdb");
    public static final MediaType APPLICATION_MS_VISIO = application(MS_VISIO, "vsd", "vst", "vsw", "vss");
    public static final MediaType APPLICATION_MS_OUTLOOK = application(MS_OUTLOOK, "msg");

    public static final MediaType APPLICATION_OOXML_DOCUMENT = application(OOXML_DOCUMENT, "docx");
    public static final MediaType APPLICATION_OOXML_DOCUMENT_TEMPLATE = application(OOXML_DOCUMENT_TEMPLATE, "dotx");
    public static final MediaType APPLICATION_MS_WORD_MACRO_ENABLED_DOCUMENT = application(MS_WORD_MACRO_ENABLED_DOCUMENT, "docm");
    public static final MediaType APPLICATION_MS_WORD_MACRO_ENABLED_TEMPLATE = application(MS_WORD_MACRO_ENABLED_TEMPLATE, "dotm");

    public static final MediaType APPLICATION_OOXML_SHEET = application(OOXML_SHEET, "xlsx");
    public static final MediaType APPLICATION_OOXML_SHEET_TEMPLATE = application(OOXML_SHEET_TEMPLATE, "xltx");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_SHEET = application(MS_EXCEL_MACRO_ENABLED_SHEET, "xlsm");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_TEMPLATE = application(MS_EXCEL_MACRO_ENABLED_TEMPLATE, "xltm");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_ADDIN = application(MS_EXCEL_MACRO_ENABLED_ADDIN, "xlam");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_SHEET_BINARY = application(MS_EXCEL_MACRO_ENABLED_SHEET_BINARY, "xlsb");

    public static final MediaType APPLICATION_OOXML_PRESENTATION = application(OOXML_PRESENTATION, "pptx");
    public static final MediaType APPLICATION_OOXML_PRESENTATION_TEMPLATE = application(OOXML_PRESENTATION_TEMPLATE, "potx");
    public static final MediaType APPLICATION_OOXML_PRESENTATION_SLIDESHOW = application(OOXML_PRESENTATION_SLIDESHOW, "ppsx");
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_PRESENTATION = application(MS_POWERPOINT_MACRO_ENABLED_PRESENTATION, "pptm");
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_TEMPLATE = application(MS_POWERPOINT_MACRO_ENABLED_TEMPLATE, "potm");
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_ADDIN = application(MS_POWERPOINT_MACRO_ENABLED_ADDIN, "ppam");
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_SLIDESHOW = application(MS_POWERPOINT_MACRO_ENABLED_SLIDESHOW, "ppsm");

    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT = application(OPENDOCUMENT_TEXT, "odt");
    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT_TEMPLATE = application(OPENDOCUMENT_TEXT_TEMPLATE, "ott");
    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT_WEB = application(OPENDOCUMENT_TEXT_WEB, "oth");
    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT_MASTER = application(OPENDOCUMENT_TEXT_MASTER, "odm");

    public static final MediaType APPLICATION_OPENDOCUMENT_SPREADSHEET = application(OPENDOCUMENT_SPREADSHEET, "ods");
    public static final MediaType APPLICATION_OPENDOCUMENT_SPREADSHEET_TEMPLATE = application(OPENDOCUMENT_SPREADSHEET_TEMPLATE, "ots");

    public static final MediaType APPLICATION_OPENDOCUMENT_PRESENTATION = application(OPENDOCUMENT_PRESENTATION, "odp");
    public static final MediaType APPLICATION_OPENDOCUMENT_PRESENTATION_TEMPLATE = application(OPENDOCUMENT_PRESENTATION_TEMPLATE, "otp");

    public static final MediaType APPLICATION_OPENDOCUMENT_GRAPHICS = application(OPENDOCUMENT_GRAPHICS, "odg");
    public static final MediaType APPLICATION_OPENDOCUMENT_GRAPHICS_TEMPLATE = application(OPENDOCUMENT_GRAPHICS_TEMPLATE, "otg");

    public static final MediaType APPLICATION_OPENDOCUMENT_IMAGE = application(OPENDOCUMENT_IMAGE, "odi");
    public static final MediaType APPLICATION_OPENDOCUMENT_IMAGE_TEMPLATE = application(OPENDOCUMENT_IMAGE_TEMPLATE, "oti");

    public static final MediaType APPLICATION_OPENDOCUMENT_FORMULA = application(OPENDOCUMENT_FORMULA, "odf");
    public static final MediaType APPLICATION_OPENDOCUMENT_FORMULA_TEMPLATE = application(OPENDOCUMENT_FORMULA_TEMPLATE, "otf");

    public static final MediaType APPLICATION_OPENDOCUMENT_CHART = application(OPENDOCUMENT_CHART, "odc");
    public static final MediaType APPLICATION_OPENDOCUMENT_CHART_TEMPLATE = application(OPENDOCUMENT_CHART_TEMPLATE, "otc");

    public static final MediaType APPLICATION_OPENDOCUMENT_BASE = application(OPENDOCUMENT_BASE, "odb");

    public static final MediaType APPLICATION_CEB = application(CEB, "ceb");
    public static final MediaType APPLICATION_CEBX = application(CEBX, "cebx");

    private final String type;
    private final String subtype;

    private final List<String> fileExtensionList;

    private MediaType(final String type, final String subtype, List<String> fileExtensionList) {
        this.type = type;
        this.subtype = subtype;
        this.fileExtensionList = fileExtensionList;
    }

    /**
     * 根据 类型、子类型、文件名后缀 创建媒体类型 {@link MediaType} 对象
     *
     * @param type           类型
     * @param subtype        子类型
     * @param fileExtensions 文件名后缀（变长参数）
     * @return 媒体类型 {@link MediaType} 对象
     */
    public static MediaType of(String type, String subtype, String... fileExtensions) {
        String fullType = StrUtil.format("{}/{}", type.toLowerCase(), subtype.toLowerCase());
        MediaType result = LOCAL_CACHE.get(fullType);
        if (!LOCAL_CACHE.containsKey(fullType)) {
            List<String> fileExtensionList = fileExtensions.length == 0
                    ? Collections.emptyList() : CollUtils.unmodifiableList(true, fileExtensions);
            result = new MediaType(type, subtype, fileExtensionList);
            LOCAL_CACHE.putIfAbsent(fullType, result);

            for (String fileExtension : fileExtensionList) {
                fileExtension = fileExtension.toLowerCase();
                if (FILE_EXTENSION_TO_MEDIA_TYPES_MAP.containsKey(fileExtension)) {
                    Set<MediaType> mediaTypes = FILE_EXTENSION_TO_MEDIA_TYPES_MAP.get(fileExtension);
                    mediaTypes.add(result);
                } else {
                    FILE_EXTENSION_TO_MEDIA_TYPES_MAP.put(fileExtension, CollUtil.newHashSet(result));
                }
            }
        }
        return result;
    }

    @Nullable
    public String getDefaultFileExtension() {
        return getFileExtensionList().isEmpty() ? null : getFileExtensionList().get(0);
    }

    public List<String> getFileExtensionList() {
        return fileExtensionList;
    }

    /**
     * 根据文件名后缀获取唯一对应的媒体类型 {@link MediaType} 对象
     *
     * @param fileExtension 文件名后缀
     * @return 媒体类型 {@link MediaType} 对象
     */
    @Nullable
    public static MediaType getOneByFileExtension(String fileExtension) {
        Set<MediaType> mediaTypes = FILE_EXTENSION_TO_MEDIA_TYPES_MAP.get(fileExtension.toLowerCase());
        if (mediaTypes != null && mediaTypes.size() == 1) {
            return CollUtil.getFirst(mediaTypes);
        } else {
            return null;
        }
    }

    public static MediaType application(String subType, String... fileExtensions) {
        return of(MEDIA_TYPE_APPLICATION, subType, fileExtensions);
    }

    public static MediaType text(String subType, String... fileExtensions) {
        return of(MEDIA_TYPE_TEXT, subType, fileExtensions);
    }

    public static MediaType image(String subType, String... fileExtensions) {
        return of(MEDIA_TYPE_IMAGE, subType, fileExtensions);
    }

    public static MediaType audio(String subType, String... fileExtensions) {
        return of(MEDIA_TYPE_AUDIO, subType, fileExtensions);
    }

    public static MediaType video(String subType, String... fileExtensions) {
        return of(MEDIA_TYPE_VIDEO, subType, fileExtensions);
    }

    public static MediaType font(String subType, String... fileExtensions) {
        return of(MEDIA_TYPE_FONT, subType, fileExtensions);
    }

    @Override
    public String toString() {
        return StrUtil.format("{}/{}", getType(), getSubtype());
    }

    /**
     * 媒体类型用到的字符串常量定义
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static class Value {
        /* Media Type constants */
        public static final String MEDIA_TYPE_TEXT = "text";
        public static final String MEDIA_TYPE_IMAGE = "image";
        public static final String MEDIA_TYPE_AUDIO = "audio";
        public static final String MEDIA_TYPE_VIDEO = "video";
        public static final String MEDIA_TYPE_FONT = "font";
        public static final String MEDIA_TYPE_APPLICATION = "application";

        /* Media Type prefixes */
        public static final String TEXT_TYPE_PREFIX = "text/";
        public static final String IMAGE_TYPE_PREFIX = "image/";
        public static final String AUDIO_TYPE_PREFIX = "audio/";
        public static final String VIDEO_TYPE_PREFIX = "video/";
        public static final String FONT_TYPE_PREFIX = "font/";
        public static final String APPLICATION_TYPE_PREFIX = "application/";


        /* text types */
        public static final String PLAIN = "plain";
        public static final String HTML = "html";
        public static final String CSS = "css";
        public static final String CSV = "csv";
        public static final String JAVASCRIPT = "javascript";
        public static final String MARKDOWN = "markdown";
        public static final String VCARD = "vcard";
        public static final String CALENDAR = "calendar";


        /* image types */
        public static final String BMP = "bmp";
        public static final String GIF = "gif";
        public static final String ICO = "vnd.microsoft.icon";
        public static final String JPEG = "jpeg";
        public static final String PNG = "png";
        public static final String APNG = "vnd.mozilla.apng";
        public static final String PSD = "vnd.adobe.photoshop";
        public static final String SVG_XML = "svg+xml";
        public static final String TIFF = "tiff";
        public static final String WEBP = "webp";
        public static final String HEIF = "heif";
        public static final String HEIC = "heic";
        public static final String JP2K = "jp2";
        public static final String DWG = "vnd.dwg";
        public static final String DXF = "vnd.dxf";


        /* audio & video types */
        public static final String MP4 = "mp4";
        public static final String MPEG = "mpeg";
        public static final String OGG = "ogg";
        public static final String OPUS = "opus";
        public static final String WEBM = "webm";
        public static final String THREE_GPP = "3gpp";
        public static final String THREE_GPP2 = "3gpp2";


        /* audio types */
        public static final String BASIC = "basic";
        public static final String WMA = "x-ms-wma";
        public static final String WAV = "wav";
        public static final String AAC = "aac";
        public static final String VORBIS = "vorbis";
        public static final String VND_WAVE = "vnd.wave";
        public static final String VND_REAL_AUDIO = "vnd.rn-realaudio";
        public static final String WAX = "x-ms-wax";


        /* video types */
        public static final String QUICKTIME = "quicktime";
        public static final String WMV = "x-ms-wmv";
        public static final String FLV = "x-flv";
        public static final String AVI = "x-msvideo";
        public static final String TS = "mp2t";



        /* font types */
        public static final String COLLECTION = "collection";
        public static final String OTF = "otf";
        public static final String SFNT = "sfnt";
        public static final String TTF = "ttf";
        public static final String WOFF = "woff";
        public static final String WOFF2 = "woff2";


        /* application types */
        public static final String FORM_DATA = "x-www-form-urlencoded";
        public static final String OCTET_STREAM = "octet-stream";
        public static final String XML = "xml";
        public static final String XUL = "vnd.mozilla.xul+xml";
        public static final String PHP = "x-httpd-php";
        public static final String PDF = "pdf";
        public static final String ZIP = "zip";
        public static final String SEVEN_ZIP = "x-7z-compressed";
        public static final String RAR = "vnd.rar";
        public static final String JSON = "json";
        public static final String JSON_LD = "ld+json";
        public static final String POSTSCRIPT = "postscript";
        public static final String PROTOBUF = "protobuf";
        public static final String JWT = "jwt";
        public static final String MBOX = "mbox";
        public static final String RTF = "rtf";
        public static final String WASM = "wasm";
        public static final String DART = "vnd.dart";

        public static final String XHTML = "xhtml+xml";
        public static final String MHTML = "x-mimearchive";

        public static final String RDF_XML = "rdf+xml";
        public static final String SOAP_XML = "soap+xml";
        public static final String ATOM_XML = "atom+xml";
        public static final String DASH_XML = "dash+xml";

        public static final String GEO_JSON = "geo+json";
        public static final String HAL_JSON = "hal+json";
        public static final String MANIFEST_JSON = "manifest+json";


        public static final String BZIP = "x-bzip";
        public static final String BZIP2 = "x-bzip2";
        public static final String GZIP = "x-gzip";
        public static final String TAR = "x-tar";
        public static final String CSH = "x-csh";
        public static final String SH = "x-sh";
        public static final String JAR = "java-archive";
        public static final String SHOCKWAVE_FLASH = "x-shockwave-flash";



        public static final String EPUB = "epub+zip";
        public static final String KEY_ARCHIVE = "pkcs12";
        public static final String EOT = "vnd.ms-fontobject";
        public static final String GOOGLE_EARTH_KML = "vnd.google-earth.kml+xml";
        public static final String GOOGLE_EARTH_KMZ = "vnd.google-earth.kmz";
        public static final String AMAZON_EBOOK = "vnd.amazon.ebook";
        public static final String APPLE_INSTALLER = "vnd.apple.installer+xml";
        public static final String APPLE_PKPASS = "vnd.apple.pkpass";
        public static final String APPLE_KEYNOTE = "vnd.apple.keynote";
        public static final String APPLE_MPEGURL = "vnd.apple.mpegurl";
        public static final String APPLE_NUMBERS = "vnd.apple.numbers";
        public static final String APPLE_PAGES = "vnd.apple.pages";


        public static final String MS_WORD = "msword";
        public static final String MS_EXCEL = "vnd.ms-excel";

        public static final String MS_POWERPOINT = "vnd.ms-powerpoint";
        public static final String MS_ACCESS = "vnd.ms-access";
        public static final String MS_OUTLOOK = "vnd.ms-outlook";
        public static final String MS_VISIO = "vnd-visio";

        public static final String OOXML_DOCUMENT = "vnd.openxmlformats-officedocument.wordprocessingml.document";
        public static final String OOXML_DOCUMENT_TEMPLATE = "vnd.openxmlformats-officedocument.wordprocessingml.template";
        public static final String MS_WORD_MACRO_ENABLED_DOCUMENT = "vnd.ms-word.document.macroEnabled.12";
        public static final String MS_WORD_MACRO_ENABLED_TEMPLATE = "vnd.ms-word.template.macroEnabled.12";

        public static final String OOXML_SHEET = "vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        public static final String OOXML_SHEET_TEMPLATE = "vnd.openxmlformats-officedocument.spreadsheetml.template";
        public static final String MS_EXCEL_MACRO_ENABLED_SHEET = "vnd.ms-excel.sheet.macroEnabled.12";
        public static final String MS_EXCEL_MACRO_ENABLED_TEMPLATE = "vnd.ms-excel.template.macroEnabled.12";
        public static final String MS_EXCEL_MACRO_ENABLED_ADDIN = "vnd.ms-excel.addin.macroEnabled.12";
        public static final String MS_EXCEL_MACRO_ENABLED_SHEET_BINARY = "vnd.ms-excel.sheet.binary.macroEnabled.12";

        public static final String OOXML_PRESENTATION = "vnd.openxmlformats-officedocument.presentationml.presentation";
        public static final String OOXML_PRESENTATION_TEMPLATE = "vnd.openxmlformats-officedocument.presentationml.template";
        public static final String OOXML_PRESENTATION_SLIDESHOW = "vnd.openxmlformats-officedocument.presentationml.slideshow";
        public static final String MS_POWERPOINT_MACRO_ENABLED_PRESENTATION = "vnd.ms-powerpoint.presentation.macroEnabled.12";
        public static final String MS_POWERPOINT_MACRO_ENABLED_TEMPLATE = "vnd.ms-powerpoint.template.macroEnabled.12";
        public static final String MS_POWERPOINT_MACRO_ENABLED_ADDIN = "vnd.ms-powerpoint.addin.macroEnabled.12";
        public static final String MS_POWERPOINT_MACRO_ENABLED_SLIDESHOW = "vnd.ms-powerpoint.slideshow.macroEnabled.12";

        public static final String OPENDOCUMENT_TEXT = "vnd.oasis.opendocument.text";
        public static final String OPENDOCUMENT_TEXT_TEMPLATE = "vnd.oasis.opendocument.text-template";
        public static final String OPENDOCUMENT_TEXT_WEB = "vnd.oasis.opendocument.text-web";
        public static final String OPENDOCUMENT_TEXT_MASTER = "vnd.oasis.opendocument.text-master";
        public static final String OPENDOCUMENT_SPREADSHEET = "vnd.oasis.opendocument.spreadsheet";
        public static final String OPENDOCUMENT_SPREADSHEET_TEMPLATE = "vnd.oasis.opendocument.spreadsheet-template";
        public static final String OPENDOCUMENT_PRESENTATION = "vnd.oasis.opendocument.presentation";
        public static final String OPENDOCUMENT_PRESENTATION_TEMPLATE = "vnd.oasis.opendocument.presentation-template";
        public static final String OPENDOCUMENT_GRAPHICS = "vnd.oasis.opendocument.graphics";
        public static final String OPENDOCUMENT_GRAPHICS_TEMPLATE = "vnd.oasis.opendocument.graphics-template";
        public static final String OPENDOCUMENT_IMAGE = "vnd.oasis.opendocument.image";
        public static final String OPENDOCUMENT_IMAGE_TEMPLATE = "vnd.oasis.opendocument.image-template";
        public static final String OPENDOCUMENT_FORMULA = "vnd.oasis.opendocument.formula";
        public static final String OPENDOCUMENT_FORMULA_TEMPLATE = "vnd.oasis.opendocument.formula-template";
        public static final String OPENDOCUMENT_CHART = "vnd.oasis.opendocument.chart";
        public static final String OPENDOCUMENT_CHART_TEMPLATE = "vnd.oasis.opendocument.chart-template";
        public static final String OPENDOCUMENT_BASE = "vnd.oasis.opendocument.base";


        /* unoffical custom types */
        public static final String CEB = "vnd.founder.ceb";
        public static final String CEBX = "vnd.founder.cebx";

        private Value() {}
    }
}
