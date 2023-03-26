package plus.hutool.media.content.type.internal;

import plus.hutool.media.content.type.MediaType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 媒体类型别名MAP
 *
 * @author bianyun
 * @date 2023/2/24
 */
@SuppressWarnings("JavadocDeclaration")
public final class MediaTypeAliasMap {
    public static final MediaTypeAliasMap INSTANCE = new MediaTypeAliasMap();
    private static final Map<MediaType, Set<String>> NORMALIZED_TO_ALIASES_MAP = new HashMap<>();
    private static final Map<String, MediaType> ALIAS_TO_NORMALIZED_MAP = new HashMap<>();

    private MediaTypeAliasMap() {}

    /**
     * 添加媒体类型别名MAP映射项
     *
     * @param normalizedMediaType 规范的媒体类型
     * @param deprecatedAliases 已废弃的其它别名
     * @return 媒体类型别名MAP
     */
    @SuppressWarnings("SameReturnValue")
    public MediaTypeAliasMap addAlias(MediaType normalizedMediaType, String... deprecatedAliases) {

        Set<String> aliasSet = NORMALIZED_TO_ALIASES_MAP.computeIfAbsent(normalizedMediaType, k -> new HashSet<>());

        for (String alias : deprecatedAliases) {
            aliasSet.add(alias);
            ALIAS_TO_NORMALIZED_MAP.putIfAbsent(alias, normalizedMediaType);
        }
        return INSTANCE;
    }

    public boolean isDeprecated(String deprecatedAlias) {
        return ALIAS_TO_NORMALIZED_MAP.containsKey(deprecatedAlias.toLowerCase());
    }

    public MediaType getNormalizedMediaType(String deprecatedAlias) {
        return ALIAS_TO_NORMALIZED_MAP.get(deprecatedAlias.toLowerCase());
    }
}
