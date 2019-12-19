package controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Bet;
import models.Category;
import models.Game;
import models.dao.CategoryDao;
import models.dao.GameDao;
import views.GameView;

public class EventsController {
	
	private AddBetController addBetController;

	protected HomeController homeController;

	private GameDao gameDao;

	private SortedList<GameView> sortedData;

	protected final ObservableList<GameView> data = FXCollections.observableArrayList();;

	protected GameView selectedGame;
	
	protected ListProperty<Category> categoriesList;

	@FXML
	protected HBox eventsPane;

	@FXML
	private TableView<GameView> gamesTable = new TableView<GameView>();

	@FXML
	private TableColumn<GameView, Integer> idColumn;

	@FXML
	private TableColumn<GameView, String> categoryColumn;

	@FXML
	private TableColumn<GameView, String> team1Column;

	@FXML
	private TableColumn<GameView, Double> odds1Column;

	@FXML
	private TableColumn<GameView, String> team2Column;

	@FXML
	private TableColumn<GameView, Double> odds2Column;

	@FXML
	private TableColumn<GameView, Double> drawOddsColumn;

	@FXML
	private TableColumn<GameView, String> winnerColumn;

	@FXML
	private TableColumn<GameView, LocalDateTime> dateColumn;
	
    @FXML
    private Label totalEventValueLabel;

    @FXML
    private Label numbersOfBetsLabel;

	@FXML
	private Button team1Button;

	@FXML
	private Button drawButton;

	@FXML
	private Button team2Button;

	@FXML
	private TextField filterField;

	@FXML
	private CheckBox updateCheckbox;

	@FXML
	private Button addEvent;

	@FXML
	private Button addBet;
	
	@FXML
	private Button addCategory;
	
	@FXML
	private Button resetButton;

	private Button buttonClicked;

	private AnchorPane addBetForm;

	private AnchorPane addEventForm;
	
    private AnchorPane addCategoryForm;
	
	final String notUpdatedMessage = "Not resolved";

	@FXML
	public void initialize() throws IOException {
		
		CategoryDao categoryDao = new CategoryDao();
    	List<Category> categories = categoryDao.getAll();
    	ObservableList<Category> categoriesObservableList = FXCollections.observableArrayList(categories);
    	categoriesList = new SimpleListProperty<>();
    	categoriesList.set(categoriesObservableList);
		
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/project/bets/AddBet.fxml"));
		addBetForm = loader.load();
		addBetController = loader.getController();
		addBetController.setParentController(this);

		loader = new FXMLLoader(this.getClass().getResource("/project/bets/AddEvent.fxml"));
		addEventForm = loader.load();
		AddEventController addEventController = loader.getController();
		addEventController.setParentController(this);
		
		loader = new FXMLLoader(this.getClass().getResource("/project/bets/AddCategory.fxml"));
		addCategoryForm = loader.load();
		AddCategoryController addCategoryController = loader.getController();
		addCategoryController.setParentController(this);

		buttonClicked = null;
		addBet.setDisable(true);
		
		gameDao = new GameDao();


		eventsPane.getChildren().addListener((ListChangeListener<Node>) c -> {
			while (c.next()) {
				if (c.wasRemoved()) {
					buttonClicked = null;
				}
			}
		});
		
		new Thread(createTask()).start();
		addGamesTableListener();
	}
	
