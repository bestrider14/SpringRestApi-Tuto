package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.cart.CartDto;
import com.codewithmosh.store.dtos.cart.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.exeptions.CartNotFoundException;
import com.codewithmosh.store.exeptions.ProductNotFoundException;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = getCart(cartId);

        var product = productRepository.findById(productId).orElse(null);
        if(product == null)
            throw new ProductNotFoundException();

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCartDto(UUID cartId) {
        var cart = getCart(cartId);
        return cartMapper.toDto(cart);
    }

    public CartItemDto updateItemCart(UUID cartId, Long productId, Integer quantity) {
        var cart = getCart(cartId);

        var cartItem = cart.getItem(productId);
        if(cartItem == null)
            throw new ProductNotFoundException();

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void removeItemCart(UUID cartId, Long productId) {
        var cart = getCart(cartId);

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = getCart(cartId);

        cart.clear();
        cartRepository.save(cart);
    }

    private Cart getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        return cart;
    }
}
