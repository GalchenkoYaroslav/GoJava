package ua.goit.serializers;

import ua.goit.model.*;

public class XMLSerializer implements Serializer {

  @Override
  public String serialize(ContainerShapes object) {
    Serializer serializer = AdapterMaps.getValue(object.getType(), SerializerType.XML);
    return serializer.serialize(object);
  }
}