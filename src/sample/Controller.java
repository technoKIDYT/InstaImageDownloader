package sample;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import sample.BL.Downloader;
import sample.BL.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField inpDest, inpUrl;

    @FXML
    Button btnDownload, btnBrowse;

    @FXML
    ProgressIndicator prog;

    File file;
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    FileUtils fileUtils;
    Utility utility;
    String url;

    DirectoryChooser directoryChooser;
    Downloader downloader;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prog.setVisible(false);
        inpUrl.setDisable(true);
        inpDest.setEditable(false);
        btnDownload.setDisable(true);
        utility = new Utility();
        downloader = new Downloader();
    }

    public void showDialog() {
        try {
            directoryChooser = new DirectoryChooser();
            file = directoryChooser.showDialog((Stage) btnBrowse.getScene().getWindow());
            inpDest.setText(file.getPath());
            if (!inpDest.getText().isEmpty() && file.isDirectory()) {
                inpUrl.setDisable(false);
            }
        } catch (Exception exception) {
            prog.setVisible(false);
            inpUrl.setDisable(true);
            btnDownload.setDisable(true);
        }
    }

    public void validate() {
        String strUrl = inpUrl.getText().toString().trim();
        if (!strUrl.isEmpty()) {
            if (utility.isValidUrl(strUrl)) {
                btnDownload.setDisable(false);
            } else {

            }
        }
    }

    public void downlaod() {
        prog.setVisible(true);
        inpUrl.setDisable(true);
        btnDownload.setDisable(true);
        btnBrowse.setDisable(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        file = downloader.downloadImage(inpUrl.getText(), new File(inpDest.getText()));

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                prog.setVisible(false);
                                btnDownload.setDisable(false);
                                inpUrl.setDisable(false);
                                btnBrowse.setDisable(false);
                                if (file.exists()) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Success");
                                    alert.setContentText("File downloaded successfully... " + file.getAbsolutePath());
                                    alert.show();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Failure");
                                    alert.setContentText("Something went wrong....");
                                    alert.show();
                                }
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }

}
