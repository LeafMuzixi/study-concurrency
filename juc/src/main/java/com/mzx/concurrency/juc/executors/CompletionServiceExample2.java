package com.mzx.concurrency.juc.executors;

import java.util.concurrent.*;

public class CompletionServiceExample2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        final List<Callable<Integer>> callableList = Arrays.asList(() -> {
//            sleep(10);
//            System.out.println("The 10 finished.");
//            return 1;
//        }, () -> {
//            sleep(20);
//            System.out.println("The 20 finished.");
//            return 2;
//        });
//        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(service);
//        List<Future<Integer>> futures = new ArrayList<>();
//        callableList.forEach(callable -> futures.add(completionService.submit(callable)));
//        Future<Integer> future = null;
//        while ((future = completionService.take()) != null) {
//            System.out.println(future.get());
//        }

//        Future<Integer> future = completionService.poll();
//        System.out.println(future);
//        System.out.println(completionService.poll(11,TimeUnit.SECONDS).get());

        ExecutorService service = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Event> completionService = new ExecutorCompletionService<>(service);
        final Event event = new Event(1);
        completionService.submit(new MyTask(event), event);
        System.out.println(completionService.take().get().result);
    }

    private static class MyTask implements Runnable {
        private final Event event;

        private MyTask(Event event) {
            this.event = event;
        }

        @Override
        public void run() {
            sleep(10);
            event.setResult("I am successfully.");
        }
    }

    private static class Event {
        private final int eventId;
        private String result;

        public Event(int eventId) {
            this.eventId = eventId;
        }

        public int getEventId() {
            return eventId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    private static void sleep(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
