
package com.example.PMS.products.service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class BarcodeImageService {

    public byte[] generateBarcodeImage(String gtin) {

        System.out.println("GTIN RECEIVED: " + gtin);
        if (gtin == null ||
                !gtin.matches("\\d{13}")) {

            throw new IllegalArgumentException(
                    "GTIN-13 must contain exactly 13 digits"
            );
        }

        try {

            int width = 500;
            int barcodeHeight = 150;
            int textHeight = 50;

            EAN13Writer writer =
                    new EAN13Writer();

            BitMatrix bitMatrix =
                    writer.encode(
                            gtin,
                            BarcodeFormat.EAN_13,
                            width,
                            barcodeHeight
                    );

            BufferedImage image =
                    new BufferedImage(
                            width,
                            barcodeHeight + textHeight,
                            BufferedImage.TYPE_INT_RGB
                    );

            Graphics2D graphics =
                    image.createGraphics();

            // Background
            graphics.setColor(Color.WHITE);

            graphics.fillRect(
                    0,
                    0,
                    width,
                    barcodeHeight + textHeight
            );

            // Barcode
            graphics.setColor(Color.BLACK);

            for (int x = 0; x < width; x++) {

                for (int y = 0;
                     y < barcodeHeight;
                     y++) {

                    if (bitMatrix.get(x, y)) {

                        image.setRGB(
                                x,
                                y,
                                Color.BLACK.getRGB()
                        );
                    }
                }
            }

            // Number
            graphics.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            24
                    )
            );

            FontMetrics metrics =
                    graphics.getFontMetrics();

            int textWidth =
                    metrics.stringWidth(gtin);

            int textX =
                    (width - textWidth) / 2;

            int textY =
                    barcodeHeight + 35;

            graphics.drawString(
                    gtin,
                    textX,
                    textY
            );

            graphics.dispose();

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            ImageIO.write(
                    image,
                    "PNG",
                    outputStream
            );

            return outputStream.toByteArray();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to generate EAN-13 barcode image",
                    e
            );
        }
    }
}

