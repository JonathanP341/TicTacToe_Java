/**
 * This is a class to store the configuration and respective score of any board position
 *
 * Date: February 16th 2024
 * @author Jonathan Peters
 */
public class Data {
    private String config;
    private int score;

    public Data(String config, int score) {
        this.config = config;
        this.score = score;
    }

    public String getConfiguration() {
        return config;
    }
    public int getScore() {
        return score;
    }
}
