package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.ProductDTORequest;
import com.huuduc.snacksnap.data.dto.RegisterDTORequest;
import com.huuduc.snacksnap.data.entity.Product;
import com.huuduc.snacksnap.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Product Controller
 *
 * @author huuduc
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @Transactional
    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@RequestPart(required = false) Product product,
                                    @RequestPart(name = "fileImages", required = false) MultipartFile fileImages) {

        return ResponseEntity.ok(this.productService.create(product, fileImages));
    }

    @Transactional
    @PostMapping(value = "/create2", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create2(@RequestPart(name = "product", required = false) ProductDTORequest productDTORequest,
                                     @RequestPart(name = "fileImages", required = false) MultipartFile fileImages) throws IOException {

        //Check null fileImages
        if (fileImages == null || fileImages.isEmpty()) {
            return ResponseEntity.ok(this.productService.create2(productDTORequest, null));
        }

        //Transfer Multipart file to file
        File tempFile = File.createTempFile("temp", null);
        fileImages.transferTo(tempFile);

        return ResponseEntity.ok(this.productService.create2(productDTORequest, tempFile));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(this.productService.getAll());
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> getAllByFavorite() {

        return ResponseEntity.ok(this.productService.getAllByFavorite());
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getAllByType(@PathVariable(required = false) int type) {

        return ResponseEntity.ok(this.productService.getAllByType(type));

    }

    @Transactional
    @PutMapping(value = "/update/{productId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateById(@PathVariable long productId,
                                        @RequestPart ProductDTORequest productDTORequest,
                                        @RequestPart(required = false, name = "fileImage") MultipartFile fileImage) throws IOException {

        //Check null fileImages
        if (fileImage == null || fileImage.isEmpty()) {
            return ResponseEntity.ok(this.productService.updateById(productId, productDTORequest, null));
        }

        //Transfer Multipart file to file
        File tempFile = File.createTempFile("temp", null);
        fileImage.transferTo(tempFile);

        return ResponseEntity.ok(this.productService.updateById(productId, productDTORequest, tempFile));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteId(@PathVariable long productId) {

        return ResponseEntity.ok(this.productService.delete(productId));
    }

    @GetMapping("/thong-ke")
    public ResponseEntity<?> thongKe() {

        return ResponseEntity.ok(this.productService.thongKe());
    }

}
