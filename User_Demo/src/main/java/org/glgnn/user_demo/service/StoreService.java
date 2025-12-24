package org.glgnn.user_demo.service;

import org.glgnn.user_demo.dto.*;

import java.util.List;

public interface StoreService {

    StoreResponse createStore(StoreCreateRequest request);

    void addProductToStore(Long storeId, Long productId);

    List<StoreProductResponse> getStoreProducts(Long storeId);
}