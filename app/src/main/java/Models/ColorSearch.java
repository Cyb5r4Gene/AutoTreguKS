package Models;

public class ColorSearch {
    boolean unspecified, white, green,grey, blue, red,orange, yellow, black,  purple, other;

    public ColorSearch(){}

    public ColorSearch(boolean unspecified, boolean white, boolean green, boolean grey, boolean blue, boolean red,
                       boolean orange, boolean yellow, boolean black, boolean purple, boolean other) {
        this.unspecified = unspecified;
        this.white = white;
        this.green = green;
        this.grey = grey;
        this.blue = blue;
        this.red = red;
        this.orange = orange;
        this.yellow = yellow;
        this.black = black;
        this.purple = purple;
        this.other = other;
    }

    public boolean isOrange() {
        return orange;
    }

    public void setOrange(boolean orange) {
        this.orange = orange;
    }

    public boolean isUnspecified() {
        return unspecified;
    }

    public void setUnspecified(boolean unspecified) {
        this.unspecified = unspecified;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isGrey() {
        return grey;
    }

    public void setGrey(boolean grey) {
        this.grey = grey;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isYellow() {
        return yellow;
    }

    public void setYellow(boolean yellow) {
        this.yellow = yellow;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public boolean isPurple() {
        return purple;
    }

    public void setPurple(boolean purple) {
        this.purple = purple;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }
}
