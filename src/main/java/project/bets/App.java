package project.bets;
 
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
	
       public static void main(String[] args) {
           launch(args);
       }
       
       @Override
       public void start(Stage primaryStage) throws IOException {
           FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/project/bets/Home.fxml"));
           HBox homePane = loader.load();
           Scene homeScene = new Scene(homePane);
           primaryStage.setScene(homeScene);
           primaryStage.setMaximized(true);
           primaryStage.show();
       }
   }