package com.shashi.jaxb;

import com.shashi.jaxb.Constants.EmployeeConstants;
import com.shashi.files.FileUtility.Files;
import com.shashi.jaxb.bean.Employee;
import com.shashi.jaxb.bean.Employees;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shashi on 21/12/15.
 */
public class JAXBUtilTest {

    private static JAXBUtil<Employee> jaxbUtil;

    @BeforeClass
    public static void setUpForJAXB() {
        jaxbUtil = new JAXBUtil<>(Employee.class);
    }

    @Test
    public void marshallSingleEmployee() throws IOException, JAXBException {
        jaxbUtil.marshallJAXBObjectToXML(Files.JAXB_FILE.getPath(), EmployeeConstants.employee1);
    }

    @Test
    public void umMarshallSingleEmployee() throws IOException, ClassNotFoundException, JAXBException {
        jaxbUtil.marshallJAXBObjectToXML(Files.JAXB_FILE.getPath(), EmployeeConstants.employee1);

        Employees employees = jaxbUtil.unmarshallXMLToJAXBObject(Files.JAXB_FILE.getPath());

        assertTrue(employees.size() == 1);
        assertTrue(employees.get(0).compareTo(EmployeeConstants.employee1) == 0);
    }

    @Test
    public void marshallMultipleEmployee() throws IOException, JAXBException {
        jaxbUtil.marshallJAXBObjectToXML(Files.JAXB_FILE.getPath(), EmployeeConstants.employee1, EmployeeConstants.employee2);
    }

    @Test
    public void umMarshallMultipleEmployee() throws IOException, ClassNotFoundException, JAXBException {
        jaxbUtil.marshallJAXBObjectToXML(Files.JAXB_FILE.getPath(), EmployeeConstants.employee1, EmployeeConstants.employee2);

        Employees employees = jaxbUtil.unmarshallXMLToJAXBObject(Files.JAXB_FILE.getPath());

        assertTrue(employees.size() == 2);
        assertTrue(employees.get(0).compareTo(EmployeeConstants.employee1) == 0);
        assertTrue(employees.get(1).compareTo(EmployeeConstants.employee2) == 0);

        assertFalse(employees.get(0).compareTo(EmployeeConstants.employee2) == 0);
    }
}
