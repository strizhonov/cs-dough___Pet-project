package controller;

import entity.PlainTruck;
import org.apache.log4j.Logger;
import parser.ParserException;
import parser.ToListParser;
import service.TruckService;
import service.TruckServiceException;
import validator.FileValidator;
import validator.ValidationResult;
import validator.XMLValidator;

import java.util.List;

public class TruckController {

    private static final Logger LOGGER = Logger.getLogger(TruckController.class);
    private TruckService service;
    private FileValidator fileValidator;
    private XMLValidator xmlValidator;
    private ToListParser<PlainTruck> parser;

    public TruckController(TruckService service, FileValidator fileValidator,
                           XMLValidator xmlValidator, ToListParser<PlainTruck> parser) {
        this.service = service;
        this.fileValidator = fileValidator;
        this.xmlValidator = xmlValidator;
        this.parser = parser;
    }

    public void processFile(String path) throws TruckControllerException {
        ValidationResult fileValidationResult = fileValidator.validate(path);
        if (!fileValidationResult.isValid()) {
            LOGGER.error("File " + path + " is not valid.");
            throw new TruckControllerException("File " + path + " is not valid.");
        }

        ValidationResult xmlValidationResult = xmlValidator.validate(path);
        if (!xmlValidationResult.isValid()) {
            LOGGER.error("XML file " + path + " didn't passed XSD validation.");
            throw new TruckControllerException("XML file " + path + " didn't passed XSD validation.");
        }

        List<PlainTruck> trucks;
        try {
            trucks = parser.parse(path);
        } catch (ParserException e) {
            LOGGER.error(e);
            throw new TruckControllerException(e);
        }

        trucks.forEach(truck -> service.create(truck));
    }

    public void processAll(long warehouseId) throws TruckControllerException {
        try {
            service.processAll(warehouseId);
        } catch (TruckServiceException e) {
            LOGGER.error(e);
            throw new TruckControllerException(e);
        }
    }

    public List<PlainTruck> getAll() {
        return service.getAll();
    }

}
