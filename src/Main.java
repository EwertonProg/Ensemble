import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class Main {
    private final static int NUMBER_OF_IMAGES = 5490;

    public static void main(String[] args) {
        File pasta = new File("./modelos");
        File[] files = pasta.listFiles();
        if (files != null) {
            int filesSize = files.length;
            int[][] values = new int[NUMBER_OF_IMAGES][filesSize];
            for (int fileNumber = 0; fileNumber < filesSize; fileNumber++) {
                File file = files[fileNumber];
                if (file.getName().startsWith("modelo_")) {
                    try {
                        Object[] lines = Files.lines(file.toPath()).toArray();
                        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
                            String line = (String) lines[lineNumber];
                            values[lineNumber][fileNumber] = Integer.parseInt(line.split(" ")[1]);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            createResultFile(ensemble(values));
        }
    }

    private static void createResultFile(int[] ensembleList) {
        try {
            FileWriter writer = new FileWriter("EWERTON_SANTANA.txt");
            for (int i = 0; i < ensembleList.length; i++) {
                int value = ensembleList[i];
                writer.write((i + 1) + ".png " + value + "\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int mode(int[] elements) {
        int mode = 0, modeRepetitions = 0, elementsSize = elements.length;
        for (int i = 0; i < elementsSize; i++) {
            int actualElement = elements[i], actualRepetitions = 0;
            for (int j = i + 1; j < elementsSize; j++) {
                int toCompareElement = elements[j];
                if (actualElement == toCompareElement) {
                    actualRepetitions++;
                }
            }
            if (actualRepetitions > modeRepetitions) {
                mode = actualElement;
                modeRepetitions = actualRepetitions;
            }
        }
        if (modeRepetitions == 0) {
            mode = elements[new Random().nextInt(elementsSize)];
        }
        return mode;
    }

    public static int[] ensemble(int[][] values) {
        int[] ensembleList = new int[NUMBER_OF_IMAGES];
        for (int i = 0; i < NUMBER_OF_IMAGES; i++) {
            ensembleList[i] = mode(values[i]);
        }
        return ensembleList;
    }
}