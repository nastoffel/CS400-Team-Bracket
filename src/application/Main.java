package application;

import java.util.ArrayList;

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
import javafx.scene.control.Button;
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
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tournament Bracket");
			gPane = new GridPane();
			Scene scene = new Scene(gPane, 800, 600, Color.DARKTURQUOISE);
			
			//gPane.setGridLinesVisible(true);
			
//			gPane.setMinHeight(bball.getNumTeams()*2+1);
//			gPane.setMaxHeight(((Math.log(bball.getNumTeams()) / Math.log(2)) * 3) + 1);
			
			Label blank = new Label();
			
			// Trying to get around the stupid grid pane collapsing
//			for(int i = 0; i < bball.getNumTeams()*2+1; i++)
//				gPane.add(new Label(), 0, i);
//			for(int i = 0; i < ((Math.log(bball.getNumTeams()) / Math.log(2)) * 3) + 1; i++)
//				gPane.add(new Label(), i, 0);
			
			
			slotSetup();
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ArrayList<String> teams = new ArrayList<String>();
		teams.add("01");
		teams.add("02");
		
		teams.add("03");
		teams.add("04");
		
		teams.add("05");
		teams.add("06");
		teams.add("07");
		teams.add("08");
		
//		teams.add("09");
//		teams.add("10");
//		teams.add("11");
//		teams.add("12");
//		teams.add("13");
//		teams.add("14");
//		teams.add("15");
//		teams.add("16");
		bball = new Bracket(3);
		bball.loadTeams(teams);
		launch(args);
	}
	
	private void slotSetup() {
		
		int numTeams = bball.getNumTeams(); // Used for # of rows for arrays
		int numGames = (int)(Math.log(bball.getNumTeams()) / Math.log(2)+1); // Used for # of cols for arrays
		
		teamLabels = new Label[numTeams][numGames];
		teamScores = new TextField[numTeams][numGames];
		submitButtons = new Button[numTeams/2][numGames]; // Needs to be half as many rows for 
				// 1 submit button per 2 teams
		
		//Testing code probably not needed anymore
		int[][] num = new int[numTeams][numGames];
		for(int i = 0; i < num.length; i++) {
			for(int j = 0; j < num[i].length; j++) {
				num[i][j] = 0;
			}
		}
		
		// Initializes children for gridpane
		for(int i = 0; i < numTeams; i++) {
			teamLabels[i][0] = new Label(bball.getTeam(i, 0)); // These inner-for loop lines put in the initial teams
			teamScores[i][0] = new TextField();
			teamLabels[i][0].setMinWidth(50);
			teamLabels[i][0].setTextAlignment(TextAlignment.RIGHT); // Doesn't work. Trying it to right align.
			teamScores[i][0].setPromptText("Enter Score");
			if(i%2 == 0)
				submitButtons[i/2][0] = new Button("Submit");
			for(int j = 1; j < numGames; j++) { // Puts in children for remaining games
				if(i < numTeams/(j+1)) { // Only puts in children for the amount of games to be played
					teamLabels[i][j] = new Label("Winner Game "); // Label for winner of the previous game
					teamScores[i][j] = new TextField(); // Blank score text field
					if(i%2 == 0)
						submitButtons[i/2][j] = new Button("Submit");
				}
			}
		}
		
		
		for(int i = 0; i < numTeams; i+=2) { // Goes up by 2 to add 2 teams per cycle
			for(int j = 0; j < numGames-1; j++) { // Goes through every column except the winning team
				if(teamLabels[i+1][j] != null) { // Only adds the children that have been constructed
					gPane.add(new Label("Blank "), (j*4)+3, i*4);
					gPane.add(teamLabels[i][j], (j*4)+1, (i*4)+1);
					gPane.add(teamScores[i][j], (j*4)+2, (i*4)+1);
					if(i%2 == 0) // Only adds 1 submit button per 2 teams
						gPane.add(submitButtons[i/2][j], (j*4)+2, (i*4)+2);
					gPane.add(teamLabels[i+1][j], (j*4)+1, (i*4)+3);
					gPane.add(teamScores[i+1][j], (j*4)+2, (i*4)+3);
				}
			}
		}
//		gPane.add(teamLabels[0][numGames-1],1,1);
		
	}
}
