import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FeatureExtraction {
    
    // Method to extract and plot the RGB histogram
    public static void plotRGBHistogram(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] red = new int[256];
        int[] green = new int[256];
        int[] blue = new int[256];

        // Iterate over every pixel to extract RGB values
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color pixel = new Color(image.getRGB(i, j));
                red[pixel.getRed()]++;
                green[pixel.getGreen()]++;
                blue[pixel.getBlue()]++;
            }
        }

        // Create a frame to display the histograms
        JFrame frame = new JFrame("RGB Histograms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(3, 1));

        // Add the RGB histograms to the frame
        frame.add(new HistogramPanel(red, Color.RED, "Red Channel"));
        frame.add(new HistogramPanel(green, Color.GREEN, "Green Channel"));
        frame.add(new HistogramPanel(blue, Color.BLUE, "Blue Channel"));

        frame.setVisible(true);
    }

    // Custom JPanel to draw the histogram
    static class HistogramPanel extends JPanel {
        private int[] histogram;
        private Color color;
        private String title;

        public HistogramPanel(int[] histogram, Color color, String title) {
            this.histogram = Arrays.copyOf(histogram, histogram.length);
            this.color = color;
            this.title = title;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.drawString(title, 10, 20);

            int max = Arrays.stream(histogram).max().orElse(1);

            for (int i = 0; i < histogram.length; i++) {
                int value = (int) ((histogram[i] / (double) max) * getHeight());
                g.setColor(color);
                g.drawLine(i, getHeight(), i, getHeight() - value);
            }
        }
    }

    // Method to convert image to grayscale and compute simple texture features
    public static void extractTextureFeatures(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Convert to grayscale
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color pixel = new Color(image.getRGB(i, j));
                int grayValue = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
                grayImage.setRGB(i, j, new Color(grayValue, grayValue, grayValue).getRGB());
            }
        }

        // Simple GLCM calculation for texture features like contrast (this is just a mock-up example)
        double contrast = 0;
        double energy = 0;

        // Compute GLCM properties (mocked up for now)
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                int pixelValue = new Color(grayImage.getRGB(i, j)).getRed();
                int neighborValue = new Color(grayImage.getRGB(i + 1, j + 1)).getRed();

                contrast += Math.pow(pixelValue - neighborValue, 2);
                energy += pixelValue * neighborValue;
            }
        }

        contrast /= (width * height);
        energy /= (width * height);

        System.out.printf("Contrast: %.2f, Energy: %.2f%n", contrast, energy);
    }

    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage image = ImageIO.read(new File("B3.jpg"));

            // Extract and plot color histograms
            plotRGBHistogram(image);

            // Extract and print texture features
            extractTextureFeatures(image);
        } catch (IOException e) {
            System.out.println("Error loading the image: " + e.getMessage());
        }
    }
}
