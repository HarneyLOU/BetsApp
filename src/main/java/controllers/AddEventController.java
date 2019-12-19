package controllers;

import java.time.LocalDateTime;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import models.Bet;
import models.Category;
import models.Game;
import models.dao.GameDao;

public class AddEventController {
	
	private EventsController eventsController;
	
	@FXML
    private AnchorPane addEventForm;

    @FXML
    private ComboBox<Category> categoriesBox;

    @FXML
    private TextField firstName;

    @FXML
    private TextField secondName;

    @FXML
    private TextField odds1;

    @FXML
    private DatePicker gameDate;

    @FXML
    private TextField odds3;

    @FXML
    private TextField odds2;

    @FXML
    private Button confirmEvent;
    
    @FXML
    private TextField hourDate;

    @FXML
    private TextField minuteDate;
    
    private GameDao gameDao;
    
    @FXML
	public void initialize() {
    	gameDao = new GameDao();
    	Platform.runLater(() -> {
    	categoriesBox.itemsProperty().bindBidirectional(eventsController.categoriesList); 	
    	});
	}
    
    @FXML
    void addGame() {
    	eventsController.eventsPane.getChildren().remove(addEventForm);
    	Game game = new Game();
    	String error = "";
    	
    	if(categoriesBox.getValue() != null) game.setCategory(categoriesBox.getValue());
    	else error = "Category not selected!";
   
    	
    	if(firstName.getText().isBlank() || secondName.getText().isBlank()) error = "Wrong name for teams!";
    	else {
        	game.setFirstName(firstName.getText());
        	game.setSecondName(secondName.getText());
    	}
    	
    	try {
        	game.setOdds1(Double.parseDouble(odds1.getText()));
        	game.setOdds2(Double.parseDouble(odds2.getText()));
        	game.setOdds3(Double.parseDouble(odds3.getText()));
    	} catch(NumberFormatException e) {
			error = "Wrong amount!";
		}
    	
    	try {
        	int hour = Integer.parseInt(hourDate.getText());
        	int minute = Integer.parseInt(minuteDate.getText());
        	if(hour > 23 || hour < 0 || minute > 59 || minute < 0) throw new NumberFormatException();
        	LocalDateTime date = gameDate.getValue().atTime(hour, minute);
        	game.setDate(date);
    	} catch(NumberFormatException e) {
    		error = "Wrong date";
    	}

    	addAlert(game, error);

    }
    
	private void addAlert(Game game, String error) {
		if(error.isEmpty()) {
	    	gameDao.add(game);
	    	Game addedGame = gameDao.get(game.getGameId());
	    	eventsController.data.add(eventsController.gameToGameView(game));
			if(addedGame != null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				String s = "Event has been added!";
				alert.setContentText(s);
				alert.showAndWait();
				clearForm();
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
    
    private void clearForm() {
    	odds1.clear();
    	odds2.clear();
    	odds3.clear();
    	firstName.clear();
    	secondName.clear();
    	hourDate.clear();
    	minuteDate.clear();
    }
}
