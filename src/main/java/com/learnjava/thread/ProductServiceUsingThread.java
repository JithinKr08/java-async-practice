package com.learnjava.thread;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingThread {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();

        //run these two task execution using thread - low level coding
        //ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);

        //same for this blocking call running in a new thread
//        Review review = reviewService.retrieveReviews(productId); // blocking call
        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);

        //start the threads
        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        System.out.println("1st join done..");
        reviewThread.join();
        System.out.println("2nd join done");

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    private class ProductInfoRunnable implements Runnable{ //here it accepts no args
        private String productId;
        private ProductInfo productInfo;

        //run method dont accepts any arg so passing the values through constructor.
        public ProductInfoRunnable(String productId){
            this.productId = productId;
        }

        //as no return type we will introduce a instance variable and give access through public getter

        public ProductInfo getProductInfo(){
            return this.productInfo;
        }
        @Override
        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }


    }

    private class ReviewRunnable implements Runnable{

        private String productId;
        private Review review;

        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        public Review getReview(){
            return this.review;
        }
        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }
    }
}
