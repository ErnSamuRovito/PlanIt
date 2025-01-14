package model;

import model.factories.UiFactory;

import java.util.HashMap;
import java.util.Map;

public class UIComponentFactoryRegistry {

    // Mappa per contenere i factory
    private final Map<String, UiFactory> factories = new HashMap<>();

    // Istanza Singleton
    private static UIComponentFactoryRegistry instance;

    // Costruttore privato per evitare istanziazione esterna
    private UIComponentFactoryRegistry() {
    }

    // Metodo statico per ottenere l'istanza Singleton
    public static UIComponentFactoryRegistry getInstance() {
        if (instance == null) {
            instance = new UIComponentFactoryRegistry();
        }
        return instance;
    }

    // Metodo per registrare una factory
    public void registerFactory(String componentType, UiFactory factory) {
        factories.put(componentType, factory);
    }

    // Metodo per ottenere una factory
    public UiFactory getFactory(String componentType) {
        UiFactory factory = factories.get(componentType);
        if (factory == null) {
            throw new IllegalArgumentException("Factory not found for component type: " + componentType);
        }
        return factory;
    }
}
