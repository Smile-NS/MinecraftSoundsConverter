import io.github.smile_ns.simplejson.SimpleJson;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileSelector {

    private final SimpleJson json;

    public FileSelector(File ref) throws IOException {
        json = new SimpleJson(ref);
        json.setSeparator("#");
    }

    public Map<String, String> getSoundFileMap() {
        Map<String, String> map = new LinkedHashMap<>();

        for (String key : json.getKeySet("objects")) {
            if (key.contains("minecraft/sounds/"))
                map.put(json.getString("objects#" + key + "#hash"), key);
        }

        return map;
    }
}
