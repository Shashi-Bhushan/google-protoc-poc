package com.shashi.serialize.bean;

import com.shashi.files.FileUtility;
import com.shashi.serialize.SerializationUtil;
import com.shashi.serialize.list.EmployeesList;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Shashi Bhushan
 *         Created on 31/12/15.
 *         For Google-Protoc
 */
public class EmployeeTest {

    private static SerializationUtil<Employee> serializationUtil;

    @BeforeClass
    public static void setup() throws IOException {
        serializationUtil = new SerializationUtil<>(Employee.class);
    }

    @Test @Ignore
    public void setupForInitialSerialization_OnlyToSerializeVersionOne()
            throws IOException {
        Path filePath = FileUtility.Files.SERIALIZE_FILE.getPath();
        Files.deleteIfExists(filePath);

        serializationUtil.serialize(filePath, EmployeesList.employeeOne);
    }

    /**
     * Have to build equals in a way that it should return false for the first test
     * but return true in second case
     */
    @Test
    public void equateSameEmployeeObjects_ShouldReturnTrue(){
        Employee employee = Employee.newBuilder(1, "Shashi")
                .setGender(Employee.Properties.Gender.MALE).build();

        Employee employee2 = Employee.newBuilder(1, "Shashi")
                .build();

        assertFalse(employee.equals(employee2));
        assertTrue(employee2.equals(employee));
    }

    /**
     * TODO: Make this test case pass
     */
    @Test
    public void equateSameEmployeeObjects_WithDifferentParams_ShouldReturnFalse(){
        Employee employee = Employee.newBuilder(1, "Shashi")
                .setGender(Employee.Properties.Gender.MALE)
                .setRole(Employee.Properties.Role.CONSULTANT)
                .build();

        Employee employee2 = Employee.newBuilder(1, "Shashi")
                .setGender(Employee.Properties.Gender.FEMALE)
                .setRole(Employee.Properties.Role.CONSULTANT)
                .build();

        assertFalse(employee.equals(employee2));
    }

    @Test
    public void setTest_addEmployeesInSet_ShouldAddProperly(){
        Employee employee = new Employee.Builder(1, "Shashi")
                .setGender(Employee.Properties.Gender.MALE).build();

        Employee employee2 = new Employee.Builder(1, "Shashi")
                .setGender(Employee.Properties.Gender.MALE).build();

        Set<Employee> employees = new HashSet<Employee>();
        employees.add(employee);

        assertFalse(employees.add(employee));
        assertFalse(employees.add(employee2));
    }

    @Test
    public void mapTest_addEmployeesInMap_ShouldRetrieveProperly(){
        Employee employee = new Employee.Builder(1, "Shashi")
                .setGender(Employee.Properties.Gender.MALE).build();

        Employee employee2 = new Employee.Builder(1, "Bhushan")
                .setGender(Employee.Properties.Gender.MALE).build();

        Map<String,Employee> employees = new HashMap<>();
        employees.put("emp1", employee);
        employees.put("emp2", employee2);

        assertTrue(employees.get("emp2") != null);
    }

    @Test
    public void deserialize_VersionOneObject_ToAnotherVersion() throws ClassNotFoundException {
        List<Employee> employees = null;
        try {
            employees = serializationUtil.deSerialize(FileUtility.Files.SERIALIZE_FILE.getPath());
            Employee employee = employees.get(0);
            assertTrue(employee.getClass() != null);
        } catch (IOException cause) {
            cause.getMessage();
        }
    }

    @Test
    public void addToMap_ShouldBeAbleToRetrieveProperly(){
        Map<com.shashi.serialize.bean.Employee, String> map = new HashMap<>();

        com.shashi.serialize.bean.Employee employee = com.shashi.serialize.bean.Employee.newBuilder(1, "Shashi")
                .setGender(com.shashi.serialize.bean.Employee.Properties.Gender.MALE).build();

        com.shashi.serialize.bean.Employee secondEmployee = com.shashi.serialize.bean.Employee.newBuilder(1, "Shashi")
                .setGender(com.shashi.serialize.bean.Employee.Properties.Gender.MALE).build();

        map.put(employee, "shashi");

        assertTrue(map.get(secondEmployee) != null);
    }
}
