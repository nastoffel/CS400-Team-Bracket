package application;
import java.util.ArrayList;

public class Bracket {
	private Team[][] bracket;
	private int round;
	private int numOfTeams;
	private int teamsLeft;
	private int currentRound;

	public Bracket() {
		bracket = new Team[0][0];
		round = 0;
		numOfTeams = 0;
		currentRound = 0;
	}

	public Bracket(int round) {
		if (round > 1) {
			this.numOfTeams = (int) (Math.pow(round, 2) / 2) * 2;
			bracket = new Team[numOfTeams][round + 1];
		} else {
			this.numOfTeams = 2;
			bracket = new Team[2][round + 1];
		}
		this.round = 0;
		this.currentRound = 0;
		this.teamsLeft = numOfTeams;

	}

	/**
	 * loads all of the team names from an ArrayList of strings into the first collumn of the bracket
	 * @param stringTeams ArrayList of string team names
	 * @return the bracket 
	 */
	public Team[][] loadTeams(ArrayList<String> stringTeams) {
		// create an arraylist of teams with team names and seeds
		ArrayList<Team> teams = new ArrayList<Team>();
		numOfTeams = stringTeams.size();
		for (int i = 0; i < numOfTeams; i++) {
			teams.add(new Team(stringTeams.get(i), i + 1));
		}

		// if there is more than 2 teams seed them
		if (teams.size() > 2) {
			teams = seedTeams(teams);
		}
		// enter the first round into the brackt [][] array of teams
		for (int i = 0; i < numOfTeams; i++) {
			bracket[i][0] = teams.get(i);
		}
		return bracket;
	}

	/**
	 * updates the winner of a specific game index depending on the highest score
	 * @param gameIndex index of the game i.e.{(game 1: 1),(game 2: 3),(game 3: 5),(game 4: 7)}
	 * @param t1Score score of top team in bracket
	 * @param t2Score score of bottom team in bracket
	 * @return the winning team
	 */
	public Team updateWinner(int gameIndex, int t1Score, int t2Score) {
		// if all games from a single round have played
		if (teamsLeft == numOfTeams / 2) {
			// update private variable to reflect the new round
			numOfTeams = teamsLeft;
			round++;
		}
		// not sure if this is what we want to return forever but temporarily this is
		// here
		if (t1Score == t2Score) {
			return null;
		} else if (t1Score < t2Score) {
			//if team 1 won then assign it to the next round relative to this games index
			bracket[gameIndex / 2][round + 1] = bracket[gameIndex - 1][round];
		} else
			//if team 2 won then assign it to the next round relative to this games index
			bracket[gameIndex / 2][round + 1] = bracket[gameIndex][round];
		teamsLeft--;
		return null;
	}

	/**
	 * seeds all teams from an ArrayList of teams as described in the seeding guidlines
	 * @param unSeededTeams list of teams that needs to be seeded
	 * @return a seeded ArrayList of teams
	 */
	private ArrayList<Team> seedTeams(ArrayList<Team> unSeededTeams) {
		ArrayList<Team> seededTeams = new ArrayList<Team>();
		int size = unSeededTeams.size();

		switch (size) {
		// for a 4 team tourney
		case 4: {
			seededTeams.add(unSeededTeams.get(0));
			seededTeams.add(unSeededTeams.get(3));
			seededTeams.add(unSeededTeams.get(2));
			seededTeams.add(unSeededTeams.get(1));
			break;
		}
		// for an 8 team tourney
		case 8: {
			// 1 vs 8
			seededTeams.add(unSeededTeams.get(0));
			seededTeams.add(unSeededTeams.get(7));
			// 3 vs 6
			seededTeams.add(unSeededTeams.get(2));
			seededTeams.add(unSeededTeams.get(5));

			// 4 vs 5
			seededTeams.add(unSeededTeams.get(4));
			seededTeams.add(unSeededTeams.get(3));
			// 2 vs 7
			seededTeams.add(unSeededTeams.get(6));
			seededTeams.add(unSeededTeams.get(1));
			break;

		}
		// for a 16 team tourney
		case 16: {
			// 1 vs 16
			seededTeams.add(unSeededTeams.get(0));
			seededTeams.add(unSeededTeams.get(15));
			// 8 vs 9
			seededTeams.add(unSeededTeams.get(7));
			seededTeams.add(unSeededTeams.get(8));
			// 4 vs 13
			seededTeams.add(unSeededTeams.get(3));
			seededTeams.add(unSeededTeams.get(12));
			// 5 vs 12
			seededTeams.add(unSeededTeams.get(4));
			seededTeams.add(unSeededTeams.get(11));

			// 2 vs 15
			seededTeams.add(unSeededTeams.get(1));
			seededTeams.add(unSeededTeams.get(14));
			// 7 vs 10
			seededTeams.add(unSeededTeams.get(6));
			seededTeams.add(unSeededTeams.get(9));
			// 3 vs 14
			seededTeams.add(unSeededTeams.get(2));
			seededTeams.add(unSeededTeams.get(13));
			// 6 vs 11
			seededTeams.add(unSeededTeams.get(5));
			seededTeams.add(unSeededTeams.get(10));
			break;
		}
		}

		return seededTeams;
	}

	/**
	 * prints out a console text representation of what is in the bracket class
	 */
	public String toString() {
		String ret = "";
		System.out.println("____________________________");
		for (int i = 0; i < bracket.length; i++) {
			for (int c = 0; c < bracket[0].length; c++) {
				if (bracket[i][c] != null) {
					if (c == 0)
						ret += "| ";
					ret += " " + bracket[i][c].getTeamName() + " |";
				} else
					ret += "    |";

			}
			
			ret += "\n";
			ret += "---------------------------\n";

		}
		return ret;
	}
	
	public int getNumTeams() {
		return numOfTeams;
	}
	public int getCurrRound()
	{
		return currentRound;
	}
	public void nextRound()
	{
		currentRound++;
	}
	public Team getTeam(int x, int y) {
		if(x < 0 || x >= numOfTeams || y < 0 || y >= bracket[0].length) 
			throw new ArrayIndexOutOfBoundsException();
		else
			return bracket[x][y];
	}
	public String getTeamName(int x, int y) {
		if(x < 0 || x >= numOfTeams || y < 0 || y >= bracket[0].length) 
			throw new ArrayIndexOutOfBoundsException();
		else
			return bracket[x][y].getTeamName();
	}
}
