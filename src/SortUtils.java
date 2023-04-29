import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SortUtils {
    public static int[][] generateRandomDataSets(int n, int dataSize) {
        int[][] dataSets = new int[n][dataSize];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < dataSize; j++) {
                dataSets[i][j] = random.nextInt(dataSize);
            }
        }

        return dataSets;
    }

    public static void saveResultsToFile(String fileName, int[][] results) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int[] result : results) {
                for (int j = 0; j < result.length; j++) {
                    writer.write(String.valueOf(result[j]));
                    if (j < result.length - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }
        }
    }

    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
