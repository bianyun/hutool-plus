package plus.hutool.media.content.type;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import plus.hutool.core.lang.annotation.Nullable;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.core.lang.ObjectUtils;
import plus.hutool.core.text.string.StrUtils;
import plus.hutool.media.content.type.internal.MainMediaType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static plus.hutool.media.content.type.internal.SubMediaType.*;


/**
 * 媒体类型常量
 *
 * @author bianyun
 * @date 2022/12/9
 */
@SuppressWarnings({"unused", "checkstyle:LineLength", "SpellCheckingInspection", "JavadocDeclaration"})
@Data
public class MediaType {
    /**
     * internal cache of mapping from fullTypeStr to {@link MediaType}
     */
    private static final Map<String, MediaType> LOCAL_CACHE = new LinkedHashMap<>();
    /**
     * Partial one-to-one mapping of file extension to {@link MediaType}
     */
    private static final Map<String, Set<MediaType>> FILE_EXTENSION_TO_MEDIA_TYPES_MAP = new HashMap<>();


    // =======================================================================
    //  Media Type Constants - TEXT
    // =======================================================================

    public static final MediaType TEXT_PLAIN = text(PLAIN, "txt", "text", "conf", "in", "def", "list", "log");
    public static final MediaType TEXT_HTML = text(HTML, "html", "htm", "shtml");
    public static final MediaType TEXT_XML = text(XML, "xml");
    public static final MediaType TEXT_CSS = text(CSS, "css");
    public static final MediaType TEXT_CSV = text(CSV, "csv");
    public static final MediaType TEXT_JAVASCRIPT = text(JAVASCRIPT, "js", "mjs");
    public static final MediaType TEXT_MARKDOWN = text(MARKDOWN, "md", "markdown");
    public static final MediaType TEXT_VCARD = text(VCARD, "vcf", "vcard");
    public static final MediaType TEXT_CALENDAR = text(CALENDAR, "ics", "ifb");
    public static final MediaType TEXT_C_SOURCE = text(C_SOURCE, "c");
    public static final MediaType TEXT_C_HEADER = text(C_HEADER, "h");
    public static final MediaType TEXT_CNF = text(CNF, "cnf");
    public static final MediaType TEXT_CPP_SOURCE = text(CPP_SOURCE, "cpp");
    public static final MediaType TEXT_INI = text(INI, "ini");
    public static final MediaType TEXT_JAVA_SOURCE = text(JAVA_SOURCE, "java");
    public static final MediaType TEXT_KOTLIN_SOURCE = text(KOTLIN_SOURCE, "kt");
    public static final MediaType TEXT_PYTHON = text(PYTHON, "py");



    // =======================================================================
    //  Media Type Constants - IMAGE
    // =======================================================================

    public static final MediaType IMAGE_BMP = image(BMP, "bmp");
    public static final MediaType IMAGE_GIF = image(GIF, "gif");
    public static final MediaType IMAGE_ICO = image(ICO, "ico");
    public static final MediaType IMAGE_IFF = image(IFF, "iff");
    public static final MediaType IMAGE_JP2 = image(JP2, "jp2", "jpg2");
    public static final MediaType IMAGE_JPEG = image(JPEG, "jpg", "jpeg");
    public static final MediaType IMAGE_PCX = image(PCX, "pcx");
    public static final MediaType IMAGE_PNG = image(PNG, "png");
    public static final MediaType IMAGE_PSD = image(PSD, "psd");
    public static final MediaType IMAGE_RAS = image(RAS, "ras");
    public static final MediaType IMAGE_RSB = image(RSB, "rsb");
    public static final MediaType IMAGE_SGI = image(SGI, "sgi");
    public static final MediaType IMAGE_TGA = image(TGA, "tga");
    public static final MediaType IMAGE_TIFF = image(TIFF, "tif", "tiff");
    public static final MediaType IMAGE_WBMP = image(WBMP, "wbmp");

    public static final MediaType IMAGE_APNG = image(APNG, "apng");
    public static final MediaType IMAGE_SVG_XML = image(SVG_XML, "svg");
    public static final MediaType IMAGE_WEBP = image(WEBP, "webp");
    public static final MediaType IMAGE_HEIF = image(HEIF, "heif");
    public static final MediaType IMAGE_HEIC = image(HEIC, "heic");
    public static final MediaType IMAGE_DWG = image(DWG, "dwg");
    public static final MediaType IMAGE_DXF = image(DXF, "dxf");


    // =======================================================================
    //  Media Type Constants - AUDO
    // =======================================================================

