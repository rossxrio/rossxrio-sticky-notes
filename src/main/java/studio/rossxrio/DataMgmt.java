package studio.rossxrio;

import java.io.*;
import java.util.ArrayList;

public class DataMgmt {
    private final static String APP_DIR_PATH = String.format("%s%s.sticky-notes", System.getProperty("user.home"), File.separator);

    public final static File APP_DIR = new File(APP_DIR_PATH);
    public final static File NOTES_DIR = new File(APP_DIR, "notes");
    public final static File CONFIG_DIR = new File(APP_DIR, ".config");

    public final static ArrayList<Data> DATA_INDEX = new ArrayList<>();
    public final static File DATA_INDEX_FILE = new File(CONFIG_DIR, "data.dat");

    public static void updateDataObjects(Data data) {
        try {
            if (data != null) DATA_INDEX.add(data);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_INDEX_FILE));
            for (Data d : DATA_INDEX) oos.writeObject(d);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadDataIndex() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_INDEX_FILE))) {
            Object o;
            while((o = ois.readObject()) != null) {
                if (o instanceof Data) DATA_INDEX.add((Data) o);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println();
        }
    }

    public static String loadData(Data data) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(data.getPath()))) {
            String str;
            while ((str = br.readLine())!= null) sb.append(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public static void saveData(Data data, String content) {
        // TO-DO
        File f = new File(NOTES_DIR, data.getName());
        createResource(f, data.getPath(), true);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(data.getPath()))) {
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initApp() throws IOException, ClassNotFoundException {
        createResource(APP_DIR, "App directory", true);
        createResource(NOTES_DIR, "Notes directory", true);
        createResource(CONFIG_DIR, "Config directory", true);
        createResource(DATA_INDEX_FILE, "data.dat", false);
        loadDataIndex();
    }

    private static void createResource(File file, String alias, boolean isDir) {
        try {
            if (!file.exists()) {
                if (isDir) {
                    if (file.mkdir()) System.out.printf("> %s directory created\n", alias);
                    else System.err.printf("> Couldn't create %s directory, possible invalid path\n", alias);
                } else {
                    if (file.createNewFile()) System.out.printf("> %s file created\n", alias);
                    else System.err.printf("> Couldn't create %s file, possible invalid path\n", alias);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}