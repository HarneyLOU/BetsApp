package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.Bet;
import models.dao.BetDao;

public class BetsController {

	private BetDao betDao;

	@FXML
	private VBox betsPane;

	@FXML
	private AnchorPane betPane;

	@FXML
	private TabPane betsTabPane;

	@FXML
	private TextField betNumberField;

	@FXML
	public void initialize() {
		betDao = new BetDao();
	}

	@FXML
	void showCheckForm() throws IOException {
		if (!betNumberField.getText().isEmpty()) {
			Bet bet = betDao.get(Integer.parseInt(betNumberField.getText()));

			if (bet != null) {
				showBet(bet);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				String s = "Bet doesn't exist!";
				alert.setContentText(s);
				alert.showAndWait();
			}
		}
	}

	void showBet(Bet bet) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/project/bets/Bet.fxml"));
		try {
			betPane = loader.load();
			BetController betController = loader.getController();
			betController.setBet(bet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Tab tab = new Tab();
		tab.setText("Bet " + bet.getBetId());
		tab.setContent(betPane);
		betsTabPane.getTabs().add(tab);
	}
}
