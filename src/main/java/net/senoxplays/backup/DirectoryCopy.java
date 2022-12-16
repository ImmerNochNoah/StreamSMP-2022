package net.senoxplays.backup;

import java.io.*;

public class DirectoryCopy {

    //Source Code: https://www.baeldung.com/java-copy-directory
    private static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

    public static void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    private static void copyFile(File inFileStr, File outFileStr) throws IOException {

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inFileStr));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFileStr))) {

            byte[] buffer = new byte[1024 * 1024];
            int read = 0;
            while ((read = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }

            bis.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
