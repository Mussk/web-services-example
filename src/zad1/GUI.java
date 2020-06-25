package zad1;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

   private Service service;

    public GUI(Service service)  {

        this.service = service;


    }

    public void showGUI(){

        JFrame main_frame = new JFrame("Services");

        JPanel panel_main = new JPanel(new BorderLayout());

        panel_main.setPreferredSize(new Dimension(600,400));

        JPanel panel_grid = new JPanel(new GridLayout(2,3));

        JPanel panel_grid_cell = new JPanel(new BorderLayout());

        JFXPanel jfxPanel = new JFXPanel();

        panel_grid.setPreferredSize(new Dimension(600,150));

        JLabel weather = new JLabel("Weather:\n");

        JLabel NBPRate = new JLabel("Currency(NBP) rate:\n");

        JLabel Currency_rate = new JLabel();

        JTextField country = new JTextField("Type here country",15);

        JTextField city = new JTextField("Type here city",15);

        JTextField currency_code = new JTextField("currency code",3);

        JButton button = new JButton("Start!");


        panel_grid_cell.add(Currency_rate,BorderLayout.CENTER);

        panel_grid_cell.add(currency_code,BorderLayout.PAGE_END);


        button.addActionListener((v) -> {

            if (v.getActionCommand().equals("Start!")){

                service.setCity_name(city.getText());

                service.setCountry_name(country.getText());

                service.setCurrency_code_foreign(currency_code.getText());

                weather.setText("Weather:\n" + service.getCity_name() +":\n" + service.getWeather(service.getCity_name()));

                NBPRate.setText("Currency(NBP) rate: " + service.getNBPRate() + " PLN");

                Currency_rate.setText("1 " + service.getCurrency().getCurrencyCode() + " = " + service.getRateFor(service.getCurrency_code_foreign()) + " " +service.getCurrency_code_foreign());

                createScene(jfxPanel,service.getCity_name());

            }

        });



        panel_main.add(jfxPanel,BorderLayout.CENTER);

        panel_grid.add(weather);

        panel_grid.add(panel_grid_cell);

        panel_grid.add(NBPRate);

        panel_grid.add(country);

        panel_grid.add(city);

        panel_grid.add(button);

        panel_main.add(panel_grid,BorderLayout.PAGE_END);

        main_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        main_frame.add(panel_main);

        main_frame.pack();

        main_frame.setVisible(true);

    }

    private void createScene(JFXPanel jfxPanel,String city_name) {

        Platform.runLater(new Runnable(){

            @Override
            public void run() {

                Stage stage = new Stage();

                stage.setResizable(true);

                Group root = new Group();
                Scene scene = new Scene(root,600,250);
                stage.setScene(scene);


                WebView browser = new WebView();
                WebEngine webEngine = browser.getEngine();
                webEngine.load("https://en.wikipedia.org/wiki/" + city_name);

                browser.prefWidthProperty().bind(scene.widthProperty());

                ObservableList<Node> children = root.getChildren();
                children.add(browser);



                jfxPanel.setScene(scene);


            }


    });

    }
}





