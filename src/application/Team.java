///////////////////////////////////////////////////////////////////////////////
//
// Main Class File: Main.java
// File:            Team.java
// Semester:        Spring 2018
//
// Authors:         Nick Stoffel, Erik Umhoefer,  Stephen Squires III,
//                  Tyler Snoberger
// Lecturer's Name: Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////
package application;

/**
 * This class contains the teamName, seed, and score
 * 
 * @author Nick Stoffel
 * @author Erik Umhoefer
 * @author Stephen Squires III
 * @author Tyler Snoberger
 */
public class Team {
	private String teamName;
	private int seed;
	private int score;

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

	/*
	 * @return score of current game
	 */
	public int getScore(){
		return score;
	}
	/*
	 * Sets score of the current game
	 */
	public void setScore(int score){
		this.score = score;	
	}
}