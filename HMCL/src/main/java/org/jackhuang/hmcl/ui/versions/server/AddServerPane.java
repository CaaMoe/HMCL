package org.jackhuang.hmcl.ui.versions.server;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.jackhuang.hmcl.ui.FXUtils;
import org.jackhuang.hmcl.ui.animation.ContainerAnimations;
import org.jackhuang.hmcl.ui.animation.TransitionPane;
import org.jackhuang.hmcl.ui.construct.DialogAware;
import org.jackhuang.hmcl.ui.construct.DialogCloseEvent;
import org.jackhuang.hmcl.ui.construct.TwoLineListItem;

import static org.jackhuang.hmcl.util.StringUtils.parseColorEscapes;
import static org.jackhuang.hmcl.util.i18n.I18n.i18n;

public class AddServerPane extends TransitionPane implements DialogAware {
    public AddServerPane(ServerData serverData){
        JFXDialogLayout rootLayout = new JFXDialogLayout();
        rootLayout.setHeading(new Label("添加服务器"));

        BorderPane root = new BorderPane();
        root.getStyleClass().add("md-list-cell");
        root.setPadding(new Insets(8));

        {
            StackPane left = new StackPane();
            root.setLeft(left);
            left.setPadding(new Insets(0, 8, 0, 0));

            ImageView imageView = new ImageView();
            left.getChildren().add(imageView);
            FXUtils.limitSize(imageView, 32, 32);
            imageView.setImage(serverData.iconImage.get() == null ? FXUtils.newBuiltinImage("/assets/img/unknown_server.png") : serverData.iconImage.get());
        }

        {
            TwoLineListItem item = new TwoLineListItem();
            root.setCenter(item);
            item.setMouseTransparent(true);
            if (serverData.name != null)
                item.setTitle(parseColorEscapes(serverData.name));
            item.setSubtitle(serverData.ip);
        }


        JFXButton cancelButton = new JFXButton(i18n("button.cancel"));
        cancelButton.getStyleClass().add("dialog-cancel");
        JFXButton finishButton = new JFXButton(i18n("wizard.finish"));
        finishButton.getStyleClass().add("dialog-accept");
        rootLayout.setActions(cancelButton, finishButton);
        rootLayout.setBody(root);
        cancelButton.setOnAction(event -> fireEvent(new DialogCloseEvent()));

        setContent(rootLayout, ContainerAnimations.NONE);
    }
}
