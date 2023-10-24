package com.anhvt.shopapp.services;

import com.anhvt.shopapp.dtos.ProductDTO;
import com.anhvt.shopapp.dtos.ProductImageDTO;
import com.anhvt.shopapp.models.Product;
import com.anhvt.shopapp.models.ProductImage;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws Exception;
//    public Page<ProductResponse> getAllProducts(String keyword,
//                                                Long categoryId, PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;

    List<Product> findProductsByIds(List<Long> productIds);
}