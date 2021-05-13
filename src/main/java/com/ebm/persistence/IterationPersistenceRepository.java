package com.ebm.persistence;


import com.ebm.domain.Iteration;
import com.ebm.domain.repository.IterationRepository;
import com.ebm.persistence.crud.IterationCrudRepository;
import com.ebm.persistence.crud.IterationProductCrudRepository;
import com.ebm.persistence.entity.EntityIteration;
import com.ebm.persistence.entity.EntityIterationProduct;
import com.ebm.persistence.entity.EntityIterationProductPK;
import com.ebm.persistence.mapper.IterationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IterationPersistenceRepository implements IterationRepository {
    @Autowired
    private IterationCrudRepository iterationCrudRepository;

    @Autowired
    private IterationProductCrudRepository iterationProductCrudRepository;

    @Autowired
    private IterationMapper mapper;

    IterationPersistenceRepository(IterationCrudRepository iterationCrudRepository, IterationMapper iterationMapper, IterationProductCrudRepository iterationProductCrudRepository) {
        this.iterationCrudRepository = iterationCrudRepository;
        this.mapper = iterationMapper;
        this.iterationProductCrudRepository = iterationProductCrudRepository;
    }

    @Override
    public List<Iteration> getAll() {
        List<EntityIteration> iterations = (List<EntityIteration>) iterationCrudRepository.findAll();
        return mapper.toIterations(iterations);
    }

    @Override
    public Optional<Iteration> getIterationById(int idIteration) {
        return iterationCrudRepository.findById(idIteration).map(mapper::toIteration);
    }

    @Override
    public Iteration getLastIteration(int idProduct) {
        return mapper.toIteration(iterationCrudRepository.getLastIteration(idProduct));
    }

    @Override
    public Iteration save(Iteration iterationDomain) {
        EntityIteration iteration = mapper.toIterationDomain(iterationDomain);
        Iteration entitySaved = mapper.toIteration(iterationCrudRepository.save(iteration));

        if (entitySaved != null) {
            EntityIterationProduct entityIterationProduct = new EntityIterationProduct();

            EntityIterationProductPK entityIterationProductPK = new EntityIterationProductPK();
            entityIterationProductPK.setIdIteration(entitySaved.getId());
            entityIterationProductPK.setIdProduct(entitySaved.getIdProduct());

            entityIterationProduct.setEntityId(entityIterationProductPK);
            iterationProductCrudRepository.save(entityIterationProduct);
        }

        return entitySaved;
    }

    @Override
    public Optional<Iteration> update(int idIteration, Iteration iteration) {
        return iterationCrudRepository.findById(idIteration).map(iterationDB -> {

            if (iteration.getName() != null && !iteration.getName().isEmpty()
                    && !iteration.getName().equals(iterationDB.getEntityName())) {
                iterationDB.setEntityName(iteration.getName());
            }

            if (iteration.getGoal() != null &&!iteration.getGoal().isEmpty()
                    && !iteration.getGoal().equals(iterationDB.getEntityGoal())) {
                iterationDB.setEntityGoal(iteration.getGoal());
            }
            if (iteration.getStartDate() != null && iteration.getStartDate() != iterationDB.getEntityStartDate()) {
                iterationDB.setEntityStartDate(iteration.getStartDate());
            }

            if (iteration.getEndDate() != null &&iteration.getEndDate() != iterationDB.getEntityEndDate()) {
                iterationDB.setEntityEndDate(iteration.getEndDate());
            }

            if (iteration.getState() != null &&!iteration.getState().isEmpty()
                    && !iteration.getState().equals(iterationDB.getEntityState())) {
                iterationDB.setEntityState(iteration.getState());
            }

            return mapper.toIteration(iterationCrudRepository.save(iterationDB));
        });
    }

    @Override
    public boolean delete(int iterationId) {
        try {
            iterationCrudRepository.deleteById(iterationId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
