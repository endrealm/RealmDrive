package net.endrealm.realmdrive.testing;

import net.endrealm.realmdrive.factory.DriveServiceFactory;
import net.endrealm.realmdrive.interfaces.*;
import net.endrealm.realmdrive.query.Query;
import net.endrealm.realmdrive.testing.model.Bar;
import net.endrealm.realmdrive.testing.model.Baz;
import net.endrealm.realmdrive.testing.model.Foo;
import net.endrealm.realmdrive.utils.DriveSettings;

import java.util.ArrayList;
import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {

        long time = System.currentTimeMillis();


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

        DriveService driveService = new DriveServiceFactory().getDriveService(
                DriveSettings.builder()
                        .type(DriveSettings.BackendType.MONGO_DB)
                        .hostURL("mongodb://localhost:27017")
                        .database("findlunch")
                        .table("stats")
                        .build()
        );

        DriveWriter writer = driveService.getWriter();
        DriveReader reader = driveService.getReader();
        ConversionHandler conversion = driveService.getConversionHandler();
        conversion.registerClasses(Foo.class, Bar.class, Baz.class);

        //Remove all traces
        clearAll(writer);

        {
            Bar bar = new Bar('b', "This is a bar", 2, 4L, 3.0f, 1.02d, (short) 2);
            Bar bar2 = new Bar('c', "This is another bar", 4, 4L, 3.0f, 1.02d, (short) 2);
            Foo foo = new Foo('a', "This is a foo", 1, 1L, 1.0f, 1.0d, bar);
            Foo foo2 = new Foo('b', "This is a foo2", 1, 1L, 1.0f, 1.0d, bar);
            Baz baz = new Baz("Its just a baz, bro!", new ArrayList<>(Arrays.asList(bar, bar, bar,bar,bar)));
            Baz baz2 = new Baz("Its just a baz2, bro!", new ArrayList<>(Arrays.asList(bar2, bar2, bar2,bar2,bar2)));

            DriveObject bazStats = conversion.transform(baz);
            DriveObject baz2Stats = conversion.transform(baz2);
            DriveObject fooStats = conversion.transform(foo);
            DriveObject foo2Stats = conversion.transform(foo2);


            //Baz quarries
            {

                //System.out.println("Val: "+reader.readObject(new Query().addEq().setField("classType").setValue("Foo").close().build()));
                //Add 1000 bazs
                for(int i = 0; i < 1000; i++)
                    writer.write(bazStats);
                //Add 1000 baz2s
                for(int i = 0; i < 1000; i++)
                    writer.write(baz2Stats);
                //Add 500 foos
                for(int i = 0; i < 500; i++)
                    writer.write(fooStats);
                //Add 500 foo2s
                for(int i = 0; i < 500; i++)
                    writer.write(foo2Stats);

                //Replace 250 foo2s by foos
                for(int i = 0; i < 250; i++)
                    writer.write(fooStats, true, new Query().addAnd().addEq().setField("value0").setValue("b").close().addEq().setField("classType").setValue(Foo.class.getName()).close().close().build());

                //System.out.println(reader.readAllObjects(new Query().addEq().setField("classType").setValue("Foo").close().build()));

                //Delete 250 baz2s with at least one bar2 inside
                writer.delete(new Query().addEq().setField("value1.value0").setValue("c").close().addEq().setField("classType").setValue(Baz.class.getName()).close().build(),250);

            }

        }

        //Remove all traces
        clearAll(writer);

        System.out.println(String.format("\nProgram took %.3f seconds to execute!",((System.currentTimeMillis()-time)/1000d)));

    }

    private static void clearAll(DriveWriter writer) {
        Query query1 = new Query()
                .addOr()
                .addEq()
                .setField("classType")
                .setValue(Foo.class.getName())
                .close()
                .addEq()
                .setField("classType")
                .setValue(Bar.class.getName())
                .close()
                .addEq()
                .setField("classType")
                .setValue(Baz.class.getName())
                .close()
                .close()
                .build();

        writer.delete(query1, -1);
    }
}
