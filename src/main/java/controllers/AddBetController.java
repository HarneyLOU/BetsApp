package controllers;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import models.Bet;
import models.dao.BetDao;
import models.dao.GameDao;
import views.GameView;

public class AddBetController {

	private EventsController eventsController;

	@FXML
	private AnchorPane addBetForm;

	@FXML
	private Label alert;

	@FXML
	private TextField eventNumberField;

	@FXML
	private TextField amountField;

	@FXML
	private ComboBox<String> teamsBox;
	
	private GameView game;
	
	private GameDao gameDao;
	
	private BetDao betDao;
	
	private Bet bet;

	@FXML
	public void initialize() {
		gameDao = new GameDao();
		betDao = new BetDao();
	}
	
	protected void setGame(GameView game) {
		this.game = game;
		eventNumberField.setText(Integer.toString(game.getGameId()));
		teamsBox.getItems().clear();
		teamsBox.getItems().addAll(game.getFirstName(), game.getSecondName(), "Draw");
	}

	@FXML
	void addBet() {
		String error = "";
		
		eventsController.eventsPane.getChildren().remove(addBetForm);
		bet = new Bet();
		bet.setGame(gameDao.get(Integer.parseInt(eventNumberField.getText())));
		
		bet.setDate(LocalDateTime.now());
		
		try {
			bet.setAmount(Double.parseDouble(amountField.getText()));
		}
		catch(NumberFormatException e) {
			error = "Wrong amount!";
		}
		
		int type = 0;
		if(game.getFirstName() == teamsBox.getValue()) type = 1;
		else if(game.getSecondName() == teamsBox.getValue()) type = 2;
		else if("Draw" == teamsBox.getValue()) type = 3;
		else error = "Team not selected!";
		bet.setType(type);
		
		addAlert(bet, error);

	}
	
	public void addAlert(Bet bet, String error) {

		if(error.isEmpty()) {
			betDao.add(bet);
			Bet addedBet = betDao.get(bet.getBetId());
			if(addedBet != null) {
				eventsController.homeController.betsController.showBet(addedBet);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				String s = "Your bet with number " + addedBet.getBetId() + " has been successfully added!";
				alert.setContentText(s);
				alert.showAndWait();	
				amountField.clear();
			}	
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(error);
			alert.showAndWait();	
		}	
	}

	public void setParentController(EventsController eventsController) {
		this.eventsController = eventsController;
	}
}
