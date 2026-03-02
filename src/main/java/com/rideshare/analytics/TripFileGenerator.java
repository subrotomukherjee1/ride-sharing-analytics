package com.rideshare.analytics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TripFileGenerator {

    private static final String FILE_NAME = "trips.txt";

    public static void main(String[] args) {
        generateTripFile(1000); // Generate 1000 sample records
    }

    public static void generateTripFile(int numberOfRecords) {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            final Random random = new Random();
            for (int i = 1; i <= numberOfRecords; i++) {
                final String tripId = "T" + i;
                final String driverId = "D" + (random.nextInt(50) + 1);
                double distance = 1 + (20 * random.nextDouble()); // 1–20 km
                double fare = 5 + (distance * 2.5); // simple fare calculation

                final String line = String.format("%s,%s,%.2f,%.2f",
                        tripId,
                        driverId,
                        distance,
                        fare);
                writer.write(line);
                writer.newLine();
            }

            System.out.println("File generated successfully: " + FILE_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
