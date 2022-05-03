package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService hws = new HelloWorldService();
    CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);

    @Test
    void helloWorld() {

        //given

        //when
        CompletableFuture<String> stringCompletableFuture = cfhw.helloWorld();

        //then

        stringCompletableFuture.thenAccept( result ->{
            assertEquals("HELLO WORLD", result);
        }).join(); // blocking the caller thread as its a async computation
    }

    @Test
    public void helloWorld_multiple_async_calls_test(){

        //when
        String result = cfhw.helloworld_multiple_async_calls();

        //then
        assertEquals("HELLO WORLD!" , result);
    }
}