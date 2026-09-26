package XmlReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class XmlDataLoader {

    private XmlDataLoader() {
    }

    public static <T> Map<String, T> loadData(String filePath, Class<T> clazz) {
        Map<String, T> dataMap = new HashMap<>();

        try {
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            NodeList dataSets = document.getElementsByTagName("dataSets");
            if (dataSets.getLength() == 0) {
                return dataMap;
            }

            NodeList nodeList = dataSets.item(0).getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                Element element = (Element) node;
                String key = element.getNodeName();
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    String fieldName = field.getName();

                    if (element.getElementsByTagName(fieldName).getLength() == 0) {
                        continue;
                    }

                    String value = element.getElementsByTagName(fieldName).item(0).getTextContent();

                    if (List.class.equals(field.getType()) && value != null && !value.isEmpty()) {
                        field.set(obj, Arrays.asList(value.split(",\\s*")));
                    } else if (field.getType().equals(int.class)) {
                        field.set(obj, Integer.parseInt(value));
                    } else {
                        field.set(obj, value);
                    }
                }

                dataMap.put(key, obj);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load XML data from " + filePath, e);
        }

        return dataMap;
    }
}
