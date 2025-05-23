package pizzashop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {


    @Test
    void testMenuItemConstructorAndGetters() {
        // Arrange
        Integer expectedId = 1;
        String expectedName = "Pizza Margherita";
        Double expectedPrice = 25.50;

        // Act
        MenuItem menuItem = new MenuItem(expectedId, expectedName, expectedPrice);

        // Assert
        assertEquals(expectedId, menuItem.getId(), "ID-ul ar trebui să fie cel setat în constructor.");
        assertEquals(expectedName, menuItem.getName(), "Numele ar trebui să fie cel setat în constructor.");
        assertEquals(expectedPrice, menuItem.getPrice(), "Prețul ar trebui să fie cel setat în constructor.");
    }

    @Test
    void testMenuItemSetters() {
        // Arrange
        MenuItem menuItem = new MenuItem(1, "Pizza Diavola", 30.00);
        Integer newId = 2;
        String newName = "Pasta Carbonara";
        Double newPrice = 28.75;

        // Act
        menuItem.setId(newId);
        menuItem.setName(newName);
        menuItem.setPrice(newPrice);

        // Assert
        assertEquals(newId, menuItem.getId(), "ID-ul ar trebui să fie actualizat de setter.");
        assertEquals(newName, menuItem.getName(), "Numele ar trebui să fie actualizat de setter.");
        assertEquals(newPrice, menuItem.getPrice(), "Prețul ar trebui să fie actualizat de setter.");
    }

}