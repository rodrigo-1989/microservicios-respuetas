package com.solucionexpress.microservicios.app.respuestas.services;

import com.solucionexpress.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId,Long examenId);
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