    public static final MediaType AUDIO_BASIC = audio(BASIC, "au");
    public static final MediaType AUDIO_AAC = audio(AAC, "aac");
    public static final MediaType AUDIO_AC3 = audio(AC3, "ac3");
    public static final MediaType AUDIO_AIFF = audio(AIFF, "aiff", "aif", "aifc");
    public static final MediaType AUDIO_AMR = audio(AMR, "amr");
    public static final MediaType AUDIO_APE = audio(APE, "ape");
    public static final MediaType AUDIO_FLAC = audio(FLAC, "flac");
    public static final MediaType AUDIO_MKA = audio(MATROSKA, "mka");
    public static final MediaType AUDIO_MP4 = audio(MP4, "m4a", "m4b", "m4r", "m4p");
    public static final MediaType AUDIO_MPEG = audio(MPEG, "mp3", "mp1", "mp2");
    public static final MediaType AUDIO_OGG = audio(OGG, "ogg", "oga", "spx", "opus");
    public static final MediaType AUDIO_VORBIS = audio("vorbis");
    public static final MediaType AUDIO_WEBM = audio(WEBM, "webm");
    public static final MediaType AUDIO_WMA = audio(WMA, "wma");
    public static final MediaType AUDIO_WAV = audio(WAV, "wav");
    public static final MediaType AUDIO_RA = audio(REAL_AUDIO_RA, "ra");
    public static final MediaType AUDIO_RAM = audio(REAL_AUDIO_RAM, "ram");
    public static final MediaType AUDIO_WAX = audio(WAX);


    // =======================================================================
    //  Media Type Constants - VIDEO
    // =======================================================================

    public static final MediaType VIDEO_3GPP = video(THREE_GPP, "3gp", "3gpp");
    public static final MediaType VIDEO_3GP = VIDEO_3GPP;
    public static final MediaType VIDEO_3GPP2 = video(THREE_GPP2, "3g2", "3gpp2");
    public static final MediaType VIDEO_3G2 = VIDEO_3GPP2;
    public static final MediaType VIDEO_ASF = video(ASF, "asf");
    public static final MediaType VIDEO_AVI = video(AVI, "avi");
    public static final MediaType VIDEO_F4V = video(F4V, "f4v");
    public static final MediaType VIDEO_FLV = video(FLV, "flv");
    public static final MediaType VIDEO_M4V = video(M4V, "m4v");
    public static final MediaType VIDEO_MKV = video(MATROSKA, "mkv");
    public static final MediaType VIDEO_MP4 = video(MP4, "mp4", "mpg4");
    public static final MediaType VIDEO_MPEG = video(MPEG, "mpg");
    public static final MediaType VIDEO_OGG = video(OGG, "ogv");
    public static final MediaType VIDEO_WEBM = video(WEBM, "webm");
    public static final MediaType VIDEO_QUICKTIME = video(QUICKTIME, "mov");
    public static final MediaType VIDEO_MOV = VIDEO_QUICKTIME;
    public static final MediaType VIDEO_WMV = video(WMV, "wmv");
    public static final MediaType VIDEO_TS = video(TS, "ts");


    // =======================================================================
    //  Media Type Constants - FONT
    // =======================================================================

    public static final MediaType FONT_COLLECTION = font(COLLECTION, "ttc");
    public static final MediaType FONT_TTC = FONT_COLLECTION;
    public static final MediaType FONT_OTF = font(OTF, "otf");
    public static final MediaType FONT_SFNT = font(SFNT);
    public static final MediaType FONT_TTF = font(TTF, "ttf");
    public static final MediaType FONT_WOFF = font(WOFF, "woff");
    public static final MediaType FONT_WOFF2 = font(WOFF2, "woff2");


    // =======================================================================
    //  Media Type Constants - MULTIPART
    // =======================================================================

    public static final MediaType MULTIPART_RELATED = multipart(RELATED);


    // =======================================================================
    //  Media Type Constants - APPLICATION
    // =======================================================================

    public static final MediaType APPLICATION_FORM_DATA = application(FORM_DATA);

    public static final MediaType APPLICATION_OCTET_STREAM = application(OCTET_STREAM, "bin", "exe", "deb", "dll", "dmg", "iso", "so", "msi");
    public static final MediaType APPLICATION_XML = application(XML, "xml");
    public static final MediaType APPLICATION_MXF = application(MXF, "mxf");
    public static final MediaType APPLICATION_RM = application(REAL_VIDEO_RM, "rm");
    public static final MediaType APPLICATION_RMVB = application(REAL_VIDEO_RMVB, "rmvb");
    public static final MediaType APPLICATION_SQL = application(SQL, "sql");
    public static final MediaType APPLICATION_CSH = application(CSH, "csh");
    public static final MediaType APPLICATION_SH = application(SH, "sh");
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
    public static final MediaType APPLICATION_MHTML = application(MHTML, "mhtml", "mht");

