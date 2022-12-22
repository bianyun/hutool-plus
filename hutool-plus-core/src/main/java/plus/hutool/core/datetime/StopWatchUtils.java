package plus.hutool.core.datetime;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.date.StopWatch.TaskInfo;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.math.NumberUtils;
import plus.hutool.core.text.string.StrUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 秒表工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class StopWatchUtils {
    private StopWatchUtils() {}

    private static final String TASK_LINE_TEMPLATE = " {}{}  => [{}] {}";

    /**
     * 获取 秒表 {@link StopWatch} 的汇总时长（更易读的形如 '1h 23m 45.678s' 的格式）
     *
     * @param sw 秒表对象
     * @return 汇总时长（更易读的形如 '1h 23m 45.678s' 的格式）
     */
    public static String getMoreReadableSummaryTime(StopWatch sw) {
        safelyStop(sw);
        return DateTimeUtils.millisToMoreReadableFormat(sw.getTotalTimeMillis());
    }

    /**
     * 获取 格式更好的详细耗时统计数据
     *
     * <p>示例代码和结果样例参见</p>
     * <p>{@link StopWatchUtils#getDetailedStatsWithBetterFormat(StopWatch, String, TaskFilter...)}</p>
     *
     * @param sw           秒表对象
     * @param summaryTitle 单行汇总标题
     * @param taskFilters  任务过滤器列表（用于控制任务行的额外指标展示）
     * @return 格式更好的详细耗时统计数据
     */
    public static List<String> getDetailedStatsWithBetterFormat(StopWatch sw,
                                                                String summaryTitle,
                                                                List<TaskFilter> taskFilters) {
        TaskFilter[] taskFilterArray = ArrayUtil.toArray(taskFilters, TaskFilter.class);
        return getDetailedStatsWithBetterFormat(sw, summaryTitle, taskFilterArray);
    }

    /**
     * 获取 格式更好的详细耗时统计数据
     * <p>使用的示例代码如下:</p>
     *
     * <pre>
     * // 示例1: 不使用额外的任务过滤器
     * List<String> result = StopWatchUtils.getDetailedStatsWithBetterFormat(sw,
     *      summaryTitle);
     * <p>返回的结果样例如下:</p>
     * ----------------------------------------------------------
     * 【总数据量/总页数: 599960/120】- 总计耗时: 2h 21m 55.155s
     * ----------------------------------------------------------
     *  1. 从 MySQL 分页查询文件状态数据  => [ 0.3%]   22.764s
     *  2. 迁移服务迁移文件               => [62.1%] 5289.366s
     *  3. 更新 ES 中的 UData 元数据      => [26.5%] 2255.761s
     *  4. 文件迁移状态数据写入 ES        => [ 3.8%]  321.192s
     *  5. 更新 MySQL 中的文件状态数据    => [ 7.4%]  626.072s
     * ----------------------------------------------------------
     *
     * // 示例2: 使用默认的分页指标过滤器
     * final TaskFilter pageMetricFilter =
     *      TaskFilter.defaultPageMetricFilter(PAGE_SIZE, TOTAL_COUNT);
     * List<String> result = StopWatchUtils.getDetailedStatsWithBetterFormat(sw,
     *      summaryTitle, pageMetricFilter);
     * <p>返回的结果样例如下:</p>
     * ----------------------------------------------------------
     * 【总数据量/总页数: 599960/120】- 总计耗时: 2h 21m 55.155s
     * ----------------------------------------------------------
     *  1. 任务步骤1  => [ 0.3%]   22.764s ( 0.189s / 5000)
     *  2. 任务步骤2  => [62.1%] 5289.366s (44.080s / 5000)
     *  3. 任务步骤3  => [26.5%] 2255.761s (18.799s / 5000)
     *  4. 任务步骤4  => [ 3.8%]  321.192s ( 2.676s / 5000)
     *  5. 任务步骤5  => [ 7.4%]  626.072s ( 5.217s / 5000)
     * ----------------------------------------------------------
     *
     * // 示例3: 使用默认的分页指标过滤器、文件传输每 MB 耗时的过滤器
     * final TaskFilter pageMetricFilter =
     *      TaskFilter.defaultPageMetricFilter(PAGE_SIZE, TOTAL_COUNT);
     * final long total = TOTAL_FILE_SIZE_IN_MEGA_BYTES;
     * final TaskFilter fileTransferFilter = TaskFilter.of(
     *      taskName -> taskName.contains("迁移服务迁移文件"),
     *      taskMillis -> {
     *          long millisPerMb = Math.round(taskMillis * 1.0 / total);
     *          return StrUtil.format("{}ms / MB", millisPerMb);
     *      }
     * );
     * List<String> result = StopWatchUtils.getDetailedStatsWithBetterFormat(sw,
     *      summaryTitle, pageMetricFilter, fileTransferFilter);
     * <p>返回的结果样例如下:</p>
     * ---------------------------------------------------------------
     * 【总数据量/总页数: 599960/120】- 总计耗时: 2h 21m 55.155s
     * ---------------------------------------------------------------
     *  1. 任务步骤1  => [ 0.3%]   22.764s ( 0.189s / 5000)
     *  2. 任务步骤2  => [62.1%] 5289.366s (44.080s / 5000, 4ms / MB)
     *  3. 任务步骤3  => [26.5%] 2255.761s (18.799s / 5000)
     *  4. 任务步骤4  => [ 3.8%]  321.192s ( 2.676s / 5000)
     *  5. 任务步骤5  => [ 7.4%]  626.072s ( 5.217s / 5000)
     * ---------------------------------------------------------------
     * </pre>
     *
     * @param sw           秒表对象
     * @param summaryTitle 汇总标题
     * @param taskFilters  任务过滤器数组（用于控制任务行的额外指标展示）
     * @return 格式更好的详细耗时统计数据
     */
    public static List<String> getDetailedStatsWithBetterFormat(StopWatch sw,
                                                                String summaryTitle,
                                                                TaskFilter... taskFilters) {
        return getDetailedStatsWithBetterFormat(sw, Collections.singletonList(summaryTitle), taskFilters);
    }

    /**
     * 获取 格式更好的详细耗时统计数据
     *
     * <p>示例代码和结果样例参见</p>
     * <p>{@link StopWatchUtils#getDetailedStatsWithBetterFormat(StopWatch, List, TaskFilter...)}</p>
     *
     * @param sw               秒表对象
     * @param summaryTitleList 汇总标题列表
     * @param taskFilters      任务过滤器列表（用于控制任务行的额外指标展示）
     * @return 格式更好的详细耗时统计数据
     */
    public static List<String> getDetailedStatsWithBetterFormat(StopWatch sw,
                                                                List<String> summaryTitleList,
                                                                List<TaskFilter> taskFilters) {
        TaskFilter[] taskFilterArray = ArrayUtil.toArray(taskFilters, TaskFilter.class);
        return getDetailedStatsWithBetterFormat(sw, summaryTitleList, taskFilterArray);
    }

    /**
     * 获取 格式更好的详细耗时统计数据
     * <p>使用的示例代码如下:</p>
     *
     * <pre>
     * final TaskFilter pageMetricFilter =
     *      TaskFilter.defaultPageMetricFilter(PAGE_SIZE, TOTAL_COUNT);
     *
     * final long total = TOTAL_FILE_SIZE_IN_MEGA_BYTES;
     * final TaskFilter fileTransferFilter = TaskFilter.of(
     *      taskName -> taskName.contains("迁移服务迁移文件"),
     *      taskMillis -> {
     *          long millisPerMb = Math.round(taskMillis * 1.0 / total);
     *          return StrUtil.format("{}ms / MB", millisPerMb);
     *      }
     * );
     * List<String> result = StopWatchUtils.getDetailedStatsWithBetterFormat(sw,
     *      summaryTitleList, pageMetricFilter, fileTransferFilter);
     * </pre>
     *
     * <p>返回的结果样例如下:</p>
     * <pre>
     * ---------------------------------------------------------------
     * 【文件迁移】-【平台管理 | 001 | 2000-01-01 至 2003-02-01】
     * ---------------------------------------------------------------
     * 【总数据量/待迁移/分页大小/页数: 599960/599960/5000/120】
     * ---------------------------------------------------------------
     * 【文件传输总大小: 1.37 TB】- 总计耗时: 2h 21m 55.155s
     * ---------------------------------------------------------------
     *  1. 任务步骤1  => [ 0.3%]   22.764s ( 0.189s / 5000)
     *  2. 任务步骤2  => [62.1%] 5289.366s (44.080s / 5000, 4ms / MB)
     *  3. 任务步骤3  => [26.5%] 2255.761s (18.799s / 5000)
     *  4. 任务步骤4  => [ 3.8%]  321.192s ( 2.676s / 5000)
     *  5. 任务步骤5  => [ 7.4%]  626.072s ( 5.217s / 5000)
     * ---------------------------------------------------------------
     * </pre>
     * @param sw               秒表对象
     * @param summaryTitleList 汇总标题列表
     * @param taskFilters      任务过滤器数组（用于控制任务行的额外指标展示）
     * @return 格式更好的详细耗时统计数据
     */
    public static List<String> getDetailedStatsWithBetterFormat(StopWatch sw,
                                                                List<String> summaryTitleList,
                                                                TaskFilter... taskFilters) {
        List<String> resolvedSummaryTitleList = new ArrayList<>(summaryTitleList.size());

        summaryTitleList.forEach(title -> {
            if (StrUtil.contains(title, StrUtils.LF)) {
                StrUtil.split(title, StrUtils.LF).forEach(piece -> {
                    piece = StrUtils.addPrefixIfPredicateSatisfied(piece, StrUtils.SPACE,
                            str -> !StrUtil.startWithAny(str, StrUtils.SPACE, StrUtils.CHN_OPEN_BRACKET));
                    piece = StrUtils.addSuffixIfPredicateSatisfied(piece, StrUtils.SPACE,
                            str -> !StrUtil.endWithAny(str, StrUtils.SPACE, StrUtils.CHN_CLOSE_BRACKET));
                    resolvedSummaryTitleList.add(piece);
                });
            } else {
                title = StrUtils.addPrefixIfPredicateSatisfied(title, StrUtils.SPACE,
                        str -> !StrUtil.startWithAny(str, StrUtils.SPACE, StrUtils.CHN_OPEN_BRACKET));
                title = StrUtils.addSuffixIfPredicateSatisfied(title, StrUtils.SPACE,
                        str -> !StrUtil.endWithAny(str, StrUtils.SPACE, StrUtils.CHN_CLOSE_BRACKET));
                resolvedSummaryTitleList.add(title);
            }
        });

        CollUtil.removeBlank(resolvedSummaryTitleList);
        Asserts.notEmpty(resolvedSummaryTitleList, "汇总标题列表 (summaryTitleList) 不能全部都是空白行");

        safelyStop(sw);
        TaskInfo[] taskInfoArray = sw.getTaskInfo();
        int totalNumOfOutputLines = taskInfoArray.length + 2 + (taskInfoArray.length * 2) + 1;

        List<String> outputLines = new ArrayList<>(totalNumOfOutputLines);
        outputLines.addAll(resolvedSummaryTitleList);

        long totalMillis = sw.getTotalTimeMillis();
        List<String> taskInfoOutputLines = buildTaskInfoOutputLines(taskInfoArray, totalMillis, taskFilters);
        outputLines.addAll(taskInfoOutputLines);

        int maxLenOfHansLine = StrUtils.maxLenOfHansStr(outputLines);
        String separatorLine = StrUtil.repeat(CharUtil.DASHED, maxLenOfHansLine);

        for (int i = 0; i <= resolvedSummaryTitleList.size(); i++) {
            outputLines.add(i * 2, separatorLine);
        }

        outputLines.add(separatorLine);
        return outputLines;
    }

    /**
     * 新建秒表对象
     *
     * @return 秒表对象
     */
    public static StopWatch newStopWatch() {
        return newStopWatch(true);
    }

    /**
     * 新建秒表对象
     *
     * @param startImmediately 是否立即启动秒表
     * @return 秒表对象
     */
    public static StopWatch newStopWatch(boolean startImmediately) {
        StopWatch sw = new StopWatch();

        if (startImmediately) {
            sw.start();
        }
        return sw;
    }

    /**
     * 以安全的方式启动任务（如果秒表处于启动状态，先停止，然后再启动）
     *
     * @param sw       {@link StopWatch} 格式的秒表
     * @param taskName 任务名称
     */
    public static void safelyStartTask(@Nullable StopWatch sw, String taskName) {
        if (sw != null) {
            if (sw.isRunning()) {
                sw.stop();
            }
            sw.start(taskName);
        }
    }

    /**
     * 以安全的方式停止秒表
     *
     * @param sw {@link StopWatch} 格式的秒表
     */
    public static void safelyStop(@Nullable StopWatch sw) {
        if (sw != null && sw.isRunning()) {
            sw.stop();
        }
    }

    private static List<String> buildTaskInfoOutputLines(TaskInfo[] taskInfoArray,
                                                         long totalMillis,
                                                         TaskFilter... taskFilters) {
        List<String> resultList = new ArrayList<>();

        Map<String, Long> taskTimeMillisMap = Arrays.stream(taskInfoArray).collect(
                Collectors.groupingBy(StopWatch.TaskInfo::getTaskName, TreeMap::new,
                        Collectors.summingLong(StopWatch.TaskInfo::getTimeMillis)));

        List<Integer> maxLenOfFilterResultList = getMaxLenOfFilterResultList(taskTimeMillisMap, taskFilters);
        int maxLenOfKey = taskTimeMillisMap.keySet().stream().mapToInt(StrUtils::lenOfHansStr)
                .max().orElseThrow(RuntimeException::new);
        int maxLenOfTimeSecondsValue = taskTimeMillisMap.values().stream()
                .mapToInt(value -> DateTimeUtils.millisToSecondsStr(value, 3).length())
                .max().orElse(0);

        taskTimeMillisMap.forEach((key, value) -> {
            String padStr = StrUtil.repeat(StrUtils.SPACE, maxLenOfKey - StrUtils.lenOfHansStr(key));
            String taskPercentage = NumberUtils.divToPercent(value, totalMillis, 1, true);
            String taskTimeConsumption = DateTimeUtils.millisToSecondsStr(value, 3);

            String taskLine = StrUtil.format(TASK_LINE_TEMPLATE, key, padStr,
                    StrUtil.padPre(taskPercentage, 5, StrUtils.SPACE),
                    StrUtil.padPre(taskTimeConsumption, maxLenOfTimeSecondsValue, StrUtils.SPACE));

            if (taskFilters.length > 0) {
                List<String> taskMetricList = new ArrayList<>();

                for (int i = 0; i < taskFilters.length; i++) {
                    TaskFilter taskFilter = taskFilters[i];
                    Integer maxLen = maxLenOfFilterResultList.get(i);

                    if (taskFilter.getTaskNamePredicate().test(key)) {
                        String metricStr = taskFilter.getTaskMillisToMetricFunc().apply(value);
                        String padStrBefore = StrUtil.repeat(StrUtils.SPACE, maxLen - StrUtils.lenOfHansStr(metricStr));

                        taskMetricList.add(padStrBefore + metricStr);
                    }
                }

                if (CollUtil.isNotEmpty(taskMetricList)) {
                    taskLine += StrUtil.SPACE + StrUtils.OPEN_PARENTHESIS +
                            StrUtils.join(", ", taskMetricList) +
                            StrUtils.CLOSE_PARENTHESIS + StrUtils.SPACE;
                }
            }

            resultList.add(taskLine);
        });
        return resultList;
    }

    private static List<Integer> getMaxLenOfFilterResultList(Map<String, Long> taskTimeMillisMap, TaskFilter... taskFilters) {
        List<Integer> resultList = new ArrayList<>();
        if (taskFilters.length > 0) {
            for (int i = 0; i < taskFilters.length; i++) {
                TaskFilter taskFilter = taskFilters[i];
                int maxLen = taskTimeMillisMap.entrySet().stream()
                        .filter(entry -> taskFilter.getTaskNamePredicate().test(entry.getKey()))
                        .mapToInt(entry -> StrUtils.lenOfHansStr(
                                taskFilter.getTaskMillisToMetricFunc().apply(entry.getValue())))
                        .max().orElse(0);
                resultList.add(i, maxLen);
            }
        }
        return resultList;
    }

    @lombok.Value(staticConstructor = "of")
    public static class TaskFilter {
        Predicate<String> taskNamePredicate;
        Function<Long, String> taskMillisToMetricFunc;

        public static TaskFilter defaultPageMetricFilter(int pageSize, long totalNum) {
            return TaskFilter.of(
                    taskName -> true,
                    taskMillis -> {
                        long millisPerPage = (long) (taskMillis * 1.0 * pageSize / totalNum);
                        return StrUtil.format("{} / {}", DateTimeUtils.millisToSecondsStr(millisPerPage, 3), pageSize);
                    }
            );
        }
    }
}
