
package com.example.PMS.products.controller;
import com.example.PMS.products.service.BarcodeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/barcode")
@RequiredArgsConstructor
public class BarcodeController {

    private final BarcodeImageService barcodeImageService;

    @GetMapping(
            value = "/{gtin}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public ResponseEntity<byte[]> generateBarcode(
            @PathVariable String gtin
    ) {

        byte[] imageBarcode =
                barcodeImageService.generateBarcodeImage(gtin);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBarcode);
    }
}

