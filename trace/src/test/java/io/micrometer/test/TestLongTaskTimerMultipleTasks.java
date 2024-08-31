package io.micrometer.test;

public class TestLongTaskTimerMultipleTasks {

	public static void main(String[] args) throws InterruptedException {
		// MeterRegistry registry = new SimpleMeterRegistry();
		// LongTaskTimer longTaskTimer = LongTaskTimer.builder("my_long_task_timer",
		// Meter.Type.TIMER,new ArrayList<Measurement>())
		// .register(registry);
		//
		// // 启动多个长时间任务
		// List<LongTaskTimer.Sample> samples = new ArrayList<>();
		// for (int i = 0; i < 3; i++) {
		// LongTaskTimer.Sample sample = longTaskTimer.start();
		// samples.add(sample);
		//
		// // 模拟长时间任务
		// final int taskDuration = (i + 1) * 2000; // 模拟不同的任务持续时间
		// new Thread(() -> {
		// try {
		// Thread.sleep(taskDuration);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } finally {
		// sample.stop();
		// }
		// }).start();
		// }
		//
		// // 在任务进行中多次获取当前持续时间和活动任务数量
		// for (int j = 0; j < 5; j++) {
		// Thread.sleep(1000); // 每隔1秒检查一次
		// System.out.println("检查 " + (j + 1) + ":");
		// for (int i = 0; i < samples.size(); i++) {
		// LongTaskTimer.Sample sample = samples.get(i);
		// double currentDuration = sample.duration(TimeUnit.SECONDS);
		// System.out.println("任务 " + (i + 1) + " 当前持续时间: " + currentDuration + " 秒");
		// }
		// System.out.println("当前活动任务数量: " + longTaskTimer.activeTasks());
		// System.out.println();
		// }
		//
		// // 等待所有任务完成
		// Thread.sleep(5000);
		//
		// // 获取统计信息
		// System.out.println("最终活动任务数量: " + longTaskTimer.activeTasks());
		// System.out.println("任务总时间: " + longTaskTimer.duration(TimeUnit.SECONDS) + "
		// 秒");
	}

}
