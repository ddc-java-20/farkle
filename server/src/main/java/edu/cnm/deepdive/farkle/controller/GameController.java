package edu.cnm.deepdive.farkle.controller;


import edu.cnm.deepdive.farkle.model.entity.Game;
import edu.cnm.deepdive.farkle.service.AbstractGameService;
import edu.cnm.deepdive.farkle.service.AbstractUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  private final AbstractGameService gameService;
  private final AbstractUserService userService; // TODO: 3/20/25 Add this to other controllers

  @Autowired
  public GameController(AbstractGameService gameService, AbstractUserService userService) {
    this.gameService = gameService;
    this.userService = userService;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Game post(){
    return gameService.startOrJoin(userService.getCurrent());
  }



}
