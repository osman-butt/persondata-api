package dk.persondata.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void givenMiddleName_whenGetFullName_thenReturnFirstMiddleAndLast() {
        // Arrange
        Person person = new Person("Harry", "James", "Potter");

        // Act
        var fullName = person.getFullName();

        // Assert
        assertEquals("Harry James Potter", fullName);
    }

    @Test
    void setFullNameWithMiddleName() {
        // Arrange
        Person person = new Person("First", "Middle", "Last");
        // Act
        person.setFullName("Harry James Potter");
        // Assert
        assertEquals("Harry", person.getFirstName());
        assertEquals("James", person.getMiddleName());
        assertEquals("Potter", person.getLastName());
    }

    @Test
    void setFullNameWithoutMiddleName() {
        // Arrange
        Person person = new Person("First", "Middle", "Last");
        // Act
        person.setFullName("Cho Chang");
        // Assert
        assertEquals("Cho", person.getFirstName());
        assertNull(person.getMiddleName());
        assertEquals("Chang", person.getLastName());
    }

    @Test
    void setFullNameWithoutLastName() {
        // Arrange
        Person person = new Person("First", "Middle", "Last");
        // Act
        person.setFullName("Harry");
        // Assert
        assertEquals("Harry", person.getFirstName());
        assertNull(person.getMiddleName());
        assertNull(person.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        // Arrange
        Person person = new Person("First", "Middle", "Last");
        // Act
        person.setFullName("Harry James Sirius Potter");
        // Assert
        assertEquals("Harry", person.getFirstName());
        assertEquals("James Sirius", person.getMiddleName());
        assertEquals("Potter", person.getLastName());
    }

    @Test
    void setFullNameWithEmptyString() {
        // Arrange
        Person person = new Person("First", "Middle", "Last");

        // Act
        person.setFullName("");

        // Act & Assert
        assertEquals("First", person.getFirstName());
        assertEquals("Middle", person.getMiddleName());
        assertEquals("Last", person.getLastName());
    }

    @Test
    void setFullNameWithNull() {
        // Arrange
        Person person = new Person("First", "Middle", "Last");

        // Act
        person.setFullName(null);

        // Assert
        assertEquals("First", person.getFirstName());
        assertEquals("Middle", person.getMiddleName());
        assertEquals("Last", person.getLastName());
    }

}