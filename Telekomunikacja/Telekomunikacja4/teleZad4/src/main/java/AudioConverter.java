import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioConverter {
    private static final int SAMPLE_RATE = 44100;
    private static final int SAMPLE_SIZE_IN_BITS = 16;
    private static final int CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;
    private static final String OUTPUT_FILE = "output.wav";
    private static final String INPUT_FILE = "output.wav";

    public static void main(String[] args) {
        System.out.print("Podaj:\n1 jeśli A/C\n2 jeśli C/A: ");
        try {
            int x = System.in.read();
            if (x == '1') {
                convertAndSaveToWav();
            } else if (x == '2') {
                convertAndPlayFromWav();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertAndSaveToWav() {
        try {
            AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Przetwornik A/C nie jest obsługiwany.");
                return;
            }
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Nagrywanie rozpoczęte...");
            AudioInputStream audioInputStream = new AudioInputStream(line);
            File outputFile = new File(OUTPUT_FILE);
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);

            line.stop();
            line.close();
            System.out.println("Nagrywanie zakończone. Dźwięk zapisano do pliku " + OUTPUT_FILE);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertAndPlayFromWav() {
        try {
            File inputFile = new File(INPUT_FILE);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputFile);
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Przetwornik C/A nie jest obsługiwany.");
                return;
            }
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Odtwarzanie rozpoczęte...");
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.stop();
            line.close();
            audioInputStream.close();
            System.out.println("Odtwarzanie zakończone.");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
