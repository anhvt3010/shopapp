package com.anhvt.shopapp.services;

import com.anhvt.shopapp.dtos.ProductDTO;
import com.anhvt.shopapp.dtos.ProductImageDTO;
import com.anhvt.shopapp.models.Product;
import com.anhvt.shopapp.models.ProductImage;

import java.util.List;

public class ProductService implements IProductService{
    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        return null;
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return null;
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        return null;
    }

    @Override
    public void deleteProduct(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        return null;
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        return null;
    }
}
