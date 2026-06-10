package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Single-window navigation, web style: swaps the content of a shared content
 * area instead of opening new windows, and keeps a clickable breadcrumb trail.
 */
public final class Navigator {

    private static HBox breadcrumbBar;
    private static StackPane contentArea;
    private static final List<Crumb> stack = new ArrayList<>();

    private Navigator() {
    }

    private record Crumb(String title, String fxml) {
    }

    /** Wire the navigator to the shell's breadcrumb bar and content area. */
    public static void init(HBox bar, StackPane content) {
        breadcrumbBar = bar;
        contentArea = content;
        stack.clear();
    }

    /** Set the home page, clearing any history. */
    public static void reset(String title, String fxml) {
        stack.clear();
        stack.add(new Crumb(title, fxml));
        render();
    }

    /** Navigate one level deeper, pushing a new breadcrumb. */
    public static void navigate(String title, String fxml) {
        stack.add(new Crumb(title, fxml));
        render();
    }

    /** Jump back to the crumb at the given depth, dropping everything after it. */
    private static void goTo(int index) {
        while (stack.size() > index + 1) {
            stack.remove(stack.size() - 1);
        }
        render();
    }

    private static void render() {
        if (contentArea == null || stack.isEmpty()) return;
        Crumb current = stack.get(stack.size() - 1);
        try {
            Node view = FXMLLoader.load(Navigator.class.getResource(current.fxml()));
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation error");
            alert.setHeaderText("Could not open: " + current.title());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        renderBreadcrumb();
    }

    private static void renderBreadcrumb() {
        breadcrumbBar.getChildren().clear();
        for (int i = 0; i < stack.size(); i++) {
            Crumb c = stack.get(i);
            boolean last = (i == stack.size() - 1);
            if (last) {
                Label current = new Label(c.title());
                current.getStyleClass().add("crumb-current");
                breadcrumbBar.getChildren().add(current);
            } else {
                Hyperlink link = new Hyperlink(c.title());
                link.getStyleClass().add("crumb");
                final int idx = i;
                link.setOnAction(e -> goTo(idx));
                Label sep = new Label("›");
                sep.getStyleClass().add("crumb-sep");
                breadcrumbBar.getChildren().addAll(link, sep);
            }
        }
    }
}
