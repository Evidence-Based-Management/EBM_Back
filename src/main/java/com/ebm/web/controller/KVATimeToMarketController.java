package com.ebm.web.controller;

import com.ebm.domain.KVATimeToMarket;
import com.ebm.domain.service.KVATimeToMarketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/KVATimeToMarket")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class KVATimeToMarketController {
    @Autowired
    private final KVATimeToMarketService kvaTimeToMarketService;

    KVATimeToMarketController(KVATimeToMarketService kvaTimeToMarketService) {
        this.kvaTimeToMarketService = kvaTimeToMarketService;
    }

    @PostMapping("/save")
    @ApiOperation("Save a new KVATimeToMarket")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVATimeToMarket> save(@RequestBody KVATimeToMarket kvaTimeToMarket) {
        return new ResponseEntity<>(kvaTimeToMarketService.save(kvaTimeToMarket), HttpStatus.CREATED);
    }

    @PutMapping("/{idKVATimeToMarket}")
    @ApiOperation("Update a KVATimeToMarket by id")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<KVATimeToMarket> update(@PathVariable("idKVATimeToMarket") int idKVATimeToMarket, @RequestBody KVATimeToMarket kvaTimeToMarket) {
        return kvaTimeToMarketService.update(idKVATimeToMarket, kvaTimeToMarket).map(kvaTimeToMarketResponse -> new ResponseEntity<>(kvaTimeToMarketResponse, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }
}
