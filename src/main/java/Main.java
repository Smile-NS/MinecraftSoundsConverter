import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Main {

    // ユーザーディレクトリ
    static final String home = System.getProperty("user.home");
    // 変換元のファイルがあるディレクトリ
    static final File dir = new File(replaceSep(home + "\\AppData\\Roaming\\.minecraft\\assets\\objects"));
    // 出力ディレクトリ
    static final File outputDir = new File(replaceSep(" "));
    // 参照するJSON
    static final File index = new File(replaceSep(home + "\\AppData\\Roaming\\.minecraft\\assets\\indexes\\1.16.json"));

    public static void main(String[] args) {
        SoundFileManager manager = new SoundFileManager(dir);

        try {
            FileSelector converter = new FileSelector(index);

            System.out.println("変換するファイルをリストアップします...");
            long begin1 = System.currentTimeMillis();
            Set<Map.Entry<String, String>> entrySet = converter.getSoundFileMap().entrySet();
            long end1 = System.currentTimeMillis();
            System.out.println("リストアップが完了しました(" + (end1 - begin1) + "ms)" );


            System.out.println("変換を開始します...");
            long begin2 = System.currentTimeMillis();
            for (Map.Entry<String, String> entry : entrySet) {
                String fileName = entry.getKey();
                String path = entry.getValue().replace("minecraft/sounds/", "");
                String outputName = path.substring(path.lastIndexOf("/") + 1);
                File file = manager.search(fileName);

                manager.output(outputDir, file, path.replace(outputName, ""), outputName, "mp3");
            }
            long end2 = System.currentTimeMillis();
            System.out.println("変換が完了しました(" + (end2 - begin2) + "ms)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String replaceSep(String path) {
        return path.replace("\\", File.separator);
    }
}
