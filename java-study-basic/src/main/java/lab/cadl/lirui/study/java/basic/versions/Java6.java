package lab.cadl.lirui.study.java.basic.versions;

import javax.tools.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Java6 {
    private static Map<String, JavaFileObject> fileObjects = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        // 1. Desktop and SystemTray
        // Desktop.getDesktop().browse(new URI("http://www.baidu.com"));

        ImageObserver imageObserver = (img, infoFlags, x, y, width, height) -> false;

        for (TrayIcon trayIcon : SystemTray.getSystemTray().getTrayIcons()) {
            System.out.printf("%dx%d\n", trayIcon.getImage().getHeight(imageObserver), trayIcon.getImage().getHeight(imageObserver));
        }

        // 2. JAXB2
        JAXBContext personContext = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = personContext.createUnmarshaller();
        Person person = (Person) unmarshaller.unmarshal(Java6.class.getResource("Person.xml"));
        System.out.println(person);

        JAXBContext listContext = JAXBContext.newInstance(PersonList.class);
        unmarshaller = listContext.createUnmarshaller();
        PersonList personList = (PersonList) unmarshaller.unmarshal(Java6.class.getResource("PersonList.xml"));
        System.out.println(personList);

        // 3. StAX, ignore

        // 4. compiler API
        String code = "public class HelloWorld {\n" +
                "    public void hello() {\n" +
                "        System.out.println(\"hello, world\");\n" +
                "    }\n" +
                "}";
        JavaObjectFromString javaObject = new JavaObjectFromString("HelloWorld", code);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        JavaFileManager javaFileManager = new MyJavaFileManager(compiler.getStandardFileManager(collector, null, null));

        JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, null, null, null, Collections.singleton(javaObject));
        boolean result = task.call();
        System.out.printf("compilation result: %s\n", result);
        Class<?> clz = new MyClassLoader().loadClass("HelloWorld");
        clz.getMethod("hello").invoke(clz.newInstance());

        // 5. HTTP Server API, ignore

        // 6. Pluggable Annotation Processing API, ignore

        // 7. Console, ignore
    }

    static class JavaObjectFromString extends SimpleJavaFileObject {
        private String code;
        private ByteArrayOutputStream byteArrayOutputStream;

        public JavaObjectFromString(URI uri, Kind kind) {
            super(uri, kind);
        }

        JavaObjectFromString(String className, String code) {
            super(URI.create("String:///" + className + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return code;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            if (byteArrayOutputStream == null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
            }

            return byteArrayOutputStream;
        }

        byte[] getCompiledBytes() {
            return byteArrayOutputStream.toByteArray();
        }
    }

    static class MyClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            JavaFileObject fileObject = fileObjects.get(name);
            if (fileObject != null) {
                byte[] bytes = ((JavaObjectFromString) fileObject).getCompiledBytes();
                return defineClass(name, bytes, 0, bytes.length);
            }
            try {
                return ClassLoader.getSystemClassLoader().loadClass(name);
            } catch (Exception e) {
                return super.findClass(name);
            }
        }
    }

    public static class MyJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        protected MyJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
            JavaFileObject javaFileObject = fileObjects.get(className);
            if(javaFileObject == null){
                super.getJavaFileForInput(location, className, kind);
            }
            return javaFileObject;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String qualifiedClassName, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            JavaFileObject javaFileObject = new JavaObjectFromString(URI.create(qualifiedClassName), kind);
            fileObjects.put(qualifiedClassName, javaFileObject);
            return javaFileObject;
        }
    }
}
