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
			
			gPane.setGridLinesVisible(true);
			
//			gPane.setMinHeight(bball.getNumTeams()*2+1);
//			gPane.setMaxHeight(((Math.log(bball.getNumTeams()) / Math.log(2)) * 3) + 1);
			
			Label blank = new Label();
			Label title = new Label();
			title.setAlignment(Pos.TOP_CENTER);
			title.setMinHeight(25);
			title.setText("Tournament Bracket");
			
//			for(int i = 0; i < bball.getNumTeams()*2+1; i++)
//				gPane.add(new Label(), 0, i);
//			for(int i = 0; i < ((Math.log(bball.getNumTeams()) / Math.log(2)) * 3) + 1; i++)
//				gPane.add(new Label(), i, 0);
			
			
			slotSetup();
			gPane.add(title, 1, 0);
			GridPane.setHalignment(title, HPos.CENTER);
			
			
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
		
		int numTeams = bball.getNumTeams();
		int numGames = (int)(Math.log(bball.getNumTeams()) / Math.log(2));
		teamLabels = new Label[numTeams][numGames];
		teamScores = new TextField[numTeams][numGames];
		submitButtons = new Button[numTeams/2][numGames];
		for(int i = 0; i < numTeams; i++) {
			teamLabels[i][0] = new Label(bball.getTeam(i, 0));
			teamScores[i][0] = new TextField();
			teamScores[i][0].setPromptText("Enter Score");
			if(i%2 == 0)
				submitButtons[i/2][0] = new Button("Submit");
			for(int j = 1; j < numGames; j++) {
				if(i/(Math.pow(2,j)) >= 0) {
					teamLabels[i][j] = new Label();
					teamScores[i][j] = new TextField();
					if(i%2 == 0)
						submitButtons[i/2][j] = new Button("Submit");
				}
			}
		}
		
		for(int i = 0; i < numTeams; i+=2) {
			for(int j = 0; j < numGames; i++) {
				if(teamLabels[i+1][j] != null) {
					gPane.add(new Label(), (j*4)+3, i*4);
					gPane.add(teamLabels[i][j], (j*4)+1, (i*4)+1);
					gPane.add(teamScores[i][j], (j*4)+2, (i*4)+1);
					if(i%2 == 0)
						gPane.add(submitButtons[i/2][j], (j*4)+2, (i*4)+2);
					gPane.add(teamLabels[i+1][j], (j*4)+1, (i*4)+3);
					gPane.add(teamScores[i+1][j], (j*4)+2, (i*4)+3);
				}
			}
		}
		
	}
}
