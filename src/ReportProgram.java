import java.io.*;
import java.text.DecimalFormat;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportProgram {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Input Files");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File[] inputFiles = fileChooser.getSelectedFiles();
            processData(inputFiles);
        }
    }

    public static void processData(File[] inputFiles) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Size");
        tableModel.addColumn("Avg. Count");
        tableModel.addColumn("Coef. Count (%)");
        tableModel.addColumn("Avg. Time");
        tableModel.addColumn("Coef. Time (%)");

        for (File inputFile : inputFiles) {
            try (Scanner scanner = new Scanner(inputFile)) {
                int size = Integer.parseInt(inputFile.getName().replaceAll("[^0-9]", "")); // Extract dataset size from
                                                                                           // file name

                List<Double> operationCounts = new ArrayList<>();
                List<Double> runtimes = new ArrayList<>();

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    System.out.println("Processing line: " + line);
                    String[] values = line.trim().split("\\s+");

                    if (values.length != 2) {
                        System.out.println("Skipping invalid line: " + line);
                        continue;
                    }

                    double operationCount = Double.parseDouble(values[0]);
                    double runtime = Double.parseDouble(values[1]);

                    operationCounts.add(operationCount);
                    runtimes.add(runtime);
                }

                double avgOperations = calculateAverage(operationCounts);
                double avgRuntime = calculateAverage(runtimes);
                double covOperations = calculateCoefficientOfVariance(operationCounts, avgOperations);
                double covRuntime = calculateCoefficientOfVariance(runtimes, avgRuntime);

                DecimalFormat df = new DecimalFormat("0.00");
                tableModel.addRow(new Object[] {
                        size,
                        df.format(avgOperations),
                        df.format(covOperations) + "%",
                        df.format(avgRuntime),
                        df.format(covRuntime) + "%"
                });

            } catch (FileNotFoundException e) {
                System.err.println("Input file not found: " + inputFile);
            }
        }

        JTable reportTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        reportTable.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        JFrame frame = new JFrame("Sorting Algorithms Report");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static double calculateAverage(List<Double> values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return (values.size() == 0) ? 0 : (sum / values.size());
    }

    public static double calculateCoefficientOfVariance(List<Double> values, double average) {
        double varianceSum = 0;
        for (double value : values) {
            varianceSum += Math.pow(value - average, 2);
        }
        double variance = (values.size() == 0) ? 0 : (varianceSum / values.size());
        double standardDeviation = Math.sqrt(variance);

        return (average == 0) ? 0 : ((standardDeviation / average) * 100);
    }

}
