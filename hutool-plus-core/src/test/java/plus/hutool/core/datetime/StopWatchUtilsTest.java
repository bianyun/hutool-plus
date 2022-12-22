package plus.hutool.core.datetime;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.date.StopWatch.TaskInfo;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import plus.hutool.core.datetime.StopWatchUtils.TaskFilter;
import plus.hutool.core.text.string.StrUtils;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StopWatchUtilsTest {
    private static final Map<String, Long> TASK_NAME_MILLIS_MAP = MapUtil.<String, Long>builder(new LinkedHashMap<>(5))
            .put("1. 从 MySQL 分页查询文件状态数据", 22764L)
            .put("2. 迁移服务迁移文件", 5289366L)
            .put("3. 更新 ES 中的 UData 元数据", 2255761L)
            .put("4. 文件迁移状态数据写入 ES", 321192L)
            .put("5. 更新 MySQL 中的文件状态数据", 626072L)
            .build();
    private static final long TOTAL_COUNT = 599960L;
    private static final long TOTAL_FILE_SIZE_IN_BYTES = 1503557740134L;
    private static final long TOTAL_FILE_SIZE_IN_MEGA_BYTES = Math.round(TOTAL_FILE_SIZE_IN_BYTES * 1.0 / 1024 / 1024);
    private static final long MEAN_FILE_SIZE_IN_BYTES = Math.round(TOTAL_FILE_SIZE_IN_BYTES * 1.0 / TOTAL_COUNT);
    private static final int PAGE_SIZE = 5000;
    private static final int PAGE_COUNT = (int) Math.ceil(TOTAL_COUNT * 1.0 / PAGE_SIZE);


    private static final long TOTAL_TIME_MILLIS = TASK_NAME_MILLIS_MAP.values().stream().mapToLong(Long::longValue).sum();
    private static final String READABLE_TOTAL_TIME = DateTimeUtils.millisToMoreReadableFormat(TOTAL_TIME_MILLIS);
    private static final TaskFilter DEFAULT_PAGE_METRIC_FILTER = TaskFilter.defaultPageMetricFilter(PAGE_SIZE, TOTAL_COUNT);

    private static final TaskFilter FILE_TRANSFER_FILTER = TaskFilter.of(
            taskName -> taskName.contains("迁移服务迁移文件"),
            taskMillis -> StrUtil.format("{}ms / MB", Math.round(taskMillis * 1.0 / TOTAL_FILE_SIZE_IN_MEGA_BYTES))
    );

    private static final TaskFilter INVALID_FILTER = TaskFilter.of(
            taskName -> taskName.contains("Invalid Task Name"),
            taskMillis -> StrUtil.format("{}", taskMillis)
    );

    @Test
    void testGetMoreReadableSummaryTime() {
        final StopWatch sw = mock(StopWatch.class);
        when(sw.getTotalTimeMillis()).thenReturn(TOTAL_TIME_MILLIS);

        final String result = StopWatchUtils.getMoreReadableSummaryTime(sw);
        assertThat(result).isEqualTo(READABLE_TOTAL_TIME);
    }

    private static StopWatch initMockOfStopWatch() {
        final StopWatch sw = mock(StopWatch.class);
        when(sw.getTotalTimeMillis()).thenReturn(TOTAL_TIME_MILLIS);

        TaskInfo[] taskInfoArray = new TaskInfo[TASK_NAME_MILLIS_MAP.size()];
        int i = 0;
        for (String taskName : TASK_NAME_MILLIS_MAP.keySet()) {
            Long taskMillis = TASK_NAME_MILLIS_MAP.get(taskName);

            TaskInfo taskInfo = mock(TaskInfo.class);
            Mockito.when(taskInfo.getTaskName()).thenReturn(taskName);
            Mockito.when(taskInfo.getTimeMillis()).thenReturn(taskMillis);

            taskInfoArray[i++] = taskInfo;
        }

        Mockito.when(sw.getTaskInfo()).thenReturn(taskInfoArray);
        return sw;
    }

    @Test
    void testGetNiceDetailedStatsAsList1() {
        StopWatch sw = initMockOfStopWatch();

        final String summaryTitle = StrUtil.format("【总数据量/分页大小/总页数: {}/{}/{}】- 总计耗时: {}",
                TOTAL_COUNT, PAGE_SIZE, PAGE_COUNT, READABLE_TOTAL_TIME);

        final List<TaskFilter> taskFilters1 = Collections.singletonList(DEFAULT_PAGE_METRIC_FILTER);
        final List<String> result1 = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitle, taskFilters1);
        assertThat(result1.size()).isEqualTo(9);
        assertThat(CollUtil.count(result1, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(5);
        assertThat(result1.get(1)).startsWith(summaryTitle);

        final List<TaskFilter> taskFilters2 = Arrays.asList(DEFAULT_PAGE_METRIC_FILTER, FILE_TRANSFER_FILTER);
        final List<String> result2 = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitle, taskFilters2);
        assertThat(result2.size()).isEqualTo(9);
        assertThat(CollUtil.count(result2, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(4);
        assertThat(CollUtil.count(result2, line -> line.contains("ms / MB) "))).isEqualTo(1);
        assertThat(result2.get(1)).startsWith(summaryTitle);

        final String summaryTitle3 =
                StrUtil.format("SummaryTitle3Line1 \n SummaryTitle3Line2 - 总计耗时: {}", READABLE_TOTAL_TIME);
        final List<TaskFilter> taskFilters3 = Collections.singletonList(DEFAULT_PAGE_METRIC_FILTER);
        final List<String> result3 = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitle3, taskFilters3);
        assertThat(result3.size()).isEqualTo(11);
        assertThat(CollUtil.count(result3, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(5);
        assertThat(result3.get(1)).startsWith(" SummaryTitle3Line1 ");
        assertThat(result3.get(3)).startsWith(StrUtil.format(" SummaryTitle3Line2 - 总计耗时: {}", READABLE_TOTAL_TIME));

        final String summaryTitle4 =
                StrUtil.format("SummaryTitle4 - 总计耗时: {}", READABLE_TOTAL_TIME);
        final List<TaskFilter> taskFilters4 = Collections.singletonList(DEFAULT_PAGE_METRIC_FILTER);
        final List<String> result4 = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitle4, taskFilters4);
        assertThat(result4.size()).isEqualTo(9);
        assertThat(CollUtil.count(result4, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(5);
        assertThat(result4.get(1)).startsWith(StrUtil.format(" SummaryTitle4 - 总计耗时: {}", READABLE_TOTAL_TIME));
    }

    @Test
    void testGetNiceDetailedStatsAsList2() {
        StopWatch sw = initMockOfStopWatch();

        final String summaryTitle = StrUtil.format("【总数据量/分页大小/总页数: {}/{}/{}】- 总计耗时: {}",
                TOTAL_COUNT, PAGE_SIZE, PAGE_COUNT, READABLE_TOTAL_TIME);

        final List<String> result1 = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitle);
        assertThat(result1.size()).isEqualTo(9);
        assertThat(CollUtil.count(result1, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(0);
        assertThat(result1.get(1)).startsWith(summaryTitle);
        Console.log("{}\n", CollUtil.join(result1, StrUtils.LF));

        final List<String> result2 = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitle, DEFAULT_PAGE_METRIC_FILTER);
        assertThat(result2.size()).isEqualTo(9);
        assertThat(CollUtil.count(result2, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(5);
        assertThat(result2.get(1)).startsWith(summaryTitle);
        Console.log("{}\n", CollUtil.join(result2, StrUtils.LF));

        final List<String> result3 = StopWatchUtils.getDetailedStatsWithBetterFormat(
                sw, summaryTitle, DEFAULT_PAGE_METRIC_FILTER, FILE_TRANSFER_FILTER);
        assertThat(result3.size()).isEqualTo(9);
        assertThat(CollUtil.count(result3, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(4);
        assertThat(CollUtil.count(result3, line -> line.contains("ms / MB) "))).isEqualTo(1);
        assertThat(result3.get(1)).startsWith(summaryTitle);

        Console.log("{}\n", CollUtil.join(result3, StrUtils.LF));
    }

    @Test
    void testGetNiceDetailedStatsAsList3() {
        StopWatch sw = initMockOfStopWatch();

        final List<String> summaryTitleList = Arrays.asList(
                "【文件迁移】-【分片(1/2)】-【平台管理 | 001 | 2000-01-01 至 2003-02-01】",
                StrUtil.format("【总数据量/待迁移/分片数据量/分页大小/分片页数: 1200287/1200287/{}/{}/{}】",
                        TOTAL_COUNT, PAGE_SIZE, PAGE_COUNT),
                StrUtil.format("【文件传输总大小: {}】-【文件传输平均大小: {}】- 总计耗时: {}",
                        FileUtil.readableFileSize(TOTAL_FILE_SIZE_IN_BYTES),
                        FileUtil.readableFileSize(MEAN_FILE_SIZE_IN_BYTES),
                        READABLE_TOTAL_TIME)
        );

        final List<TaskFilter> taskFilters = Arrays.asList(DEFAULT_PAGE_METRIC_FILTER, FILE_TRANSFER_FILTER);
        final List<String> result = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitleList, taskFilters);
        assertThat(result.size()).isEqualTo(13);
        assertThat(CollUtil.count(result, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(4);
        assertThat(CollUtil.count(result, line -> line.contains("ms / MB) "))).isEqualTo(1);
        assertThat(result.get(1)).startsWith(summaryTitleList.get(0));
        assertThat(result.get(3)).startsWith(summaryTitleList.get(1));
        assertThat(result.get(5)).startsWith(summaryTitleList.get(2));
    }

    @Test
    void testGetNiceDetailedStatsAsList4() {
        StopWatch sw = initMockOfStopWatch();

        final List<String> summaryTitleList = Arrays.asList(
                "【文件迁移】-【分片(1/2)】-【平台管理 | 001 | 2000-01-01 至 2003-02-01】",
                StrUtil.format("【总数据量/待迁移/分片数据量/分页大小/分片页数: 1200287/1200287/{}/{}/{}】",
                        TOTAL_COUNT, PAGE_SIZE, PAGE_COUNT),
                StrUtil.format("【文件传输总大小: {}】-【文件传输平均大小: {}】- 总计耗时: {}",
                        FileUtil.readableFileSize(TOTAL_FILE_SIZE_IN_BYTES),
                        FileUtil.readableFileSize(MEAN_FILE_SIZE_IN_BYTES),
                        READABLE_TOTAL_TIME)
        );

        final List<String> result = StopWatchUtils.getDetailedStatsWithBetterFormat(
                sw, summaryTitleList, DEFAULT_PAGE_METRIC_FILTER, FILE_TRANSFER_FILTER);
        assertThat(result.size()).isEqualTo(13);
        assertThat(CollUtil.count(result, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(4);
        assertThat(CollUtil.count(result, line -> line.contains("ms / MB) "))).isEqualTo(1);
        assertThat(result.get(1)).startsWith(summaryTitleList.get(0));
        assertThat(result.get(3)).startsWith(summaryTitleList.get(1));
        assertThat(result.get(5)).startsWith(summaryTitleList.get(2));

        Console.log("{}\n", CollUtil.join(result, StrUtils.LF));

        final List<String> result2 = StopWatchUtils.getDetailedStatsWithBetterFormat(sw, summaryTitleList, INVALID_FILTER);
        assertThat(result2.size()).isEqualTo(13);
        assertThat(CollUtil.count(result2, line -> line.contains(StrUtil.format("s / {})", PAGE_SIZE)))).isEqualTo(0);
        assertThat(result2.get(1)).startsWith(summaryTitleList.get(0));
        assertThat(result2.get(3)).startsWith(summaryTitleList.get(1));
        assertThat(result2.get(5)).startsWith(summaryTitleList.get(2));
    }

    @Test
    void testNewStopWatch() {
        final StopWatch stopWatchNotStartImmediately = StopWatchUtils.newStopWatch(false);
        assertThat(stopWatchNotStartImmediately.isRunning()).isFalse();
        assertThat(stopWatchNotStartImmediately.getTaskCount()).isEqualTo(0);

        final StopWatch stopWatchStartImmediately = StopWatchUtils.newStopWatch(true);
        assertThat(stopWatchStartImmediately.isRunning()).isTrue();
        assertThat(stopWatchStartImmediately.getTaskCount()).isEqualTo(0);
        stopWatchStartImmediately.stop();
        assertThat(stopWatchStartImmediately.isRunning()).isFalse();
        assertThat(stopWatchStartImmediately.getTaskCount()).isEqualTo(1);
        assertThat(stopWatchStartImmediately.getLastTaskName()).isEqualTo(StrUtils.EMPTY);

        final StopWatch defaultNewStopWatch = StopWatchUtils.newStopWatch();
        assertThat(defaultNewStopWatch.isRunning()).isTrue();
        assertThat(defaultNewStopWatch.getTaskCount()).isEqualTo(0);
        defaultNewStopWatch.stop();
        assertThat(defaultNewStopWatch.getTaskCount()).isEqualTo(1);
        assertThat(defaultNewStopWatch.getLastTaskName()).isEqualTo(StrUtils.EMPTY);
    }

    @Test
    void testSafelyStartTask() {
        StopWatchUtils.safelyStartTask(null, "");

        final StopWatch sw = StopWatchUtils.newStopWatch(false);
        assertThat(sw.isRunning()).isFalse();
        assertThat(sw.getTaskCount()).isEqualTo(0);

        final String taskStepOne = "taskStepOne";
        StopWatchUtils.safelyStartTask(sw, taskStepOne);
        assertThat(sw.isRunning()).isTrue();
        assertThat(sw.getTaskCount()).isEqualTo(0);
        sw.stop();
        assertThat(sw.isRunning()).isFalse();
        assertThat(sw.getTaskCount()).isEqualTo(1);
        assertThat(sw.getLastTaskName()).isEqualTo(taskStepOne);

        final String taskStepTwo = "taskStepTwo";
        StopWatchUtils.safelyStartTask(sw, taskStepTwo);
        assertThat(sw.isRunning()).isTrue();
        assertThat(sw.getTaskCount()).isEqualTo(1);
        sw.stop();
        assertThat(sw.isRunning()).isFalse();
        assertThat(sw.getTaskCount()).isEqualTo(2);
        assertThat(sw.getLastTaskName()).isEqualTo(taskStepTwo);

        final String taskStepThree = "taskStepThree";
        StopWatchUtils.safelyStartTask(sw, taskStepThree);
        assertThat(sw.isRunning()).isTrue();

        final String taskStepFour = "taskStepFour";
        StopWatchUtils.safelyStartTask(sw, taskStepFour);
        assertThat(sw.isRunning()).isTrue();
    }

    @Test
    void testSafelyStop() {
        StopWatchUtils.safelyStop(null);

        final StopWatch sw = StopWatchUtils.newStopWatch(false);

        assertThat(sw.isRunning()).isFalse();
        StopWatchUtils.safelyStop(sw);
        StopWatchUtils.safelyStop(sw);
        assertThat(sw.isRunning()).isFalse();

        StopWatchUtils.safelyStartTask(sw, StrUtils.EMPTY);
        assertThat(sw.isRunning()).isTrue();
        StopWatchUtils.safelyStop(sw);
        assertThat(sw.isRunning()).isFalse();
    }
}
