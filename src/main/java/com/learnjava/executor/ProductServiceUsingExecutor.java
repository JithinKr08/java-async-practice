package com.learnjava.executor;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingExecutor {

    //creating a thread pool of size of available processor
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException {
        stopWatch.start();

//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call

        Future<ProductInfo> productInfoFuture = executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
        System.out.println("Sunmitted first task..");
        Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));
        System.out.println("Submitted second task..");

        ProductInfo productInfo = productInfoFuture.get();//blocks the caller thread
        Review review = reviewFuture.get();

        System.out.println("all operations done....");

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("Available core:" + Runtime.getRuntime().availableProcessors());

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
        executorService.shutdown();
    }
}
