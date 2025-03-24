//package edu.cnm.deepdive.farkle.controller;
//
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import edu.cnm.deepdive.farkle.model.entity.Game;
//import edu.cnm.deepdive.farkle.model.entity.State;
//import edu.cnm.deepdive.farkle.model.entity.User;
//import edu.cnm.deepdive.farkle.service.AbstractGameService;
//import edu.cnm.deepdive.farkle.service.AbstractUserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class GameControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  private AbstractGameService gameService;
//  private AbstractUserService userService;
//
//  @BeforeEach
//  void setupMocks() {
//    gameService = Mockito.mock(AbstractGameService.class);
//    userService = Mockito.mock(AbstractUserService.class);
//  }
//
//  @Test
//  void postNewGame() throws Exception {
//    Game mockGame = new Game();
//    mockGame.setState(State.PRE_GAME);
//
//    Mockito.when(userService.getCurrent()).thenReturn(new User());
//    Mockito.when(gameService.startOrJoin(Mockito.any(User.class))).thenReturn(mockGame);
//
//    mockMvc.perform(post("/games")
//            .accept(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.state").value("PRE_GAME"));
//  }
//}