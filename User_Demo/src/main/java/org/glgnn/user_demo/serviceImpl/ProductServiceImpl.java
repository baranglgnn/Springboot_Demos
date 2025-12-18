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

        if (productRepository.existsBySerialNumber(request.serialNumber()))
            throw new RuntimeException("Serial number exists");

        Product product = new Product(
                request.productName(),
                request.serialNumber(),
                request.price()
        );

        return map(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return map(findActiveProduct(productId));
    }

    @Override
    public List<ProductResponse> getAllActiveProducts() {
        return productRepository.findAllByStatusTrue()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<ProductResponse> getActiveProductsByUserId(Long userId) {

        userService.getActiveUserEntity(userId);

        return productRepository.findAllByOwnerUserIdAndStatusTrue(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public ProductResponse updateProductPrice(Long productId, ProductUpdatePriceRequest request) {
        Product product = findActiveProduct(productId);
        product.changePrice(request.price());
        return map(product);
    }

    @Override
    public ProductResponse assignProductToUser(Long productId, ProductAssignRequest request) {

        Product product = findActiveProduct(productId);

        if (product.getOwnerUser() != null)
            throw new RuntimeException("Product already assigned");

        product.assignToUser(userService.getActiveUserEntity(request.userId()));
        return map(product);
    }

    @Override
    public void unassignProduct(Long productId) {
        findActiveProduct(productId).unassignUser();
    }

    @Override
    public void softDeleteProduct(Long productId) {
        findActiveProduct(productId).deactivate();
    }

    @Override
    public void hardDeleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private Product findActiveProduct(Long id) {
        return productRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Active product not found"));
    }

    private ProductResponse map(Product product) {

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
