Realm Drive can automatically find a constructor matching the type/super type of the parent object and will inject this to a constructor.
**To enable** this behaviour put `@InjectParent` above the class. If no suitable constructor is found it will fallback to the no args constructor.

### Example 1
```java
@SaveAll
public class Parent1 {
    private Child1 child1;
}

@InjectParent
public class Child1 {

    @IgnoreVar
    private Parent1 parent1;

    // This constructor will be used. If child one is 
    // loaded without Parent1 as its parent it will 
    // throw an error as no no-args constructor is supplied.
    public Child1(Parent1 parent1) {
        this.parent1 = parent1;
    }
}
```

### Example 2
```java
@SaveAll
public class Parent1 {
    private Child1 child1;
}
@SaveAll
public class Parent2 extends Parent1 {
    
}


@InjectParent
public class Child1 {

    @IgnoreVar
    private Parent1 parent1;

    // This constructor will be used. If child one is 
    // loaded without Parent1 or parent 2 as its parent it will 
    // throw an error as no no-args constructor is supplied.
    public Child1(Parent1 parent1) {
        this.parent1 = parent1;
    }
}
```

### Example 3
```java
@SaveAll
public class Parent1 {
    private Child1 child1;
}
@SaveAll
public class Parent2 extends Parent1 {
    
}


@InjectParent
public class Child1 {

    @IgnoreVar
    private Parent1 parent1;

    // This will be used if the parent object doesn't exist or is not a type of Parent1 or Parent2
    public Child1() {
    
    }

    // This constructor will be used for parent 1
    public Child1(Parent1 parent1) {
        this.parent1 = parent1;
    } 

    // This constructor will be used for parent 2
    public Child1(Parent1 parent1) {
        this.parent1 = parent1;
    }
}
```
