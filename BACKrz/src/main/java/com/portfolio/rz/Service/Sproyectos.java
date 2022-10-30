
package com.portfolio.rz.Service;

import com.portfolio.rz.Entity.Proyectos;
import com.portfolio.rz.Repository.RProyectos;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
@Transactional
public class Sproyectos {
    @Autowired
    RProyectos rProyectos;

    public List<Proyectos> list() {
        return rProyectos.findAll();
    }

    public Optional<Proyectos> getOne(int id) {
        return rProyectos.findById(id);
    }

    public Optional<Proyectos> getByNombreE(String nombreE) {
        return rProyectos.findByNombreE(nombreE);
    }

    public void save(Proyectos proyectos) {
        rProyectos.save(proyectos);
    }

    public void delete(int id) {
        rProyectos.deleteById(id);

    }

    public boolean existsById(int id) {
        return rProyectos.existsById(id);
    }

    public boolean existsByNombreE(String nombreE) {
        return rProyectos.existsByNombreE(nombreE);
    }
}