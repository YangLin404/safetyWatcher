package be.linyang.safetyWatcher.views;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.MaskerPane;

/**
 * Created by yanglin on 28/05/17.
 */
public class MyPreloader extends Preloader {

    private Stage preloaderStage;
    private MaskerPane maskerPane = new MaskerPane();


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        Image splash = new Image("/pictures/safety.jpg",500,500,false,false);
        ImageView imageView = new ImageView(splash);
        imageView.setPreserveRatio(true);

        this.maskerPane.setVisible(true);

        StackPane root = new StackPane();
        root.getChildren().addAll(imageView,this.maskerPane);

        Scene scene = new Scene(root);

        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
            this.maskerPane.setVisible(false);
        }


    }
}
