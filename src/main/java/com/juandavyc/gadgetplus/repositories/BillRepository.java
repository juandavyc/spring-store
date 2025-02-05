package com.juandavyc.gadgetplus.repositories;

import com.juandavyc.gadgetplus.entities.BillEntity;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<BillEntity, String> {
}
