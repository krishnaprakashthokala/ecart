package com.luv2shopdev.ecommerce.controller;

import com.luv2shopdev.ecommerce.dto.PaymentInfo;
import com.luv2shopdev.ecommerce.dto.Purchase;
import com.luv2shopdev.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.luv2shopdev.ecommerce.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
	Logger log = LoggerFactory.getLogger(CheckoutController.class);

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
      log.info("place order");
        return checkoutService.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {

        var paymentIntent = checkoutService.createPaymentIntent(paymentInfo);
        var paymentStr = paymentIntent.toJSONString();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}

