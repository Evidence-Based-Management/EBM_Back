package com.ebm.web.controller;

import com.ebm.domain.KVACurrentValue;
import com.ebm.domain.service.KVACurrentValueService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/KVACurrentValue")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class KVACurrentValueController {
    @Autowired
    private final KVACurrentValueService kvaCurrentValueService;

    KVACurrentValueController(KVACurrentValueService kvaCurrentValueService) {
        this.kvaCurrentValueService = kvaCurrentValueService;
    }

    @PostMapping("/save")
    @ApiOperation("Save a new KVACurrentValue")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVACurrentValue> save(@RequestBody KVACurrentValue kvaCurrentValue) {
        return new ResponseEntity<>(kvaCurrentValueService.save(kvaCurrentValue), HttpStatus.CREATED);
    }

    @PutMapping("/{idKVACurrentValue}")
    @ApiOperation("Update a KVACurrentValue by id")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVACurrentValue> update(@PathVariable("idKVACurrentValue") int idKVACurrentValue, @RequestBody KVACurrentValue kvaCurrentValue) {
        return kvaCurrentValueService.update(idKVACurrentValue, kvaCurrentValue).map(kvaCurrentValueResponse -> new ResponseEntity<>(kvaCurrentValueResponse, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }

}
