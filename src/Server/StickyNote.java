package Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StickyNote {
    @Override
    public String toString() {
        return "color: " + color +
                "\n x: " + x +
                "\n y : " + y +
                "\n name: " + name +
                "\n height: " + height +
                "\n width: " + width +
                "\n message: " + message +
                "\r\n";

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String color = "";
    private int x = 0;
    private int y = 0;
    private String name = "";
    private int width = 0;
    private int  height = 0;
    private String  message = "";

}
