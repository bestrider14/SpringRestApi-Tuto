package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.exeptions.CartIsEmptyException;
import com.codewithmosh.store.exeptions.CartNotFoundException;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CheckoutService {

    private final AuthService authService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Value("${websiteUrl}")
    private String websiteUrl;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) throws StripeException {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();

        if(cart.isEmpty())
            throw new CartIsEmptyException();

        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

       try {
           // Create a checkout session
           var builder = SessionCreateParams.builder()
               .setMode(SessionCreateParams.Mode.PAYMENT)
               .setSuccessUrl(websiteUrl+"/checkout-success?orderId="+order.getId())
               .setCancelUrl(websiteUrl+"/checkout-cancel");

           for(var item : order.getItems())
           {
               var lineItem = SessionCreateParams.LineItem.builder()
                   .setQuantity(Long.valueOf(item.getQuantity()))
                   .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                       .setCurrency("cad")
                       .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                       .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                           .setName(item.getProduct().getName())
                           .build())
                       .build())
                   .build();
               builder.addLineItem(lineItem);
           }

           var session = Session.create(builder.build());

           cartService.clearCart(cart.getId());

           return new CheckoutResponse(order.getId(), session.getUrl());
       } catch (Exception e) {
           System.out.println(e.getMessage());
           orderRepository.delete(order);
           throw e;
       }
    }
}
