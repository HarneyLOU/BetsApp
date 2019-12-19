package controllers;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import models.Category;
import models.dao.CategoryDao;

public class AddCategoryController {
	
	private EventsController eventsController;
	
	private List<Category> categories;
	
	private CategoryDao categoryDao;

    @FXML
    private AnchorPane addCategoryForm;

    @FXML
    private Button confirmEvent;

    @FXML
    private Label alert;

    @FXML
    private ComboBox<Category> categoriesBox;

    @FXML
    private TextField newCategoryField;

    @FXML
    private TextField nameField;
    
    @FXML
	public void initialize() {
    	categoryDao = new CategoryDao();
    	Platform.runLater(() -> {
    	categoriesBox.itemsProperty().bindBidirectional(eventsController.categoriesList); 	
    	});
    }
    
    @FXML
    void setCategory() {
    	nameField.setText(categoriesBox.getValue().toString());
    }

    @FXML
    void addCategory() {
    	Category category = new Category();
    	category.setName(newCategoryField.getText());
    	if(!newCategoryField.getText().isEmpty()) {
        	categoryDao.add(category);
        	eventsController.categoriesList.add(category);
        	
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Success");
    		String s = "Category successfully added!";
    		alert.setContentText(s);
    		alert.showAndWait();	
    	}
    }

    @FXML
    void confirm() {
    	eventsController.eventsPane.getChildren().remove(addCategoryForm);
    }

    @FXML
    void deleteCategory() {
    	Category category = categoriesBox.getValue();
    	try {
    		categoryDao.remove(category.getCategoryId());
    		eventsController.categoriesList.remove(category);
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Success");
    		String s = "Category successfully deleted!";
    		alert.setContentText(s);
    		alert.showAndWait();
    	}
    	catch(Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		String s = "Category can't be deleted!";
    		alert.setContentText(s);
    		alert.showAndWait();	
    	}		
    }

    @FXML
    void editName() {
    	Category category = categoriesBox.getValue();
    	category.setName(nameField.getText());
    	categoryDao.update(category);
    	eventsController.categoriesList.set(categoriesBox.getSelectionModel().getSelectedIndex(), category);
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		String s = "Category successfully edited!";
		alert.setContentText(s);
		alert.showAndWait();	
    }
    
    public void setParentController(EventsController eventsController) {
    	this.eventsController = eventsController;
    }

}
