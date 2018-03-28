package lab.cadl.lirui.study.java.basic.versions;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "person-list")
public class PersonList {
    @XmlElement
    private String departName;
    @XmlElements({
            @XmlElement(name = "person", type = Person.class),
            @XmlElement(name = "student", type = Student.class)
    })
    private List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    @Override
    public String toString() {
        return "PersonList{" +
                "departName='" + departName + '\'' +
                ", personList=" + personList +
                '}';
    }
}
