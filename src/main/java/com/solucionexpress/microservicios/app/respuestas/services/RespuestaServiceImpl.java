package com.solucionexpress.microservicios.app.respuestas.services;

import com.solucionexpress.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.solucionexpress.microservicios.app.respuestas.models.entity.Respuesta;
import com.solucionexpress.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.solucionexpress.microservicios.commons.examenes.models.entity.Examen;
import com.solucionexpress.microservicios.commons.examenes.models.entity.Pregunta;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RespuestaServiceImpl implements RespuestaService{

    @Autowired
    private RespuestaRepository repository;
    @Autowired
    private ExamenFeignClient examenClient;

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return repository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
    	Examen examen = examenClient.obtenerExamenPorId(examenId);
    	List<Pregunta> preguntas = examen.getPreguntas();
    	
    	List<Long> preguntasIds = preguntas.stream().map( p-> p.getId()).collect(Collectors.toList());
    	List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntasIds(alumnoId, preguntasIds);
    	respuestas = respuestas.stream().map( r ->{
    		preguntas.forEach( p->{
    			if( p.getId() == r.getPreguntaId()) {
    				r.setPregunta(p);
    			}
    		});
    		return r;
    	}).collect(Collectors.toList());
        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
    	List<Respuesta> respuestaAlumno =(List<Respuesta>) repository.findByAlumnoId(alumnoId);
    	List<Long> examenIds = Collections.emptyList();
    	
    	if(respuestaAlumno.size() > 0) {
    		List<Long> preguntaIds = respuestaAlumno.stream().map( r -> r.getPreguntaId()).collect(Collectors.toList());
    		examenIds = examenClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
    	}
        return examenIds;
    }

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumniId) {
		return repository.findByAlumnoId(alumniId);
	}
}
