package by.training.controller;

import by.training.entity.PlainWarehouse;
import org.apache.log4j.Logger;
import by.training.parser.ParserException;
import by.training.parser.ToListParser;
import by.training.service.WarehouseService;
import by.training.validator.FileValidator;
import by.training.validator.ValidationResult;
import by.training.validator.XMLValidator;

import java.util.List;

public class WarehouseController {

    private static final Logger LOGGER = Logger.getLogger(WarehouseController.class);
    private WarehouseService service;
    private FileValidator fileValidator;
    private XMLValidator xmlValidator;
    private ToListParser<PlainWarehouse> parser;

    public WarehouseController(WarehouseService service, FileValidator fileValidator,
                               XMLValidator xmlValidator, ToListParser<PlainWarehouse> parser) {
        this.service = service;
        this.fileValidator = fileValidator;
        this.xmlValidator = xmlValidator;
        this.parser = parser;
    }

    public void processFile(String path) throws WarehouseControllerException {
        ValidationResult fileValidationResult = fileValidator.validate(path);
        if (!fileValidationResult.isValid()) {
            LOGGER.error("File " + path + " is not valid.");
            throw new WarehouseControllerException("File " + path + " is not valid.");
        }

        ValidationResult xmlValidationResult = xmlValidator.validate(path);
        if (!xmlValidationResult.isValid()) {
            LOGGER.error("XML file " + path + " didn't passed XSD validation.");
            throw new WarehouseControllerException("XML file " + path + " didn't passed XSD validation.");
        }

        List<PlainWarehouse> warehouses;
        try {
            warehouses = parser.parse(path);
        } catch (ParserException e) {
            LOGGER.error(e);
            throw new WarehouseControllerException(e);
        }

        warehouses.forEach(warehouse -> service.create(warehouse));
    }

}
