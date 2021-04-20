package com.ebm.web.controller;

import com.ebm.domain.KVAUnrealizedValue;
import com.ebm.domain.service.KVAUnrealizedValueService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/KVAUnrealizedValue")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class KVAUnrealizedValueController {
    @Autowired
    private final KVAUnrealizedValueService kvaUnrealizedValueService;

    KVAUnrealizedValueController(KVAUnrealizedValueService kvaUnrealizedValueService) {
        this.kvaUnrealizedValueService = kvaUnrealizedValueService;
    }

    @GetMapping("/{idKVAUnrealizedValue}")
    @ApiOperation("Get KVAUnrealizedValue by id")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVAUnrealizedValue> getById(@ApiParam(value = "The id of KVAUnrealizedValue", required = true, example = "1") @PathVariable("idKVAUnrealizedValue") int idKVAUnrealizedValue) {
        return kvaUnrealizedValueService.getById(idKVAUnrealizedValue).map(kvaUnrealizedValue -> new ResponseEntity<>(kvaUnrealizedValue, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/save")
    @ApiOperation("Save a new KVAUnrealizedValue")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVAUnrealizedValue> save(@RequestBody KVAUnrealizedValue kvaUnrealizedValue) {
        return new ResponseEntity<>(kvaUnrealizedValueService.save(kvaUnrealizedValue), HttpStatus.CREATED);
    }

    @PutMapping("{idKVAUnrealizedValue}")
    @ApiOperation("Update a KVAUnrealizedValue by id")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVAUnrealizedValue> update(@PathVariable("idKVAUnrealizedValue") int idKVAUnrealizedValue, @RequestBody KVAUnrealizedValue kvaUnrealizedValue) {
        return kvaUnrealizedValueService.update(idKVAUnrealizedValue, kvaUnrealizedValue).map(kvaUnrealizedValueResponse -> new ResponseEntity<>(kvaUnrealizedValueResponse, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }
}
