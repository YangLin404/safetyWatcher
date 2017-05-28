package be.linyang.safetyWatcher;

import be.linyang.safetyWatcher.views.MyPreloader;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by yanglin on 28/05/17.
 */
@SpringBootApplication
public class SafetyWatcherApp extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;

    public static void main(String[] args) {
        //launch(args);
        LauncherImpl.launchApplication(SafetyWatcherApp.class, MyPreloader.class, args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(SafetyWatcherApp.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        rootNode = fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(rootNode);
        scene.getStylesheets().add("bootstrapfx.css");
        //scene.getStylesheets().addAll("/css/CalendarStyle.css","/css/EventPopOverStyle.css");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1100);
        primaryStage.setHeight(650);
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
