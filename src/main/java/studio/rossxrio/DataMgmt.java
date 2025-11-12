package studio.rossxrio;

import java.io.*;
import java.util.HashSet;

public class DataMgmt {
    private final static String APP_DIR_PATH = String.format("%s%s.sticky-notes", System.getProperty("user.home"), File.separator);

    public final static File APP_DIR = new File(APP_DIR_PATH);
    public final static File NOTES_DIR = new File(APP_DIR, "notes");
    public final static File CONFIG_DIR = new File(APP_DIR, ".config");

    public final static HashSet<Data> DATA_INDEX = new HashSet<>();
    public final static File DATA_INDEX_FILE = new File(CONFIG_DIR, "data.dat");

    public static void initData() {
        createResource(APP_DIR, "App directory", true);
        createResource(NOTES_DIR, "Notes directory", true);
        createResource(CONFIG_DIR, "Config directory", true);
        createResource(DATA_INDEX_FILE, "data.dat", false);
        loadDataIndex();
        removeNonIndexedFiles();
    }

    public static void updateDataObjects() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_INDEX_FILE));
            for (Data d : DATA_INDEX) oos.writeObject(d);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveData(Data data, String content) {
        File f = new File(NOTES_DIR, data.getName());
        createResource(f, data.getName(), true);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(data.getPath()))) {
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadData(Data data) {
        if (data.getName().equalsIgnoreCase("new")) return "";
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(data.getPath()))) {
            String str;
            while ((str = br.readLine())!= null) sb.append(str).append('\n');
        } catch (IOException e) {
            System.err.println("File not found" + data.getPath());
        }

        return sb.toString();
    }

    public static void loadDataIndex() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_INDEX_FILE))) {
            Object o;
            while((o = ois.readObject()) != null) {
                if (o instanceof Data) DATA_INDEX.add((Data) o);
            }
            System.out.printf("> %d Data items loaded", DATA_INDEX.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println();
        }
    }

    public static void removeNonIndexedFiles() {
        // TO-DO temp implementation. This will do the trick for
        // now, however I want it to be more efficient.

        File[] files = NOTES_DIR.listFiles();
        assert files != null;

        if (DATA_INDEX.isEmpty()) {
            for (File f : files) {
                recursiveDelete(f);
            }
            return;
        }

        int i = 0;
        for (Data dataIndex : DATA_INDEX) {
            if (!dataIndex.getName().equalsIgnoreCase(files[i].getName())) recursiveDelete(files[i]);
            i++;
        }

        if (files.length > DATA_INDEX.size()) {
            int start = files.length - (files.length - DATA_INDEX.size());
            for (int j = start; j < files.length; j++) {
                recursiveDelete(files[j]);
            }
        }

    }

    private static void recursiveDelete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) recursiveDelete(f);
                    else f.delete();
                }
            }
            file.delete();

        } else file.delete();
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