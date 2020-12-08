package com.solucionexpress.microservicios.app.respuestas.controllers;

import com.solucionexpress.microservicios.app.respuestas.models.entity.Respuesta;
import com.solucionexpress.microservicios.app.respuestas.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RespuestaController {

    @Autowired
    private RespuestaService service;

    @PostMapping
    public ResponseEntity<?>crear(@RequestBody Iterable<Respuesta> respuestas){
        Iterable<Respuesta> respuestasDb = service.saveAll(respuestas);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
    }
    @GetMapping("/alumno/{alumnoId}/examen/{examenId}")
    public ResponseEntity<?> obtenerRespuestaPorAlumnoPorExamen(@PathVariable Long alumnoId,@PathVariable Long examenId){
        Iterable<Respuesta> respuestas = service.findRespuestaByAlumnoByExamen(alumnoId,examenId);
        return ResponseEntity.ok(respuestas);
    }
    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public ResponseEntity <?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId){
        Iterable<Long> examenesIds = service.findExamenesIdsConRespuestasByAlumno(alumnoId);
        return ResponseEntity.ok(examenesIds);
    }
}
