package parser;

import entity.PlainTruck;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class TruckParser extends StAXToListParser<PlainTruck> {

    private static final String TRUCK = "truck";
    private static final String ID = "id";
    private static final String PERISHABLE = "perishable";
    private static final String CAPACITY = "capacity";
    private static final String CARGO = "cargo";

    private PlainTruck.Builder plainTruckBuilder;

    protected void proceedStartElement(XMLEvent event) {
        StartElement startElement = event.asStartElement();

        String qName = startElement.getName().getLocalPart();

        if (TRUCK.equals(qName)) {
            plainTruckBuilder = new PlainTruck.Builder();

            QName idName = new QName(ID);
            Attribute idAttribute = startElement.getAttributeByName(idName);
            String sId = idAttribute.getValue();
            long id = Long.parseLong(sId);

            boolean perishable;
            QName perishableName = new QName(PERISHABLE);
            Attribute perishableAttribute = startElement.getAttributeByName(perishableName);
            String sPerishable = perishableAttribute.getValue();
            perishable = Boolean.parseBoolean(sPerishable);

            plainTruckBuilder = plainTruckBuilder.setId(id).setPerishable(perishable);
        }
    }

    protected void proceedEndElement(XMLEvent event) {
        EndElement endElement = event.asEndElement();
        String qName = endElement.getName().getLocalPart();
        switch (qName) {
            case CAPACITY:
                int capacity = Integer.parseInt(data);
                plainTruckBuilder = plainTruckBuilder.setCapacity(capacity);
                break;
            case CARGO:
                int cargoWeight = Integer.parseInt(data);
                plainTruckBuilder = plainTruckBuilder.setCargoWeight(cargoWeight);
                break;
            case TRUCK:
                PlainTruck plainTruck = plainTruckBuilder.build();
                items.add(plainTruck);
                break;
            default:
                break;
        }
    }

}
