package plus.hutool.core.text.string;


import cn.hutool.core.util.CharUtil;

/**
 * 常见的不可见字符串常量
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
interface InvisibleStrs {

    // -------------------------------------------------------------------------------------------------------
    // The following characters are defined as whitespace (White_Space=yes) in the Unicode Character Database.
    // -------------------------------------------------------------------------------------------------------

    /**
     * Empty string
     */
    String EMPTY = "";

    /**
     * (U+0009) Horizontal Tab, Character tabulation
     */
    @SuppressWarnings("SpellCheckingInspection")
    String HTAB = "\t";

    /**
     * (U+000A) Line feed
     */
    String LF = "\n";

    /**
     * (U+000B) Vertical Tab, Line tabulation
     */
    String VTAB = "\u000B";

    /**
     * (U+000C) Form feed
     */
    String FF = "\f";

    /**
     * (U+000D) Carriage return
     */
    String CR = "\r";

    /**
     * (U+0020) Normal ASCII space
     */
    String SPACE = " ";

    /**
     * (U+3000) Ideographic space, as wide as a CJK character cell
     */
    String FULL_WIDTH_SPACE = "　";


    // ----------------------------------------------------------------------------------------------------------
    // The following characters are defined as NOT whitespace (White_Space=no) in the Unicode Character Database.
    // ----------------------------------------------------------------------------------------------------------

    /**
     * (U+0000) Null char
     */
    String NUL = CharUtil.toString(Character.MIN_VALUE);

    /**
     * (U+FEFF) Aka: Zero Width No-Break Space
     */
    @SuppressWarnings("SpellCheckingInspection")
    String BYTE_ORDER_MARK = "\uFEFF";

    /**
     * (U+202A) Left-To-Right Embedding
     */
    String LTR_EMBED = "\u202A";

    /**
     * Indent with four consecutive spaces
     */
    String INDENT_WITH_FOUR_SPACE = "    ";
}
