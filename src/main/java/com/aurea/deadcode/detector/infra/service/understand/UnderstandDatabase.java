package com.aurea.deadcode.detector.infra.service.understand;

import com.scitools.understand.Database;
import com.scitools.understand.Entity;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

public class UnderstandDatabase implements Closeable {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Database database;
    private final Lock lock;

    public UnderstandDatabase(Database database, Lock lock) {
        this.database = database;
        this.lock = lock;
        open();
    }

    private void open() {
        executorService.submit(lock::lock);
    }

    @Override
    public void close() throws IOException {
        this.database.close();
        executorService.submit(lock::unlock);
    }

    public Entity[] ents(String kinds) {
        return database.ents(kinds);
    }

}