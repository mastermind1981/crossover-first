package com.aurea.deadcode.detector.infra.service.understand;

import com.scitools.understand.Understand;
import com.scitools.understand.UnderstandException;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class UnderstandDatabaseFactory {

    private UnderstandDatabase database;

    public UnderstandDatabase getDatabase(final String udbPath) {

            try {
                this.database = new UnderstandDatabase(Understand.open(udbPath), new ReentrantLock());
//                this.database = (Understand.open(udbPath));
            } catch (UnderstandException e) {
                e.printStackTrace();
            }
//        }

        return database;
    }

}
