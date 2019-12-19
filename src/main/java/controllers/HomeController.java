package controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HomeController {
	
	private ScrollPane eventsScrollPane;
	
	private ScrollPane betsScrollPane;
	
	private ScrollPane aboutScrollPane;
	
	protected BetsController betsController;
	
	protected EventsController eventsController;
	
	@FXML
	private HBox homePane;
	
	@FXML
	public void initialize() throws IOException {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/project/bets/Events.fxml"));
			eventsScrollPane = loader.load();
			eventsController = loader.getController();
			eventsController.setParentController(this);
			HBox.setHgrow(eventsScrollPane, Priority.ALWAYS);
			loader = new FXMLLoader(this.getClass().getResource("/project/bets/Bets.fxml"));
			betsScrollPane = loader.load();
		    betsController = loader.getController();
			VBox.setVgrow(betsScrollPane, Priority.ALWAYS);
			HBox.setHgrow(betsScrollPane, Priority.ALWAYS);
			loader = new FXMLLoader(this.getClass().getResource("/project/bets/About.fxml"));
			aboutScrollPane = loader.load();
			VBox.setVgrow(aboutScrollPane, Priority.ALWAYS);
			HBox.setHgrow(aboutScrollPane, Priority.ALWAYS);
			showEvents();
	}

	@FXML
	public void showEvents() throws IOException {
		if(homePane.getChildren().size()>1) {
			homePane.getChildren().remove(1);
		}
		homePane.getChildren().add(eventsScrollPane);
	}

	@FXML
    void showBets() throws IOException {
		if(homePane.getChildren().size()>1) {
			homePane.getChildren().remove(1);
		}
		homePane.getChildren().add(betsScrollPane);
    }
	
	@FXML
    void showAbout() throws IOException {
		if(homePane.getChildren().size()>1) {
			homePane.getChildren().remove(1);
		}
		homePane.getChildren().add(aboutScrollPane);
    }
	
	 @FXML
	    void exit() {
		 Platform.exit();
	    }
}
