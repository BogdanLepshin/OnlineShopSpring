package ua.finalproject.onlineshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.finalproject.onlineshop.dto.ProductDTO;
import ua.finalproject.onlineshop.entity.Category;
import ua.finalproject.onlineshop.entity.Product;
import ua.finalproject.onlineshop.repository.CategoryRepository;
import ua.finalproject.onlineshop.service.ProductService;
import ua.finalproject.onlineshop.utils.FileUploadUtil;

import java.io.IOException;

@Slf4j
@Controller
public class AdminController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AdminController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
    }

    @GetMapping("/products_manager/new-product")
    public String createNewProductView(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "admin/new_product";
    }

    @GetMapping("/products_manager")
    public String productsManagerView(Model model) {
        //TODO pagination
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("category", new Category());
        return "admin/products_manager";
    }

    @PostMapping("/products_manager")
    public String productsByCategory(@ModelAttribute Category category, Model model) {
        if (category.getId() == 0) {
            model.addAttribute("products", productService.getAllProducts());
            return "admin/products_manager";
        }
        model.addAttribute("products", productService.findProductsByCategory(category.getId()));
        return "admin/products_manager";
    }

    @GetMapping("/products_manager/edit/{id}")
    public String editView(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "admin/edit_product";
    }

    @PostMapping("/products_manager/edit/save")
    public String editSave(@ModelAttribute Product product, Model model) {
        productService.updateProduct(new ProductDTO().toProductDto(product));
        return "admin/edit_product";
    }

    @PostMapping("/products_manager/new-product/save")
    public String addNewProduct(@ModelAttribute ProductDTO productDTO,
                                @RequestParam("product_pic") MultipartFile file, Model model) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        productDTO.setImage(fileName);
        Product savedProduct = saveNewProduct(productDTO, model);
        if (savedProduct == null)
            return "redirect:/products_manager/new-product";
        uploadImage(file, fileName, savedProduct);
        return "redirect:/products_manager";
    }

    private Product saveNewProduct(ProductDTO productDTO, Model model) {
        Product savedProduct;
        try {
            savedProduct = productService.saveNewProduct(productDTO);
        } catch (IllegalArgumentException e) {
            log.error("{}", e);
            model.addAttribute("creation_error", true);
            return null;
        }
        return savedProduct;
    }

    private void uploadImage(MultipartFile file, String fileName, Product savedProduct) {
        String uploadDir = "product_images/" + savedProduct.getId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
