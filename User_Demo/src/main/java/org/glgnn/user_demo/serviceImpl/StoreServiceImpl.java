package org.glgnn.user_demo.serviceImpl;

import jakarta.transaction.Transactional;
import org.glgnn.user_demo.dto.*;
import org.glgnn.user_demo.entity.Product;
import org.glgnn.user_demo.entity.Store;
import org.glgnn.user_demo.repository.ProductRepository;
import org.glgnn.user_demo.repository.StoreRepository;
import org.glgnn.user_demo.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public StoreServiceImpl(StoreRepository storeRepository,
                            ProductRepository productRepository) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public StoreResponse createStore(StoreCreateRequest request) {

        if (storeRepository.existsByName(request.name()))
            throw new RuntimeException("Store already exists");

        Store store = new Store(request.name());
        storeRepository.save(store);

        return new StoreResponse(store.getId(), store.getName());
    }

    @Override
    public void addProductToStore(Long storeId, Long productId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Product product = productRepository.findByIdAndStatusTrue(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStore() != null)
            throw new RuntimeException("Product already in a store");

        product.assignToStore(store);
    }

    @Override
    public List<StoreProductResponse> getStoreProducts(Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        return store.getProducts()
                .stream()
                .map(p -> new StoreProductResponse(
                        p.getId(),
                        p.getProductName(),
                        p.getSerialNumber(),
                        p.getOwnerUser() != null
                ))
                .toList();
    }
}
