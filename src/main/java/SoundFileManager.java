import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SoundFileManager {

    private final File dir;

    public SoundFileManager(File dir) {
        this.dir = dir;
    }

    public File search(String fileName) {
        File[] listFile = dir.listFiles();
        assert listFile != null;

        for (File file : listFile) {

            if (file.isFile() && file.getName().equalsIgnoreCase(fileName)) return file;
            else if (file.isDirectory()) {
                File result = new SoundFileManager(file).search(fileName);
                if (result != null) return result;
            }
        }
        return null;
    }

    public void output(File outputDir, File source, String restorePath, String outputName, String ext) {
        restorePath = restorePath.replace("/", File.separator);
        String path = outputDir.getPath() +  File.separator + restorePath;
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File target = new File(path + File.separator + outputName.replace("ogg", ext));
        if (target.exists()) {
            System.out.println("このファイルは既に存在します");
            return;
        }

        Path sourcePath = Paths.get(source.getPath());
        Path targetPath = Paths.get(target.getPath());

        try {
            Files.copy(sourcePath, targetPath);
            System.out.println(target.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
