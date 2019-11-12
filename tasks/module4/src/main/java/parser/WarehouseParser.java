package parser;

import entity.PlainWarehouse;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class WarehouseParser extends StAXToListParser<PlainWarehouse> {

    private static final String WAREHOUSE = "warehouse";
    private static final String ID = "id";
    private static final String CAPACITY = "capacity";
    private static final String CARGO_WEIGHT = "cargo_weight";
    private static final String TERMINALS_COUNT = "terminals_count";

    private PlainWarehouse.Builder plainWarehouseBuilder;

    protected void proceedStartElement(XMLEvent event) {
        StartElement startElement = event.asStartElement();

        String qName = startElement.getName().getLocalPart();

        if (WAREHOUSE.equals(qName)) {
            plainWarehouseBuilder = new PlainWarehouse.Builder();

            QName idName = new QName(ID);
            Attribute idAttribute = startElement.getAttributeByName(idName);
            String sId = idAttribute.getValue();
            long id = Long.parseLong(sId);

            plainWarehouseBuilder = plainWarehouseBuilder.setId(id);
        }

    }

    protected void proceedEndElement(XMLEvent event) {
        EndElement endElement = event.asEndElement();
        String qName = endElement.getName().getLocalPart();
        switch (qName) {
            case CAPACITY:
                int capacity = Integer.parseInt(data);
                plainWarehouseBuilder = plainWarehouseBuilder.setCapacity(capacity);
                break;
            case CARGO_WEIGHT:
                int cargoWeight = Integer.parseInt(data);
                plainWarehouseBuilder = plainWarehouseBuilder.setCargoWeight(cargoWeight);
                break;
            case TERMINALS_COUNT:
                int terminalsCount = Integer.parseInt(data);
                plainWarehouseBuilder = plainWarehouseBuilder.setTerminalsCount(terminalsCount);
                break;
            case WAREHOUSE:
                PlainWarehouse plainWarehouse = plainWarehouseBuilder.build();
                items.add(plainWarehouse);
                break;
            default:
                break;
        }
    }

}