    public static final MediaType APPLICATION_RDF_XML = application(RDF_XML, "rdf", "owl");
    public static final MediaType APPLICATION_SOAP_XML = application(SOAP_XML);
    public static final MediaType APPLICATION_ATOM_XML = application(ATOM_XML, "atom");
    public static final MediaType APPLICATION_DASH_XML = application(DASH_XML, "mpd");

    public static final MediaType APPLICATION_GEO_JSON = application(GEO_JSON, "geojson");
    public static final MediaType APPLICATION_HAL_JSON = application(HAL_JSON, "json");
    public static final MediaType APPLICATION_MANIFEST_JSON = application(MANIFEST_JSON, "webmanifest");

    public static final MediaType APPLICATION_BZIP = application(BZIP, "bz");
    public static final MediaType APPLICATION_BZIP2 = application(BZIP2, "bz2", "boz");
    public static final MediaType APPLICATION_GZIP = application(GZIP, "gz", "tgz");
    public static final MediaType APPLICATION_XZ = application(XZ, "xz");
    public static final MediaType APPLICATION_TAR = application(TAR, "tar");

    public static final MediaType APPLICATION_JAR = application(JAR, "jar");
    public static final MediaType APPLICATION_SHOCKWAVE_FLASH = application(SHOCKWAVE_FLASH, "swf");
    public static final MediaType APPLICATION_SWF = APPLICATION_SHOCKWAVE_FLASH;

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

    public static final MediaType APPLICATION_MS_WORD = application(MS_WORD, "doc", "dot", "wps");
    public static final MediaType APPLICATION_DOC = APPLICATION_MS_WORD;
    public static final MediaType APPLICATION_WPS = APPLICATION_MS_WORD;
    public static final MediaType APPLICATION_MS_EXCEL = application(MS_EXCEL, "xls", "xlt", "xla");
    public static final MediaType APPLICATION_XLS = APPLICATION_MS_EXCEL;
    public static final MediaType APPLICATION_MS_POWERPOINT = application(MS_POWERPOINT, "ppt", "pot", "pps", "ppa");
    public static final MediaType APPLICATION_PPT = APPLICATION_MS_POWERPOINT;
    public static final MediaType APPLICATION_PPS = APPLICATION_MS_POWERPOINT;
    public static final MediaType APPLICATION_MS_ACCESS = application(MS_ACCESS, "mdb");
    public static final MediaType APPLICATION_MS_VISIO = application(MS_VISIO, "vsd", "vst", "vsw", "vss");
    public static final MediaType APPLICATION_MS_OUTLOOK = application(MS_OUTLOOK, "msg");

    public static final MediaType APPLICATION_OOXML_DOCUMENT = application(OOXML_DOCUMENT, "docx");
    public static final MediaType APPLICATION_DOCX = APPLICATION_OOXML_DOCUMENT;
    public static final MediaType APPLICATION_OOXML_DOCUMENT_TEMPLATE = application(OOXML_DOCUMENT_TEMPLATE, "dotx");
    public static final MediaType APPLICATION_MS_WORD_MACRO_ENABLED_DOCUMENT = application(MS_WORD_MACRO_ENABLED_DOCUMENT, "docm");
    public static final MediaType APPLICATION_MS_WORD_MACRO_ENABLED_TEMPLATE = application(MS_WORD_MACRO_ENABLED_TEMPLATE, "dotm");

    public static final MediaType APPLICATION_OOXML_SHEET = application(OOXML_SHEET, "xlsx");
    public static final MediaType APPLICATION_XLSX = APPLICATION_OOXML_SHEET;
    public static final MediaType APPLICATION_OOXML_SHEET_TEMPLATE = application(OOXML_SHEET_TEMPLATE, "xltx");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_SHEET = application(MS_EXCEL_MACRO_ENABLED_SHEET, "xlsm");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_TEMPLATE = application(MS_EXCEL_MACRO_ENABLED_TEMPLATE, "xltm");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_ADDIN = application(MS_EXCEL_MACRO_ENABLED_ADDIN, "xlam");
    public static final MediaType APPLICATION_MS_EXCEL_MACRO_ENABLED_SHEET_BINARY = application(MS_EXCEL_MACRO_ENABLED_SHEET_BINARY, "xlsb");

