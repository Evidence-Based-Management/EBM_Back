package com.ebm.web.controller;

import com.ebm.domain.Product;
import com.ebm.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ProductController {
    @Autowired
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all products with iterations")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{idProduct}")
    @ApiOperation("Get a Product by Id")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 404, message = "Product not found")
            })
    public ResponseEntity<Product> getProductById(@ApiParam(value = "The id of the product", required = true, example = "7") @PathVariable("idProduct") int idProduct) {
        return productService.getProductById(idProduct).map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }


    @GetMapping("/byuser/{idUser}")
    @ApiOperation("Get all products with iterations")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<List<Product>> getByUserId(@ApiParam(value = "The id of the product", required = true, example = "7") @PathVariable("idUser") int idUser) {
        return new ResponseEntity<>(productService.getProductsByUserId(idUser), HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation("Create a new product")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }
}
