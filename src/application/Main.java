package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Main extends Application {

	private static Bracket bball;
	private GridPane gPane;
	private Label[][] teamLabels;
	private TextField[][] teamScores;
	private Button[][] submitButtons;
	private Label[][] gameLabels;
	private Label winner, second, third;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tournament Bracket");
			gPane = new GridPane();
			gPane.setPadding(new Insets(10,10,10,10));
			Scene scene = new Scene(gPane, 1200, 700, Color.WHITE);
			slotSetup();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ArrayList<String> teams = new ArrayList<String>();

		if (args.length == 0) {
			File file = new File("src\\application\\Teams\\16Team.txt"); // Change the name to get the correct team#
			try {
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					teams.add(scan.nextLine().trim());
				}
				scan.close();
			} catch (FileNotFoundException e) {
				System.out.println("file not found");
			}
		} else {
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

	private void slotSetup() {

		int numTeams = bball.getNumTeams(); // Used for # of rows for arrays
		if(numTeams > 0) {
			winner = new Label("Winner: ");
			second = new Label("Second: ");
			third = new Label("Third: ");
			winner.setFont(Font.font(18));
			second.setFont(Font.font(18));
			third.setFont(Font.font(18));
			
			if(numTeams > 1) {
				int numRounds = (int) (Math.log(numTeams) / Math.log(2) + 1); // Used for # of cols for arrays

				teamLabels = new Label[numTeams+1][numRounds];
				teamScores = new TextField[numTeams][numRounds];
				submitButtons = new Button[numTeams / 2][numRounds]; // Needs to be half as many rows for
				// 1 submit button per 2 teams
				gameLabels = new Label[numTeams/2][numRounds];

				// Initializes children for gridpane
				for (int i = 0; i < numTeams; i++) {
					teamLabels[i][0] = new Label(bball.getTeam(i, 0)); // These inner-for loop lines put in the initial teams
					teamScores[i][0] = new TextField();
					teamLabels[i][0].setMinWidth(50);
					teamScores[i][0].setMaxWidth(75);
					teamScores[i][0].setPromptText("Enter Score");
					if (i % 2 == 0) {
						submitButtons[i / 2][0] = new Button("Submit");
					}
					for (int j = 1; j < numRounds - 1; j++) { // Puts in children for remaining games
						if (j == 3) {
							if (i < 2) {
								teamLabels[i][j] = new Label(""); // Label for winner of the previous game
								teamScores[i][j] = new TextField(); // Blank score text field
								teamScores[i][j].setMaxWidth(75);
								if (i % 2 == 0)
									submitButtons[i / 2][j] = new Button("");
							}

						} else {

							if (i < numTeams / (j + 1)) { // Only puts in children for the amount of games to be played

								teamLabels[i][j] = new Label(""); // Label for winner of the previous game
								teamScores[i][j] = new TextField(); // Blank score text field
								teamScores[i][j].setMaxWidth(75);
								if (i % 2 == 0)
									submitButtons[i / 2][j] = new Button("");
							}
						}
					}

				}

				int games = 0;

				for(int col = 0; col < numRounds - 1; col++) {
					for(int row = 0; row < numTeams/Math.pow(2, col+1); row++) {
						games ++;
						gameLabels[row][col] = new Label("Game " + games);
					}
				}

				for (int i = 0; i < numTeams; i += 2) { // Goes up by 2 to add 2 teams per cycle
					for (int j = 0; j < numRounds - 1; j++) { // Goes through every column except the winning team
						if (teamLabels[i + 1][j] != null) { // Only adds the children that have been constructed
							gPane.add(gameLabels[i/2][j], (j * 4), i * 4);
							gPane.add(teamLabels[i][j], (j * 4) + 2, (i * 4) + 1);
							gPane.add(teamScores[i][j], (j * 4) + 3, (i * 4) + 1);
							if(j<numRounds-2)
								action(submitButtons[i/2][j],teamLabels[i][j], teamScores[i][j],teamLabels[i+1][j], teamScores[i+1][j], teamLabels[i/2][j+1], submitButtons[i/4][j+1]);
							else
								System.out.println("Winner Button");
							gPane.add(submitButtons[i / 2][j], (j * 4) + 3, (i * 4) + 2);
							gPane.add(teamLabels[i + 1][j], (j * 4) + 2, (i * 4) + 3);
							gPane.add(teamScores[i + 1][j], (j * 4) + 3, (i * 4) + 3);
						}
					}
				}

				gPane.add(new Label("Placements "), ((numRounds-1) * 4), 0);

				gPane.add(winner, ((numRounds-1) * 4) +1, 1);
				gPane.add(second, ((numRounds-1) * 4) +1, 2);
				if(numTeams > 2)
					gPane.add(third, ((numRounds-1) * 4) +1, 3);
			}
			else {
				winner.setText(winner.getText() + bball.getTeam(0, 0));
				gPane.add(winner, 0, 0);
			}
		}

	}
	
	private boolean isNum(String s)
	{
		if(s.isEmpty())
			return false;
	    for (char c : s.toCharArray())
	    {
	        if (!Character.isDigit(c)) 
	        	return false;
	    }
	    return true;
	}
	
	private void action(Button submit, Label team1, TextField team1Score, Label team2, TextField team2Score, Label win, Button next)
	{
				submit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					if(!isNum(team1Score.getText()) && !isNum(team2Score.getText()))
					{
						Alert alert = new Alert(AlertType.WARNING, "Input must be an integer");
						alert.showAndWait().filter(response -> response == ButtonType.OK);
					}
					if(submit.getText().equals("Submit") && isNum(team1Score.getText()) && isNum(team2Score.getText())) {
						if(Integer.valueOf(team1Score.getText()) > Integer.valueOf(team2Score.getText()))
						{
							win.setText(team1.getText());
							submit.setText("");
							next.setText("Submit");
						}
						else if(Integer.valueOf(team1Score.getText()) == Integer.valueOf(team2Score.getText()))
						{
							Alert alert = new Alert(AlertType.WARNING, "Team Scores are the same");
							alert.showAndWait().filter(response -> response == ButtonType.OK);
						}
						else
						{
							win.setText(team2.getText());
							submit.setText("");
							next.setText("Submit");
						}
				
					}
					else
						{
							
						}
				}
			});
	}
}