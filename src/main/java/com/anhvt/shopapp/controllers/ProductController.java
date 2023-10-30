package com.anhvt.shopapp.controllers;

import com.anhvt.shopapp.dtos.ProductDTO;
import com.anhvt.shopapp.dtos.ProductImageDTO;
import com.anhvt.shopapp.models.Product;
import com.anhvt.shopapp.models.ProductImage;
import com.anhvt.shopapp.responses.ProductListResponse;
import com.anhvt.shopapp.responses.ProductResponse;
import com.anhvt.shopapp.services.IProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getAll(@RequestParam("page") int page,
                                                      @RequestParam("limit") int limit )
    {
        // tao pageable tu thong tin trang va gioi han
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPages = productService.getAllProducts(pageRequest);
        // lay ra tong so trang
        int totalPages = productPages.getTotalPages();
        List<ProductResponse> products = productPages.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                        .products(products)
                        .totalPages(totalPages)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable("id") Long id) {
        try {
            Product existingProduct = productService.getProductById(id);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<?> add(@Valid @RequestBody ProductDTO productDTO,
                                 BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage) // lay ra message error da quy dinh
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@ModelAttribute("file") List<MultipartFile> files,
                                          @PathVariable("id") Long productId) throws Exception {
        Product existingProduct = productService.getProductById(productId);
        files = files == null ? new ArrayList<>() : files;
        if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
            return ResponseEntity.badRequest().body("You cant upload more than "
                    + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT
                    + " pictures!");
        }
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files){
            if(file.getSize() ==0){
                continue;
            }
            // kiem tra kich thuoc file
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("File is too large bro ! Maximum size is 10MB");
            }

            //kiem tra dinh dang file
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }

            //luu va cap nhat thumbnail trong DTO
            String filename = storeFile(file);
            //luu vao bang product_images
            ProductImage newProductImage = productService.createProductImage(
                    existingProduct.getId(),
                    ProductImageDTO.builder()
                            .imageUrl(filename)
                            .build());
            productImages.add(newProductImage);
        }
        return ResponseEntity.ok(productImages);
    }

    private String storeFile(MultipartFile file) throws IOException{
        if(!isImageFile(file) || file.getOriginalFilename() == null){
            throw new IOException("Invalid image format");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //them UUID vao trc ten file de doi ten file
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        //duong dan den thu muc muon luu file
        Path uploadDir = Paths.get("uploads");

        //kiem tra va tao thu muc neu no khong ton tai
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }

        //duong dan day du den file dich
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);

        //sao chep file vao thu muc dich
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                         @RequestBody ProductDTO productDTO){
        try{
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Delete product successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product not found!");
        }
    }

//    @PostMapping("/generateFakeProducts")
    public ResponseEntity<?> generateFakeProducts(){
        Faker faker = new Faker();
        for(int i = 0; i < 100; i++){
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(0, 90000000))
                    .thumbnail("")
                    .description(faker.lorem().sentence())
                    .categoryId((long) faker.number().numberBetween(2,6))
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake products created successfully");
    }
}
