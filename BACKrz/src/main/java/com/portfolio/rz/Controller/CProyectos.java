
package com.portfolio.rz.Controller;

import com.portfolio.rz.Dto.dtoProyectos;
import com.portfolio.rz.Entity.Proyectos;
import com.portfolio.rz.Security.Controller.Mensaje;
import com.portfolio.rz.Service.Sproyectos;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/proyectos")
@CrossOrigin(origins = "https://porfoliorz.web.app")


public class CProyectos {

    @Autowired
    Sproyectos sProyectos;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Proyectos>> list(){
        List<Proyectos> list = sProyectos.list();
        return new ResponseEntity(list, HttpStatus.OK);
        
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Proyectos> getById(@PathVariable("id")int id){
        if(!sProyectos.existsById(id)){
         return new ResponseEntity(new Mensaje("ID inexistente"), HttpStatus.NOT_FOUND);
        }
        Proyectos proyectos = sProyectos.getOne(id).get();
         return new ResponseEntity(proyectos, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if (!sProyectos.existsById(id)){
            return new ResponseEntity(new Mensaje("Ip inexistente"), HttpStatus.NOT_FOUND);
        }
        sProyectos.delete(id);
         return new ResponseEntity(new Mensaje("Proyecto eliminado"), HttpStatus.OK);
        
    }
    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody dtoProyectos dtoproyectos){
        if(StringUtils.isBlank(dtoproyectos.getNombreE())){
            return new ResponseEntity(new Mensaje("Nombre obligatorio"),HttpStatus.BAD_REQUEST);
        }
        if(sProyectos.existsByNombreE(dtoproyectos.getNombreE())){
             return new ResponseEntity(new Mensaje("Nombre existente"),HttpStatus.BAD_REQUEST);
        }
        Proyectos proyectos = new Proyectos(dtoproyectos.getNombreE(), dtoproyectos.getDescripcionE());
        sProyectos.save(proyectos);
       return new ResponseEntity(new Mensaje("Proyecto indexado"),HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update (@PathVariable("id")int id, @RequestBody dtoProyectos dtoproyectos){
    if(!sProyectos.existsById(id)){
        return new ResponseEntity(new Mensaje("Id inexistente"),HttpStatus.NOT_FOUND);
    }
    if(sProyectos.existsByNombreE(dtoproyectos.getNombreE()) && sProyectos.getByNombreE(dtoproyectos.getNombreE()).get().getId() !=id){
        return new ResponseEntity(new Mensaje("Nombre existente"),HttpStatus.BAD_REQUEST);
    }
    if(StringUtils.isBlank(dtoproyectos.getNombreE())){
        return new ResponseEntity(new Mensaje("No puede estar vacío"),HttpStatus.BAD_REQUEST);
    }
    Proyectos proyectos = sProyectos.getOne(id).get();
    
    proyectos.setNombreE(dtoproyectos.getNombreE());
    proyectos.setDescripcionE(dtoproyectos.getDescripcionE());
    
    sProyectos.save(proyectos);
     return new ResponseEntity(new Mensaje("Proyectos actualizados actualizado"),HttpStatus.OK);
    
}
}