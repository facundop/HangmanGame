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
		game.play();
	}

	private void play() {
		input = new Scanner(System.in);
		playerName = getPlayerName(input);
		wordToGuess = getRandomWordToGuess();
		gameIsRunning = true;
		lives = 5;
		lettersToGuess = new HashSet<Character>();
		lettersGuessed = new HashSet<Character>();
		lettersWrong = new HashSet<Character>();

		// Fill lettersToGuess (We use this to compare the letters picked
		for (Character c : wordToGuess.toCharArray()) {
			lettersToGuess.add(c);
		}
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
			if (lettersToGuess.contains(letter)) {
				lettersGuessed.add(letter);
			} else {
				lettersWrong.add(letter);
				lives--;
				System.out.println("Your letter is not in the word.");
				System.out.println("Lives remaining: " + lives);
			}
			showWordToGuess(wordToGuess, lettersGuessed);

			// Check to see if the game is over (either win or lose)
			if ((lives == 0)
					|| (lettersGuessed.size() == lettersToGuess.size())) {
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