package com.ebm.persistence;

import com.ebm.domain.Iteration;
import com.ebm.persistence.crud.IterationCrudRepository;
import com.ebm.persistence.crud.IterationProductCrudRepository;
import com.ebm.persistence.entity.EntityIteration;
import com.ebm.persistence.entity.EntityIterationProduct;
import com.ebm.persistence.entity.EntityIterationProductPK;
import com.ebm.persistence.mapper.IterationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class IterationPersistenceRepositoryTest {
    IterationPersistenceRepository tester;
    IterationCrudRepository iterationCrudRepository;
    IterationMapper mapper;
    IterationProductCrudRepository iterationProductCrudRepository;


    @BeforeEach
    void initEach() {
        iterationCrudRepository = Mockito.mock(IterationCrudRepository.class);
        mapper = Mockito.mock(IterationMapper.class);
        iterationProductCrudRepository = Mockito.mock(IterationProductCrudRepository.class);
        tester = new IterationPersistenceRepository(iterationCrudRepository, mapper, iterationProductCrudRepository);
    }

    @Test
    void TestGetAllWithOutData() {
        assertEquals(new ArrayList<>(), tester.getAll(), "getAll must be []");
    }

    @Test
    void TestGetAllWithData() {
        List<Iteration> iterationsDomain = new ArrayList<>();
        Iteration iterationDomain = new Iteration();
        iterationsDomain.add(iterationDomain);

        List<EntityIteration> iterations = new ArrayList<>();
        EntityIteration iteration = new EntityIteration();
        iterations.add(iteration);

        when(iterationCrudRepository.findAll()).thenReturn(iterations);
        when(mapper.toIterations(iterations)).thenReturn(iterationsDomain);

        List<Iteration> iterationsResult = tester.getAll();

        assertEquals(1, iterationsResult.size(), "getAll must have an iteration");
        assertEquals(iterationDomain, iterationsResult.toArray()[0], "getAll must have an iteration equal to object defined");
    }

    @Test
    void TestGetByIdTeamWithOutData() {
        assertEquals(Optional.empty(), tester.getIterationById(0), "getByIdTeam must be Optional.empty()");
    }

    @Test
    void TestGetByIdTeamWithData() {

        Optional<Iteration> iterationDomain = Optional.of(new Iteration());
        Optional<EntityIteration> iterations = Optional.of(new EntityIteration());
        when(iterationCrudRepository.findById(1)).thenReturn(iterations);
        when(mapper.toIteration(iterations.get())).thenReturn(iterationDomain.get());

        Optional<Iteration> iterationsResult = tester.getIterationById(1);

        assertEquals(iterationDomain, iterationsResult, "getIterationById must be Optional.of(new EntityIteration())");

    }


    @Test
    void TestSaveNull() {
        assertNull(tester.save(new Iteration()), "save must be null");
    }

    @Test
    void TestSaveWithData() {
        EntityIteration iteration = new EntityIteration();
        Iteration iterationDomain = new Iteration();
        when(mapper.toIterationDomain(iterationDomain)).thenReturn(iteration);
        when(mapper.toIteration(iteration)).thenReturn(iterationDomain);

        when(iterationCrudRepository.save(iteration)).thenReturn(iteration);

        EntityIterationProduct entityIterationProduct = new EntityIterationProduct();

        EntityIterationProductPK entityIterationProductPK = new EntityIterationProductPK();
        entityIterationProductPK.setIdIteration(2);
        entityIterationProductPK.setIdProduct(2);

        entityIterationProduct.setEntityId(entityIterationProductPK);
        when(iterationProductCrudRepository.save(entityIterationProduct)).thenReturn(entityIterationProduct);


        assertEquals(iterationDomain, tester.save(iterationDomain), "save must be new instance Iteration");
    }

    @Test
    void TestDelete() {
        boolean result = tester.delete(1);
        assertTrue(result, "delete must be true");
    }

    @Test
    void TestDeleteError() {
        doThrow(NullPointerException.class)
                .when(iterationCrudRepository)
                .deleteById(1);
        boolean result = tester.delete(1);
        assertFalse(result, "delete must be false");
    }

    @Test
    void TestUpdateNull() {
        assertEquals(Optional.empty(), tester.update(1, new Iteration()), "update must be Optional.empty()");
    }

    @Test
    void TestUpdateWithData() {
        Optional<EntityIteration> iteration = Optional.of(new EntityIteration());
        Optional<Iteration> iterationDomain = Optional.of(new Iteration());
        iterationDomain.get().setName("My iteration");
        iterationDomain.get().setGoal("My Goal");
        iterationDomain.get().setStartDate(LocalDateTime.now());
        iterationDomain.get().setEndDate(LocalDateTime.now());
        iterationDomain.get().setState("My State");

        when(iterationCrudRepository.findById(1)).thenReturn(iteration);
        when(iterationCrudRepository.save(iteration.get())).thenReturn(iteration.get());
        when(mapper.toIteration(iteration.get())).thenReturn(iterationDomain.get());


        assertEquals(iterationDomain, tester.update(1, iterationDomain.get()), "update must be new instance Iteration");
    }

    @Test
    void TestUpdateWithDiffData() {
        LocalDateTime dateTime = LocalDateTime.now();
        EntityIteration eIteration =new EntityIteration();
        eIteration.setEntityName("My iteration");
        eIteration.setEntityGoal("My Goal");
        eIteration.setEntityStartDate(dateTime);
        eIteration.setEntityEndDate(dateTime);
        eIteration.setEntityState("My State");

        Optional<EntityIteration> iteration = Optional.of(eIteration);
        Optional<Iteration> iterationDomain = Optional.of(new Iteration());
        iterationDomain.get().setName("My iteration");
        iterationDomain.get().setGoal("My Goal");
        iterationDomain.get().setStartDate(dateTime);
        iterationDomain.get().setEndDate(dateTime);
        iterationDomain.get().setState("My State");

        when(iterationCrudRepository.findById(1)).thenReturn(iteration);
        when(iterationCrudRepository.save(iteration.get())).thenReturn(iteration.get());
        when(mapper.toIteration(iteration.get())).thenReturn(iterationDomain.get());


        assertEquals(iterationDomain, tester.update(1, iterationDomain.get()), "update must be new instance Iteration");
    }

    @Test
    void TestUpdateWithNullData() {
        Optional<EntityIteration> iteration = Optional.of(new EntityIteration());
        Optional<Iteration> iterationDomain = Optional.of(new Iteration());

        when(iterationCrudRepository.findById(1)).thenReturn(iteration);
        when(iterationCrudRepository.save(iteration.get())).thenReturn(iteration.get());
        when(mapper.toIteration(iteration.get())).thenReturn(iterationDomain.get());
        when(mapper.toIterationDomain(iterationDomain.get())).thenReturn(iteration.get());

        assertEquals(iterationDomain, tester.update(1, iterationDomain.get()), "update must be new instance Iteration");
    }

    @Test
    void TestUpdateWithEmptyData() {
        Optional<EntityIteration> iteration = Optional.of(new EntityIteration());
        Optional<Iteration> iterationDomain = Optional.of(new Iteration());

        iterationDomain.get().setName("");
        iterationDomain.get().setGoal("");
        iterationDomain.get().setState("");
        iterationDomain.get().setStartDate(null);
        iterationDomain.get().setEndDate(null);

        when(iterationCrudRepository.findById(1)).thenReturn(iteration);
        when(iterationCrudRepository.save(iteration.get())).thenReturn(iteration.get());
        when(mapper.toIteration(iteration.get())).thenReturn(iterationDomain.get());
        when(mapper.toIterationDomain(iterationDomain.get())).thenReturn(iteration.get());

        assertEquals(iterationDomain, tester.update(1, iterationDomain.get()), "update must be new instance Iteration");
    }


    @Test
    void TestGetIterationById() {
        assertNull(tester.getLastIteration(1), "getLastIteration must be null");
    }


    @Test
    void TestGetIterationByIdWithData() {
        EntityIteration iteration = new EntityIteration();
        Iteration iterationDomain = new Iteration();
        when(iterationCrudRepository.getLastIteration(1)).thenReturn(iteration);
        when(mapper.toIteration(iteration)).thenReturn(iterationDomain);

        Iteration iterationResult = tester.getLastIteration(1);

        assertEquals(iterationDomain, iterationResult, "getLastIteration must be new Iteration()");
    }

}
