# MANUAL DE USUARIO — MEDITRACK
## Sistema de Gestión de Tareas Clínicas

**Versión 1.0 — Abril 2026**

---

## ÍNDICE

1. [Introducción](#1-introducción)
   - 1.1. [¿Qué es MediTrack?](#11-qué-es-meditrack)
   - 1.2. [¿Para quién es MediTrack?](#12-para-quién-es-meditrack)
   - 1.3. [¿Qué problemas resuelve?](#13-qué-problemas-resuelve)
   - 1.4. [Cómo usar este manual](#14-cómo-usar-este-manual)

2. [Requisitos Técnicos](#2-requisitos-técnicos)
   - 2.1. [Navegadores Compatibles](#21-navegadores-compatibles)
   - 2.2. [Resoluciones Recomendadas](#22-resoluciones-recomendadas)
   - 2.3. [Conexión a Internet](#23-conexión-a-internet)

3. [Acceso al Sistema](#3-acceso-al-sistema)
   - 3.1. [URL de Acceso](#31-url-de-acceso)
   - 3.2. [Pantalla de Login](#32-pantalla-de-login)
   - 3.3. [Credenciales de Prueba](#33-credenciales-de-prueba)
   - 3.4. [Primer Inicio de Sesión](#34-primer-inicio-de-sesión)
   - 3.5. [Cerrar Sesión](#35-cerrar-sesión)

4. [Gestión de Usuarios](#4-gestión-de-usuarios)
   - 4.1. [¿Quién Puede Crear Usuarios?](#41-quién-puede-crear-usuarios)
   - 4.2. [Crear un Nuevo Usuario](#42-crear-un-nuevo-usuario)
   - 4.3. [Editar un Usuario Existente](#43-editar-un-usuario-existente)
   - 4.4. [Eliminar un Usuario](#44-eliminar-un-usuario)

5. [Interfaz Común a Todos los Roles](#5-interfaz-común-a-todos-los-roles)
   - 5.1. [Barra de Navegación](#51-barra-de-navegación)
   - 5.2. [Indicadores Visuales de Estado y Prioridad](#52-indicadores-visuales-de-estado-y-prioridad)
   - 5.3. [Comportamiento Responsive](#53-comportamiento-responsive)

6. [Rol Enfermería](#6-rol-enfermería)
   - 6.1. [Dashboard de Enfermería](#61-dashboard-de-enfermería)
   - 6.2. [Mis Tareas](#62-mis-tareas)
   - 6.3. [Cómo Actualizar el Estado de una Tarea](#63-cómo-actualizar-el-estado-de-una-tarea)
   - 6.4. [Añadir Observaciones](#64-añadir-observaciones)
   - 6.5. [Limitaciones del Rol Enfermería](#65-limitaciones-del-rol-enfermería)

7. [Rol Medicina](#7-rol-medicina)
   - 7.1. [Dashboard de Medicina](#71-dashboard-de-medicina)
   - 7.2. [Crear una Tarea Clínica](#72-crear-una-tarea-clínica)
   - 7.3. [Gestión de Pacientes](#73-gestión-de-pacientes)
   - 7.4. [Crear un Nuevo Paciente](#74-crear-un-nuevo-paciente)
   - 7.5. [Limitaciones del Rol Medicina](#75-limitaciones-del-rol-medicina)

8. [Rol Supervisor](#8-rol-supervisor)
   - 8.1. [Dashboard de Supervisor](#81-dashboard-de-supervisor)
   - 8.2. [Dashboard en Tiempo Real](#82-dashboard-en-tiempo-real)
   - 8.3. [Cómo Interpretar el Dashboard en Tiempo Real](#83-cómo-interpretar-el-dashboard-en-tiempo-real)
   - 8.4. [Gestión de Carga de Trabajo](#84-gestión-de-carga-de-trabajo)
   - 8.5. [Reasignar Tareas](#85-reasignar-tareas)
   - 8.6. [Gestión de Usuarios](#86-gestión-de-usuarios)
   - 8.7. [Permisos Completos del Supervisor](#87-permisos-completos-del-supervisor)

9. [Casos de Uso Prácticos](#9-casos-de-uso-prácticos)
   - 9.1. [Caso 1: Jornada Completa de un Enfermero](#91-caso-1-jornada-completa-de-un-enfermero)
   - 9.2. [Caso 2: Médico Crea una Tarea Urgente](#92-caso-2-médico-crea-una-tarea-urgente)
   - 9.3. [Caso 3: Supervisor Reasigna Tareas por Baja de Personal](#93-caso-3-supervisor-reasigna-tareas-por-baja-de-personal)
   - 9.4. [Caso 4: Incorporación de un Nuevo Enfermero](#94-caso-4-incorporación-de-un-nuevo-enfermero)
   - 9.5. [Caso 5: Supervisión del Turno de Noche](#95-caso-5-supervisión-del-turno-de-noche)
   - 9.6. [Caso 6: Alta de Nuevo Paciente y Creación de Tareas](#96-caso-6-alta-de-nuevo-paciente-y-creación-de-tareas)

10. [Preguntas Frecuentes (FAQ)](#10-preguntas-frecuentes-faq)
    - 10.1. [Sobre el Login y el Acceso](#101-sobre-el-login-y-el-acceso)
    - 10.2. [Sobre las Tareas](#102-sobre-las-tareas)
    - 10.3. [Sobre el Dashboard en Tiempo Real](#103-sobre-el-dashboard-en-tiempo-real)
    - 10.4. [Sobre los Pacientes](#104-sobre-los-pacientes)
    - 10.5. [Sobre los Usuarios y Roles](#105-sobre-los-usuarios-y-roles)

11. [Solución de Problemas](#11-solución-de-problemas)
    - 11.1. [No Puedo Iniciar Sesión](#111-no-puedo-iniciar-sesión)
    - 11.2. [La Página No Carga](#112-la-página-no-carga)
    - 11.3. [El Dashboard en Tiempo Real No Se Actualiza](#113-el-dashboard-en-tiempo-real-no-se-actualiza)
    - 11.4. [Un Formulario No Se Envía](#114-un-formulario-no-se-envía)
    - 11.5. [Veo un Error 403 "Acceso Denegado"](#115-veo-un-error-403-acceso-denegado)
    - 11.6. [Los Gráficos No Aparecen](#116-los-gráficos-no-aparecen)

12. [Glosario de Términos](#12-glosario-de-términos)

13. [Contacto y Soporte](#13-contacto-y-soporte)

14. [Apéndices](#14-apéndices)
    - Apéndice A: [Resumen de Permisos por Rol](#apéndice-a-resumen-de-permisos-por-rol)
    - Apéndice B: [Códigos de Color y Significado](#apéndice-b-códigos-de-color-y-significado)
    - Apéndice C: [Ciclo de Vida de una Tarea](#apéndice-c-ciclo-de-vida-de-una-tarea)
    - Apéndice D: [Tipos de Tarea Disponibles](#apéndice-d-tipos-de-tarea-disponibles)

15. [Registro de Cambios del Manual](#15-registro-de-cambios-del-manual)

---

## 1. Introducción

### 1.1. ¿Qué es MediTrack?

**MediTrack** es un sistema informático diseñado específicamente para hospitales y unidades de atención sanitaria. Su función principal es organizar, asignar y hacer seguimiento de las **tareas clínicas** que el personal de enfermería debe realizar con los pacientes cada día.

Antes de sistemas como MediTrack, la gestión de tareas clínicas se realizaba con listas en papel, pizarras en el control de enfermería, o herramientas genéricas como hojas de cálculo. Estos métodos presentaban problemas comunes:

- 📄 **Tareas duplicadas:** Dos enfermeros realizaban el mismo procedimiento por falta de comunicación.
- 🕐 **Tareas olvidadas:** Una tarea anotada en papel quedaba sin hacer al cambiar de turno.
- ❓ **Falta de trazabilidad:** Era difícil saber quién había realizado qué y cuándo.
- 📞 **Comunicación ineficiente:** Los médicos tenían que buscar físicamente a los enfermeros para asignar tareas urgentes.

MediTrack resuelve todos estos problemas ofreciendo una plataforma centralizada, accesible desde cualquier ordenador del hospital, donde cada tarea tiene un responsable asignado, un estado actualizado y un historial de observaciones.

### 1.2. ¿Para quién es MediTrack?

MediTrack está diseñado para tres tipos de usuarios que trabajan de forma coordinada:

---

**👩‍⚕️ Personal de Enfermería**

Los enfermeros y enfermeras son los **ejecutores** del sistema. Reciben tareas asignadas por el personal médico y las actualizan a medida que las van realizando. MediTrack les permite ver de un vistazo qué tienen que hacer en su turno, en qué orden de prioridad, y registrar las observaciones pertinentes.

---

**🩺 Personal Médico (Médicos)**

Los médicos son los **creadores** de tareas. Cuando necesitan que se realice un procedimiento con un paciente (administrar medicación, controlar constantes vitales, realizar una cura, etc.), crean la tarea en MediTrack, la asignan al enfermero del turno correspondiente y establecen la prioridad. También pueden dar de alta a nuevos pacientes en el sistema.

---

**📊 Supervisores de Unidad**

Los supervisores tienen una **visión global** de la unidad. Pueden ver en tiempo real cómo está repartida la carga de trabajo entre los turnos y los enfermeros, reasignar tareas cuando hay ausencias o desequilibrios, gestionar los usuarios del sistema y tomar decisiones informadas basadas en datos actualizados.

### 1.3. ¿Qué Problemas Resuelve?

| Problema habitual | Cómo lo resuelve MediTrack |
|---|---|
| "No sé qué tareas me tocan hoy" | Dashboard personal con todas las tareas asignadas a cada enfermero |
| "No sé si alguien ya hizo esto" | Cada tarea tiene un estado visible (PENDIENTE, EN_CURSO, REALIZADA) |
| "El médico no me encontró para dar la orden" | El médico crea la tarea digitalmente y aparece al instante en el dashboard del enfermero |
| "Un enfermero está de baja, ¿quién hace sus tareas?" | El supervisor las reasigna en segundos desde la pantalla de reasignación |
| "No sé si hay sobrecarga en algún turno" | Dashboard en tiempo real con gráficos de carga por turno |
| "¿Quién atendió a este paciente y qué observaciones dejó?" | Cada tarea tiene un historial de observaciones registradas |
| "Se perdieron las instrucciones del médico" | Las tareas incluyen descripción, tipo y observaciones, accesibles siempre |

### 1.4. Cómo Usar este Manual

Este manual está organizado por **roles de usuario**. Te recomendamos que:

1. **Leas las secciones 1 a 5 siempre** (introducción, requisitos y acceso, que son comunes a todos).
2. **Luego vayas a la sección de tu rol:** sección 6 (Enfermería), sección 7 (Medicina), sección 8 (Supervisor).
3. **Consulta la sección 9** (casos prácticos) para ver flujos de trabajo completos.
4. **Revisa el glosario (sección 12)** si encuentras términos desconocidos.

> 💡 **Consejo:** Marca esta página como favorito en tu navegador para acceder rápidamente cuando tengas dudas.

---

## 2. Requisitos Técnicos

No necesitas instalar nada especial en tu ordenador para usar MediTrack. Solo necesitas un **navegador web** y acceso a la red del hospital.

### 2.1. Navegadores Compatibles

| Navegador | Versión mínima | ¿Recomendado? | Notas |
|---|---|---|---|
| 🟢 Google Chrome | 90 o superior | ✅ Sí, el mejor | Mejor rendimiento con los gráficos en tiempo real |
| 🟢 Mozilla Firefox | 88 o superior | ✅ Sí | Funciona perfectamente |
| 🟢 Microsoft Edge | 90 o superior | ✅ Sí | Versión basada en Chromium |
| 🟡 Safari | 14 o superior | ⚠️ Aceptable | Funciona, pero los gráficos pueden ser más lentos |
| 🔴 Internet Explorer | — | ❌ No | No compatible. Actualiza a Edge |

> ⚠️ **Importante:** Si tu navegador es antiguo, algunas funcionalidades como el **Dashboard en Tiempo Real** (gráficos y actualización automática) pueden no funcionar correctamente. Actualiza tu navegador para la mejor experiencia.

### 2.2. Resoluciones Recomendadas

MediTrack se adapta automáticamente al tamaño de tu pantalla (diseño "responsive"), pero funciona mejor con estas resoluciones:

| Dispositivo | Resolución recomendada | Observaciones |
|---|---|---|
| 🖥️ Ordenador de sobremesa | 1920×1080 o mayor | Experiencia óptima con todos los elementos visibles |
| 💻 Portátil | 1366×768 o mayor | Funciona bien. El menú puede colapsarse |
| 📱 Tablet | 1024×768 o mayor | Los formularios se muestran en una sola columna |
| 📱 Móvil | 375×667 o mayor | Funcional pero se recomienda tablet/PC para tareas complejas |

> 💡 **Consejo:** Para el **Dashboard en Tiempo Real** con los gráficos, se recomienda pantalla de al menos 1366×768 para ver todos los elementos correctamente.

### 2.3. Conexión a Internet

- **Conexión a la red del hospital:** Obligatoria para acceder al sistema (la aplicación corre en los servidores del hospital).
- **Para el Dashboard en Tiempo Real:** Una conexión estable garantiza que las actualizaciones cada 5 segundos lleguen sin interrupciones. Si la conexión es inestable, el sistema lo indica con el badge 🔴 "Desconectado" y se reconecta automáticamente.
- **Velocidad mínima:** Con 1 Mbps de ancho de banda es suficiente, ya que los datos que se transmiten son pequeños (texto y números, no vídeo).

---

## 3. Acceso al Sistema

### 3.1. URL de Acceso

Para acceder a MediTrack, abre tu navegador e introduce la siguiente dirección:

```
http://localhost:8080
```

> 📌 **En entorno hospitalario real:** El administrador del sistema te proporcionará la URL definitiva de acceso (por ejemplo: `http://meditrack.hospital.es` o la IP del servidor). Pide esta información a tu departamento de informática.

Si la dirección es correcta y el sistema está activo, serás redirigido automáticamente a la **pantalla de login**.

### 3.2. Pantalla de Login

Al acceder, verás una pantalla de inicio de sesión con fondo azul-blanco y el logo de MediTrack. Es la puerta de entrada al sistema.

**Elementos que verás en la pantalla:**

```
┌─────────────────────────────────────┐
│           🏥 MediTrack              │
│    Sistema de Tareas Clínicas       │
│                                     │
│  👤 Usuario:  [________________]    │
│                                     │
│  🔒 Contraseña: [______________]    │
│                                     │
│       [  Iniciar Sesión  ]          │
│                                     │
│  ──────────────────────────────     │
│  Usuarios de prueba:                │
│  • enfermera / enfermera123         │
│  • medico / medico123               │
│  • supervisor / supervisor123       │
└─────────────────────────────────────┘
```

**Descripción de cada elemento:**

- **Campo "Usuario":** Introduce tu nombre de usuario (username). Es sensible a mayúsculas: `Enfermera` y `enfermera` son distintos.
- **Campo "Contraseña":** Introduce tu contraseña. Los caracteres se muestran como puntos (•••) por seguridad.
- **Botón "Iniciar Sesión":** Pulsa para enviar tus credenciales y entrar al sistema.

**Si las credenciales son incorrectas:** Verás un mensaje de error en la parte superior del formulario: *"Usuario o contraseña incorrectos"*. Revisa que no hayas escrito mal tu usuario o contraseña.

### 3.3. Credenciales de Prueba

El sistema incluye tres usuarios de prueba para empezar a trabajar de inmediato:

---

**👩‍⚕️ Usuario de Enfermería**

| Campo | Valor |
|---|---|
| Usuario | `enfermera` |
| Contraseña | `enfermera123` |
| Nombre completo | Ana García López |
| Rol | ENFERMERIA |
| Acceso | Sección 6 de este manual |

**Con este usuario puedes:** Ver tus tareas asignadas y actualizar su estado.

---

**🩺 Usuario de Medicina**

| Campo | Valor |
|---|---|
| Usuario | `medico` |
| Contraseña | `medico123` |
| Nombre completo | Carlos Martínez Ruiz |
| Rol | MEDICINA |
| Acceso | Sección 7 de este manual |

**Con este usuario puedes:** Crear tareas clínicas, crear pacientes y ver el estado de todas las tareas.

---

**📊 Usuario de Supervisor**

| Campo | Valor |
|---|---|
| Usuario | `supervisor` |
| Contraseña | `supervisor123` |
| Nombre completo | Laura Fernández Sánchez |
| Rol | SUPERVISOR |
| Acceso | Sección 8 de este manual |

**Con este usuario puedes:** Acceso completo al sistema, incluyendo el dashboard en tiempo real, reasignación de tareas y gestión de usuarios.

> ⚠️ **Aviso de seguridad:** Estas credenciales son solo para pruebas y formación. En un entorno real, el administrador asignará credenciales únicas a cada profesional y las contraseñas de prueba **deben cambiarse**.

### 3.4. Primer Inicio de Sesión

Cuando entras por primera vez con tu usuario, el sistema te lleva directamente al **dashboard de tu rol**:

- Si eres **ENFERMERIA** → Vas a `/enfermeria/dashboard`
- Si eres **MEDICINA** → Vas a `/medicina/dashboard`
- Si eres **SUPERVISOR** → Vas a `/supervisor/dashboard`

**Lo que verás al entrar:**

La pantalla principal mostrará un resumen adaptado a tu función. La **barra de navegación** en la parte superior cambia de color según tu rol:
- 🔵 Azul para enfermería
- 🟢 Verde para medicina
- 🟣 Morado para supervisor

Si es la primera vez que se accede al sistema (base de datos vacía), los contadores aparecerán en cero (0). Esto es normal: significa que aún no hay pacientes, tareas ni actividad registrada.

> 💡 **Para empezar a trabajar con datos reales:** Un médico o supervisor debe primero crear pacientes y asignar tareas. El sistema se va "llenando" de información a medida que el equipo lo usa.

### 3.5. Cerrar Sesión

Cuando termines de trabajar con MediTrack, es importante cerrar sesión correctamente, especialmente si usas un ordenador compartido.

**Cómo cerrar sesión:**

1. Localiza el botón **"Salir"** en la esquina superior derecha de la barra de navegación (visible en todas las pantallas).
2. Tiene un icono de puerta 🚪 o similar.
3. Haz clic en él.
4. Serás redirigido a la pantalla de login con el mensaje: *"Has cerrado sesión correctamente"*.

> ⚠️ **Importante:** No cierres simplemente la ventana del navegador sin hacer logout. Tu sesión podría quedar activa durante un tiempo en el servidor. Usa siempre el botón "Salir".

---

## 4. Gestión de Usuarios

### 4.1. ¿Quién Puede Crear Usuarios?

La creación, modificación y eliminación de usuarios es una función **exclusiva del rol SUPERVISOR**. Los usuarios con rol ENFERMERIA y MEDICINA no tienen acceso a estas funciones.

Esto garantiza que solo el personal autorizado pueda dar acceso al sistema a nuevos miembros del equipo.

### 4.2. Crear un Nuevo Usuario

Sigue estos pasos cuando un nuevo profesional se incorpore al equipo y necesite acceso a MediTrack:

---

**Paso 1: Acceder a Gestión de Usuarios**

1. Inicia sesión con tu cuenta de **SUPERVISOR**.
2. En la barra de navegación morada en la parte superior, haz clic en **"Usuarios"** (icono de personas 👥).
3. Verás la lista de todos los usuarios actuales del sistema.

---

**Paso 2: Iniciar la Creación**

En la página de usuarios, busca y haz clic en el botón **"Crear Nuevo Usuario"** (normalmente en la parte superior de la tabla, con un icono ➕).

Se abrirá el formulario de creación.

---

**Paso 3: Rellenar el Formulario**

Completa todos los campos del formulario:

| Campo | Descripción | Ejemplo | Obligatorio |
|---|---|---|---|
| **Nombre** | Nombre de pila del profesional | `María` | ✅ Sí |
| **Apellidos** | Apellidos completos | `Sánchez Torres` | ✅ Sí |
| **Usuario (username)** | Identificador único para el login | `msanchez` | ✅ Sí |
| **Contraseña** | Contraseña inicial (mínimo 8 caracteres) | `Temporal2026!` | ✅ Sí |
| **Rol** | Nivel de acceso | `ENFERMERIA` | ✅ Sí |

**Reglas importantes para el campo "Usuario (username)":**

- ✅ Usa letras minúsculas, números y puntos. Ejemplo: `ana.garcia`, `clopez2`
- ✅ Debe ser único en todo el sistema (no puede existir otro igual)
- ❌ No uses espacios, tildes ni caracteres especiales como ñ, @, #
- ❌ No uses el mismo username de otro usuario ya existente

**Seleccionar el Rol correcto:**

| Rol | Para quién | ¿Qué podrá hacer? |
|---|---|---|
| `ENFERMERIA` | Enfermeros/as | Ver sus tareas y actualizar estados |
| `MEDICINA` | Médicos/as | Crear tareas y pacientes |
| `SUPERVISOR` | Supervisores de unidad | Acceso completo al sistema |

---

**Paso 4: Guardar el Nuevo Usuario**

1. Revisa que todos los campos son correctos.
2. Haz clic en el botón **"Crear Usuario"**.
3. **Si todo va bien:** Eres redirigido a la lista de usuarios y ves al nuevo usuario en la tabla.
4. **Si hay un error:** Aparece un mensaje en rojo explicando el problema, por ejemplo:
   - *"Ya existe un usuario con ese nombre de usuario"* → Elige un username diferente.
   - *"Todos los campos son obligatorios"* → Rellena los campos vacíos.

---

**Paso 5: Comunicar las Credenciales**

Una vez creado el usuario, comunica al nuevo profesional:
- Su **nombre de usuario** (username)
- Su **contraseña temporal**
- La **URL del sistema** para acceder

> 🔒 **Consejo de seguridad:** Comunica las credenciales de forma segura (en persona o por canal cifrado). Nunca por correo electrónico sin cifrar. Recomienda al usuario cambiar la contraseña en su primer acceso.

### 4.3. Editar un Usuario Existente

Si un profesional necesita cambiar su contraseña, actualizar sus apellidos o cambiar de rol:

1. Ve a **Menú → Usuarios** (como SUPERVISOR).
2. Localiza al usuario en la tabla.
3. Haz clic en el botón **"Editar"** de ese usuario.
4. Modifica los campos necesarios.
5. **Para cambiar la contraseña:** Introduce la nueva en el campo "Contraseña". Si dejas el campo vacío, la contraseña actual no cambia.
6. Haz clic en **"Guardar Cambios"**.

> 💡 **Nota:** Si dejas el campo "Contraseña" en blanco al editar, la contraseña actual del usuario se mantiene. No hace falta volver a introducirla si solo quieres cambiar otros datos.

### 4.4. Eliminar un Usuario

> ⚠️ **Acción irreversible.** Eliminar un usuario no borra las tareas que tiene asignadas, pero esas tareas quedarán sin un responsable activo. Se recomienda **reasignar las tareas antes de eliminar** al usuario.

1. Ve a **Menú → Usuarios** (como SUPERVISOR).
2. Localiza al usuario en la tabla.
3. Haz clic en el botón **"Eliminar"** de ese usuario.
4. Confirma la acción si el sistema te lo solicita.
5. El usuario desaparece de la lista y ya no podrá iniciar sesión.

---

## 5. Interfaz Común a Todos los Roles

### 5.1. Barra de Navegación

La **barra de navegación** (o "navbar") es la franja de color en la parte superior de todas las pantallas. Es tu panel de control principal.

```
┌──────────────────────────────────────────────────────────────────────┐
│ 🏥 MediTrack  | Dashboard | Mis Tareas |  ...  |  👤 enfermera  Salir│
└──────────────────────────────────────────────────────────────────────┘
```

**Elementos de la barra de navegación:**

| Elemento | Descripción |
|---|---|
| **🏥 MediTrack** (logo, izquierda) | Haz clic para volver al dashboard de tu rol en cualquier momento |
| **Opciones del menú** (centro) | Varían según tu rol. Se describen en las secciones 6, 7 y 8 |
| **👤 [Nombre de usuario]** (derecha) | Muestra quién está conectado actualmente |
| **Botón "Salir"** (derecha) | Cierra tu sesión de forma segura |

**Colores de la barra según el rol:**

- 🔵 **Azul** → Sesión de ENFERMERIA
- 🟢 **Verde** → Sesión de MEDICINA
- 🟣 **Morado** → Sesión de SUPERVISOR

De un vistazo, cualquier persona puede saber con qué rol está trabajando la sesión activa.

**En pantallas pequeñas (móvil/tablet):** El menú se "pliega" y aparece un botón ☰ (hamburguesa) en la esquina derecha. Al hacer clic, el menú se despliega hacia abajo.

### 5.2. Indicadores Visuales de Estado y Prioridad

MediTrack usa **etiquetas de colores** (llamadas "badges") para indicar el estado y la prioridad de las tareas de un vistazo. No hace falta leer el texto; el color ya comunica la información más importante.

**Badges de Estado de Tarea:**

| Color | Texto | Significado |
|---|---|---|
| 🟡 Amarillo | PENDIENTE | La tarea aún no ha sido iniciada |
| 🔵 Azul | EN CURSO | La tarea está siendo realizada en este momento |
| 🟢 Verde | REALIZADA | La tarea se ha completado con éxito |
| ⚫ Gris | CANCELADA | La tarea fue cancelada y no se realizará |

**Badges de Prioridad:**

| Color | Texto | Significado |
|---|---|---|
| 🔴 Rojo intenso | URGENTE | Atención inmediata. No puede esperar |
| 🟠 Naranja | ALTA | Importante. Hacer en las próximas horas |
| 🟡 Gris medio | MEDIA | Tarea rutinaria dentro del turno |
| ⚪ Gris claro | BAJA | Sin urgencia. Puede esperar si hay otras más importantes |

### 5.3. Comportamiento Responsive

El sistema se adapta automáticamente al tamaño de tu pantalla:

- **En ordenador:** Todos los elementos se muestran en una sola fila, con tablas completas y gráficos amplios.
- **En tablet:** Los menús se colapsan. Las tablas muestran menos columnas, y los formularios se reorganizan en una sola columna.
- **En móvil:** El menú se oculta detrás del botón ☰. Las tarjetas se apilan verticalmente. Las tablas se pueden desplazar horizontalmente.

---

## 6. Rol Enfermería

Esta sección es para ti si tu usuario tiene el rol **ENFERMERIA**. Aquí aprenderás todo lo que necesitas para gestionar tus tareas clínicas diarias.

### 6.1. Dashboard de Enfermería

El **dashboard** es la primera pantalla que ves al iniciar sesión. Es tu página de inicio, tu resumen del día.

**¿Qué información aparece?**

Al entrar, verás:

1. **Tu nombre** en la bienvenida: *"Bienvenida, Ana"*
2. **Tarjeta de resumen** con tres números:
   - Total de tareas asignadas a ti
   - Tareas pendientes (las que aún tienes que hacer)
   - Tareas en curso (las que estás haciendo ahora mismo)
3. **Tabla de las 5 tareas más recientes** para que veas de un vistazo las últimas asignaciones.
4. **Acceso rápido** al botón "Ver Todas mis Tareas".

**Información en la tabla de tareas:**

Cada fila de la tabla muestra:
- 🏥 Nombre del paciente
- 📋 Descripción breve de la tarea
- 🏷️ Tipo (Medicación, Cura, etc.)
- 🎯 Prioridad (con su color correspondiente)
- 📊 Estado actual (con su color)
- 📅 Fecha/hora programada
- 🔄 Botón para actualizar el estado

> 💡 **Consejo:** Revisa tu dashboard siempre al empezar el turno. Te dará una visión general de tu carga de trabajo para el día.

### 6.2. Mis Tareas

La sección **"Mis Tareas"** es donde gestionas tu trabajo diario. Accede desde el menú superior haciendo clic en **"Mis Tareas"**.

**Diferencia con el dashboard:**
- El **dashboard** muestra solo las 5 tareas más recientes.
- **"Mis Tareas"** muestra **todas** tus tareas asignadas, sin límite.

**¿Qué puedes hacer aquí?**

- ✅ Ver toda la lista completa de tus tareas
- ✅ Ver el detalle de cada tarea (descripción, tipo, prioridad, paciente, turno)
- ✅ Cambiar el estado de una tarea
- ✅ Añadir observaciones a una tarea

**Cómo está organizada la tabla:**

Cada fila de la tabla tiene las siguientes columnas:
1. Paciente
2. Descripción de la tarea
3. Tipo
4. Prioridad
5. Estado actual
6. Turno asignado
7. Fecha/hora
8. Observaciones actuales
9. Formulario de actualización

### 6.3. Cómo Actualizar el Estado de una Tarea

Actualizar el estado de tus tareas es la acción más importante que realizarás en MediTrack. Así se registra el progreso de tu trabajo.

**Guía paso a paso:**

---

**Paso 1: Localiza la tarea**

En la página "Mis Tareas", busca la tarea que quieres actualizar. Puedes identificarla por el nombre del paciente o la descripción de la actividad.

---

**Paso 2: Busca el formulario de actualización**

Cada fila tiene, a la derecha, un pequeño formulario compuesto por:
- Un **desplegable** (selector) para el nuevo estado
- Un **campo de texto** para observaciones (opcional)
- Un **botón "Actualizar"**

---

**Paso 3: Selecciona el nuevo estado**

Haz clic en el desplegable de estado. Verás las opciones disponibles:

| Opción | ¿Cuándo usarla? |
|---|---|
| **PENDIENTE** | La tarea aún no ha empezado (estado inicial) |
| **EN_CURSO** | Acabas de empezar a realizarla |
| **REALIZADA** | Ya la has completado |
| **CANCELADA** | No se puede realizar (consulta con el médico o supervisor antes) |

**Flujo habitual de una tarea:**
```
PENDIENTE → EN_CURSO → REALIZADA
```

---

**Paso 4: Añade observaciones si es necesario**

El campo de texto junto al desplegable te permite añadir notas. Esto es **opcional** pero muy recomendable para dejar constancia de cómo fue el procedimiento.

---

**Paso 5: Guarda los cambios**

Haz clic en el botón **"Actualizar"** de esa fila.

- El sistema guarda el cambio inmediatamente.
- La tabla se recarga mostrando el estado actualizado.
- El badge de color de la tarea cambia para reflejar el nuevo estado.

> ⚠️ **Importante:** Una vez que marcas una tarea como **REALIZADA**, comunícaselo a tu supervisor si la tarea era urgente. Aunque puedes cambiar el estado a CANCELADA después, los estados son cambios permanentes en el historial.

### 6.4. Añadir Observaciones

Las observaciones son notas de texto libre que puedes añadir a cualquier tarea. Son muy valiosas para:

- **Informar al médico** sobre cómo reaccionó el paciente
- **Advertir al siguiente turno** sobre algo a tener en cuenta
- **Documentar incidencias** durante el procedimiento

**Ejemplos de buenas observaciones:**

✅ *"Paciente toleró bien la medicación. Sin efectos adversos observados."*

✅ *"Durante la cura, la herida presentaba signos de inflamación. Informado Dr. Martínez."*

✅ *"Control de constantes: TA 130/85, FC 78 lpm, Temp 36.8°C. Dentro de parámetros normales."*

✅ *"Paciente rechazó el procedimiento de higiene. Programado para dentro de 2 horas."*

**Cómo añadir observaciones:**

1. En el campo de texto junto al selector de estado, escribe tus observaciones.
2. Si ya existen observaciones previas, tu texto **reemplaza** las anteriores (o se añade según la configuración).
3. Haz clic en **"Actualizar"** para guardar.

> 💡 **Consejo:** Sé específico en las observaciones. Anota valores numéricos cuando sea posible (temperatura, tensión, frecuencia cardíaca). Esto ayuda al equipo médico a tomar decisiones informadas.

### 6.5. Limitaciones del Rol Enfermería

Para mantener la seguridad y la integridad de los datos, el rol ENFERMERIA tiene ciertas restricciones:

| Acción | ¿Puede hacerlo? |
|---|---|
| Ver mis tareas asignadas | ✅ Sí |
| Actualizar estado de mis tareas | ✅ Sí |
| Añadir observaciones a mis tareas | ✅ Sí |
| Ver información de mis pacientes | ✅ Sí |
| Crear nuevas tareas | ❌ No (solo MEDICINA y SUPERVISOR) |
| Ver tareas de otros enfermeros | ❌ No (solo MEDICINA y SUPERVISOR) |
| Asignar tareas a otros | ❌ No |
| Crear nuevos pacientes | ❌ No |
| Eliminar tareas | ❌ No (solo SUPERVISOR) |
| Gestionar usuarios | ❌ No (solo SUPERVISOR) |
| Dashboard en tiempo real | ❌ No (solo SUPERVISOR) |

Si intentas acceder a una función que no tienes permitida (por ejemplo, tecleando una URL directamente), el sistema mostrará un error **"403 Acceso Denegado"** y te redirigirá a tu área.

---

## 7. Rol Medicina

Esta sección es para usuarios con rol **MEDICINA**. El personal médico es quien genera el trabajo en MediTrack: crea las tareas y da de alta a los pacientes.

### 7.1. Dashboard de Medicina

Al iniciar sesión como médico, verás el dashboard con una visión general del sistema:

**¿Qué información aparece?**

1. **Tarjetas de resumen** con:
   - Total de tareas en el sistema
   - Número de tareas pendientes
   - Total de pacientes registrados
2. **Tabla de las 5 tareas más recientes** en el sistema (no solo las tuyas)
3. **Accesos rápidos:**
   - Botón "Crear Nueva Tarea"
   - Botón "Ver Pacientes"

> 💡 **Diferencia respecto a enfermería:** El médico ve **todas las tareas del sistema**, mientras que el enfermero solo ve las suyas. Esto permite al médico monitorizar el estado de todas las órdenes que ha emitido.

### 7.2. Crear una Tarea Clínica

Crear una tarea es el proceso más importante para el personal médico. Una tarea bien creada garantiza que el enfermero sepa exactamente qué hacer, con qué paciente y cuándo.

**Acceso:** Menú superior → **"Crear Tarea"** (icono ➕)

---

**Paso 1: Seleccionar el Paciente**

El primer campo es un **desplegable** con todos los pacientes registrados en el sistema.

- Cada opción muestra: *Nombre Apellidos — NHC: CÓDIGO*
- Ejemplo: *"Juan Pérez García — NHC: HC-001"*
- Si el paciente no aparece en la lista, primero debes **darlo de alta** (ver sección 7.4).

---

**Paso 2: Asignar al Enfermero**

Selecciona el enfermero responsable de ejecutar la tarea:

- Solo aparecen usuarios con rol **ENFERMERIA**.
- Verás el nombre completo de cada enfermero.
- Elige el enfermero del turno correspondiente a la tarea.

> 💡 **Consejo:** Si no sabes quién es el enfermero responsable, consulta con el supervisor o revisa la "Carga de Trabajo" en el panel del supervisor.

---

**Paso 3: Seleccionar el Turno**

Elige en qué turno debe realizarse la tarea:

| Turno | Horario |
|---|---|
| **Mañana** | 07:00 — 15:00 |
| **Tarde** | 15:00 — 23:00 |
| **Noche** | 23:00 — 07:00 |

Asegúrate de que el enfermero que seleccionaste trabaja en ese turno.

---

**Paso 4: Escribir la Descripción**

La descripción es el corazón de la tarea. Debe ser **clara y concreta** para que el enfermero sepa exactamente qué hacer.

| ❌ Descripción vaga | ✅ Descripción clara |
|---|---|
| "Medicación" | "Administrar 500mg de paracetamol vía oral con agua" |
| "Revisar paciente" | "Controlar constantes vitales: TA, FC y temperatura cada 2 horas" |
| "Cura" | "Realizar cura de úlcera presión zona sacra con apósito hidrocoloide" |

- Máximo: **500 caracteres**
- Escribe en lenguaje claro que cualquier enfermero pueda entender

---

**Paso 5: Seleccionar el Tipo de Tarea**

Elige la categoría que mejor describe el procedimiento:

| Tipo | ¿Cuándo usarlo? |
|---|---|
| **Medicación** | Administrar medicamentos (oral, IV, IM, tópico) |
| **Control Signos Vitales** | Medir TA, frecuencia cardíaca, temperatura, saturación O₂ |
| **Cura** | Curas de heridas, suturas, úlceras |
| **Higiene** | Aseo del paciente, higiene bucal, ducha |
| **Movilización** | Cambios posturales, sedestación, fisioterapia básica |
| **Otro** | Cualquier procedimiento no incluido en los anteriores |

---

**Paso 6: Establecer la Prioridad**

La prioridad determina la urgencia de la tarea y el orden en que el enfermero debe atenderla:

| Prioridad | ¿Cuándo usarla? | Ejemplo |
|---|---|---|
| 🔴 **URGENTE** | Riesgo para el paciente si no se hace inmediatamente | Administrar adrenalina en reacción alérgica |
| 🟠 **ALTA** | Importante, debe hacerse en las próximas 1-2 horas | Control de glucemia en paciente diabético |
| 🟡 **MEDIA** | Tarea rutinaria dentro del turno | Control de constantes cada 4 horas |
| ⚪ **BAJA** | Sin urgencia, puede posponerse | Higiene completa del paciente estable |

> ⚠️ **Usa URGENTE con criterio.** Si todas las tareas son "urgentes", el término pierde significado y el enfermero no puede priorizar correctamente.

---

**Paso 7: Estado Inicial**

Por defecto, todas las tareas nuevas empiezan en estado **PENDIENTE**. Este es el estado correcto para la mayoría de los casos (la tarea existe pero aún no ha sido iniciada).

En casos excepcionales, podrías crear una tarea ya en estado **EN_CURSO** si la sabes que ya está siendo atendida en ese momento.

---

**Paso 8: Fecha y Hora**

Selecciona cuándo debe realizarse la tarea.

- Usa el selector de fecha y hora que aparece en el campo.
- La fecha y hora indican el **momento previsto** de realización, no una alarma automática.
- Si es una tarea urgente, pon la fecha/hora actual.

---

**Paso 9: Observaciones Adicionales (Opcional)**

Campo de texto libre para instrucciones especiales, contraindicaciones, advertencias, o cualquier información adicional relevante.

Ejemplo: *"Paciente alérgico al ibuprofeno. Verificar antes de administrar. En caso de duda, consultar historia clínica."*

Máximo: **1000 caracteres**.

---

**Paso 10: Crear la Tarea**

1. Revisa toda la información introducida.
2. Haz clic en **"Crear Tarea"**.
3. **Si todo va bien:** Eres redirigido al dashboard donde puedes ver la tarea recién creada.
4. **Si hay errores:** Aparecen mensajes en rojo indicando qué campo tiene el problema.

> ✅ **¡Listo!** El enfermero asignado verá la tarea inmediatamente en su dashboard y en "Mis Tareas". Si el supervisor tiene abierto el Dashboard en Tiempo Real, también verá el incremento en los contadores al cabo de máximo 5 segundos.

### 7.3. Gestión de Pacientes

**Acceso:** Menú superior → **"Pacientes"** (icono 🏥)

**¿Qué verás?**

Una tabla con todos los pacientes registrados en el sistema, con las columnas:
- Nombre completo
- Fecha de nacimiento
- Número de Historia Clínica (NHC)
- Habitación asignada

**¿Para qué sirve esta página?**

- Consultar si un paciente ya está dado de alta en el sistema antes de crear una tarea.
- Ver la información básica de cada paciente.
- Acceder al botón de crear un nuevo paciente.

> 💡 **Consejo:** Antes de crear una tarea, comprueba en esta página que el paciente existe. Si no aparece, primero debes darlo de alta con el formulario de "Crear Nuevo Paciente".

### 7.4. Crear un Nuevo Paciente

**Acceso:** Página de Pacientes → Botón **"Crear Nuevo Paciente"** (icono ➕)

**Formulario de alta de paciente:**

---

**Campo: Nombre** (Obligatorio)

Nombre de pila del paciente. Solo el nombre, no los apellidos.

Ejemplo: `Juan`

---

**Campo: Apellidos** (Obligatorio)

Apellidos completos del paciente.

Ejemplo: `Pérez Martínez`

---

**Campo: Fecha de Nacimiento** (Opcional)

Usa el selector de fecha del formulario. Formato: DD/MM/AAAA.

Ejemplo: `15/03/1975`

---

**Campo: Número de Historia Clínica** (Obligatorio)

El identificador único del paciente en el hospital. Debe coincidir con el número de su historia clínica física o en el sistema del hospital.

> ⚠️ **Muy importante:** Este número debe ser **único**. Si introduces un NHC que ya existe en el sistema, obtendrás un error. Verifica siempre el NHC antes de crear el paciente.

Ejemplo: `HC-2026-0042`

---

**Campo: Habitación** (Opcional)

La habitación donde está ingresado actualmente.

Ejemplo: `305`, `UCI-2`, `205B`

---

**Guardar el Paciente:**

1. Rellena todos los campos obligatorios.
2. Haz clic en **"Crear Paciente"**.
3. **Si todo va bien:** Redirige a la lista de pacientes donde aparece el nuevo registro.
4. **Si hay error:** Mensaje en rojo con la causa (normalmente, NHC duplicado).

### 7.5. Limitaciones del Rol Medicina

| Acción | ¿Puede hacerlo? |
|---|---|
| Crear tareas clínicas | ✅ Sí |
| Crear pacientes | ✅ Sí |
| Ver todas las tareas | ✅ Sí |
| Ver todos los pacientes | ✅ Sí |
| Actualizar estado de tareas | ✅ Sí |
| Reasignar tareas | ❌ No (solo SUPERVISOR) |
| Eliminar tareas | ❌ No (solo SUPERVISOR) |
| Eliminar pacientes | ❌ No (solo SUPERVISOR) |
| Gestionar usuarios | ❌ No (solo SUPERVISOR) |
| Dashboard en tiempo real | ❌ No (solo SUPERVISOR) |
| Crear usuarios | ❌ No (solo SUPERVISOR) |

---

## 8. Rol Supervisor

El rol SUPERVISOR tiene acceso completo a MediTrack. Esta sección cubre todas las funcionalidades adicionales que no están disponibles para los otros roles.

### 8.1. Dashboard de Supervisor

**Acceso:** Menú → **"Dashboard"** o directamente al entrar.

El dashboard del supervisor muestra un resumen global de la actividad del sistema:

- **Total de tareas** en el sistema (todas, de todos los estados)
- **Total de usuarios** registrados (enfermeros, médicos y supervisores)
- **Total de turnos** configurados

Este dashboard es **estático**: muestra datos actualizados en el momento en que cargaste la página. Para ver datos en tiempo real que se actualizan automáticamente, usa el **Dashboard en Tiempo Real** (sección 8.2).

### 8.2. Dashboard en Tiempo Real

**Acceso:** Menú superior → **"📊 Dashboard Tiempo Real"**

Esta es la funcionalidad más avanzada de MediTrack. A diferencia del dashboard estático, esta pantalla **se actualiza automáticamente cada 5 segundos** sin que tengas que hacer nada.

> 💡 **¿Qué significa "en tiempo real"?** Que si en este momento un enfermero cambia el estado de una tarea de PENDIENTE a EN_CURSO, verás ese cambio reflejado en tu pantalla en menos de 5 segundos, sin tener que refrescar.

**¿Qué información se muestra?**

La pantalla está dividida en cuatro áreas principales:

---

**Área 1: Indicadores de Conexión**

En la parte superior derecha:
- 🟢 **Conectado** (verde): El sistema está recibiendo datos correctamente.
- 🔴 **Desconectado** (rojo): Se ha perdido la conexión. El sistema reintenta automáticamente.
- **Última actualización:** Hora exacta del último dato recibido. Ejemplo: *"Última actualización: 10:35:42"*

---

**Área 2: Tarjetas de Estadísticas Generales**

Siete tarjetas con los números más importantes del sistema:

| Tarjeta | Color | ¿Qué mide? |
|---|---|---|
| Total Tareas | Azul | Suma de todas las tareas en el sistema |
| Pendientes | Amarillo | Tareas aún no iniciadas |
| En Curso | Cyan | Tareas siendo ejecutadas ahora |
| Realizadas | Verde | Tareas completadas |
| Canceladas | Rojo | Tareas canceladas |
| Total Pacientes | Gris | Pacientes registrados |
| Total Enfermeros | Negro | Usuarios con rol ENFERMERIA |

Cuando un valor cambia respecto al anterior, la tarjeta se **anima brevemente** (efecto de zoom) para llamar tu atención sobre el cambio.

---

**Área 3: Gráficos Interactivos**

**Gráfico de Barras — Carga por Turno:**

Muestra en barras verticales cuántas tareas tiene asignadas cada turno:
- Barra azul: Turno de Mañana
- Barra verde: Turno de Tarde
- Barra morada: Turno de Noche

Te permite detectar de inmediato si un turno está sobrecargado (barra muy alta) respecto a los otros.

**Gráfico de Donut — Tareas por Prioridad:**

Muestra la distribución porcentual de las tareas por nivel de urgencia:
- 🔴 Rojo: URGENTE
- 🟠 Naranja: ALTA
- 🟡 Amarillo: MEDIA
- ⚪ Gris: BAJA

Si el trozo rojo es muy grande, hay muchas tareas urgentes acumuladas que requieren atención inmediata.

---

**Área 4: Top 5 Enfermeros**

Una tabla con el ranking de los 5 enfermeros que más tareas tienen asignadas:

| # | Nombre | Tareas Asignadas |
|---|---|---|
| 🥇 1 | Ana García López | 8 |
| 🥈 2 | Carlos Ruiz Pérez | 6 |
| 🥉 3 | María Sánchez Torres | 4 |
| 4 | Pedro López Gil | 3 |
| 5 | Laura Martín Vega | 2 |

Esta tabla te ayuda a detectar si la carga de trabajo está muy desigualmente distribuida entre el personal.

### 8.3. Cómo Interpretar el Dashboard en Tiempo Real

El dashboard en tiempo real no es solo información estética: es una herramienta de gestión. Aquí tienes una guía para interpretar lo que ves:

**🚨 Señales de alerta que debes vigilar:**

| Lo que ves | Lo que significa | Qué hacer |
|---|---|---|
| Muchas tareas en rojo (URGENTE) | Acumulación de trabajo crítico | Revisa si hay suficiente personal, considera reasignar |
| Una barra de turno mucho más alta | Ese turno está sobrecargado | Redistribuye tareas al turno menos cargado |
| Un enfermero con muchas más tareas | Desequilibrio de carga individual | Reasigna algunas de sus tareas a otros enfermeros |
| Badge "Desconectado" | Problema de red | Verifica la conexión; los datos se actualizarán al reconectar |
| Tareas pendientes muy elevadas | Trabajo acumulado | Verifica causas: falta de personal, tareas bloqueadas, etc. |
| Realizadas aumentando rápido | Turno muy productivo | Buena señal, sin acción requerida |

### 8.4. Gestión de Carga de Trabajo

**Acceso:** Menú → **"Carga de Trabajo"**

Esta pantalla ofrece una tabla detallada de cuántas tareas tiene asignadas cada turno, con una barra visual de progreso para facilitar la comparación.

**¿Para qué sirve?**

- Identificar rápidamente qué turno tiene más carga
- Tomar decisiones de reasignación antes de que empiece el turno
- Planificar la distribución del personal

**Información mostrada:**

| Turno | Horario | Tareas Asignadas | Barra visual |
|---|---|---|---|
| Mañana | 07:00 — 15:00 | 18 | ████████████░░░░░ |
| Tarde | 15:00 — 23:00 | 12 | ████████░░░░░░░░░ |
| Noche | 23:00 — 07:00 | 6 | ████░░░░░░░░░░░░░ |

> 💡 **Consejo:** Usa esta vista junto con el Dashboard en Tiempo Real para tener una imagen completa de la situación.

### 8.5. Reasignar Tareas

La reasignación es una de las funciones más importantes del supervisor. Permite transferir una tarea de un enfermero a otro de forma instantánea.

**Acceso:** Menú → **"Reasignar Tareas"**

**¿Cuándo reasignar?**

- Un enfermero está de baja o ausente
- Un turno está sobrecargado y otro tiene capacidad libre
- Un enfermero no tiene las competencias para una tarea específica
- Se ha cometido un error en la asignación original

---

**Proceso de Reasignación — Paso a Paso:**

**Paso 1: Ver la lista de tareas**

Al entrar en "Reasignar Tareas", verás una tabla con **todas las tareas del sistema**. Cada fila incluye:
- Descripción de la tarea
- Paciente asociado
- Enfermero actualmente asignado
- Estado de la tarea
- Prioridad
- Un selector para el nuevo enfermero
- Botón "Reasignar"

---

**Paso 2: Localiza la tarea a reasignar**

Busca la tarea buscando el nombre del enfermero original, el paciente, o la descripción.

---

**Paso 3: Selecciona el nuevo responsable**

En el desplegable de esa fila, selecciona el enfermero al que quieres transferir la tarea. El selector muestra todos los usuarios con rol ENFERMERIA.

---

**Paso 4: Confirma la reasignación**

Haz clic en el botón **"Reasignar"** de esa fila.

**Resultado inmediato:**
- La tabla se actualiza: la tarea ahora muestra el nuevo enfermero asignado.
- El **enfermero anterior** ya no ve esa tarea en su "Mis Tareas".
- El **nuevo enfermero** la ve inmediatamente en su dashboard y "Mis Tareas".
- En el Dashboard en Tiempo Real, el ranking de top enfermeros se actualiza en el siguiente ciclo de 5 segundos.

> ⚠️ **Consideración:** Reasignar una tarea que ya está en estado EN_CURSO puede confundir al enfermero que la estaba realizando. Comunícate siempre verbalmente con el equipo cuando hagas reasignaciones de tareas activas.

### 8.6. Gestión de Usuarios

**Acceso:** Menú → **"Usuarios"**

Como supervisor, tienes acceso completo a la gestión de todos los usuarios del sistema.

**¿Qué puedes hacer aquí?**

- ✅ Ver la lista completa de usuarios (enfermeros, médicos, supervisores)
- ✅ Crear nuevos usuarios (ver sección 4.2)
- ✅ Editar datos de usuarios existentes (ver sección 4.3)
- ✅ Eliminar usuarios (ver sección 4.4)

**Información mostrada por usuario:**

Cada fila de la tabla muestra:
- Nombre completo
- Username (identificador de login)
- Rol (ENFERMERIA, MEDICINA o SUPERVISOR)
- Botones de acción (Editar, Eliminar)

**Uso responsable de la gestión de usuarios:**

> ⚠️ La eliminación de un usuario es permanente y no puede deshacerse. Asegúrate de reasignar las tareas del usuario antes de eliminarlo.

> 🔒 Gestiona las contraseñas con responsabilidad. No compartas contraseñas por canales no seguros.

### 8.7. Permisos Completos del Supervisor

El supervisor tiene acceso a absolutamente todo en MediTrack:

| Función | ¿Puede hacerlo? |
|---|---|
| Ver todas las tareas | ✅ Sí |
| Crear tareas | ✅ Sí |
| Actualizar estado de tareas | ✅ Sí |
| Reasignar tareas | ✅ Sí (exclusivo) |
| Eliminar tareas | ✅ Sí (exclusivo) |
| Ver todos los pacientes | ✅ Sí |
| Crear pacientes | ✅ Sí |
| Eliminar pacientes | ✅ Sí (exclusivo) |
| Gestionar usuarios (crear/editar/eliminar) | ✅ Sí (exclusivo) |
| Dashboard en tiempo real con gráficos | ✅ Sí (exclusivo) |
| Ver carga de trabajo por turno | ✅ Sí (exclusivo) |
| Gestionar turnos (vía API) | ✅ Sí (exclusivo) |

---

## 9. Casos de Uso Prácticos

En esta sección encontrarás escenarios completos de trabajo con MediTrack, desde el principio hasta el fin. Son ejemplos reales de cómo se usaría el sistema en el día a día hospitalario.

### 9.1. Caso 1: Jornada Completa de un Enfermero

**Escenario:** Ana García empieza su turno de mañana (07:00). Tiene 4 tareas asignadas.

---

**🔑 07:00 — Inicio del turno: Login**

1. Ana abre el navegador en el ordenador del control de enfermería.
2. Va a `http://localhost:8080`.
3. Introduce: usuario `enfermera`, contraseña `enfermera123`.
4. Hace clic en "Iniciar Sesión".
5. Es redirigida automáticamente a su dashboard personal.

---

**📋 07:05 — Revisar el dashboard**

Ana ve en su dashboard:
- 4 tareas asignadas
- 1 tarea con prioridad URGENTE (badge rojo)
- 3 tareas con prioridad MEDIA

Hace clic en "Mis Tareas" para ver el detalle completo.

---

**🚨 07:10 — Atender la tarea urgente primero**

Ana localiza la tarea URGENTE: *"Administrar insulina 10 UI subcutánea antes del desayuno — Paciente: Juan Pérez, Hab. 305"*

1. Hace clic en el selector de estado de esa tarea.
2. Cambia de PENDIENTE a **EN_CURSO**.
3. Hace clic en "Actualizar".

Va a habitación 305 a atender al paciente Juan Pérez.

---

**✅ 07:30 — Completar la tarea urgente**

Tras administrar la insulina:

1. Vuelve al ordenador.
2. En "Mis Tareas", localiza la tarea de Juan Pérez.
3. Cambia el estado a **REALIZADA**.
4. En el campo de observaciones escribe: *"Insulina administrada SC en zona deltoidea izquierda. Glucemia previa: 210 mg/dl. Paciente toleró bien. Desayuno administrado a continuación."*
5. Hace clic en "Actualizar".

---

**🔄 07:30 — 14:50: Completar las demás tareas**

Ana repite el proceso con las 3 tareas restantes:
- Cambiar a EN_CURSO al empezar
- Cambiar a REALIZADA al terminar
- Añadir observaciones relevantes en cada una

---

**🚪 15:00 — Fin del turno: Logout**

Antes de marcharse:
1. Ana comprueba que todas sus tareas están en estado REALIZADA o CANCELADA.
2. Si alguna quedó pendiente, añade una observación explicando el motivo.
3. Hace clic en "Salir" en la barra de navegación.
4. Cierra el navegador.

El siguiente enfermero del turno de tarde tendrá la información de lo que se ha hecho actualizada.

---

### 9.2. Caso 2: Médico Crea una Tarea Urgente

**Escenario:** El Dr. Carlos Martínez está en planta y detecta que el paciente María López (habitación 102) necesita control de constantes urgente.

---

1. El Dr. Martínez abre MediTrack desde el ordenador de planta.
2. Login: `medico` / `medico123`.
3. Click en **"Crear Tarea"** en el menú superior.

**Rellena el formulario:**

- **Paciente:** Busca y selecciona *"María López — NHC: HC-002"*
- **Asignar a:** Selecciona *"Ana García López"* (enfermera de turno de mañana)
- **Turno:** Mañana (07:00 — 15:00)
- **Descripción:** *"Control urgente de signos vitales: TA, FC, Temp y saturación O₂. Paciente refiere disnea y palpitaciones."*
- **Tipo:** Control Signos Vitales
- **Prioridad:** URGENTE
- **Estado:** PENDIENTE
- **Fecha/Hora:** Hoy, 10:30 (en los próximos 15 minutos)
- **Observaciones:** *"Paciente con antecedentes de arritmia. Si FC > 110 o TA sistólica < 90, avisar inmediatamente al médico de guardia."*

4. Click en **"Crear Tarea"**.

**Resultado:** La tarea aparece inmediatamente en el dashboard de Ana García con el badge rojo URGENTE. Ana puede verla sin necesidad de que el médico la avise por teléfono.

---

### 9.3. Caso 3: Supervisor Reasigna Tareas por Baja de Personal

**Escenario:** Laura (supervisora) recibe a las 14:45 la notificación de que Ana García (enfermera de turno) no vendrá al turno de tarde por enfermedad. Hay 6 tareas asignadas a Ana para el turno de tarde que ahora quedan sin responsable.

---

1. Laura hace login: `supervisor` / `supervisor123`.
2. Abre el **Dashboard en Tiempo Real**.

En el gráfico de carga por turnos, ve que el turno de Tarde tiene 6 tareas asignadas a Ana, mientras que Carlos Ruiz (también del turno de tarde) solo tiene 2. La carga está muy desequilibrada.

3. Va a **"Reasignar Tareas"** en el menú.
4. En la tabla, filtra las tareas del turno de tarde.
5. Identifica las 6 tareas asignadas a Ana García.

Para cada una de las 6 tareas:
- En el desplegable, selecciona "Carlos Ruiz Pérez"
- Hace clic en "Reasignar"

6. Vuelve al Dashboard en Tiempo Real.

Ahora el gráfico muestra que Carlos tiene 8 tareas. Decide reasignar 2 de ellas a un tercer enfermero para equilibrar mejor la carga:
- María Sánchez (disponible en ese turno) recibe 2 de las tareas.

7. Llama a Carlos y María para avisarles del cambio.

**Resultado:** En menos de 5 minutos, toda la carga del turno está redistribuida sin ninguna tarea sin responsable, y Carlos y María lo ven en sus dashboards cuando entren a su turno.

---

### 9.4. Caso 4: Incorporación de un Nuevo Enfermero

**Escenario:** Pedro Sánchez se incorpora mañana al equipo de enfermería. Laura (supervisora) necesita crearlo en el sistema hoy.

---

1. Laura hace login como supervisora.
2. Va a **Menú → Usuarios**.
3. Hace clic en **"Crear Nuevo Usuario"**.

**Rellena el formulario:**

- **Nombre:** Pedro
- **Apellidos:** Sánchez Gil
- **Usuario:** psanchez
- **Contraseña:** TempPedro2026! *(contraseña temporal segura)*
- **Rol:** ENFERMERIA

4. Hace clic en "Crear Usuario".
5. Pedro aparece en la lista de usuarios.

6. Laura llama a Pedro por teléfono y le dice:
   - *"Tu usuario es `psanchez` y tu contraseña temporal es `TempPedro2026!`. La URL del sistema es [URL del hospital]."*

7. Al día siguiente, Pedro hace login con esas credenciales y empieza a recibir tareas asignadas.

---

### 9.5. Caso 5: Supervisión del Turno de Noche

**Escenario:** Laura (supervisora) quiere hacer un seguimiento remoto del turno de noche sin estar físicamente en el hospital.

---

1. Laura accede a MediTrack desde su ordenador en casa (conectada a la VPN del hospital).
2. Login: `supervisor` / `supervisor123`.
3. Abre **"Dashboard en Tiempo Real"**.
4. Deja la pantalla abierta en segundo plano.

Cada vez que quiere consultar el estado del turno, echa un vistazo a la pantalla:
- ¿Las tareas URGENTES están siendo atendidas? (tarjeta "En Curso" debe aumentar)
- ¿Algún enfermero tiene una carga desproporcionada?
- ¿Hay muchas tareas acumuladas como PENDIENTE a medianoche?

Si detecta algo preocupante (por ejemplo, 5 tareas urgentes PENDIENTES a las 3:00 AM), puede contactar al supervisor de guardia para informarle.

**Ventaja:** La supervisora tiene visibilidad del turno en tiempo real sin desplazarse al hospital.

---

### 9.6. Caso 6: Alta de Nuevo Paciente y Creación de Tareas

**Escenario:** Ingresa un nuevo paciente en urgencias. El Dr. Martínez necesita darlo de alta en el sistema y crear las primeras órdenes de enfermería.

---

**Parte 1: Dar de alta al paciente**

1. Dr. Martínez hace login: `medico` / `medico123`.
2. Va a **Menú → Pacientes**.
3. Hace clic en **"Crear Nuevo Paciente"**.

**Rellena el formulario:**
- Nombre: Roberto
- Apellidos: García Fernández
- Fecha de nacimiento: 22/08/1958
- Número HC: HC-2026-0078
- Habitación: 401

4. Hace clic en "Crear Paciente". Roberto García ya está en el sistema.

---

**Parte 2: Crear las tareas iniciales**

El médico crea 3 tareas para el nuevo paciente:

**Tarea 1 (Urgente):**
- Paciente: Roberto García
- Enfermera: Ana García
- Turno: Mañana
- Descripción: "Control de constantes cada hora durante las primeras 6 horas"
- Tipo: Control Signos Vitales
- Prioridad: URGENTE

**Tarea 2 (Alta):**
- Paciente: Roberto García
- Enfermera: Ana García
- Turno: Mañana
- Descripción: "Administrar 1g de cefazolina IV en 100ml SSF 0.9% en 30 minutos"
- Tipo: Medicación
- Prioridad: ALTA

**Tarea 3 (Media):**
- Paciente: Roberto García
- Enfermera: Ana García
- Turno: Tarde
- Descripción: "Aseo completo del paciente y cambio de ropa de cama"
- Tipo: Higiene
- Prioridad: MEDIA

5. Crea las 3 tareas haciendo clic en "Crear Tarea" para cada una.

**Resultado:** Ana García ve inmediatamente las 2 tareas del turno de mañana en su dashboard. La tarea de tarde aparecerá para el enfermero que trabaje en ese turno.

---

## 10. Preguntas Frecuentes (FAQ)

### 10.1. Sobre el Login y el Acceso

**P: ¿Olvidé mi contraseña, qué hago?**

R: No existe en la versión actual una función de "¿Olvidé mi contraseña?" automática. Debes contactar con tu supervisor para que:
1. Acceda a la gestión de usuarios.
2. Edite tu usuario y establezca una nueva contraseña temporal.
3. Te la comunique de forma segura.

---

**P: ¿Puedo cambiar mi propia contraseña?**

R: En la versión actual, el cambio de contraseña solo puede realizarlo el supervisor desde la gestión de usuarios. Esta función de autoservicio está planificada para versiones futuras.

---

**P: ¿Puedo usar MediTrack desde mi móvil personal?**

R: Sí, la interfaz es responsive y funciona en móviles. Sin embargo, se recomienda el uso en ordenadores o tablets para tareas que impliquen rellenar formularios (crear tareas, pacientes), ya que la experiencia en pantallas pequeñas es más limitada.

---

**P: ¿Cuánto tiempo dura mi sesión antes de expirar?**

R: La sesión tiene una duración configurada por el administrador del sistema. Si llevas tiempo sin actividad, es posible que al intentar hacer algo te redirija al login. Esto es normal por seguridad. Vuelve a hacer login.

---

**P: ¿Puede otro usuario ver mi contraseña?**

R: No. Las contraseñas se almacenan de forma encriptada (irreversible) en el sistema. Ni el supervisor ni el administrador pueden ver tu contraseña en texto plano. Solo pueden reemplazarla por una nueva.

---

### 10.2. Sobre las Tareas

**P: Marqué una tarea como REALIZADA por error. ¿Puedo deshacer esto?**

R: En la versión actual, los estados no son reversibles desde el interfaz de enfermería. Contacta con tu supervisor para que corrija el estado desde la API o la gestión avanzada.

---

**P: ¿Puedo ver las tareas de mis compañeros de turno?**

R: Depende de tu rol:
- **ENFERMERIA:** Solo ves tus propias tareas asignadas.
- **MEDICINA y SUPERVISOR:** Ven todas las tareas del sistema.

---

**P: ¿Las tareas desaparecen una vez REALIZADAS?**

R: No. Todas las tareas, independientemente de su estado, quedan registradas en el sistema de forma permanente. Esto sirve como historial de actividad. Solo un supervisor puede eliminarlas manualmente.

---

**P: ¿Qué diferencia hay entre CANCELADA y REALIZADA?**

R: 
- **REALIZADA:** La tarea se completó exitosamente. El procedimiento se llevó a cabo.
- **CANCELADA:** La tarea no se realizará. Puede ser porque el paciente fue dado de alta, el médico la retiró, o surgió algún impedimento. Antes de cancelar, consulta con el médico responsable.

---

**P: Tengo muchas tareas en PENDIENTE pero no son mías, ¿qué hago?**

R: Si como ENFERMERIA ves en tu dashboard tareas que crees que no deberían ser tuyas (por ejemplo, de un paciente que no conoces), consulta con el supervisor. Es posible que se hayan reasignado incorrectamente o que pertenezcan a otra unidad.

---

### 10.3. Sobre el Dashboard en Tiempo Real

**P: ¿Por qué aparece "Desconectado" en rojo?**

R: Significa que la conexión entre tu navegador y el servidor se ha interrumpido momentáneamente. Causas posibles:
1. Tu conexión a la red del hospital se cortó.
2. El servidor MediTrack se reinició brevemente.
3. Tu ordenador entró en modo de suspensión.

**El sistema se reconecta automáticamente** cuando vuelve la conexión. Si el badge sigue en rojo más de 2-3 minutos, recarga la página (Ctrl+F5 o Cmd+R).

---

**P: ¿Puedo tener el Dashboard en Tiempo Real abierto todo el turno?**

R: Sí. Está diseñado para eso. Puedes dejar la pestaña del dashboard abierta en un monitor secundario o en una pantalla grande del control de enfermería, y se mantendrá actualizado automáticamente sin consumir recursos significativos.

---

**P: El "Última actualización" lleva muchos minutos sin cambiar. ¿Es un error?**

R: Si la hora de "última actualización" no cambia en más de 30 segundos, es señal de que hay un problema de conexión. Verifica:
1. El badge de estado (¿está en rojo?).
2. Tu conexión a internet.
3. Recarga la página si el problema persiste.

---

**P: Los números del dashboard no cuadran con lo que yo veo en mis tareas. ¿Por qué?**

R: El dashboard muestra datos de **todas las tareas del sistema**, no solo de tu turno o de tus pacientes. Es normal que los totales sean mayores que lo que tú gestionas individualmente.

---

### 10.4. Sobre los Pacientes

**P: El paciente ya existe en el otro sistema del hospital. ¿Tengo que volver a crearlo?**

R: Actualmente MediTrack no está integrado con otros sistemas hospitalarios (HIS, HCE). Deberás crear el paciente también en MediTrack. En versiones futuras se prevé integración con sistemas hospitalarios externos.

---

**P: ¿Puedo editar los datos de un paciente después de crearlo?**

R: Sí, los usuarios con rol MEDICINA y SUPERVISOR pueden editar los datos de un paciente usando la API REST o los formularios de edición si están disponibles en la interfaz web.

---

**P: ¿Puedo eliminar un paciente que tiene tareas asociadas?**

R: Técnicamente solo el SUPERVISOR puede eliminar pacientes. Sin embargo, si el paciente tiene tareas asociadas, deberías gestionar esas tareas primero (cancelarlas o marcarlas como realizadas) antes de eliminar el paciente.

---

### 10.5. Sobre los Usuarios y Roles

**P: ¿Puede un médico ver el Dashboard en Tiempo Real?**

R: No. El Dashboard en Tiempo Real está disponible exclusivamente para el rol SUPERVISOR. Si un médico necesita ver esta información, puede solicitárselo al supervisor de turno.

---

**P: ¿Puede un enfermero crear pacientes?**

R: No. La creación de pacientes está limitada a los roles MEDICINA y SUPERVISOR.

---

**P: ¿Puede haber varios supervisores en el sistema?**

R: Sí. El sistema no limita el número de usuarios con rol SUPERVISOR. Un hospital puede tener varios supervisores de unidad, cada uno con su propia cuenta.

---

## 11. Solución de Problemas

Esta sección te ayuda a resolver los problemas más comunes que puedes encontrar al usar MediTrack.

### 11.1. No Puedo Iniciar Sesión

**Síntoma:** Introduces usuario y contraseña y aparece *"Usuario o contraseña incorrectos"* o similar.

**Checklist de verificación:**

1. ✅ **¿Escribiste bien el usuario?** Los usernames distinguen mayúsculas y minúsculas. `Enfermera` ≠ `enfermera`. Asegúrate de que está en minúsculas.

2. ✅ **¿Escribiste bien la contraseña?** Verifica que no tienes el Bloq Mayús activado (mira el indicador en tu teclado).

3. ✅ **¿Usas los usuarios de prueba correctos?** Los usuarios son exactamente: `enfermera`, `medico`, `supervisor` (sin tildes, sin espacios).

4. ✅ **¿El sistema está activo?** Si la página de login carga pero las credenciales fallan, el sistema está funcionando. Si la página no carga del todo, ve a la sección 11.2.

5. ✅ **¿Tienes las credenciales correctas?** Si eres un usuario real (no de prueba), tus credenciales las ha creado el supervisor. Solicítale una contraseña nueva si sospechas que puede estar incorrecta.

6. ✅ **Limpia la caché del navegador:** Pulsa Ctrl+Shift+Delete (Windows) o Cmd+Shift+Delete (Mac), selecciona "Cookies y datos de sitios" y "Archivos en caché", y haz clic en "Eliminar".

**Si nada de lo anterior funciona:** Contacta con el administrador del sistema (ver sección 13).

### 11.2. La Página No Carga

**Síntomas:** Pantalla en blanco, "No se puede conectar", error 404, o el navegador tarda indefinidamente.

**Pasos de diagnóstico:**

1. **Verifica la URL:** ¿Estás usando exactamente `http://localhost:8080`? ¿Hay errores tipográficos?

2. **¿Está corriendo la aplicación?** MediTrack necesita que alguien haya iniciado el servidor. Si eres un usuario final, contacta con el departamento de informática o el administrador del sistema para que arranquen el servidor (`mvn spring-boot:run`).

3. **Verifica tu conexión a la red del hospital:** Abre otra web (por ejemplo, la intranet del hospital) para confirmar que tienes conexión a la red. Si no tienes conexión, el problema es de red, no de MediTrack.

4. **Prueba con otro navegador:** Si Chrome no funciona, prueba con Firefox o Edge.

5. **Refresca forzadamente:** Pulsa Ctrl+F5 (Windows) o Cmd+Shift+R (Mac) para recargar sin caché.

**Si nada funciona:** Informa al administrador de sistemas indicando:
- La URL que estás usando
- El mensaje de error que ves (o captura de pantalla)
- Tu sistema operativo y navegador

### 11.3. El Dashboard en Tiempo Real No Se Actualiza

**Síntomas:** El badge de conexión está en rojo ("Desconectado"), o la hora de "Última actualización" lleva mucho tiempo sin cambiar.

**Pasos de resolución:**

1. **Espera 30 segundos:** El sistema intenta reconectarse automáticamente. Es normal que haya interrupciones breves.

2. **Verifica tu conexión a la red:** Si tu red está caída, el tiempo real no puede funcionar.

3. **Recarga la página:** Pulsa F5 o Ctrl+F5. El EventSource (conexión en tiempo real) se reiniciará.

4. **Verifica que el backend está funcionando:** Intenta acceder a otra pantalla de MediTrack (por ejemplo, "Carga de Trabajo"). Si esa página carga bien, el servidor está activo.

5. **Cierra y vuelve a abrir la pestaña:** A veces el navegador necesita reiniciar la conexión SSE desde cero.

6. **Revisa la consola del navegador (avanzado):** Pulsa F12, ve a la pestaña "Console". Si ves mensajes de error en rojo relacionados con "EventSource" o "Failed to fetch", confirma que hay un problema de conexión con el servidor.

### 11.4. Un Formulario No Se Envía

**Síntomas:** Haces clic en "Crear Tarea", "Actualizar" u otro botón de formulario, y no pasa nada, o aparecen campos marcados en rojo.

**Pasos de resolución:**

1. **Lee los mensajes de error:** Los campos con problemas se resaltan en rojo o naranja con un mensaje descriptivo debajo. Lee qué dice el mensaje. Ejemplo: *"Este campo es obligatorio"*, *"Número de historia clínica ya existe"*.

2. **Rellena todos los campos obligatorios:** Busca campos con asterisco (*) o marcados visualmente como obligatorios.

3. **Verifica el formato de las fechas:** El campo fecha/hora debe rellenarse en el formato correcto (usa siempre el selector de fecha, no escribas manualmente).

4. **Verifica que los desplegables tienen una opción seleccionada:** Los selectores de "Paciente", "Enfermero" y "Turno" deben tener una opción seleccionada (no pueden estar en blanco).

5. **Limpia el formulario y vuelve a empezar:** Si el problema persiste, recarga la página (F5) y rellena el formulario de nuevo.

### 11.5. Veo un Error 403 "Acceso Denegado"

**Síntoma:** Aparece un mensaje de error indicando que no tienes permisos para ver esa página.

**Causa:** Estás intentando acceder a una funcionalidad que no corresponde a tu rol. Por ejemplo:
- Un enfermero intentando acceder a `/supervisor/dashboard`.
- Un médico intentando acceder a `/api/usuarios`.

**Solución:** Vuelve a tu área de trabajo correspondiente haciendo clic en el logo **MediTrack** en la barra de navegación. Esto te lleva a tu dashboard. Si crees que deberías tener acceso a esa funcionalidad, consulta con el supervisor para que cambie tu rol.

### 11.6. Los Gráficos No Aparecen

**Síntomas:** En el Dashboard en Tiempo Real, las áreas de gráficos aparecen vacías, grises o con un error.

**Causas y soluciones:**

1. **El navegador no cargó Chart.js:** Los gráficos dependen de una librería externa (Chart.js) que se carga desde internet. Si no hay conexión a internet (solo intranet), los gráficos no cargarán. Verifica si tienes acceso a internet o pide al administrador que configure Chart.js localmente.

2. **JavaScript deshabilitado:** Los gráficos requieren JavaScript. Verifica que JavaScript está habilitado en tu navegador (está habilitado por defecto en todos los navegadores modernos).

3. **Extensión del navegador bloqueando contenido:** Algunas extensiones de privacidad o ad-blockers pueden bloquear la carga de librerías externas. Prueba deshabilitando temporalmente las extensiones.

4. **Recargar la página:** Pulsa Ctrl+F5 para forzar la recarga completa incluyendo los scripts.

---

## 12. Glosario de Términos

Este glosario explica los términos que encontrarás en MediTrack y en este manual, en lenguaje sencillo.

| Término | Definición |
|---|---|
| **Asignación** | Acto de designar una tarea a un enfermero concreto. El enfermero "asignado" es el responsable de realizar esa tarea. |
| **Badge** | Etiqueta de color pequeña que aparece junto a los estados y prioridades. Permite identificar el estado de un vistazo sin leer el texto. |
| **Dashboard** | Panel de control. Pantalla de resumen con la información más importante organizada visualmente. |
| **Estado de Tarea** | La situación actual de una tarea: PENDIENTE (no iniciada), EN_CURSO (en proceso), REALIZADA (completada) o CANCELADA. |
| **Formulario** | Pantalla con campos de texto, desplegables y botones para introducir información en el sistema. |
| **Gráfico de Barras** | Representación visual con barras rectangulares donde la altura de cada barra representa un valor numérico. |
| **Gráfico de Donut** | Gráfico circular con un agujero en el centro. Muestra la distribución proporcional de un total. |
| **Historia Clínica (HC / NHC)** | Número identificador único que tiene cada paciente en el sistema hospitalario. Permite distinguir a pacientes con el mismo nombre. |
| **Login** | Proceso de identificarse en el sistema introduciendo usuario y contraseña para obtener acceso. |
| **Logout / Cerrar Sesión** | Proceso de terminar tu sesión en el sistema de forma segura. |
| **Navbar** | Barra de navegación. La franja de color en la parte superior de la pantalla con el menú y el botón de salir. |
| **Observaciones** | Campo de texto libre donde el personal puede anotar notas, incidencias, resultados o advertencias relacionadas con una tarea. |
| **Prioridad** | Nivel de urgencia de una tarea: URGENTE, ALTA, MEDIA o BAJA. Determina el orden en que deben atenderse las tareas. |
| **Reasignación** | Cambio del enfermero responsable de una tarea. Solo los supervisores pueden reasignar tareas. |
| **Responsive** | Diseño que se adapta automáticamente al tamaño de la pantalla (ordenador, tablet, móvil). |
| **Rol** | Nivel de acceso y permisos de un usuario. MediTrack tiene tres roles: ENFERMERIA, MEDICINA y SUPERVISOR. |
| **SSE (Server-Sent Events)** | Tecnología que permite al servidor enviar actualizaciones automáticas al navegador sin que el usuario tenga que recargar la página. Es la base del Dashboard en Tiempo Real. |
| **Tarea Clínica** | Procedimiento médico o de enfermería que debe realizarse con un paciente: administrar medicación, controlar constantes, realizar curas, etc. |
| **Tiempo Real** | Información que se actualiza automáticamente a medida que ocurren cambios, sin necesidad de recargar la página. |
| **Turno** | Período de trabajo del personal sanitario: Mañana (07:00—15:00), Tarde (15:00—23:00), Noche (23:00—07:00). |
| **Username** | Nombre de usuario único utilizado para identificarse al hacer login. Es diferente del nombre real del profesional. |

---

## 13. Contacto y Soporte

### Para Problemas Técnicos

Si el sistema no funciona correctamente (no carga, da errores, datos incorrectos), contacta con el **departamento de informática** o el **administrador del sistema MediTrack**.

Cuando contactes, ten preparada la siguiente información para acelerar la resolución:

1. **Qué estabas haciendo** cuando ocurrió el problema (paso a paso)
2. **Qué mensaje de error** aparece (si lo hay)
3. **Captura de pantalla** del error si es posible (tecla PrintScreen o Ctrl+Shift+S)
4. **Tu usuario y rol** en el sistema
5. **Tu navegador** (Chrome, Firefox, etc.) y versión
6. **La hora** aproximada en que ocurrió el problema

### Para Dudas sobre Cómo Usar el Sistema

1. **Consulta este manual** en primer lugar. Usa el índice para encontrar tu sección.
2. **Pregunta a tu supervisor de unidad**. Los supervisores tienen formación completa en el sistema.
3. **Formación interna**: Si tu hospital organiza sesiones de formación en MediTrack, asiste a ellas. La práctica directa es la mejor forma de aprender.

### Para Reportar Errores o Sugerencias de Mejora

Si encuentras algo que no funciona como esperabas, o tienes ideas para mejorar el sistema, comunícalo a través de los canales internos de tu hospital. Tu feedback ayuda a mejorar el sistema para todos.

Cuando reportes un error:
- Describe qué esperabas que pasara
- Describe qué pasó realmente
- Adjunta capturas de pantalla si es posible

---

## 14. Apéndices

### Apéndice A: Resumen de Permisos por Rol

Esta tabla resume de forma rápida qué puede hacer cada rol en MediTrack:

| Función | 👩‍⚕️ ENFERMERIA | 🩺 MEDICINA | 📊 SUPERVISOR |
|---|---|---|---|
| Ver mis tareas | ✅ | — | — |
| Ver todas las tareas | — | ✅ | ✅ |
| Actualizar estado de mis tareas | ✅ | ✅ | ✅ |
| Añadir observaciones a tareas | ✅ | ✅ | ✅ |
| Crear tareas clínicas | ❌ | ✅ | ✅ |
| Reasignar tareas | ❌ | ❌ | ✅ |
| Eliminar tareas | ❌ | ❌ | ✅ |
| Ver lista de pacientes | ✅ | ✅ | ✅ |
| Crear pacientes | ❌ | ✅ | ✅ |
| Editar pacientes | ❌ | ✅ | ✅ |
| Eliminar pacientes | ❌ | ❌ | ✅ |
| Ver lista de usuarios | ❌ | ❌ | ✅ |
| Crear usuarios | ❌ | ❌ | ✅ |
| Editar usuarios | ❌ | ❌ | ✅ |
| Eliminar usuarios | ❌ | ❌ | ✅ |
| Dashboard en tiempo real | ❌ | ❌ | ✅ |
| Ver carga de trabajo por turno | ❌ | ❌ | ✅ |

---

### Apéndice B: Códigos de Color y Significado

Referencia rápida de todos los colores que encontrarás en MediTrack:

**Estados de Tarea:**

| Color | Estado | Situación |
|---|---|---|
| 🟡 Amarillo | PENDIENTE | La tarea aún no ha empezado |
| 🔵 Azul | EN CURSO | La tarea está siendo realizada ahora mismo |
| 🟢 Verde | REALIZADA | La tarea se completó con éxito |
| ⚫ Gris oscuro | CANCELADA | La tarea no se realizará |

**Prioridades de Tarea:**

| Color | Prioridad | Urgencia |
|---|---|---|
| 🔴 Rojo intenso | URGENTE | Actuar de inmediato |
| 🟠 Naranja | ALTA | Actuar en las próximas horas |
| 🟡 Gris medio | MEDIA | Dentro del turno normal |
| ⚪ Gris claro | BAJA | Sin urgencia |

**Conexión del Dashboard en Tiempo Real:**

| Color | Texto | Significado |
|---|---|---|
| 🟢 Verde | Conectado | Recibiendo actualizaciones correctamente |
| 🔴 Rojo | Desconectado | Conexión interrumpida; reconectando automáticamente |
| 🔘 Gris | Conectando... | Estableciendo conexión por primera vez |

**Barras de Navegación por Rol:**

| Color | Rol |
|---|---|
| 🔵 Azul | ENFERMERIA |
| 🟢 Verde | MEDICINA |
| 🟣 Morado | SUPERVISOR |

---

### Apéndice C: Ciclo de Vida de una Tarea

Una tarea clínica pasa por los siguientes estados desde que se crea hasta que se completa:

```
CREACIÓN
    │
    ▼
┌─────────┐
│PENDIENTE│ ◄── Estado inicial al crear la tarea
└────┬────┘
     │  El enfermero empieza a trabajar en ella
     ▼
┌─────────┐
│ EN_CURSO│ ◄── La tarea está siendo ejecutada ahora mismo
└────┬────┘
     │  El enfermero completa el procedimiento
     ▼
┌─────────┐
│REALIZADA│ ◄── El procedimiento se realizó con éxito ✅
└─────────┘

En cualquier momento: CANCELADA ❌
(si la tarea ya no es necesaria o no puede realizarse)
```

> ⚠️ **Nota:** Una vez que una tarea está en estado REALIZADA o CANCELADA, normalmente no debería volver a estados anteriores. Si necesitas corregir un error, contacta con tu supervisor.

---

### Apéndice D: Tipos de Tarea Disponibles

Referencia completa de los tipos de tarea que puedes asignar al crear una tarea clínica:

| Tipo | Descripción | Ejemplos prácticos |
|---|---|---|
| **Medicación** | Administración de cualquier tipo de medicamento | Inyección de insulina, suero intravenoso, pastillas, pomada tópica |
| **Control Signos Vitales** | Medición de parámetros fisiológicos del paciente | Tensión arterial, frecuencia cardíaca, temperatura, saturación de oxígeno, glucemia |
| **Cura** | Procedimientos de curación y cicatrización | Cura de herida quirúrgica, cambio de apósito, cura de úlcera por presión |
| **Higiene** | Aseo y limpieza del paciente | Ducha asistida, aseo en cama, higiene bucal, cambio de ropa |
| **Movilización** | Movimiento y cambios posturales | Cambio postural cada 2 horas, sedestación al sillón, ejercicios de fisioterapia básica |
| **Otro** | Cualquier procedimiento no incluido en los anteriores | Canalización de vía, extracción de analítica, preparación para prueba diagnóstica |

---

## 15. Registro de Cambios del Manual

| Versión | Fecha | Cambios realizados | Autor |
|---|---|---|---|
| 1.0 | Abril 2026 | Versión inicial completa del manual | Pedro Vela (TFG) |

---

> **¿Encontraste algo confuso o incorrecto en este manual?**
>
> Reporta los errores o sugerencias a través de los canales de soporte de tu hospital (sección 13). Tu ayuda mejora la documentación para todo el equipo.

---

*Manual de Usuario MediTrack — Versión 1.0*
*Sistema de Gestión de Tareas Clínicas Hospitalarias*
*Desarrollado como Trabajo Fin de Grado — Pedro Vela — 2026*

---

**FIN DEL MANUAL**
