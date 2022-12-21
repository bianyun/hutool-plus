package plus.hutool.extra.json;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import plus.hutool.core.datetime.DateTimeUtils;
import plus.hutool.extra.test.TestUser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class JsonUtilsTest {

    private static final ObjectMapper DEFAULT_MAPPER = JsonUtils.getDefaultJacksonObjectMapper();

    @Test
    void testGetDefaultJacksonObjectMapper() {
        final ObjectMapper result = JsonUtils.getDefaultJacksonObjectMapper();

        assertThat(result).isNotNull();
        assertThat(result.getDateFormat()).isEqualTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    void testToJsonStr1() {
        final TestUser user = new TestUser();
        user.setId(123L);
        user.setName("张三");
        user.setBirthday(LocalDate.of(1981, 10, 18));
        user.setLastLoginTime(DateTimeUtils.parseLocalDateTime("2022-10-13 12:34:56"));

        assertThat(JsonUtils.toJsonStr(DEFAULT_MAPPER, user)).isEqualTo("{\"id\":123,\"name\":\"张三\",\"birthday\":\"1981-10-18\",\"lastLoginTime\":\"2022-10-13T12:34:56\"}");

        ObjectMapper mapper = JsonMapper.builder()
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .findAndAddModules()
                .build();

        assertThat(JsonUtils.toJsonStr(mapper, user)).isEqualTo("{\"id\":123,\"name\":\"张三\",\"birthday\":\"1981-10-18\",\"createdTime\":null,\"lastLoginTime\":\"2022-10-13T12:34:56\"}");

        assertThat(JsonUtils.toJsonStr(mapper, "test str")).isEqualTo("test str");
    }

    @Test
    void testToJsonStr2() {
        final TestUser user = new TestUser();
        user.setId(123L);
        user.setName("张三");
        user.setBirthday(LocalDate.of(1981, 10, 18));
        user.setLastLoginTime(DateTimeUtils.parseLocalDateTime("2022-10-13 12:34:56"));

        assertThat(JsonUtils.toJsonStr(DEFAULT_MAPPER, user)).isEqualTo("{\"id\":123,\"name\":\"张三\",\"birthday\":\"1981-10-18\",\"lastLoginTime\":\"2022-10-13T12:34:56\"}");
        assertThat(JsonUtils.toJsonStr("test str")).isEqualTo("test str");
    }

    @Test
    void testToJsonStrWithExceptionThrown() {
        ObjectMapper mapper = mock(ObjectMapper.class);
        try {
            Mockito.when(mapper.writeValueAsString(Mockito.any(TestUser.class)))
                    .thenThrow(ReflectUtil.newInstance(JsonProcessingException.class, "json process error"));

            assertThatThrownBy(() -> JsonUtils.toJsonStr(mapper, new TestUser()))
                    .isExactlyInstanceOf(IORuntimeException.class)
                    .hasMessage("JsonProcessingException: json process error")
                    .hasCauseExactlyInstanceOf(JsonProcessingException.class)
                    .hasRootCauseMessage("json process error");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testToPrettyJsonStr1() {
        final TestUser user = new TestUser();
        user.setId(123L);
        user.setName("张三");
        user.setBirthday(LocalDate.of(1981, 10, 18));
        user.setCreatedTime(DateTimeUtils.toLegacyDate(DateTimeUtils.parseLocalDateTime("2022-10-13 11:23:45")));
        user.setLastLoginTime(DateTimeUtils.parseLocalDateTime("2022-10-13 12:34:56"));

        String expectedResult = "{\n" +
                "    \"id\": 123,\n" +
                "    \"name\": \"张三\",\n" +
                "    \"birthday\": \"1981-10-18\",\n" +
                "    \"createdTime\": \"2022-10-13 11:23:45\",\n" +
                "    \"lastLoginTime\": \"2022-10-13T12:34:56\"\n" +
                "}";
        assertThat(JsonUtils.toPrettyJsonStr(DEFAULT_MAPPER, user)).isEqualTo(expectedResult);

        assertThat(JsonUtils.toPrettyJsonStr(DEFAULT_MAPPER, "test str")).isEqualTo("test str");
    }

    @Test
    void testToPrettyJsonStr2() {
        final TestUser user = new TestUser();
        user.setId(123L);
        user.setName("张三");
        user.setBirthday(LocalDate.of(1981, 10, 18));
        user.setCreatedTime(DateTimeUtils.toLegacyDate(DateTimeUtils.parseLocalDateTime("2022-10-13 11:23:45")));
        user.setLastLoginTime(DateTimeUtils.parseLocalDateTime("2022-10-13 12:34:56"));

        String expectedResult = "{\n" +
                "    \"id\": 123,\n" +
                "    \"name\": \"张三\",\n" +
                "    \"birthday\": \"1981-10-18\",\n" +
                "    \"createdTime\": \"2022-10-13 11:23:45\",\n" +
                "    \"lastLoginTime\": \"2022-10-13T12:34:56\"\n" +
                "}";
        assertThat(JsonUtils.toPrettyJsonStr(user)).isEqualTo(expectedResult);
        assertThat(JsonUtils.toPrettyJsonStr("test str")).isEqualTo("test str");
    }

    @Test
    void testToPrettyJsonStrWithExceptionThrown() {
        ObjectMapper mapper = mock(ObjectMapper.class);
        ObjectWriter writer = mock(ObjectWriter.class);
        try {
            Mockito.when(mapper.writer(Mockito.any(JsonUtils.CustomizedPrettyPrinter.class))).thenReturn(writer);
            Mockito.when(writer.writeValueAsString(Mockito.any(TestUser.class)))
                    .thenThrow(ReflectUtil.newInstance(JsonProcessingException.class, "json process error"));

            assertThatThrownBy(() -> JsonUtils.toPrettyJsonStr(mapper, new TestUser()))
                    .isExactlyInstanceOf(IORuntimeException.class)
                    .hasMessage("JsonProcessingException: json process error")
                    .hasCauseExactlyInstanceOf(JsonProcessingException.class)
                    .hasRootCauseMessage("json process error");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testConvertJsonObjectToMap() {
        final TestUser user = new TestUser();
        user.setId(123L);
        user.setName("张三");
        user.setBirthday(LocalDate.of(1981, 10, 18));
        user.setCreatedTime(DateTimeUtils.toLegacyDate(DateTimeUtils.parseLocalDateTime("2022-10-13 11:23:45")));
        user.setLastLoginTime(DateTimeUtils.parseLocalDateTime("2022-10-13 12:34:56"));

        JSONConfig config = JSONConfig.create().setDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = JSONUtil.parseObj(user, config);

        final Map<String, String> resultMap = JsonUtils.convertHutoolJsonObjectToMap(jsonObject);
        assertThat(resultMap).hasSize(5).containsKeys("id", "name", "birthday", "createdTime", "lastLoginTime");
        assertThat(resultMap.get("id")).isEqualTo("123");
        assertThat(resultMap.get("name")).isEqualTo("张三");
        assertThat(resultMap.get("birthday")).isEqualTo("1981-10-18 00:00:00");
        assertThat(resultMap.get("createdTime")).isEqualTo("2022-10-13 11:23:45");
        assertThat(resultMap.get("lastLoginTime")).isEqualTo("2022-10-13 12:34:56");
    }

    @Test
    void testConvertObjectNodeToMap() {
        String jsonStr = "{\"id\":123,\"name\":\"张三\",\"birthday\":\"1981-10-18\",\"createdTime\":null,\"lastLoginTime\":\"2022-10-13 12:34:56\"}";
        try {
            ObjectNode objectNode = (ObjectNode) DEFAULT_MAPPER.readTree(jsonStr);
            final Map<String, String> resultMap = JsonUtils.convertObjectNodeToMap(objectNode);

            assertThat(resultMap).hasSize(5).containsKeys("id", "name", "birthday", "createdTime", "lastLoginTime");
            assertThat(resultMap.get("id")).isEqualTo("123");
            assertThat(resultMap.get("name")).isEqualTo("张三");
            assertThat(resultMap.get("birthday")).isEqualTo("1981-10-18");
            assertThat(resultMap.get("createdTime")).isNull();
            assertThat(resultMap.get("lastLoginTime")).isEqualTo("2022-10-13 12:34:56");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testToBean1() {
        String jsonStr = "{\"id\":123,\"name\":\"张三\",\"birthday\":\"1981-10-18\",\"createdTime\":null,\"lastLoginTime\":\"2022-10-13T12:34:56\"}";
        TestUser result = JsonUtils.toBean(jsonStr, TestUser.class);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(123L);
        assertThat(result.getName()).isEqualTo("张三");
        assertThat(result.getBirthday()).isEqualTo(DateTimeUtils.parseLocalDate("1981-10-18"));
        assertThat(result.getCreatedTime()).isNull();
        assertThat(result.getLastLoginTime()).isEqualTo(DateTimeUtils.parseLocalDateTime("2022-10-13T12:34:56"));
    }

    @Test
    void testToBean2() {
        String jsonStr = "{\"id\":123,\"name\":\"张三\",\"birthday\":\"1981-10-18\",\"createdTime\":null,\"lastLoginTime\":\"2022-10-13T12:34:56\"}";
        TestUser result = JsonUtils.toBean(DEFAULT_MAPPER, jsonStr, TestUser.class);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(123L);
        assertThat(result.getName()).isEqualTo("张三");
        assertThat(result.getBirthday()).isEqualTo(DateTimeUtils.parseLocalDate("1981-10-18"));
        assertThat(result.getCreatedTime()).isNull();
        assertThat(result.getLastLoginTime()).isEqualTo(DateTimeUtils.parseLocalDateTime("2022-10-13T12:34:56"));
    }

    @Test
    void testToBeanWithExceptionThrown() {
        String jsonStr = "{\"id\":123,\"name\":\"张三\",\"birthday\":\"1981-10-18\",\"createdTime\":null,\"lastLoginTime\":\"2022-10-13T12:34:56\"}";

        ObjectMapper mapper = mock(ObjectMapper.class);
        try {
            Mockito.when(mapper.readValue(jsonStr, TestUser.class))
                    .thenThrow(ReflectUtil.newInstance(JsonProcessingException.class, "json process error"));

            assertThatThrownBy(() -> JsonUtils.toBean(mapper, jsonStr, TestUser.class))
                    .isExactlyInstanceOf(IORuntimeException.class)
                    .hasMessage("JsonProcessingException: json process error")
                    .hasCauseExactlyInstanceOf(JsonProcessingException.class)
                    .hasRootCauseMessage("json process error");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
