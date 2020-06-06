package com.mzx.concurrency.juc.utils.countDownLatch;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CountDownLatchExample4 {
    private static final int QUEUE_MAX_WAIT = 1024;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("executor-%d").build();

    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingDeque<>(QUEUE_MAX_WAIT);

    private static final ExecutorService SERVICE = new ThreadPoolExecutor(5, 5, 30, TimeUnit.SECONDS, BLOCKING_QUEUE, THREAD_FACTORY);

    static class Event {
        int id = 0;

        public Event(int id) {
            this.id = id;
        }
    }

    interface Watcher {
        void done(Table table);
    }

    static class TaskBatch implements Watcher {
        private CountDownLatch latch;

        private TaskGroup taskGroup;

        public TaskBatch(TaskGroup taskGroup, int size) {
            this.latch = new CountDownLatch(size);
            this.taskGroup = taskGroup;
        }

        @Override
        public void done(Table table) {
            latch.countDown();
            if (latch.getCount() == 0) {
                System.out.println("The table " + table.tableName + " finished work,[" + table + "]");
                taskGroup.done(table);
            }
        }
    }

    static class TaskGroup implements Watcher {
        private CountDownLatch latch;

        private Event event;

        public TaskGroup(Event event, int size) {
            this.latch = new CountDownLatch(size);
            this.event = event;
        }

        @Override
        public void done(Table table) {
            latch.countDown();
            if (latch.getCount() == 0) {
                System.out.println("All of table down in event: " + event.id);
            }
        }
    }

    static class Table {
        String tableName;
        long sourceRecordCount = 10;
        long targetCount;
        String sourceColumnSchema = "<table name='a'><column name='coll' type='varchar2'/></table>";
        String targetColumnSchema;

        public Table(String tableName, long sourceRecordCount) {
            this.tableName = tableName;
            this.sourceRecordCount = sourceRecordCount;
        }

        @Override
        public String toString() {
            return "Table{" +
                    "tableName='" + tableName + '\'' +
                    ", sourceRecordCount=" + sourceRecordCount +
                    ", targetCount=" + targetCount +
                    ", sourceColumnSchema='" + sourceColumnSchema + '\'' +
                    ", targetColumnSchema='" + targetColumnSchema + '\'' +
                    '}';
        }
    }

    private static List<Table> capture(Event event) {
        List<Table> list = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            list.add(new Table("table-" + event.id + "-" + i, i * 1000));
        });
        return list;
    }

    public static void main(String[] args) {
        Event[] events = {new Event(1), new Event(2)};
        for (Event event : events) {
            List<Table> tables = capture(event);
            TaskGroup taskGroup = new TaskGroup(event, tables.size());
            tables.forEach(table -> {
                TaskBatch taskBatch = new TaskBatch(taskGroup, 2);
                TrustSourceColumns columnsRunnable = new TrustSourceColumns(table, taskBatch);
                TrustSourceRecordCount recordCountRunnable = new TrustSourceRecordCount(table, taskBatch);
                SERVICE.submit(columnsRunnable);
                SERVICE.submit(recordCountRunnable);
            });
        }
        SERVICE.shutdown();
    }

    static class TrustSourceRecordCount implements Runnable {
        private final Table table;

        private final TaskBatch taskBatch;

        TrustSourceRecordCount(Table table, TaskBatch taskBatch) {
            this.table = table;
            this.taskBatch = taskBatch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetCount = table.sourceRecordCount;
//            System.out.println("The table " + table.tableName + " target record count capture done and update the data.");
            taskBatch.done(table);
        }
    }

    static class TrustSourceColumns implements Runnable {
        private final Table table;

        private final TaskBatch taskBatch;

        TrustSourceColumns(Table table, TaskBatch taskBatch) {
            this.table = table;
            this.taskBatch = taskBatch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetColumnSchema = table.sourceColumnSchema;
//            System.out.println("The table " + table.tableName + " target columns capture done and update the data.");
            taskBatch.done(table);
        }
    }
}
