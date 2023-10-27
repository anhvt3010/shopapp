package com.anhvt.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
public class ProductDTO {
    @NotBlank(message = "title is required")
    @Size(min = 3, max = 200, message = "title must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "price must be greater than or equal to 0")
    @Max(value = 1000000000, message = "price must be less than or equal to 1,000,000,000")
    private Float price;

    private String thumbnail;

    private List<MultipartFile> files;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;
}
