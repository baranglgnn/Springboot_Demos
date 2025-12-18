package org.glgnn.user_demo.serviceImpl;

import jakarta.transaction.Transactional;
import org.glgnn.user_demo.dto.PurchaseCreateRequest;
import org.glgnn.user_demo.dto.PurchaseResponse;
import org.glgnn.user_demo.entity.Product;
import org.glgnn.user_demo.entity.Purchase;
import org.glgnn.user_demo.entity.User;
import org.glgnn.user_demo.repository.ProductRepository;
import org.glgnn.user_demo.repository.PurchaseRepository;
import org.glgnn.user_demo.service.PurchaseService;
import org.glgnn.user_demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public PurchaseServiceImpl(
            PurchaseRepository purchaseRepository,
            ProductRepository productRepository,
            UserService userService
    ) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public PurchaseResponse purchaseProduct(PurchaseCreateRequest request) {

        if (purchaseRepository.existsByProductIdAndActiveTrue(request.productId())) {
            throw new RuntimeException("Product already purchased");
        }

        User user = userService.getActiveUserEntity(request.userId());

        Product product = productRepository.findByIdAndStatusTrue(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Purchase purchase = new Purchase(user, product);

        product.assignToUser(user);

        return mapToResponse(purchaseRepository.save(purchase));
    }

    @Override
    public void returnProduct(Long purchaseId) {

        Purchase purchase = purchaseRepository.findByIdAndActiveTrue(purchaseId)
                .orElseThrow(() -> new RuntimeException("Active purchase not found"));

        purchase.returnProduct();
        purchase.getProduct().unassignUser();
    }

    @Override
    public List<PurchaseResponse> getPurchasesByUser(Long userId) {

        userService.getActiveUserEntity(userId);

        return purchaseRepository.findAllByBuyerId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PurchaseResponse mapToResponse(Purchase purchase) {

        return new PurchaseResponse(
                purchase.getId(),
                purchase.getProduct().getProductName(),
                purchase.getProduct().getSerialNumber(),
                purchase.getBuyer().getName(),
                purchase.getBuyer().getEmail(),
                purchase.getPurchaseDate(),
                purchase.getActive()
        );
    }
}
