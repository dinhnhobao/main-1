package seedu.mark.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.mark.MainApp;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Url;

/**
 * The Browser Panel of Mark.
 */
public class BrowserPanel extends UiPart<Region> {

    /**
     * Default html page to be loaded when not connected to internet.
     */
    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class
                    .getResource(FXML_FILE_FOLDER + "defaultOfflinePage.html"));
    public static final String HOME_PAGE_URL = "https://google.com.sg";

    /**
     * Name of corresponding fxml file.
     */
    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private TextField addressBar;
    @FXML
    private ImageView homepageLogo;
    @FXML
    private Button homeButton;

    private WebEngine webEngine;
    private ObservableStringValue currentPageUrl;
    private boolean isConnected;

    public BrowserPanel(SimpleObjectProperty<Url> currentBookmarkUrl) {
        super(FXML);
        this.isConnected = true;

        loadGuiAddress();
        loadGuiGoogleButton();
        loadGuiBrowser();

        // Load page when current bookmark url changes.
        currentBookmarkUrl.addListener((observable, oldValue, newValue) -> {
            logger.info("Current bookmark url changed to: " + newValue);
            if (!isConnected) {
                return;
            }
            // Return if the change is due to the change of currentPageUrl
            if (newValue == null || newValue.value.equals(currentPageUrl.getValue())) {
                return;
            }
            loadPage(newValue.toString());
        });

        // Whenever currentPageUrl changes, update the currentBookmarkUrl
        currentPageUrl.addListener((observable, oldValue, newValue) -> {
            logger.info("Current page url changed to: " + newValue);
            if (!isConnected) {
                currentBookmarkUrl.setValue(null);
                return;
            }
            // Return if the change is due to the change of currentBookmarkUrl
            if (currentBookmarkUrl.getValue() != null && newValue.equals(currentBookmarkUrl.getValue().value)) {
                return;
            }
            currentBookmarkUrl.setValue(new Url(newValue));
        });
    }

    /**
     * Initialises the address bar.
     */
    private void loadGuiAddress() {
        addressBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue) {
                    logger.info("Address bar in focus");
                    addressBar.selectAll();
                } else {
                    logger.info("Address bar out of focus");
                    if (!currentPageUrl.getValue().equals(addressBar.getText())) {
                        addressBar.setText(currentPageUrl.getValue());
                    }
                }
            });
        });
    }

    /**
     * Initialises the button that leads to google home page when pressed.
     */
    private void loadGuiGoogleButton() {
        homepageLogo.setImage(new Image(
                MainWindow.class.getResourceAsStream("/images/googleLogo.png")
        ));
    }

    /**
     * Initialises the embedded browser.
     */
    private void loadGuiBrowser() {
        webEngine = browser.getEngine();
        currentPageUrl = webEngine.locationProperty();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == State.FAILED) {
                logger.info("browser: unable to connect to internet");
                isConnected = false;
                loadDefaultPage();
            } else if (newValue == State.SUCCEEDED) {
                isConnected = true;
                showAddressOnAddressBar(currentPageUrl.getValue());
            }
        });
        loadHomepage();
    }

    ////////////////////// MAIN METHODS /////////////////////////

    /**
     * Loads page with url on the webview.
     *
     * @param url valid url
     */
    public void loadPage(String url) {
        assert isValidUrl(url) : "invalid url passed to webEngine";

        Platform.runLater(() -> webEngine.load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

    /**
     * Changes webview to google search page.
     */
    @FXML
    private void loadHomepage() {
        loadPage(HOME_PAGE_URL);
    }

    /**
     * Sets address bar text as url.
     *
     * @param url url to set address bar content to.
     */
    private void showAddressOnAddressBar(String url) {
        addressBar.setText(url);
    }

    /**
     * Changes webview to site based on input entered in the address bar.
     */
    @FXML
    private void handleAddressBarInput() {
        //TODO: link address bar to web engine
        //if address legit then load
        //if not legit h
        String input = addressBar.getText();
        logger.info("Reading address from address bar: " + input);

        logger.info("Checking validity of input URL: " + isValidUrl(input));
        //dunnid clear input
    }

    /**
     * Checks if given url is a valid url.
     *
     * @param url suspected url
     * @return true if url is valid; else false.
     */
    private boolean isValidUrl(String url) {
        //TODO: check Url.isValidUrl is appropriate for this (parse and check if the url is a valid url)
        //check if have protocol in front
        //if true then test out by creating a url and catching malinformedurlexception?
        return Url.isValidUrl(url); //dummy code
    }

    /**
     * Parses input into a valid url.
     * If the input is simply lacking a protocol, http:// is prepended.
     * Otherwise, the input is passed into google search.
     *
     * @param input non-url input
     * @return a valid url based on given input.
     */
    private String makeValidUrl(String input) {
        //TODO: parse a non url into a valid url (either by adding protocol or doing google search)
        //if is url without protocol, add protocol http://
        //else google search it... how?
        return input; //dummy code
    }
}