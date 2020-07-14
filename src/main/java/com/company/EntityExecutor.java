package com.company;

import javax.persistence.EntityManager;
import java.util.function.Function;

@FunctionalInterface
public interface EntityExecutor {
    Void apply(Function<EntityManager, Void> l);
}
