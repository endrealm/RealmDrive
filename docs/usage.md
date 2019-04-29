# Usage
## Build
This project currently has no pre-built versions. The only way to get the dependency jar is to build it yourself.
### Build with Maven
**Used IDE:** *Intellij IDEA*
1. Clone or download the project
2. Open project folder with the IDE
3. Right-Click the pom.xml and hit add as maven project
4. Import changes if required
5. After the dependencies are loaded, open up the maven tab on the right side and click install under lifecycle
The latest version should than be installed to your local repository. Continue with [Include into project](https://github.com/endrealm/RealmDrive/blob/master/docs/usage.md#include-into-project)

## Include into project
### Include with Maven

Add the following dependency to your pom.xml
```xml
<dependency>
    <groupId>net.endrealm</groupId>
    <artifactId>realm-drive</artifactId>
    <version>0.0.1-PRE</version>
    <scope>compile</scope>
</dependency>
```
Choose any version you like. To always use the newest version use `LATEST` (Though this might lead to unwanted updates). On top of that you will probably want to depend on the driver of the backend you want to use. To keep the RealmDrive Lightweighted the drivers are not shaded along. Check [here](https://github.com/endrealm/RealmDrive/blob/master/docs/driver-support.md) to find supported drivers.

## Getting started

### Basics
To get started using RealmDrive you will probably want to know how it works. Here is a simple explanation:
You can comunicate with the backend through the reader and writer held in a driver service. You additionally have direct access to the backend implementation, but do keep in mind that direct interactions might require backend specific code and will prevent switching the backend later on. Java classes can directly be converted into database entities and saved into the database. For this to work classes must be registered into the conversion handler of the used service. Fields to save must be annotated with `@SaveVar`. Retrieving data out of the backend is simple as well. Just build a query with the QueryBuilder, do note that some complex query setups might be incompatible with some backends. Retrieved values can be automatically converted into Java classes. Either by handing over a mapped class or by letting the conversion service try to automatically find the right class. Unmapped objects can be interacted with as DriveObject instances.

### Setup
To get started we will first need a DriveService. The DriveServiceFactory will help out here. First thing we need is a DriveSettings object. The following code shows an example on how the settings for a MongoDB database is setup.
```java
DriveSettings settings = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("yourDatabase")
                .table("defaultTable")
                .build()
```
The table and database defined here are used as a default/fallback, if no other is specified in the used query.

Now there is little left to get a simple DriveService implementation
```java
DriveService service = new DriveServiceFactory().getDriveService(settings);
```

#### Marking fields
Now that you have your service you will probably want to start mapping your first classes. As an example we will map the class `GreatEntity` here.
```java
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

  @SaveVar
  private String entityName;
  
  @SaveVar
  private int age;
  
  @SaveVar
  private String[] addresses;
  
  private int feet;
}
```
**Note** All variables, but `feet` are annotated with @SaveVar. Only those annotated will be saved and retrieved.
#### Register classes
Now we will want to go ahead and tell the conversion handler that this class can be saved. This is required to automatically transform database entries to classes.
```java
//service is the implementation of DriveService
ConversionHandler conversion = service.getConversionHandler();
conversion.registerClasses(GreatEntity.class);
```

#### Writing
> Writing in the current version has not been optimized yet.

Now we are going to write objects to our database. We are going to keep using our example class `GreatEntity`, our DriveService instance `service` and the retrieved instance of the ConversionHandler named `conversion`. From here it is pretty straight forward.

##### Simple Write
This will add an entry to the database. It will try to add the entry, this can cause duplicate entries.
```java
service.getWriter().write(conversion.transform(new GreatEntity("Josh", 19, new String[] {"nowhere lol"}, 3)));
```
##### Overwrite
When choosing overwrite all entities matching the query will be removed and the data will be added to the database.
```java
Query query = new Query().addEq().setField("entityName").setValue("Josh").close().build();
service.getWriter().write(conversion.transform(new GreatEntity("Josh", 19, new String[] {"nowhere lol"}, 3)), true, query);
```
##### Deleting
Delete will delete values matching the query. It will delete the specified amount of entities.
```java
Query query = new Query().addEq().setField("entityName").setValue("Josh").close().build();
service.getWriter().delete(query, 12); //Deletes twelve entities where entityname == Josh
```
#### Reading

Now we are going to read objects from our database. We are going to keep using our example class `GreatEntity`, our DriveService instance `service` and the retrieved instance of the ConversionHandler named `conversion`. From here it is pretty straight forward as well.

##### Read One
```java
Query query = new Query().addEq().setField("entityName").setValue("Josh").close().build();
DriveObject object = service.getReader().readObject(query); //Reads an object as a DriveObject. This can later be converted into a class object via a DriveObject method
GreatEntity entity = service.getReader().readObject(query, GreatEntity.class);
```

##### Read All
```java
Query query = new Query().addEq().setField("entityName").setValue("Josh").close().build();
Iterable<DriveObject> objects = service.getReader().readAllObjects(query);
Iterable<GreatEntity> entities = service.getReader().readAllObjects(query, GreatEntity.class);
```

#### Writing Queries
When interacting with the reader and writer you will have to hand over Query objects. Some operations are database **specific** e.g. MongoDB features embedded documents that can be easily be searched for with `setField("child1.name")`, while other databases e.g. MySQL don't. Those issues will have to be resolved manually when switching backend.
Writing queries is pretty easy. Just create a new Query object and append requirements e.g. addAnd(). Each requirement has to be closed with .close(). Although right now not required, be sure to call .build() at the end later versions might require this.
**Example Query:**
```java
Query query = new Query()
        .addAnd()
            .addEq()
                .setField("name")
                .setValue("James")
            .close()
            .addIn()
                .setField("age")
                .addValue(1)
                .addValue(2)
                .addValue(40)
            .close()
        .close()
        .addOr()
            .addGt()
                .setField("height")
                .setValue(10)
            .close()
        .close()
    .build();
```
