///////////////////////////////////////////////////////////////////////////////
//
// Main Class File: Main.java
// File:            Main.java
// Semester:        Spring 2018
//
// Authors:         Nick Stoffel, Erik Umhoefer,  Stephen Squires III,
//                  Tyler Snoberger
// Lecturer's Name: Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class is the main driver method for the GUI
 * 
 * @author Nick Stoffel
 * @author Erik Umhoefer
 * @author Stephen Squires III
 * @author Tyler Snoberger
 */
public class Main extends Application {

	private static Bracket bball;
	private GridPane gPane;
	private Label[][] teamLabels;
	private TextField[][] teamScores;
	private Button[][] submitButtons;
	private Label[][] gameLabels;
	private Label winner, second, third;

	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Creates the stage / shows the GUI application.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tournament Bracket");
			gPane = new GridPane();
			gPane.setPadding(new Insets(10, 10, 10, 10));
			Scene scene = new Scene(gPane, 1400, 1000, Color.WHITE);
			slotSetup();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method of the program, sets up the teams and bracket from
	 * a file path from args[0] or a hardcoded file path.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> teams = new ArrayList<String>();

		if (args.length == 0) {
			File file = new File("src\\application\\16Team.txt"); // Change the
			// name to get the correct team#
			try {
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					teams.add(scan.nextLine().trim());
				}
				scan.close();
			} catch (FileNotFoundException e) {
				System.out.println("file not found");
			}
		}
		else {
			File file = new File(args[0]);
			try {
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					teams.add(scan.nextLine().trim());
				}
				scan.close();
			} catch (FileNotFoundException e) {
				System.out.println("file not found");
			}
		}

		int round = 0;
		switch (teams.size()) {
		case 1:
			round = 0;
			break;
		case 2:
			round = 1;
			break;
		case 4:
			round = 2;
			break;
		case 8:
			round = 3;
			break;
		case 16:
			round = 4;
			break;
		}
		bball = new Bracket(round);
		bball.loadTeams(teams);
		launch(args);

	}

	/**
	 * Initial setup of bracket - loads teams and adds components to GUI.
	 */
	private void slotSetup() {

		int numTeams = bball.getNumTeams(); // Used for # of rows for arrays
		if (numTeams > 0) {
			winner = new Label("Winner: ");
			second = new Label("Second: ");
			third = new Label("Third: ");
			winner.setFont(Font.font(18));
			second.setFont(Font.font(18));
			third.setFont(Font.font(18));

			if (numTeams > 1) {
				int numRounds = (int) (Math.log(numTeams) / Math.log(2) + 1); // Used for # of cols for arrays

				teamLabels = new Label[numTeams + 1][numRounds];
				teamScores = new TextField[numTeams][numRounds];
				submitButtons = new Button[numTeams / 2][numRounds]; // Needs to be half as many rows for
				// 1 submit button per 2 teams
				gameLabels = new Label[numTeams / 2][numRounds];

				// Initializes children for gridpane
				for (int i = 0; i < numTeams; i++) {
					teamLabels[i][0] = new Label(bball.getTeamName(i, 0)); // These inner-for loop lines put in the
					// initial teams
					teamScores[i][0] = new TextField();
					teamLabels[i][0].setMinWidth(50);
					teamScores[i][0].setMaxWidth(75);
					teamScores[i][0].setPromptText("Enter Score");
					if (i % 2 == 0)
						submitButtons[i / 2][0] = new Button("Submit");
					for (int j = 1; j < numRounds - 1; j++) { // Puts in children for remaining games
						if (j == 3) {
							if (i < 2) {
								teamLabels[i][j] = new Label("Winner Prev Game "); // Label for winner of the previous
								// game
								teamScores[i][j] = new TextField(); // Blank score text field
								teamScores[i][j].setMaxWidth(75);
								if (i % 2 == 0)
									submitButtons[i / 2][j] = new Button("");
							}

						} else {

							if (i < numTeams / (j + 1)) { // Only puts in children for the amount of games to be played

								teamLabels[i][j] = new Label("Winner Prev Game "); // Label for winner of the previous
								// game
								teamScores[i][j] = new TextField(); // Blank score text field
								teamScores[i][j].setMaxWidth(75);
								if (i % 2 == 0)
									submitButtons[i / 2][j] = new Button("");
							}
						}
					}

				}

				int games = 0;

				//Creates labels for each match
				for (int col = 0; col < numRounds - 1; col++) {
					for (int row = 0; row < numTeams / Math.pow(2, col + 1); row++) {
						games++;
						gameLabels[row][col] = new Label("Game " + games);
					}
				}

				for (int i = 0; i < numTeams; i += 2) { // Goes up by 2 to add 2 teams per cycle
					for (int j = 0; j < numRounds - 1; j++) { // Goes through every column except the winning team
						if (teamLabels[i + 1][j] != null) { // Only adds the children that have been constructed
							gPane.add(gameLabels[i / 2][j], (j * 4), i * 4);
							gPane.add(teamLabels[i][j], (j * 4) + 2, (i * 4) + 1);
							gPane.add(teamScores[i][j], (j * 4) + 3, (i * 4) + 1);
							if (j < numRounds - 2) {
								action(submitButtons[i / 2][j], teamLabels[i][j], teamScores[i][j],
										teamLabels[i + 1][j], teamScores[i + 1][j], teamLabels[i / 2][j + 1],
										submitButtons[i / 4][j + 1], i, j);

							} else
								winnerAction(submitButtons[i / 2][j], teamLabels[i][j], teamScores[i][j],
										teamLabels[i + 1][j], teamScores[i + 1][j], i, j);

							gPane.add(submitButtons[i / 2][j], (j * 4) + 3, (i * 4) + 2);
							gPane.add(teamLabels[i + 1][j], (j * 4) + 2, (i * 4) + 3);
							gPane.add(teamScores[i + 1][j], (j * 4) + 3, (i * 4) + 3);
						}
					}
				}

				gPane.add(new Label("Placements "), ((numRounds - 1) * 4), 0);

				gPane.add(winner, ((numRounds - 1) * 4) + 1, 1);
				gPane.add(second, ((numRounds - 1) * 4) + 1, 2);
				if (numTeams > 2)
					gPane.add(third, ((numRounds - 1) * 4) + 1, 3);
			} else {
				winner.setText(winner.getText() + bball.getTeam(0, 0));
				gPane.add(winner, 0, 0);
			}
		}

	}

	/**
	 * Returns true if the string only has digits in it
	 * 
	 * @param s The input of the text label to be determined
	 * if it is an integer / acceptable number
	 * @return true if the input is a number / acceptable input
	 */
	private boolean isNum(String s) {
		if (s.isEmpty())
			return false;
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}


	/*
	 * Handler method for each match that handles all user input / interaction and it's associated output / effect on the GUI
	 * @parameter submit is the submit button of the current match
	 * @parameter team1 is the label of team1 of the current match
	 * @parameter team1Score is the score found in the textfield of the current match for team1
	 * @parameter team2 is the label of team2 of the current match
	 * @parameter team2Score is the score found in the textfield of the current match for team2
	 * @parameter win is the label in the next round of the bracket that will be the winner of the current match
	 */
	/**
	 * Handler method for each match that handles all user input / interaction and it's associated output / effect on the GUI.
	 * 
	 * @param submit The submit button of the current match.
	 * @param team1 The label of team1 of the current match.
	 * @param team1Score The score found in the textfield of the current match for team1.
	 * @param team2 The label of team2 of the current match.
	 * @param team2Score The score found in the textfield of the current match for team2.
	 * @param win The label in the next round of the bracket that will be the winner of the current match.
	 * @param next The submit button of the next match.
	 * @param row Row of the team.
	 * @param col Column of the team.
	 */
	private void action(Button submit, Label team1, TextField team1Score, Label team2, TextField team2Score, Label win,
			Button next, int row, int col) {
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				/*
				 * Checks if both games that will constitute the next match have been finished
				 * If only one match has been finished / submitted, an alert is portrayed
				 */
				if (!(submit.getText().equals("Submit") || next.getText().equals("Needs Other Team"))) {
					Alert alert = new Alert(AlertType.WARNING, "Cannot submit without previous games played.");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				} 
				/*
				 * Both matches that will constitute the next match have been submitted / finished
				 */
				else if (isNum(team1Score.getText()) && isNum(team2Score.getText())) {
					//Team 1 is the winner of the match
					if (Integer.valueOf(team1Score.getText()) > Integer.valueOf(team2Score.getText())) {
						//Set score of team 1 to score in text field
						bball.getTeam(row, col).setScore(Integer.parseInt(team1Score.getText()));
						//Set score of team 2 to score in text field
						bball.getTeam(row + 1, col).setScore(Integer.parseInt(team2Score.getText()));
						//Update the winner in bracket to team1
						bball.updateWinner(row + 1, Integer.parseInt(team2Score.getText()),
								Integer.parseInt(team1Score.getText()));
						//Set text of label for next match to the winner (team 1)
						win.setText(team1.getText());
						team1Score.setEditable(false);
						team2Score.setEditable(false);
						submit.setText(""); //Close submit button
						//Set submit button of next match to submit
						if (next.getText().equals(" "))
							next.setText("Submit");
						//Match that constitutes other team in next match not submitted yet
						else
							next.setText(" ");
					}
					//Team scores are the same, portray alert message
					else if (Integer.valueOf(team1Score.getText()) == Integer.valueOf(team2Score.getText())) {
						Alert alert = new Alert(AlertType.WARNING, "Team Scores are the same.");
						alert.showAndWait().filter(response -> response == ButtonType.OK);
					}
					//Team 2 is the winner of the match
					else {
						//Set score of team 1 to score in text field
						bball.getTeam(row, col).setScore(Integer.parseInt(team1Score.getText()));
						//Set score of team 2 to score in text field
						bball.getTeam(row + 1, col).setScore(Integer.parseInt(team2Score.getText()));
						//Update the winner in bracket to team2
						bball.updateWinner(row + 1, Integer.parseInt(team2Score.getText()),
								Integer.parseInt(team1Score.getText()));
						win.setText(team2.getText());
						team1Score.setEditable(false);
						team2Score.setEditable(false);
						submit.setText("");
						//Set submit button of next match to submit
						if (next.getText().equals(" "))
							next.setText("Submit");
						//Match that constitutes other team in next match not submitted yet
						else
							next.setText(" ");
					}

				}
				//Incorrect input, portrays an error message
				else {
					Alert alert = new Alert(AlertType.WARNING, "Input must be an integer.");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				}
			}

		});
	}

	/**
	 * Handler method for the final placings of the match. Displays first, second, and third place champions.
	 * 
	 * @param submit The submit button of the current match.
	 * @param team1 The label of team1 of the current match.
	 * @param team1Score The score found in the textfield of the current match for team1.
	 * @param team2 The label of team2 of the current match.
	 * @param team2Score The score found in the textfield of the current match for team2.
	 * @param row Row of the team.
	 * @param col Column of the team.
	 */
	private void winnerAction(Button submit, Label team1, TextField team1Score, Label team2, TextField team2Score,
			int row, int col) {
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				/*
				 * Checks if both games that will constitute the next match have been finished
				 * If only one match has been finished / submitted, an alert is portrayed
				 */
				if (!(submit.getText().equals("Submit"))) {
					Alert alert = new Alert(AlertType.WARNING, "Cannot submit without previous games played.");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				}
				/*
				 * Both matches that will constitute the next match have been submitted / finished
				 */
				else if (isNum(team1Score.getText()) && isNum(team2Score.getText())) {
					//Team 1 is the winner of the match
					if (Integer.valueOf(team1Score.getText()) > Integer.valueOf(team2Score.getText())) {
						if (bball.getNumTeams() > 2) {
							String thirdPlace = "";

							double maxScore = (int) Math.max(
									Math.min(bball.getTeam(row, col - 1).getScore(),
											bball.getTeam(row + 1, col - 1).getScore()),
									Math.min(bball.getTeam(row + 2, col - 1).getScore(),
											bball.getTeam(row + 3, col - 1).getScore()));
							if (bball.getTeam(row, col - 1).getScore() == maxScore) {
								thirdPlace = bball.getTeam(row, col - 1).getTeamName();
							} else if (bball.getTeam(row + 1, col - 1).getScore() == maxScore) {
								thirdPlace = bball.getTeam(row + 1, col - 1).getTeamName();
							} else if (bball.getTeam(row + 2, col - 1).getScore() == maxScore) {
								thirdPlace = bball.getTeam(row + 2, col - 1).getTeamName();
							} else {
								thirdPlace = bball.getTeam(row + 3, col - 1).getTeamName();
							}

							third.setText(third.getText() + thirdPlace);
						}
						team1Score.setEditable(false);
						team2Score.setEditable(false);
						submit.setText("");
						winner.setText(winner.getText() + team1.getText());
						second.setText(second.getText() + team2.getText());
					}
					//Team scores are the same, portray alert message
					else if (Integer.valueOf(team1Score.getText()) == Integer.valueOf(team2Score.getText())) {
						Alert alert = new Alert(AlertType.WARNING, "Team Scores are the same.");
						alert.showAndWait().filter(response -> response == ButtonType.OK);
					} else {
						if (bball.getNumTeams() > 2) {
							String thirdPlace = "";
							//Gets the next highest score of the Semi-Finals that wasn't the 2 winners
							double maxScore = (int) Math.max(
									Math.min(bball.getTeam(row, col - 1).getScore(),
											bball.getTeam(row + 1, col - 1).getScore()),
									Math.min(bball.getTeam(row + 2, col - 1).getScore(),
											bball.getTeam(row + 3, col - 1).getScore()));
							//Sets thirdPlace to be the correct 3rd place team.
							if (bball.getTeam(row, col - 1).getScore() == maxScore) {
								thirdPlace = bball.getTeam(row, col - 1).getTeamName();
							} else if (bball.getTeam(row + 1, col - 1).getScore() == maxScore) {
								thirdPlace = bball.getTeam(row + 1, col - 1).getTeamName();
							} else if (bball.getTeam(row + 2, col - 1).getScore() == maxScore) {
								thirdPlace = bball.getTeam(row + 2, col - 1).getTeamName();
							} else {
								thirdPlace = bball.getTeam(row + 3, col - 1).getTeamName();
							}
							third.setText(third.getText() + thirdPlace);
						}
						team1Score.setEditable(false);
						team2Score.setEditable(false);
						submit.setText("");
						winner.setText(winner.getText() + team2.getText());
						second.setText(second.getText() + team1.getText());
					}

				}
				//Incorrect input, portrays an error message
				else {
					Alert alert = new Alert(AlertType.WARNING, "Input must be an integer.");
					alert.showAndWait().filter(response -> response == ButtonType.OK);
				}

			}

		});
	}
}