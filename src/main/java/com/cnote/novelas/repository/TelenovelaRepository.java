package com.cnote.novelas.repository;

import com.cnote.novelas.domain.Telenovela;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Telenovela entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelenovelaRepository extends JpaRepository<Telenovela, Long> {

    @Query(value = "select distinct telenovela from Telenovela telenovela left join fetch telenovela.actors",
        countQuery = "select count(distinct telenovela) from Telenovela telenovela")
    Page<Telenovela> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct telenovela from Telenovela telenovela left join fetch telenovela.actors")
    List<Telenovela> findAllWithEagerRelationships();

    @Query("select telenovela from Telenovela telenovela left join fetch telenovela.actors where telenovela.id =:id")
    Optional<Telenovela> findOneWithEagerRelationships(@Param("id") Long id);

}
