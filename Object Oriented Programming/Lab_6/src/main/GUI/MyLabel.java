package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class MyLabel extends Label {

    public MyLabel(String text, int[] size) {
        super(text);
        setPrefWidth(size[0]);
        setPrefHeight(size[1]);
        setAlignment(Pos.CENTER);
    }
}
