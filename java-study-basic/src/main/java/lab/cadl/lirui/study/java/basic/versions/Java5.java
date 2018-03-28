package lab.cadl.lirui.study.java.basic.versions;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import static lab.cadl.lirui.study.java.basic.versions.StaticMethods.*;

public class Java5 {
    enum Enum {
        ENUM1,
        ENUM2
    }

    public static void main(String[] args) throws Exception {
        // 1. auto box
        int number = new Integer(5);
        Integer numberObject = 5;

        // 2. enum
        Enum e = Enum.ENUM1;

        // 3. static import
        helloWorld();
        hello("Li Rui");

        // 4. variable arguments
        helloAll("Li Si", "Zhang San", "Wang Wu");

        // 5. Introspect
        Person person = new Person("Li Rui", 28);
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        // 7. for each
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            System.out.printf("%s(%s): %s\n", descriptor.getName(), descriptor.getDisplayName(), descriptor.getReadMethod().invoke(person));
        }

        // 6. generic
        List<String> names = new ArrayList<String>();
        names.add("Age");
    }

    private static void helloAll(String ...names) {
        StringBuilder sb = new StringBuilder("hello, ");
        for (String name : names) {
            sb.append(name).append(", ");
        }

        System.out.println(sb);
    }
}
