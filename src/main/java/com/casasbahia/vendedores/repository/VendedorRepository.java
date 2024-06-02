package com.casasbahia.vendedores.repository;

import com.casasbahia.vendedores.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Vendedor getReferenceByMatricula(String matricula);

    void deleteByMatricula(String matricula);

}
