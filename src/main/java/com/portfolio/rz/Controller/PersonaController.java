package com.portfolio.rz.Controller;

import com.portfolio.rz.Dto.dtoPersona;
import com.portfolio.rz.Entity.Persona;
import com.portfolio.rz.Security.Controller.Mensaje;
import com.portfolio.rz.Service.ImpPersonaService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personas")
@CrossOrigin(origins = "https://porfoliorz.web.app")
public class PersonaController {

    @Autowired
    ImpPersonaService personaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list() {
        List<Persona> list = personaService.list();
        return new ResponseEntity(list, HttpStatus.OK);

    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id) {
        if (!personaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("ID inexistente"), HttpStatus.NOT_FOUND);
        }
        Persona persona = personaService.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }

    /*@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if (!personaService.existsById(id)){
            return new ResponseEntity(new Mensaje("Ip inexistente"), HttpStatus.NOT_FOUND);
        }
        personaService.delete(id);
         return new ResponseEntity(new Mensaje("Educacion eliminada"), HttpStatus.OK);
        
    }*/
 /*@PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody dtoEducacion dtoeducacion){
        if(StringUtils.isBlank(dtoeducacion.getNombreE())){
            return new ResponseEntity(new Mensaje("Nombre obligatorio"),HttpStatus.BAD_REQUEST);
        }
        if(personaService.existsByNombreE(dtoeducacion.getNombreE())){
             return new ResponseEntity(new Mensaje("Nombre existente"),HttpStatus.BAD_REQUEST);
        }
        Educacion educacion = new Educacion(dtoeducacion.getNombreE(), dtoeducacion.getDescripcionE());
        personaService.save(educacion);
       return new ResponseEntity(new Mensaje("Educacion indexada"),HttpStatus.OK);
    }*/
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody dtoPersona dtopersona) {
        if (!personaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("Id inexistente"), HttpStatus.NOT_FOUND);
        }
        if (personaService.existsByNombre(dtopersona.getNombre()) && personaService.getByNombre(dtopersona.getNombre()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("Nombre existente"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(dtopersona.getNombre())) {
            return new ResponseEntity(new Mensaje("No puede estar vacío"), HttpStatus.BAD_REQUEST);
        }
        Persona persona = personaService.getOne(id).get();

        persona.setNombre(dtopersona.getNombre());
        persona.setApellido(dtopersona.getApellido());
        persona.setDescripcion(dtopersona.getDescripcion());
        persona.setProfesion(dtopersona.getProfesion());
        persona.setImg(dtopersona.getImg());

        personaService.save(persona);
        return new ResponseEntity(new Mensaje("Persona actualizada"), HttpStatus.OK);

    }
}
