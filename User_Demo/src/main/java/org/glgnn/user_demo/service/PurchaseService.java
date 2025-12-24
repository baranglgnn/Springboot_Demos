package org.glgnn.user_demo.service;

import org.glgnn.user_demo.dto.PurchaseCreateRequest;
import org.glgnn.user_demo.dto.PurchaseResponse;

import java.util.List;

public interface PurchaseService {

    PurchaseResponse purchaseProduct(PurchaseCreateRequest request);

    void returnProduct(Long purchaseId);

    List<PurchaseResponse> getPurchasesByUser(Long userId);
}



