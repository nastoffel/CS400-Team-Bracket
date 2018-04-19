import java.util.ArrayList;

public class Bracket {
	private Team[][] bracket;
	private int round;
	private int numOfTeams;
	private int teamsLeft;

	public Bracket() {
		bracket = new Team[0][0];
		round = 0;
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
		this.teamsLeft = numOfTeams;

	}

	public Team[][] loadTeams(ArrayList<String> teams) {
		if (teams.size() > 2) {
			teams = seedTeams(teams);
		}
		for (int i = 0; i < numOfTeams; i++) {
			bracket[i][0] = new Team(teams.get(i), i);
		}
		return bracket;
	}

	public Team updateWinner(int gameIndex, int t1Score, int t2Score) {
		if (teamsLeft == numOfTeams / 2) {
			numOfTeams = teamsLeft;
			round++;
		}
		if (t1Score == t2Score) {
			return null;
		} else if (t1Score > t2Score) {
			bracket[gameIndex / 2][round + 1] = bracket[gameIndex - 1][round];
		} else
			bracket[gameIndex / 2][round + 1] = bracket[gameIndex][round];
		teamsLeft--;
		return null;
	}

	private ArrayList<String> seedTeams(ArrayList<String> unSeededTeams) {
		ArrayList<String> seededTeams = new ArrayList<String>();
		int size = unSeededTeams.size();

		switch (size) {
		case 4: {
			seededTeams.add(unSeededTeams.get(0));
			seededTeams.add(unSeededTeams.get(3));
			seededTeams.add(unSeededTeams.get(2));
			seededTeams.add(unSeededTeams.get(1));
			break;
		}
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

	public String toString() {
		String ret = "";
		System.out.println("_______________________________________________________________");
		for (int i = 0; i < bracket.length; i++) {
			for (int c = 0; c < bracket[0].length; c++) {
				if (bracket[i][c] != null) {
					if (c == 0)
						ret += "| ";
					ret += " " + bracket[i][c].getTeamName() + " |";
				} else
					
					ret += "   |";

			}
			ret += "\n";

		}
		return ret;
	}
}
