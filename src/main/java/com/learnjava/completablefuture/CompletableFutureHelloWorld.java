package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;

import java.util.concurrent.CompletableFuture;



public class CompletableFutureHelloWorld {


    HelloWorldService service;

    public CompletableFutureHelloWorld(HelloWorldService service){
        this.service = service;
    }

    public CompletableFuture<String>  helloWorld() {
        return CompletableFuture.supplyAsync(service::helloWorld) // initiating a async computation
                .thenApply(String::toUpperCase); // transforming the data
    }

    public String helloworld_multiple_async_calls(){
        CommonUtil.startTimer();

        String hwResult="";

        CompletableFuture<String> helloCompletableFuture = CompletableFuture.supplyAsync(() -> service.hello());
        CompletableFuture<String> worldCompletableFuture = CompletableFuture.supplyAsync(() -> service.world());

        //we have two future lets combine both now

        hwResult = helloCompletableFuture
                .thenCombine(worldCompletableFuture, (h,w) -> h+w) // here it accepts a Completion stage and a bifunction
                        .thenApply(String::toUpperCase)
                                .join();

        CommonUtil.timeTaken();
        return hwResult;
    }

    public String combining_3_async

    public static void main(String[] args) {

        HelloWorldService service = new HelloWorldService();

        CompletableFuture.supplyAsync(service::helloWorld) // initiating a async computation
                .thenApply(String::toUpperCase) // transforming the data
                .thenAccept(result -> {                //accepting the result using a consumer
                    System.out.println("result" + result);
                });
    }
}
