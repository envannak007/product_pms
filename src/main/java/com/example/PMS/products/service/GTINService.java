package com.example.PMS.products.service;

import com.example.PMS.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GTINService {

    private final ProductRepository productRepository;

    public String createGtin13() {

        String gtin;

        do {
            String baseNumber = generateBaseNumber();

            int checkDigit =
                    calculateCheckDigit(baseNumber);

            gtin = baseNumber + checkDigit;

        } while (productRepository.existsByGtin(gtin));

        return gtin;
    }

    private String generateBaseNumber() {

        // Example prefix for your PMS
        int randomNumber =
                ThreadLocalRandom.current()
                        .nextInt(
                                100_000_000,
                                1_000_000_000
                        );

        return "884" + randomNumber;
    }

    private int calculateCheckDigit(String baseNumber) {

        int sum = 0;

        for (int i = 0; i < baseNumber.length(); i++) {

            int digit =
                    baseNumber.charAt(i) - '0';

            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += digit * 3;
            }
        }

        return (10 - (sum % 10)) % 10;
    }
}