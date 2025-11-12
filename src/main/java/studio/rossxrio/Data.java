package studio.rossxrio;

import java.io.File;
import java.io.Serializable;

public class Data implements Serializable {
    private String name;
    private String path;

    private final static String DEFAULT_PATH = DataMgmt.NOTES_DIR.getPath();

    public Data(String name) {
        this.name = name;
        this.path = (name.isBlank() ?
                String.format("%s%s%s%s%s.txt", DEFAULT_PATH, File.separator, "unnamed", File.separator, "unnamed") :
                String.format("%s%s%s%s%s.txt", DEFAULT_PATH, File.separator, name, File.separator, name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        path = String.format("%s%s%s%s%s.txt", DEFAULT_PATH, File.separator, this.name, File.separator, this.name);
    }

    public String getPath() {
        return path;
    }
}
