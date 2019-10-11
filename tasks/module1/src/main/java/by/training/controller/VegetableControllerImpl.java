package by.training.controller;

import by.training.builder.EntityCreator;
import by.training.entity.Vegetable;
import org.apache.log4j.Logger;
import by.training.reader.VegetableAttributesReader;
import by.training.service.EntityService;

import java.io.IOException;
import java.util.List;

public class VegetableControllerImpl implements VegetableController {

    private static final Logger LOGGER = Logger.getLogger(VegetableControllerImpl.class);

    private EntityCreator<Vegetable> entityCreator;
    private EntityService<Vegetable> entityService;
    private VegetableAttributesReader reader;

    public VegetableControllerImpl(EntityCreator<Vegetable> entityCreator,
                                   EntityService<Vegetable> entityService,
                                   VegetableAttributesReader reader) {
        this.entityCreator = entityCreator;
        this.entityService = entityService;
        this.reader = reader;
    }

    @Override
    public void transformFileToVegetables(String filePath) throws IOException {
        List<String> attributes = reader.getAttributesFrom(filePath);

        for (int i = 0; i < attributes.size(); i++) {
            try {
                Vegetable vegetable = entityCreator.createItemFrom(attributes.get(i));
                entityService.addToRepo(vegetable);
                LOGGER.info("Line " + i + " parsed and object created.");
            } catch (InvalidLineException e) {
                LOGGER.error("Line " + i + " is invalid.", e);
            }
        }

    }

    public EntityCreator<Vegetable> getEntityCreator() {
        return entityCreator;
    }

    public EntityService<Vegetable> getEntityService() {
        return entityService;
    }

    public VegetableAttributesReader getReader() {
        return reader;
    }

    public List<Vegetable> getAll() {
        return entityService.getAll();
    }
}
