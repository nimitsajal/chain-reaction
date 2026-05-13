enum TextColor {
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    CYAN("\u001B[36m"),
    MAGENTA("\u001B[35m"),
    WHITE("\u001B[37m"),
    RESET("\u001B[0m");

    private final String colorCode;

    TextColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

}
