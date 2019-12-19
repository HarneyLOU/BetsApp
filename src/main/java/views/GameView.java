package views;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameView {
	
	private SimpleIntegerProperty gameId;

	private SimpleStringProperty category;
	
	private LocalDateTime date;
	
	private SimpleStringProperty firstName;
	
	private SimpleStringProperty secondName;
	
	private SimpleStringProperty winner;
	
	private SimpleDoubleProperty odds1;

	private SimpleDoubleProperty odds2;
	
	private SimpleDoubleProperty odds3;
	
	public GameView(int id, String c, LocalDateTime d, String f, String s, String w, double o1, double o2, double o3) {
		gameId = new SimpleIntegerProperty(id);
		category = new SimpleStringProperty(c);
		date = d;
		firstName = new SimpleStringProperty(f);
		secondName = new SimpleStringProperty(s);
		winner = new SimpleStringProperty(w);
		odds1 = new SimpleDoubleProperty(o1);
		odds2 = new SimpleDoubleProperty(o2);
		odds3 = new SimpleDoubleProperty(o3);
	}
	
	public int getGameId() {
		return gameId.get();
	}
	
	public SimpleIntegerProperty getGameIdProperty() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId.set(gameId);
	}

	public String getCategory() {
		return category.get();
	}
	
	public SimpleStringProperty getCategoryProperty() {
		return category;
	}

	public void setCategory(String category) {
		this.category.set(category);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getSecondName() {
		return secondName.get();
	}

	public void setSecondName(String secondName) {
		this.secondName.set(secondName);
	}

	public String getWinner() {
		return winner.get();
	}
	
	public SimpleStringProperty getWinnerProperty() {
		return winner;
	}

	public void setWinner(String w) {
		this.winner.set(w);
	}

	public double getOdds1() {
		return odds1.get();
	}

	public void setOdds1(SimpleDoubleProperty odds1) {
		this.odds1 = odds1;
	}

	public double getOdds2() {
		return odds2.get();
	}

	public void setOdds2(SimpleDoubleProperty odds2) {
		this.odds2 = odds2;
	}

	public double getOdds3() {
		return odds3.get();
	}

	public void setOdds3(SimpleDoubleProperty odds3) {
		this.odds3 = odds3;
	}
}
