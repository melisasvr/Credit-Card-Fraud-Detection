import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        String fileName = "creditcard.csv";
        int totalRows = 500; // Small enough to see, large enough to train
        Random rand = new Random();

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write Header (Time, V1-V28, Amount, Class)
            writer.append("Time");
            for (int i = 1; i <= 28; i++) writer.append(",V" + i);
            writer.append(",Amount,Class\n");

            for (int i = 0; i < totalRows; i++) {
                double time = i * 2.0;
                double amount;
                int isFraud;

                // Make ~10% of data fraud so the simple model can learn easily
                if (rand.nextDouble() < 0.10) {
                    isFraud = 1;
                    amount = 500 + rand.nextDouble() * 1500; // Fraud = High amount
                } else {
                    isFraud = 0;
                    amount = 1 + rand.nextDouble() * 100;    // Normal = Low amount
                }

                writer.append(String.valueOf(time));
                for (int j = 0; j < 28; j++) {
                    // Generate random PCA-like features
                    double feature = (rand.nextGaussian() * (isFraud == 1 ? 2.0 : 1.0));
                    writer.append("," + feature);
                }
                writer.append("," + amount);
                writer.append("," + isFraud + "\n");
            }

            System.out.println("Successfully created " + fileName + " with " + totalRows + " rows.");
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
}