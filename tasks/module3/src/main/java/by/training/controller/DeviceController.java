package by.training.controller;

import by.training.command.Command;
import by.training.command.CommandException;
import by.training.command.CommandProvider;
import by.training.command.CommandType;
import by.training.entity.Device;
import by.training.service.DeviceService;
import by.training.validator.FileValidator;
import by.training.validator.ValidationResult;
import by.training.validator.XMLValidator;
import org.apache.log4j.Logger;

import java.util.List;

public class DeviceController {

    private static final Logger LOGGER = Logger.getLogger(DeviceController.class);
    private FileValidator fileValidator;
    private XMLValidator xmlValidator;
    private CommandProvider<Device> commandProvider;
    private DeviceService service;

    public DeviceController(FileValidator fileValidator, XMLValidator xmlValidator,
                            CommandProvider<Device> commandProvider, DeviceService service) {
        this.fileValidator = fileValidator;
        this.xmlValidator = xmlValidator;
        this.commandProvider = commandProvider;
        this.service = service;
    }

    public void process(String path, CommandType commandType) throws DeviceControllerException {
        ValidationResult fileValidationResult = fileValidator.validate(path);
        if (!fileValidationResult.isValid()) {
            LOGGER.error("File " + path + " is not valid.");
            throw new DeviceControllerException("File " + path + " is not valid.");
        }

        ValidationResult xmlValidationResult = xmlValidator.validate(path);
        if (!xmlValidationResult.isValid()) {
            LOGGER.error("XML file " + path + " didn't passed XSD validation.");
            throw new DeviceControllerException("XML file " + path + " didn't passed XSD validation.");
        }

        Command<Device> command = commandProvider.get(commandType);
        if (command == null) {
            LOGGER.error("No command found by " + commandType + " type.");
            throw new DeviceControllerException("No command found by " + commandType + " type.");
        }

        List<Device> devices;
        try {
            devices = command.execute(path);
        } catch (CommandException e) {
            LOGGER.error(e);
            throw new DeviceControllerException(e);
        }

        devices.forEach(device -> service.create(device));

    }
}
