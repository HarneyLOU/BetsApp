package models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "GAME")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GameId")
	private int gameId;

	@ManyToOne
    @JoinColumn(name = "CategoryId")
	private Category category;
	
	@Column(name = "Date")
	private LocalDateTime date;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "SecondName")
	private String secondName;
	
	@Column(name = "Winner")
	private int winner;
	
	@Column(name = "odds1")
	private double odds1;

	@Column(name = "odds2")
	private double odds2;
	
	@Column(name = "odds3")
	private double odds3;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "GameId")
    Set<Bet> bets = new HashSet<Bet>();
	
	public Set<Bet> getBets() {
		return bets;
	}

	public void setBets(Set<Bet> bets) {
		this.bets = bets;
	}

	public Game() {
		
	}
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public double getOdds1() {
		return odds1;
	}

	public void setOdds1(double odds1) {
		this.odds1 = odds1;
	}

	public double getOdds2() {
		return odds2;
	}

	public void setOdds2(double odds2) {
		this.odds2 = odds2;
	}

	public double getOdds3() {
		return odds3;
	}

	public void setOdds3(double odds3) {
		this.odds3 = odds3;
	}
}