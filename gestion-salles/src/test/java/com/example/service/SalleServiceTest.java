package com.example.service;

import com.example.model.Salle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class SalleServiceTest {

    private EntityManagerFactory emf;
    private SalleService service;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("gestion-salles");
        service = new SalleService(emf);
    }

    @After
    public void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testCrudOperations() {

        // CREATE
        Salle salle = new Salle("Test Room", 20);
        salle.setDescription("Test description");
        salle.setEtage(1);
        salle.setDisponible(true);

        Salle savedSalle = service.save(salle);
        assertNotNull(savedSalle);
        assertNotNull(savedSalle.getId());

        // READ
        Optional<Salle> foundSalle = service.findById(savedSalle.getId());
        assertTrue(foundSalle.isPresent());
        assertEquals("Test Room", foundSalle.get().getNom());

        // UPDATE
        Salle toUpdate = foundSalle.get();
        toUpdate.setCapacite(30);
        service.update(toUpdate);

        Optional<Salle> updatedSalle = service.findById(savedSalle.getId());
        assertTrue(updatedSalle.isPresent());
        assertEquals(Integer.valueOf(30), updatedSalle.get().getCapacite());

        // DELETE
        service.delete(updatedSalle.get());

        Optional<Salle> deletedSalle = service.findById(savedSalle.getId());
        assertFalse(deletedSalle.isPresent());
    }

    @Test
    public void testFindByDisponible() {

        Salle s1 = new Salle("Available Room", 20);
        s1.setDisponible(true);

        Salle s2 = new Salle("Unavailable Room", 30);
        s2.setDisponible(false);

        s1 = service.save(s1);
        s2 = service.save(s2);

        List<Salle> availableRooms = service.findByDisponible(true);

        assertTrue(
                availableRooms.stream()
                        .anyMatch(s -> s.getNom().equals("Available Room"))
        );

        assertFalse(
                availableRooms.stream()
                        .anyMatch(s -> s.getNom().equals("Unavailable Room"))
        );

        // Clean up
        service.delete(s1);
        service.delete(s2);
    }

    @Test
    public void testFindByCapaciteMinimum() {

        Salle s1 = service.save(new Salle("Small Room", 10));
        Salle s2 = service.save(new Salle("Medium Room", 50));
        Salle s3 = service.save(new Salle("Large Room", 100));

        List<Salle> roomsMin50 = service.findByCapaciteMinimum(50);

        assertEquals(2,
                roomsMin50.stream()
                        .filter(s -> s.getNom().equals("Medium Room")
                                || s.getNom().equals("Large Room"))
                        .count()
        );

        List<Salle> roomsMin80 = service.findByCapaciteMinimum(80);

        assertEquals(1,
                roomsMin80.stream()
                        .filter(s -> s.getNom().equals("Large Room"))
                        .count()
        );

        // Clean up
        service.delete(s1);
        service.delete(s2);
        service.delete(s3);
    }
}
