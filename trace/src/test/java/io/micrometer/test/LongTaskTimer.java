package io.micrometer.test;

import io.micrometer.core.instrument.Meter;

import java.util.concurrent.TimeUnit;

/**
 * 长时间任务监控：适用于需要监控长时间运行任务的场景，如批处理任务、数据迁移任务等。
 * 并发任务监控：可以监控并发执行的任务数量和每个任务的持续时间
 */
public interface LongTaskTimer extends Meter {

    /**
     * 启动一个新的长时间任务，用于记录任务的开始时间
     *
     * @return {@link Sample}
     */
    Sample start();

    /**
     * 获取所有活动任务的总持续时间。
     *
     * @param unit 时间单位，如秒、毫秒、微秒等
     * @return 返回所有活动任务的总持续时间
     */
    double duration(TimeUnit unit);

    /**
     * 获取当前正在执行的活动任务数量。
     *
     * @return 返回当前活动任务的数量。
     */
    int activeTasks();

    /**
     * 获取当前所有活动任务中持续时间最长的任务的持续时间。
     *
     * @param unit 时间单位，如秒、毫秒、微秒等
     * @return 返回当前所有活动任务中持续时间最长的任务的持续时间。
     */
    double max(TimeUnit unit);

    interface Sample {
        /**
         * 停止计时，并记录任务的结束时间。
         *
         * @return 返回任务的持续时间（以纳秒为单位）。
         */
        long stop();

        /**
         * 获取任务的持续时间。
         * @param unit 时间单位，如秒、毫秒、微秒等。
         * @return 返回任务的持续时间.
         */
        double duration(TimeUnit unit);
    }

}
