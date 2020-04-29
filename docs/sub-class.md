Realm Drive allows defining sub and abstract classes.

> **Note** this only works, if the object was saved with RealmDrive.

#### Example:

> Removed getter/setter/noargs constructor/allargs constructor for simplicity

**Model classes**
```java
import net.endrealm.realmdrive.annotations.SaveAll;

@SaveAll
public class MyObject {
    private DynamicInput input;
}

/**
* This could also be a normal/abstract class
*/
public interface DynamicInput {
    String getAddress();
}

@SaveAll
public class Input1 implements DynamicInput {
    private String street;
    private int houseNr;
    
    public String getAddress() {
        return street + " " + houseNr;
    }
}

@SaveAll
public class Input2 implements DynamicInput {
    private double longitude;
    private double latitude;
    
    public String getAddress() {
        return someLatLongToAddressCode(this);
    }
}
```

**Usage example**
```java
public class Main {
    public static void main(String[] args) {
        DriveService service = ...; // Get your service

        // Register classes
        service.getConversionHandler()
            .registerClasses(MyObject.class, Input1.class, Input2.class);
        
        // Create example objects
        MyObject obj1 = new MyObject(new Input1("adams-street", 14));
        MyObject obj2 = new MyObject(new Input2(12.2, 12.2));
        
        service.getWriter().write(obj1);
        service.getWriter().write(obj2);

        Query query = new Query().build(); // select all
        List<MyObject> all = service.getReader().readAll(query, GreatEntity.class);
    
        assert all.get(0).equals(obj1);
        assert all.get(1).equals(obj2);
    }
}
```
