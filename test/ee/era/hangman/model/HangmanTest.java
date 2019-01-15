package ee.era.hangman.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HangmanTest {
  Hangman game;

  private void startGame(String word) {
    game = new Hangman(word);
  }

  @Test
  public void userHas7Tries() {
    startGame("sofa");
    game.guessLetter('p');
    game.guessLetter('q');
    game.guessLetter('j');
    game.guessLetter('h');
    game.guessLetter('z');
    assertEquals("____", game.getWord());
    assertEquals(5, game.getErrors());
    game.guessLetter('i');
    assertTrue(game.isLost());
    assertEquals("sofa", game.getWord());
    assertFalse(game.isWon());
  }

  @Test
  public void userCanGuessLetters() {
    startGame("sofa");
    assertEquals("____", game.getWord());
    assertEquals(0, game.getErrors());

    game.guessLetter('a');
    assertEquals("___a", game.getWord());
    assertEquals(0, game.getErrors());

    game.guessLetter('b');
    assertEquals("___a", game.getWord());
    assertEquals(1, game.getErrors());
  }

  @Test
  public void userWinsWhenAllLettersOfWordAreGuessed() {
    startGame("sofa");
    game.guessLetter('o');
    assertEquals("_o__", game.getWord());
    game.guessLetter('p');
    assertEquals("_o__", game.getWord());
    game.guessLetter('f');
    assertEquals("_of_", game.getWord());
    game.guessLetter('a');
    assertEquals("_ofa", game.getWord());
    game.guessLetter('s');
    assertEquals("sofa", game.getWord());
    assertTrue(game.isWon());
    assertFalse(game.isLost());
  }

  @Test
  public void duplicateLetters() {
    startGame("hologram");
    game.guessLetter('o');
    assertEquals("_o_o____", game.getWord());
    assertEquals(0, game.getErrors());
  }

  @Test
  public void matchingIsCaseInsensitive() {
    startGame("TopConf");
    game.guessLetter('t');
    game.guessLetter('C');
    game.guessLetter('o');
    assertEquals("To_Co__", game.getWord());
    assertEquals(0, game.getErrors());
  }

  @Test
  public void supportsCyrillicCharacters() {
    startGame("Диван");
    game.guessLetter('н');
    game.guessLetter('а');
    game.guessLetter('в');
    game.guessLetter('и');
    game.guessLetter('д');
    assertEquals("Диван", game.getWord());
    assertTrue(game.isWon());
  }
}
