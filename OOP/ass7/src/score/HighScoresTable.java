package score;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighScoresTable {
    public static final String FILE_NAME = "highscores.ser";
    public static final int SCORES_NUMBER = 8;

    private int size;
    private List<ScoreInfo> scores;

    // Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.size = size;
        this.scores = new ArrayList<>();
    }

    // Add a high-score.
    public void add(ScoreInfo score) {
        this.scores.add(score);
        sortListByScore(this.scores);
    }

    // Return table size.
    public int size() {
        return this.size;
    }

    // Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    // return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {

    	if(this.scores.size() != 0) {
			if (score > this.scores.get(0).getScore()) {
				return 1;
			}

			if (score < this.scores.get(Math.min(this.scores.size(), size()) - 1).getScore()) {
				return size() + 1;
			}
			return size();
		}

    	return 1;

    }

    // Clears the table
    public void clear() {
        this.scores.removeAll(scores);
    }

    // Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        String fileName = FILE_NAME;

        ObjectInputStream objectInputStream = null;

        try {
            ScoreInfo si = null;
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(fileName));
            this.scores = (ArrayList<ScoreInfo>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + fileName);
            return;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + fileName);
            }
        }
    }


    // Save table data to the specified file.
    public void save(File filename) throws IOException {


        // use conventional 'ser' file extension for java serialized objects
        String fileName = FILE_NAME;

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(fileName));

        objectOutputStream.writeObject(this.scores);
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (IOException e) {
            System.err.println("Failed closing file: " + fileName);
        }
    }

    // Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoresTable = new HighScoresTable(8);
        try {
            highScoresTable.load(filename);
            return highScoresTable;
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return new HighScoresTable(8);
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return new HighScoresTable(8);
        }
    }

    private void sortListByScore(List<ScoreInfo> scores) {

        Collections.sort(scores, new Comparator<ScoreInfo>() {
            public int compare(ScoreInfo s1, ScoreInfo s2) {
                return Math.max(s1.getScore(), s2.getScore());
            }
        });
    }
}