    public static final MediaType APPLICATION_OOXML_PRESENTATION = application(OOXML_PRESENTATION, "pptx");
    public static final MediaType APPLICATION_PPTX = APPLICATION_OOXML_PRESENTATION;
    public static final MediaType APPLICATION_OOXML_PRESENTATION_TEMPLATE = application(OOXML_PRESENTATION_TEMPLATE, "potx");
    public static final MediaType APPLICATION_OOXML_PRESENTATION_SLIDESHOW = application(OOXML_PRESENTATION_SLIDESHOW, "ppsx");
    public static final MediaType APPLICATION_PPSX = APPLICATION_OOXML_PRESENTATION_SLIDESHOW;
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_PRESENTATION = application(MS_POWERPOINT_MACRO_ENABLED_PRESENTATION, "pptm");
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_TEMPLATE = application(MS_POWERPOINT_MACRO_ENABLED_TEMPLATE, "potm");
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_ADDIN = application(MS_POWERPOINT_MACRO_ENABLED_ADDIN, "ppam");
    public static final MediaType APPLICATION_MS_POWERPOINT_MACRO_ENABLED_SLIDESHOW = application(MS_POWERPOINT_MACRO_ENABLED_SLIDESHOW, "ppsm");

    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT = application(OPENDOCUMENT_TEXT, "odt");
    public static final MediaType APPLICATION_ODT = APPLICATION_OPENDOCUMENT_TEXT;
    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT_TEMPLATE = application(OPENDOCUMENT_TEXT_TEMPLATE, "ott");
    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT_WEB = application(OPENDOCUMENT_TEXT_WEB, "oth");
    public static final MediaType APPLICATION_OPENDOCUMENT_TEXT_MASTER = application(OPENDOCUMENT_TEXT_MASTER, "odm");

    public static final MediaType APPLICATION_OPENDOCUMENT_SPREADSHEET = application(OPENDOCUMENT_SPREADSHEET, "ods");
    public static final MediaType APPLICATION_ODS = APPLICATION_OPENDOCUMENT_SPREADSHEET;
    public static final MediaType APPLICATION_OPENDOCUMENT_SPREADSHEET_TEMPLATE = application(OPENDOCUMENT_SPREADSHEET_TEMPLATE, "ots");

    public static final MediaType APPLICATION_OPENDOCUMENT_PRESENTATION = application(OPENDOCUMENT_PRESENTATION, "odp");
    public static final MediaType APPLICATION_ODP = APPLICATION_OPENDOCUMENT_PRESENTATION;
    public static final MediaType APPLICATION_OPENDOCUMENT_PRESENTATION_TEMPLATE = application(OPENDOCUMENT_PRESENTATION_TEMPLATE, "otp");

    public static final MediaType APPLICATION_OPENDOCUMENT_GRAPHICS = application(OPENDOCUMENT_GRAPHICS, "odg");
    public static final MediaType APPLICATION_ODG = APPLICATION_OPENDOCUMENT_GRAPHICS;
    public static final MediaType APPLICATION_OPENDOCUMENT_GRAPHICS_TEMPLATE = application(OPENDOCUMENT_GRAPHICS_TEMPLATE, "otg");

    public static final MediaType APPLICATION_OPENDOCUMENT_IMAGE = application(OPENDOCUMENT_IMAGE, "odi");
    public static final MediaType APPLICATION_ODI = APPLICATION_OPENDOCUMENT_IMAGE;
    public static final MediaType APPLICATION_OPENDOCUMENT_IMAGE_TEMPLATE = application(OPENDOCUMENT_IMAGE_TEMPLATE, "oti");

    public static final MediaType APPLICATION_OPENDOCUMENT_FORMULA = application(OPENDOCUMENT_FORMULA, "odf");
    public static final MediaType APPLICATION_ODF = APPLICATION_OPENDOCUMENT_FORMULA;
    public static final MediaType APPLICATION_OPENDOCUMENT_FORMULA_TEMPLATE = application(OPENDOCUMENT_FORMULA_TEMPLATE, "otf");

    public static final MediaType APPLICATION_OPENDOCUMENT_CHART = application(OPENDOCUMENT_CHART, "odc");
    public static final MediaType APPLICATION_ODC = APPLICATION_OPENDOCUMENT_CHART;
    public static final MediaType APPLICATION_OPENDOCUMENT_CHART_TEMPLATE = application(OPENDOCUMENT_CHART_TEMPLATE, "otc");

