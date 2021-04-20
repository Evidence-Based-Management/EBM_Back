package com.ebm.web.controller;

import com.ebm.domain.KVAAbilityToInnovate;
import com.ebm.domain.service.KVAAbilityToInnovateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/KVAAbilityToInnovate")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class KVAAbilityToInnovateController {
    @Autowired
    private final KVAAbilityToInnovateService kvaAbilityToInnovateService;

    KVAAbilityToInnovateController(KVAAbilityToInnovateService kvaAbilityToInnovateService) {
        this.kvaAbilityToInnovateService = kvaAbilityToInnovateService;
    }

    @PostMapping("/save")
    @ApiOperation("Save a new KVATimeToMarket")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVAAbilityToInnovate> save(@RequestBody KVAAbilityToInnovate kvaAbilityToInnovate) {
        return new ResponseEntity<>(kvaAbilityToInnovateService.save(kvaAbilityToInnovate), HttpStatus.CREATED);
    }

    @PutMapping("/{idKVAAbilityToInnovate}")
    @ApiOperation("Update a KVAAbilityToInnovate by id")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVAAbilityToInnovate> update(@PathVariable("idKVAAbilityToInnovate") int idKVAAbilityToInnovate, @RequestBody KVAAbilityToInnovate kvaAbilityToInnovate) {
        return kvaAbilityToInnovateService.update(idKVAAbilityToInnovate, kvaAbilityToInnovate).map(kvaAbilityToInnovateResponse -> new ResponseEntity<>(kvaAbilityToInnovateResponse, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }
}
