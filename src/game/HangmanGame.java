package game;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HangmanGame {
	private Scanner input;
	private String playerName;
	private String wordToGuess;
	private boolean gameIsRunning;
	private int lives;
	private char letter;
	private Set<Character> lettersToGuess;
	private Set<Character> lettersGuessed;
	private Set<Character> lettersWrong;

	public static void main(String[] args) {
		HangmanGame game = new HangmanGame();
		game.run();
	}

	private void run() {
		input = new Scanner(System.in);
		playerName = getPlayerName(input);
		wordToGuess = getRandomWordToGuess();
		gameIsRunning = true;
		lives = 5;
		lettersToGuess = new HashSet<Character>();
		lettersGuessed = new HashSet<Character>();
		lettersWrong = new HashSet<Character>();

		fillLettersToGuess();
		// Put first and last letter into lettersGuessed set.
		lettersGuessed.add(wordToGuess.charAt(0));
		lettersGuessed.add(wordToGuess.charAt(wordToGuess.length() - 1));

		System.out.println("I've picked a word for you to guess.");
		while (gameIsRunning) {
			showHangmanState();
			showWordToGuess(wordToGuess, lettersGuessed);
			System.out.println(playerName + ", guess a letter!");
			showWrongLetters();
			pickLetter();
			if (isLetterInWord(letter, lettersToGuess)) {
				addLetterToLettersGuessed(letter, lettersGuessed);
			} else {
				wrongLetter(letter, lettersWrong);
				lifeLost();
				System.out.println("Your letter is not in the word.");
				System.out.println("Lives remaining: " + lives);
			}
			showWordToGuess(wordToGuess, lettersGuessed);
			if (isGameOver()) {
				gameIsRunning = false;
				System.out.println("You " + ((lives == 0) ? "lost!" : "won!"));
			}
		}
		input.close();
	}

	/*
	 * Method to ask for the player's name
	 */
	private String getPlayerName(Scanner keyboard) {
		System.out.println("Hi there! What's your name?");
		String playerName = keyboard.nextLine().trim();
		System.out.println("Hi " + playerName + ", let's play!");
		return playerName;
	}

	/*
	 * Returns a random word to guess and play.
	 */
	private String getRandomWordToGuess() {
		// Load a word from dictionary. To do: Implement a dictionary
		String[] wordList = { "hello", "test", "elephant", "car", "table",
				"stack", "help", "someone", "yellow", "purple" };
		int min = 0;
		int max = wordList.length - 1;
		int wordToGuessPosition = getRandomNumber(min, max);
		return wordList[wordToGuessPosition];
	}

	/*
	 * Returns a random number. To do: Move it to a helper class.
	 */
	private int getRandomNumber(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	/*
	 * Shows the word to guess.
	 */
	private void showWordToGuess(String wordToGuess,
			Set<Character> lettersGuessed) {

		for (int i = 0; i < wordToGuess.length(); i++) {

			// Checks lettersGuessed to know what to reveal to the user
			if (lettersGuessed.contains(wordToGuess.charAt(i))) {
				System.out.print(wordToGuess.charAt(i));
			} else {
				System.out.print("*");
			}
		}
		// Hardcoded newline. Is there any fancier way to do this?
		System.out.println();
	}

	/*
	 * Checks if the letter guessed is in the lettersToGuess.
	 */
	private boolean isLetterInWord(char letter, Set<Character> lettersToGuess) {
		return lettersToGuess.contains(letter);
	}

	/*
	 * Adds the guessed letter to the set that contains the guessed letters.
	 */
	private void addLetterToLettersGuessed(char letter,
			Set<Character> lettersGuessed) {
		lettersGuessed.add(letter);
	}

	/*
	 * Adds the wrong letter to the set that contains wrong letters.
	 */
	private void wrongLetter(char letter, Set<Character> lettersWrong) {
		lettersWrong.add(letter);
	}

	/*
	 * We lose 1 life per wrong letter. We will use this method to handle the
	 * draw to display.
	 */
	private void lifeLost() {
		lives--;
	}

	/*
	 * Fills lettersToGuess from wordToGuess
	 */
	private void fillLettersToGuess() {
		for (Character c : wordToGuess.toCharArray()) {
			lettersToGuess.add(c);
		}
	}

	/*
	 * Show Hangman state. To do: draw hangman in his 5 stages.
	 */
	private void showHangmanState() {
		System.out.println("+++++++++++++++++++++++++++++++++");
		System.out.println("+++++Hangman draw goes here.+++++");
		System.out.println("+++++Hangman draw goes here.+++++");
		System.out.println("+++++Hangman draw goes here.+++++");
		System.out.println("+++++Hangman draw goes here.+++++");
		System.out.println("+++++++++++++++++++++++++++++++++");
		System.out.println("Word to guess:");
	}

	/*
	 * We check if the game is over (win or lose)
	 */
	private boolean isGameOver() {
		return ((lives == 0) || (lettersGuessed.size() == lettersToGuess.size()));
	}

	/*
	 * Show wrong letters (if any)
	 */
	private void showWrongLetters() {
		if (lettersWrong.size() > 0) {
			System.out.print("Wrong letters: ");
			for (Character c : lettersWrong) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}

	/*
	 * Ask the user to type a letter and verify if it isn't in the game already
	 */
	private void pickLetter() {
		do {
			letter = input.nextLine().trim().toLowerCase().charAt(0);
			if (lettersWrong.contains(letter)
					|| lettersGuessed.contains(letter)) {
				System.out.println("The letter " + letter
						+ " is already in the game!.");
				System.out.println(playerName + ", guess a new letter!");
			}
		} while (lettersWrong.contains(letter)
				|| lettersGuessed.contains(letter));
	}
}