package FSMLite.violetParsers.importer;

public class StateViolet {

    String javaVersion;
    String javaClass;
    String mainObjectClass;

    public StateViolet(String javaVersion, String javaClass, String objClass) {
        this.javaVersion = javaVersion;
        this.javaClass = javaClass;
        this.mainObjectClass = objClass;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public String getMainObjectClass() {
        return mainObjectClass;
    }

    public String toString() {
        String str = "JavaVersion: " + javaVersion
                + ", JavaClass: " + javaClass
                + ", mainObjectClass: " + mainObjectClass;

        return str;
    }
}
