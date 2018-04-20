package application;

public class Team {
	private String teamName;
	private int seed;
	
	/**
	 * default no arg constructor
	 */
	public Team() {
		teamName = "";
		seed = 0;
	}
	
	/**
	 * constructs a Team object with a name and a seed
	 * @param teamName name of the team
	 * @param seed seed in the tournament
	 */
	public Team(String teamName, int seed) {
		this.teamName = teamName;
		this.seed = seed;
	}
	
	/**
	 * returns string of team name
	 * @return teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * returns team seed
	 * @return seed
	 */
	public int getSeed() {
		return seed;
	}
}
