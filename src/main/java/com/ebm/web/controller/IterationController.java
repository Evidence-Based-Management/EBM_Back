package com.ebm.web.controller;

import com.ebm.domain.Iteration;
import com.ebm.domain.service.IterationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iterations")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class IterationController {
    @Autowired
    private final IterationService iterationService;

    IterationController(IterationService iterationService) {
        this.iterationService = iterationService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all iterations")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<List<Iteration>> getAll() {
        return new ResponseEntity<>(iterationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/iteration/{idIteration}")
    @ApiOperation("Get iterations by id")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<Iteration> getIterationById(@ApiParam(value = "The id of the iteration", required = true, example = "7") @PathVariable("idIteration") int idIteration) {
        return iterationService.getIterationById(idIteration).map(iteration -> new ResponseEntity<>(iteration, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/save")
    @ApiOperation("Create a new iteration")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<Iteration> save(@RequestBody Iteration iteration) {
        return new ResponseEntity<>(iterationService.save(iteration), HttpStatus.CREATED);
    }


    @PutMapping("/iteration/{idIteration}")
    @ApiOperation("Update a iteration by id")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<Iteration> update(@PathVariable("idIteration") int idIteration, @RequestBody Iteration iteration) {
        return iterationService.update(idIteration, iteration).map(iterationResponse -> new ResponseEntity<>(iterationResponse, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }


    @GetMapping("/last/{idProduct}")
    @ApiOperation("Get last iteration by product")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<Iteration> getLastIteration(@ApiParam(value = "The id of the product", required = true, example = "7") @PathVariable("idProduct") int idProduct) {
        return new ResponseEntity<>(iterationService.getLastIteration(idProduct), HttpStatus.OK);
    }
}
