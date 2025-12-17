package org.glgnn.user_demo.serviceImpl;

import jakarta.transaction.Transactional;
import org.glgnn.user_demo.dto.*;
import org.glgnn.user_demo.entity.Product;
import org.glgnn.user_demo.entity.User;
import org.glgnn.user_demo.repository.ProductRepository;
import org.glgnn.user_demo.service.ProductService;
import org.glgnn.user_demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {

        if (productRepository.existsBySerialNumber(request.serialNumber())) {
            throw new RuntimeException("Serial number already exists");
        }

        Product product = new Product(
                request.productName(),
                request.serialNumber(),
                request.price()
        );

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findByIdAndStatusTrue(productId)
                .orElseThrow(() -> new RuntimeException("Active product not found"));
        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getAllActiveProducts() {
        return productRepository.findAllByStatusTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getActiveProductsByUserId(Long userId) {

        // User aktif mi?
        userService.getActiveUserEntity(userId);

        return productRepository.findAllByOwnerUserIdAndStatusTrue(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProductPrice(Long productId, ProductUpdatePriceRequest request) {

        Product product = productRepository.findByIdAndStatusTrue(productId)
                .orElseThrow(() -> new RuntimeException("Active product not found"));

        product.changePrice(request.price());
        return mapToResponse(product);
    }

    @Override
    public ProductResponse assignProductToUser(Long productId, ProductAssignRequest request) {

        Product product = productRepository.findByIdAndStatusTrue(productId)
                .orElseThrow(() -> new RuntimeException("Active product not found"));

        if (product.getOwnerUser() != null) {
            throw new RuntimeException("Product already assigned");
        }

        User user = userService.getActiveUserEntity(request.userId());
        product.assignToUser(user);

        return mapToResponse(product);
    }

    @Override
    public void unassignProduct(Long productId) {

        Product product = productRepository.findByIdAndStatusTrue(productId)
                .orElseThrow(() -> new RuntimeException("Active product not found"));

        product.unassignUser();
    }

    @Override
    public void softDeleteProduct(Long productId) {

        Product product = productRepository.findByIdAndStatusTrue(productId)
                .orElseThrow(() -> new RuntimeException("Active product not found"));

        product.deactivate();
    }

    @Override
    public void hardDeleteProduct(Long productId) {

        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(productId);
    }

    private ProductResponse mapToResponse(Product product) {

        OwnerInfoResponse owner = null;

        if (product.getOwnerUser() != null) {
            owner = new OwnerInfoResponse(
                    product.getOwnerUser().getId(),
                    product.getOwnerUser().getName(),
                    product.getOwnerUser().getEmail()
            );
        }

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getSerialNumber(),
                product.getPrice(),
                product.getStatus(),
                owner
        );
    }
}
