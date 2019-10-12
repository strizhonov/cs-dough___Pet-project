package by.training.controller;

import by.training.builder.EntityCreator;
import by.training.entity.Vegetable;
import by.training.reader.VegetableAttributesReader;
import by.training.service.VegetableService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class VegetableController {

    private static final Logger LOGGER = Logger.getLogger(VegetableController.class);

    private EntityCreator<Vegetable> entityCreator;
    private VegetableService service;
    private VegetableAttributesReader reader;

    public VegetableController(EntityCreator<Vegetable> entityCreator,
                               VegetableService service,
                               VegetableAttributesReader reader) {
        this.entityCreator = entityCreator;
        this.service = service;
        this.reader = reader;
    }

    public void transformFileToVegetables(String filePath) throws IOException {
        List<String> attributes = reader.getAttributesFrom(filePath);

        for (int i = 0; i < attributes.size(); i++) {
            try {
                Vegetable vegetable = entityCreator.createItemFrom(attributes.get(i));
                service.addToRepo(vegetable);
                LOGGER.info("Line " + i + " parsed and object created.");
            } catch (InvalidLineException e) {
                LOGGER.error("Line " + i + " is invalid.", e);
            }
        }

    }

    public EntityCreator<Vegetable> getEntityCreator() {
        return entityCreator;
    }

    public VegetableService getService() {
        return service;
    }

    public VegetableAttributesReader getReader() {
        return reader;
    }

    public List<Vegetable> getAll() {
        return service.getAll();
    }
}
