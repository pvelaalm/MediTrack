-- Insertar turnos iniciales
INSERT INTO turno (nombre, hora_inicio, hora_fin)
VALUES ('Mañana', '07:00:00', '15:00:00')
    ON CONFLICT (nombre) DO NOTHING;

INSERT INTO turno (nombre, hora_inicio, hora_fin)
VALUES ('Tarde', '15:00:00', '23:00:00')
    ON CONFLICT (nombre) DO NOTHING;

INSERT INTO turno (nombre, hora_inicio, hora_fin)
VALUES ('Noche', '23:00:00', '07:00:00')
    ON CONFLICT (nombre) DO NOTHING;