import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleFraudDetector {

    public static void main(String[] args) throws Exception {
        String path = "creditcard.csv"; 
        System.out.println("--- Starting Manual Fraud Detector ---");
        System.out.println("Loading data...");

        List<double[]> features = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        // 1. CSV LOADER
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] f = new double[30];
                for (int i = 0; i < 30; i++) {
                    f[i] = Double.parseDouble(values[i]);
                }
                features.add(f);
                labels.add(Integer.parseInt(values[30].replace("\"", "")));
            }
        }

        System.out.println("Total Rows Loaded: " + features.size());

        // 2. SCALING (Preprocessing)
        for (double[] row : features) {
            row[29] = row[29] / 1000.0; // Scale Amount
            row[0] = row[0] / 100000.0; // Scale Time
        }

        // 3. TRAIN/TEST SPLIT (80/20)
        int trainSize = (int) (features.size() * 0.8);
        double[][] trainX = new double[trainSize][30];
        int[] trainY = new int[trainSize];
        
        for(int i=0; i<trainSize; i++) {
            trainX[i] = features.get(i);
            trainY[i] = labels.get(i);
        }

        // 4. TRAINING (Learning Logic)
        double[] weights = new double[30];
        double bias = 0.0;
        double learningRate = 0.01;
        int epochs = 50;

        System.out.println("Training model...");
        for (int epoch = 0; epoch < epochs; epoch++) {
            double totalLoss = 0; 
            for (int i = 0; i < trainX.length; i++) {
                double linearOutput = bias;
                for (int j = 0; j < 30; j++) {
                    linearOutput += trainX[i][j] * weights[j];
                }
                double prediction = 1.0 / (1.0 + Math.exp(-linearOutput));
                double error = trainY[i] - prediction;
                
                totalLoss += Math.pow(error, 2); 

                for (int j = 0; j < 30; j++) {
                    weights[j] += learningRate * error * trainX[i][j];
                }
                bias += learningRate * error;
            }
            if ((epoch + 1) % 10 == 0) {
                System.out.println("Epoch " + (epoch + 1) + " | Avg Loss: " + (totalLoss / trainSize));
            }
        }

        // 5. EVALUATION (Full Metrics)
        System.out.println("\n--- Final Evaluation ---");
        int correct = 0;
        int fraudCaught = 0;
        int actualFraud = 0;
        int falseAlarms = 0;
        int testCount = features.size() - trainSize;

        for (int i = trainSize; i < features.size(); i++) {
            double[] testRow = features.get(i);
            double linearOutput = bias;
            for (int j = 0; j < 30; j++) {
                linearOutput += testRow[j] * weights[j];
            }
            double prob = 1.0 / (1.0 + Math.exp(-linearOutput));
            
            int pred = prob > 0.3 ? 1 : 0; 
            int actual = labels.get(i);

            if (pred == actual) correct++;
            if (actual == 1) {
                actualFraud++;
                if (pred == 1) fraudCaught++;
            } else if (pred == 1) {
                falseAlarms++;
            }
        }

        System.out.println("Accuracy: " + String.format("%.2f", ((double)correct / testCount * 100)) + "%");
        System.out.println("Total Frauds in Test Set: " + actualFraud);
        System.out.println("Frauds Successfully Caught: " + fraudCaught);
        System.out.println("False Alarms (Normal flagged as fraud): " + falseAlarms);

        // 6. REAL-TIME PREDICTOR (New Addition)
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Interactive Prediction Mode ---");
        
        while (true) {
            System.out.print("\nEnter Amount to check (or -1 to exit): ");
            double userAmount = sc.nextDouble();
            if (userAmount == -1) break;

            // Simple prediction based on Amount (Index 29)
            double score = bias + (userAmount / 1000.0) * weights[29];
            double prob = 1.0 / (1.0 + Math.exp(-score));

            System.out.println("Analyzed Result:");
            System.out.printf(" > Fraud Probability: %.2f%%\n", prob * 100);
            if (prob > 0.3) {
                System.out.println(" > STATUS: [!!!] HIGH RISK");
            } else {
                System.out.println(" > STATUS: [OK] SAFE");
            }
        }
        System.out.println("Closing Detector...");
        sc.close();
    }
}