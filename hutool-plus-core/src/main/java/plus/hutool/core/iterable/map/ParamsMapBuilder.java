package plus.hutool.core.iterable.map;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import plus.hutool.core.iterable.enums.EnumUtils;

import java.util.Map;

/**
 * 参数 MAP 构造器（继承自 hutool 的 MapBuilder）
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public class ParamsMapBuilder {
    private final MapBuilder<String, Object> mapBuilder;

    /**
     * 参数 MAP 构造器构造方法
     *
     * @param map 内层的 Map实现类的对象
     */
    public ParamsMapBuilder(Map<String, Object> map) {
        mapBuilder = MapUtil.builder(map);
    }

    /**
     * 放入枚举类型到参数MAP中（参数名采用枚举类型简单类名的 camelCase风格）
     *
     * @param enumValue 枚举对象的值
     * @param <T>       枚举类型的泛型参数类型
     * @return 当前对象的引用
     */
    public <T extends Enum<T>> ParamsMapBuilder putEnumValue(T enumValue) {
        String paramName = EnumUtils.defaultParamName(enumValue.getDeclaringClass());
        mapBuilder.put(paramName, enumValue);
        return this;
    }

    /**
     * 放入普通参数值到参数MAP中
     *
     * @param key   参数的键
     * @param value 参数的值
     * @return 当前对象的引用
     */
    public ParamsMapBuilder putParamValue(String key, Object value) {
        mapBuilder.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return mapBuilder.build();
    }

}
