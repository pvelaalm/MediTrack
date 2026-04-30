package com.hospital.meditrack.controller;

import com.hospital.meditrack.model.dto.DashboardData;
import com.hospital.meditrack.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/supervisor")
public class SupervisorRealtimeController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "/dashboard/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<DashboardData>> streamDashboard() {
        AtomicLong secuencia = new AtomicLong(0);

        return Flux.interval(Duration.ofSeconds(5))
                .map(tick -> {
                    DashboardData data = new DashboardData();
                    data.setTimestamp(LocalDateTime.now());
                    data.setStats(dashboardService.obtenerEstadisticasGenerales());
                    data.setCargaPorTurno(dashboardService.obtenerCargaPorTurno());
                    data.setTareasPorPrioridad(dashboardService.obtenerTareasPorPrioridad());
                    data.setTopEnfermeros(dashboardService.obtenerTop5EnfermerosConMasTareas());

                    return ServerSentEvent.<DashboardData>builder()
                            .id(String.valueOf(secuencia.getAndIncrement()))
                            .event("dashboard-update")
                            .data(data)
                            .build();
                });
    }
}