package org.glgnn.user_demo.service;

import org.glgnn.user_demo.dto.*;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse getProductById(Long productId);

    List<ProductResponse> getAllActiveProducts();

    List<ProductResponse> getActiveProductsByUserId(Long userId);

    ProductResponse updateProductPrice(Long productId, ProductUpdatePriceRequest request);

    ProductResponse assignProductToUser(Long productId, ProductAssignRequest request);

    void unassignProduct(Long productId);

    void softDeleteProduct(Long productId);

    void hardDeleteProduct(Long productId);
}

