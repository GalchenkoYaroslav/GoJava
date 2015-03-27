package ua.goit.test;

import org.junit.Before;
import org.junit.Test;
import ua.goit.graphElements.Group;
import ua.goit.graphElements.GroupImpl;
import ua.goit.graphElements.Point;
import ua.goit.graphElements.PointImpl;
import ua.goit.serialization.ConcreteFactory;
import ua.goit.serialization.SerializationType;
import ua.goit.shapes.Circle;
import ua.goit.shapes.Triangle;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class XMLSerializatorTest {
    private Point point1 = new PointImpl(1,2);
    private Point point2 = new PointImpl(2,1);
    private Triangle triangle1 = new Triangle("triangle1");
    private Triangle triangle2 = new Triangle("triangle2");
    private Triangle triangle3 = new Triangle("triangle3");
    private Circle circle1 = new Circle("circle1");
    private Circle circle2 = new Circle("circle2");
    private Group group1 = new GroupImpl("group1");
    private Group group2 = new GroupImpl("group2");
    private Group group3 = new GroupImpl("group3");
    private ConcreteFactory factory = new ConcreteFactory();
    private final Map<String, String> initForMethod = new HashMap<String, String>();


    @Before
    public void  init() {
        triangle1.addPoint(point1);
        triangle2.addPoint(point2);
        group1.setElement(triangle1);
        group1.setElement(triangle2);
        group2.setGroup(group1);
        group2.setElement(circle1);
        group3.setElement(triangle1);
        group3.setGroup(group2);
        group3.setElement(circle2);

        initForMethod.put("testSerializeOneElement", factory.getSerializationFor(SerializationType.XML).serialize(triangle1).toString());
        initForMethod.put("testSerializeOneGroup", factory.getSerializationFor(SerializationType.XML).serialize(group1).toString());
        initForMethod.put("testSerializeInnerGroup", factory.getSerializationFor(SerializationType.XML).serialize(group2).toString());
        initForMethod.put("testSerializeFewInnerGroup", factory.getSerializationFor(SerializationType.XML).serialize(group3).toString());

    }


    @Test
    public void testSerializeOneElement() throws Exception {
        String exceptedResult = "<Triangle>triangle1<Points><Point>(1, 2)</Point></Points></Triangle>";
        String actualResult = initForMethod.get("testSerializeOneElement");
        assertEquals(exceptedResult, actualResult);
    }

    @Test
    public void testSerializeOneGroup() throws Exception {
        String exceptedResult = "<Group>group1<Triangle>triangle1<Points><Point>(1, 2)</Point></Points></Triangle>" +
                "<Triangle>triangle2<Points><Point>(2, 1)</Point></Points></Triangle></Group>";
        String actualResult = initForMethod.get("testSerializeOneGroup");
        assertEquals(exceptedResult, actualResult);
    }

    @Test
    public void testSerializeInnerGroup() throws Exception {
        String exceptedResult = "<Group>group2<Group>group1<Triangle>triangle1<Points>" +
                "<Point>(1, 2)</Point></Points></Triangle><Triangle>triangle2<Points><Point>(2, 1)</Point></Points>" +
                "</Triangle></Group><Circle>circle1<Points></Points></Circle></Group>";
        String actualResult = initForMethod.get("testSerializeInnerGroup");
        assertEquals(exceptedResult, actualResult);
    }

    @Test
    public void testSerializeFewInnerGroup() throws Exception {
        String exceptedResult = "<Group>group3<Group>group2<Group>group1<Triangle>triangle1<Points><Point>(1, 2)</Point></Points>" +
                "</Triangle><Triangle>triangle2<Points><Point>(2, 1)</Point></Points></Triangle></Group>" +
                "<Circle>circle1<Points></Points></Circle></Group><Triangle>triangle1<Points><Point>(1, 2)</Point></Points></Triangle>" +
                "<Circle>circle2<Points></Points></Circle></Group>";
        String actualResult = initForMethod.get("testSerializeFewInnerGroup");
        assertEquals(exceptedResult, actualResult);
    }


}