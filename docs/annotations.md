# Using the annotations API
First thing for anything to work is register the affected classes.
```java
//service is the implementation of DriveService
ConversionHandler conversion = service.getConversionHandler();
conversion.registerClasses(GreatEntity.class);
```
If you forget doing this errors will occur.
## Simple field saving
Now that you have your service you will probably want to start mapping your first classes. As an example we will map the class `GreatEntity` here.
```java
@SaveAll
public class GreatEntity {

  //No-Args constructor is required, when reading values
  public GreatEntity() {
    this.feet = 2;
  }
  
  public GreatEntity(String entityName, int age, String[] addresses, int feet) {
    this.entityName = entityName;
    this.age = age;
    this.addresses = addresses;
    this.feet = feet;
  }

  private String entityName;  
  private int age;  
  private String[] addresses;
  
  private int feet;
}
```

## Advanced annotation handling

`@SaveVar(properties)` | scope = Field
properties:
* name: String | default "" || name field will be saved under and loaded from. Leave empty for fieldName-
* aliases: String[] | default {} || A list of aliases this fields value might be saved under. Used when name not found
* optional: Boolean | default true || Is the field optional. If not class cannot be loaded without the value being asigned
> Fields marked with this will be saved and loaded under this value


`@SaveTable(properties)` | scope = Class
properties:
*  value: String | default "" || table name to save under, if query does not force other
> Should be used to save the entity under a specific table. This will pregenerate a table depending on the backend type

`@SaveAll(properties)` | scope = Class
properties:
* ignore: String[] | default {} || field names to ignore
> This will tell the program to save all fields. Note: @SaveVar and @IgnoreVar will overwrite this behaviour


`@IgnoreVar()` | scope = Field
> Fields marked with this will be ignored when saving and loading

`@WriteOnly()` | scope = Field
> Fields marked with this will only be saved, but not loaded.

`@ReadOnly()` | scope = Field
> Fields marked with this will only be loaded, but not saved.

> **Note:** Having both ReadOnly and WriteOnly on a field will lead to unexpected behaviour and should be replaced by SaveVar