	private void addGamesTableListener() {
		gamesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observableValue, Object oldValue, Object newValue) {
				if (gamesTable.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel<GameView> selectionModel = gamesTable.getSelectionModel();
					selectedGame = selectionModel.getSelectedItem();
					if (selectedGame != null) {
						addBetController.setGame(selectedGame);
						
						Game game = gameDao.get(selectedGame.getGameId());
						setAdditonalInfo(game);
						
						if(selectedGame.getWinner() == notUpdatedMessage) addBet.setDisable(false);
						else addBet.setDisable(true);
						
						team1Button.setText(selectedGame.getFirstName());
						team1Button.setDisable(false);
						team2Button.setText(selectedGame.getSecondName());
						team2Button.setDisable(false);
						drawButton.setDisable(false);
						resetButton.setDisable(false);
					} else {
						addBet.setDisable(true);
						team1Button.setDisable(true);
						team2Button.setDisable(true);
						drawButton.setDisable(true);
						resetButton.setDisable(true);
					}
				}
			}
		});
		
	}

	private void setAdditonalInfo(Game game) {
		numbersOfBetsLabel.setText(Integer.toString(game.getBets().size()));
		double totalEventValue = 0;
		for(Bet bet : game.getBets()) {
			totalEventValue += bet.getAmount();
		}
		totalEventValueLabel.setText(Double.toString(totalEventValue) + "$");
		
	}

	public void setParentController(HomeController homeController) {
		this.homeController = homeController;
	}

	@FXML
	void showAddingForm() {
		if (buttonClicked == null) {
			buttonClicked = addEvent;
			eventsPane.getChildren().add(addEventForm);
		} else if (buttonClicked == addEvent) {
			buttonClicked = null;
			eventsPane.getChildren().remove(addEventForm);
		} else {
			eventsPane.getChildren().remove(1);
			buttonClicked = addEvent;
			eventsPane.getChildren().add(addEventForm);
		}
	}

	@FXML
	void showBettingForm() {
		if (buttonClicked == null) {
			buttonClicked = addBet;
			eventsPane.getChildren().add(addBetForm);
		} else if (buttonClicked == addBet) {
			buttonClicked = null;
			eventsPane.getChildren().remove(addBetForm);
		} else {
			eventsPane.getChildren().remove(1);
			buttonClicked = addBet;
			eventsPane.getChildren().add(addBetForm);
		}
	}
	
	@FXML
	void addCategoryForm() {
		if (buttonClicked == null) {
			buttonClicked = addCategory;
			eventsPane.getChildren().add(addCategoryForm);
		} else if (buttonClicked == addCategory) {
			buttonClicked = null;
			eventsPane.getChildren().remove(addCategoryForm);
		} else {
			eventsPane.getChildren().remove(1);
			buttonClicked = addCategory;
			eventsPane.getChildren().add(addCategoryForm);
		}
	}

	public GameView gameToGameView(Game game) {
		int id = game.getGameId();
		String category = game.getCategory().getName();

		LocalDateTime date = game.getDate();
		
		String team1 = game.getFirstName();
		String team2 = game.getSecondName();

		String winner = null;
		switch (game.getWinner()) {
		case 0:
			winner = notUpdatedMessage;
			break;
		case 1:
			winner = game.getFirstName();
			break;
		case 2:
			winner = game.getSecondName();
			break;
		case 3:
			winner = "Draw";
			break;
		}

		double odds1 = game.getOdds1();
		double odds2 = game.getOdds2();
		double odds3 = game.getOdds3();

		return new GameView(id, category, date, team1, team2, winner, odds1, odds2, odds3);
	}
	
	 protected Task<Void> createTask() {
		 return new Task<Void>() {
		        @Override
		        protected Void call() throws Exception {
		        	setUpTableView();
		        	populateTableView();
		            return null;
		        }
		    };
	 };

	private void setUpTableView() {
		data.clear();

		idColumn.setCellValueFactory(new PropertyValueFactory<GameView, Integer>("gameId"));
		categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
		team1Column.setCellValueFactory(new PropertyValueFactory<GameView, String>("firstName"));
		odds1Column.setCellValueFactory(new PropertyValueFactory<GameView, Double>("odds1"));
		team2Column.setCellValueFactory(new PropertyValueFactory<GameView, String>("secondName"));
		odds2Column.setCellValueFactory(new PropertyValueFactory<GameView, Double>("odds2"));
		drawOddsColumn.setCellValueFactory(new PropertyValueFactory<GameView, Double>("odds3"));
		winnerColumn.setCellValueFactory(cellData -> cellData.getValue().getWinnerProperty());
		dateColumn.setCellValueFactory(new PropertyValueFactory<GameView, LocalDateTime>("date"));
		
		FilteredList<GameView> filteredData = new FilteredList<>(data, p -> true);
		
		ObjectProperty<Predicate<GameView>> nameFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<GameView>> updateFilter = new SimpleObjectProperty<>();

        nameFilter.bind(Bindings.createObjectBinding(() -> 
            game -> {
            	try {
            		if (Integer.parseInt(filterField.getText()) == game.getGameId()) return true;
            		return false;
            	}
            	catch (NumberFormatException e) {
					if (game.getFirstName().toLowerCase().contains(filterField.getText())) {
						return true;
					} else if (game.getSecondName().toLowerCase().contains(filterField.getText())) {
						return true;
					}
					return false;
				}
            },
				filterField.textProperty()));

		updateFilter.bind(Bindings.createObjectBinding(() -> game -> {
			if (updateCheckbox.isSelected() && game.getWinner() != notUpdatedMessage)
				return false;
			else return true;
		}, updateCheckbox.selectedProperty()));

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                () -> nameFilter.get().and(updateFilter.get()), 
                nameFilter, updateFilter));

		sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(gamesTable.comparatorProperty());

		gamesTable.setItems(sortedData);
	}
	
	private void populateTableView() {
		List<Game> games = gameDao.getAll();

    	Platform.runLater(() -> {
    		ProgressIndicator progressIndicator = new ProgressIndicator();
    		gamesTable.setPlaceholder(progressIndicator);		
    	});

		try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e1){}
		
		for (Game game : games) {
			data.add(gameToGameView(game));
		}
		
		gamesTable.setPlaceholder(new Label("No data found"));

	}

	@FXML
	void deleteGame() {

		if (gameDao.get(selectedGame.getGameId()).getBets().size() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			String s = "You can't delete this event. It currently has bets associated!";
			alert.setContentText(s);
			alert.showAndWait();
		}
		else if (selectedGame != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete the event");
			String s = "Are you sure you want to delete this even?";
			alert.setContentText(s);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				gameDao.remove(selectedGame.getGameId());
				data.remove(selectedGame);
				if(gamesTable.getItems().isEmpty()) gamesTable.setPlaceholder(new Label("No data"));
			}
		}
	}
	
	@FXML
	void refreshTable() {
		addBet.setDisable(true);
		new Thread(createTask()).start();
	}

	@FXML
	void setWinnerDraw() {
		addBet.setDisable(true);
		gameDao.update(selectedGame.getGameId(), 3);
		sortedData.get(sortedData.indexOf(selectedGame)).setWinner("Draw");
	}

	@FXML
	void setWinner1() {
		addBet.setDisable(true);
		gameDao.update(selectedGame.getGameId(), 1);
		sortedData.get(sortedData.indexOf(selectedGame)).setWinner(selectedGame.getFirstName());
	}

	@FXML
	void setWinner2() {
		addBet.setDisable(true);
		gameDao.update(selectedGame.getGameId(), 2);
		sortedData.get(sortedData.indexOf(selectedGame)).setWinner(selectedGame.getSecondName());
	}
	
	@FXML
	void setWinnerReset() {
		addBet.setDisable(false);
		gameDao.update(selectedGame.getGameId(), 0);
		sortedData.get(sortedData.indexOf(selectedGame)).setWinner(notUpdatedMessage);
	}
}