    public static final MediaType APPLICATION_OPENDOCUMENT_BASE = application(OPENDOCUMENT_BASE, "odb");
    public static final MediaType APPLICATION_ODB = APPLICATION_OPENDOCUMENT_BASE;
    public static final MediaType APPLICATION_CEB = application(CEB, "ceb");
    public static final MediaType APPLICATION_CEBX = application(CEBX, "cebx");
    public static final MediaType APPLICATION_OFD = application(OFD, "ofd");

    private final MainMediaType mainType;
    private final String mainTypeStr;
    private final String subTypeStr;

    private final List<String> fileExtensionList;

    private MediaType(final MainMediaType mainType, final String subtype, List<String> fileExtensionList) {
        this.mainType = mainType;
        this.mainTypeStr = mainType.strValue();
        this.subTypeStr = subtype;
        this.fileExtensionList = fileExtensionList;
    }

    /**
     * 根据 媒体类型主类型 和 媒体类型子类型 以及文件名后缀获取对应的 {@link MediaType} 对象
     *
     * @param mainType 媒体类型主类型
     * @param subtype 媒体类型子类型
     * @param fileExtensions 文件名后缀
     * @return {@link MediaType} 对象
     */
    static MediaType of(MainMediaType mainType, String subtype, String... fileExtensions) {
        String fullType = StrUtil.format("{}/{}", mainType.strValue(), subtype.toLowerCase());
        MediaType result = LOCAL_CACHE.get(fullType);
        if (!LOCAL_CACHE.containsKey(fullType)) {
            List<String> fileExtensionList = fileExtensions.length == 0
                    ? Collections.emptyList() : CollUtils.unmodifiableList(true, fileExtensions);
            result = new MediaType(mainType, subtype, fileExtensionList);
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

    public String strValue() {
        return toString();
    }

    @Nullable
    public String getDefaultFileExtension() {
        return getFileExtensionList().isEmpty() ? null : getFileExtensionList().get(0);
    }

    public List<String> getFileExtensionList() {
        return fileExtensionList;
    }

    public boolean containsFileExtension(String fileExtension) {
        return fileExtensionList.contains(fileExtension.toLowerCase());
    }

    public boolean isAnyOf(MediaType first, MediaType... remainings) {
        return ObjectUtils.isAnyOf(this, first, remainings);
    }

    public boolean isAnyOf(Collection<MediaType> mediaTypes) {
        return mediaTypes.contains(this);
    }

    public boolean notAnyOf(MediaType first, MediaType... remainings) {
        return !ObjectUtils.isAnyOf(this, first, remainings);
    }

    /**
     * 根据文件名后缀获取对应的唯一 {@link MediaType} 对象
     *
     * @param fileExtension 文件名后缀
     * @return {@link MediaType} 对象
     */
    @Nullable
    public static MediaType getOneByFileExtension(String fileExtension) {
        String fileExtWithoutLeadingDot = StrUtil.removePrefix(fileExtension.toLowerCase(), StrUtils.DOT);

        Set<MediaType> mediaTypes = FILE_EXTENSION_TO_MEDIA_TYPES_MAP.get(fileExtWithoutLeadingDot);
        if (mediaTypes != null && mediaTypes.size() == 1) {
            return CollUtil.getFirst(mediaTypes);
        } else {
            return null;
        }
    }

    public static MediaType application(String subType, String... fileExtensions) {
        return of(MainMediaType.APPLICATION, subType, fileExtensions);
    }

    public static MediaType text(String subType, String... fileExtensions) {
        return of(MainMediaType.TEXT, subType, fileExtensions);
    }

    public static MediaType image(String subType, String... fileExtensions) {
        return of(MainMediaType.IMAGE, subType, fileExtensions);
    }

    public static MediaType audio(String subType, String... fileExtensions) {
        return of(MainMediaType.AUDIO, subType, fileExtensions);
    }

    public static MediaType video(String subType, String... fileExtensions) {
        return of(MainMediaType.VIDEO, subType, fileExtensions);
    }

    public static MediaType font(String subType, String... fileExtensions) {
        return of(MainMediaType.FONT, subType, fileExtensions);
    }

    public static MediaType multipart(String subType, String... fileExtensions) {
        return of(MainMediaType.MULTIPART, subType, fileExtensions);
    }

    @Override
    public String toString() {
        return StrUtil.format("{}/{}", getMainTypeStr(), getSubTypeStr());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaType)) {
            return false;
        }
        MediaType mediaType = (MediaType) o;
        return mainTypeStr.equals(mediaType.mainTypeStr) && subTypeStr.equals(mediaType.subTypeStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainTypeStr, subTypeStr);
    }
}
