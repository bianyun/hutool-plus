package plus.hutool.extra.json;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import plus.hutool.core.text.string.StrUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class JsonUtils {

    private static final String PRETTY_OBJECT_FIELD_VALUE_SEPARATOR = ": ";
    private static final DefaultPrettyPrinter CUSTOMIZED_PRETTY_PRINTER = new CustomizedPrettyPrinter();

    private static final ObjectMapper DEFAULT_MAPPER = JsonMapper.builder()
            .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .findAndAddModules()
            .build();

    private JsonUtils() {}

    public static ObjectMapper getDefaultJacksonObjectMapper() {
        return DEFAULT_MAPPER;
    }

    /**
     * 将对象转换为 JSON字符串（单行）
     *
     * <p>
     * 如果对象 为字符序列的子类对象（比如：String, StringBuilder, StringBuffer），则直接返回对应的字符串；
     * 如果是其它类型的对象，则调用默认的 Jackson ObjectMapper 进行序列化
     * </p>
     *
     * @param obj 待转换的对象
     * @return 转换后的字符串
     */
    public static String toJsonStr(Object obj) {
        return toJsonStr(getDefaultJacksonObjectMapper(), obj);
    }

    /**
     * 将对象转换为 JSON字符串（单行）
     *
     * <p>
     * 如果对象 为字符序列的子类对象（比如：String, StringBuilder, StringBuffer），则直接返回对应的字符串；
     * 如果是其它类型的对象，则调用指定的 <code>Jackson</code> {@link ObjectMapper} 进行序列化
     * </p>
     *
     * @param mapper <code>Jackson</code> {@link ObjectMapper} 对象
     * @param obj    待转换的对象
     * @return 转换后的字符串
     */
    public static String toJsonStr(ObjectMapper mapper, Object obj) {
        if (obj instanceof CharSequence) {
            return StrUtil.str((CharSequence) obj);
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 用自定义格式化器将对象转换为格式美化后的 JSON字符串
     *
     * <p>
     * 如果对象 为字符序列的子类对象（比如：String, StringBuilder, StringBuffer），则直接返回对应的字符串；
     * 如果是其它类型的对象，则调用默认的 <code>Jackson</code> {@link ObjectMapper} 进行序列化
     * </p>
     *
     * @param obj 待转换的对象
     * @return 转换后的字符串
     */
    public static String toPrettyJsonStr(Object obj) {
        return toPrettyJsonStr(getDefaultJacksonObjectMapper(), obj);
    }

    /**
     * 用自定义格式化器将对象转换为格式美化后的 JSON字符串
     *
     * <p>
     * 如果对象 为字符序列的子类对象（比如：String, StringBuilder, StringBuffer），则直接返回对应的字符串；
     * 如果是其它类型的对象，则调用指定的 <code>Jackson</code> {@link ObjectMapper} 进行序列化
     * </p>
     *
     * @param mapper <code>Jackson</code> {@link ObjectMapper} 对象
     * @param obj    待转换的对象
     * @return 转换后的字符串
     */
    public static String toPrettyJsonStr(ObjectMapper mapper, Object obj) {
        if (obj instanceof CharSequence) {
            return StrUtil.str((CharSequence) obj);
        }

        try {
            ObjectWriter writer = mapper.writer(CUSTOMIZED_PRETTY_PRINTER);
            return writer.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 将 JSON 字符串 转换为 <code>Map&lt;String, String&gt;</code> 对象
     *
     * @param jsonStr JSON 字符串
     * @return <code>Map&lt;String, String&gt;</code> 对象
     */
    private static Map<String, String> convertJsonStrToMap(String jsonStr) {
        JSONObject jsonObj = JSONUtil.parseObj(jsonStr);

        Map<String, String> resultMap = MapUtil.newHashMap();
        jsonObj.forEach((key, value) -> resultMap.put(key, value instanceof JSONNull ? null : value.toString()));
        return resultMap;
    }

    /**
     * 将 <code>Hutool</code> {@link JSONObject} 对象 转换为 <code>Map&lt;String, String&gt;</code> 对象
     *
     * @param jsonObject <code>Hutool</code> {@link JSONObject} 对象
     * @return <code>Map&lt;String, String&gt;</code> 对象
     */
    public static Map<String, String> convertHutoolJsonObjectToMap(JSONObject jsonObject) {
        return convertJsonStrToMap(jsonObject.toString());
    }

    /**
     * 将 <code>Jackson</code> {@link ObjectNode} 对象 转换为 <code>Map&lt;String, String&gt;</code> 对象
     *
     * @param objectNode <code>Jackson</code> {@link ObjectNode} 对象
     * @return <code>Map&lt;String, String&gt;</code> 对象
     */
    public static Map<String, String> convertObjectNodeToMap(ObjectNode objectNode) {
        return convertJsonStrToMap(objectNode.toString());
    }

    /**
     * JSON字符串转为实体类对象，转换异常将被抛出
     *
     * @param jsonString JSON字符串
     * @param beanClass  实体类对象
     * @param <T>        Bean类型
     * @return 实体类对象
     */
    public static <T> T toBean(String jsonString, Class<T> beanClass) {
        return toBean(getDefaultJacksonObjectMapper(), jsonString, beanClass);
    }

    /**
     * JSON 字符串转为实体类对象，转换异常将被抛出
     *
     * @param mapper     <code>Jackson</code> {@link ObjectMapper} 对象
     * @param jsonString JSON字符串
     * @param beanClass  实体类对象
     * @param <T>        Bean类型
     * @return 实体类对象
     */
    public static <T> T toBean(ObjectMapper mapper, String jsonString, Class<T> beanClass) {
        try {
            return mapper.readValue(jsonString, beanClass);
        } catch (JsonProcessingException e) {
            throw new IORuntimeException(e);
        }
    }

    static class CustomizedPrettyPrinter extends DefaultPrettyPrinter {
        private static final long serialVersionUID = 4577397349940273020L;

        public CustomizedPrettyPrinter() {
            super();
            DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter(StrUtils.INDENT_WITH_FOUR_SPACE, StrUtils.LF);
            this.indentObjectsWith(indenter);
            this.indentArraysWith(indenter);
        }

        @Override
        public void writeObjectFieldValueSeparator(JsonGenerator g) throws IOException {
            g.writeRaw(PRETTY_OBJECT_FIELD_VALUE_SEPARATOR);
        }

        @Override
        public DefaultPrettyPrinter createInstance() {
            return new CustomizedPrettyPrinter();
        }
    }
}
