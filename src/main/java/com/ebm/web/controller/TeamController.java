package com.ebm.web.controller;

import com.ebm.domain.Team;
import com.ebm.domain.service.TeamService;
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
@RequestMapping("/teams")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class TeamController {
    @Autowired
    private final TeamService teamService;

    TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all teams with iterations")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<List<Team>> getAll() {
        return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{idTeam}")
    @ApiOperation("Get a team by Id")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 404, message = "Team not found")
            })
    public ResponseEntity<Team> getTeamById(@ApiParam(value = "The id of the team", required = true, example = "7") @PathVariable("idTeam") int idTeam) {
        return teamService.getTeamById(idTeam).map(team -> new ResponseEntity<>(team, HttpStatus.OK)).orElse(new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }


    @GetMapping("/byuserid/{idUser}")
    @ApiOperation("Get all teams by idUser")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<List<Team>> getTeamsByIdUser(@ApiParam(value = "The id of the user", required = true, example = "7") @PathVariable("idUser") int idUser) {
        return new ResponseEntity<>(teamService.getTeamsByIdUser(idUser), HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation("Create a new team")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<Team> save(@RequestBody Team team) {
        return new ResponseEntity<>(teamService.save(team), HttpStatus.CREATED);
    }

}
