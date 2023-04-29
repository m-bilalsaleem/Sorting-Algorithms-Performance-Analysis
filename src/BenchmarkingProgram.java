import java.io.IOException;

public class BenchmarkingProgram {
    public static void main(String[] args) {
        int[] dataSizes = { 100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600, 51200, 102400, 204800 };
        int runs = 40;

        AbstractSort mergeSort = new MergeSort();
        AbstractSort selectionSort = new SelectionSort();

        for (int dataSize : dataSizes) {
            int[][] mergeSortResults = new int[runs][2];
            int[][] selectionSortResults = new int[runs][2];

            for (int run = 0; run < runs; run++) {
                int[] dataSet = SortUtils.generateRandomDataSets(1, dataSize)[0];
                int[] dataSetCopy = dataSet.clone();

                mergeSort.sort(dataSet);
                if (!SortUtils.isSorted(dataSet)) {
                    throw new RuntimeException("Merge sort failed to sort the array.");
                }
                mergeSortResults[run][0] = (int) mergeSort.getCount();
                mergeSortResults[run][1] = (int) mergeSort.getTime();

                selectionSort.sort(dataSetCopy);
                if (!SortUtils.isSorted(dataSetCopy)) {
                    throw new RuntimeException("Selection sort failed to sort the array.");
                }
                selectionSortResults[run][0] = (int) selectionSort.getCount();
                selectionSortResults[run][1] = (int) selectionSort.getTime();
            }

            try {
                SortUtils.saveResultsToFile("merge_sort_" + dataSize + ".txt", mergeSortResults);
                SortUtils.saveResultsToFile("selection_sort_" + dataSize + ".txt", selectionSortResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
