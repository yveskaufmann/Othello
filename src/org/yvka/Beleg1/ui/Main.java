package org.yvka.Beleg1.ui;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GameField gameField = new GameField(5);
			Slider slider = new Slider();
			slider.setValue(5);
			slider.setMin(5);
			slider.setMax(10);
			slider.setMajorTickUnit(1);
			slider.setBlockIncrement(1);
			VBox root = new VBox();
			root.getChildren().addAll(
				gameField, slider
			);
			slider.valueProperty().addListener((evt, oldValue, newValue) -> {
				gameField.setSize(newValue.intValue());
				gameField.autosize();
				gameField.layout();
				primaryStage.sizeToScene();
				System.out.println(newValue);
			});
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
