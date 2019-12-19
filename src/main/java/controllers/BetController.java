package controllers;

import java.time.format.DateTimeFormatter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import models.Bet;
import models.Game;
import models.dao.BetDao;
import models.dao.GameDao;

public class BetController {
	
	private Bet bet;
	
	private Game game;
	
	private GameDao gameDao;
	
	private BetDao betDao;

	@FXML
    private Label betNumber;

    @FXML
    private Label eventNumber;

    @FXML
    private Label type;

    @FXML
    private Label amount;

    @FXML
    private Label eventStatus;

    @FXML
    private Label odds;

    @FXML
    private Label possibleWin;
	
    @FXML
    private Label date;
    
    @FXML
    private Label signed;
    
	@FXML
	public void initialize() {
		gameDao = new GameDao();
		betDao = new BetDao();
		Platform.runLater(() -> {
			game = gameDao.get(bet.getGame().getGameId());
			betNumber.setText(betNumber.getText() + Integer.toString(bet.getBetId()));
			type.setText(type.getText() + Integer.toString(bet.getType()));
			amount.setText(amount.getText() + Double.toString(bet.getAmount()) + "$");
			
			eventNumber.setText(eventNumber.getText() + Integer.toString(game.getGameId()));
			double betOdds = 0;
			switch(bet.getType()) {
			case 1:
				betOdds = game.getOdds1();
				break;
			case 2:
				betOdds = game.getOdds2();
				break;
			case 3:
				betOdds = game.getOdds3();
				break;	
			}
			odds.setText(odds.getText() + Double.toString(betOdds) + "$");
			possibleWin.setText(possibleWin.getText() + Double.toString( Math.round(betOdds*bet.getAmount()*100)/100.0) + "$");
			
			int winner = game.getWinner();
			if(winner == 0) {
				eventStatus.setText(eventStatus.getText() + "Not resolved");
			}
			else if (winner == bet.getType()) {
				eventStatus.setText(eventStatus.getText() + "Win");
			}
			else {
				eventStatus.setText(eventStatus.getText() + "Loss");
			}
			
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		     String formatDateTime = bet.getBetTime().format(formatter);
			 date.setText(formatDateTime);
			
			if(bet.getState() == 1) signed.setVisible(true);
	    });
	}
	
	public void setBet(Bet bet) {
		this.bet = bet;
	}
	
    @FXML
    void signBet() {
    	if(game.getWinner() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("The event has not been resolved yet!");
			alert.showAndWait();	
    	}
    	else {
        	bet.setState(1);
        	betDao.update(bet);
        	signed.setVisible(true);
    	}
    }

}
