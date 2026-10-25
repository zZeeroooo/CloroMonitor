# CloroMonitor

Aplicación móvil Android para el monitoreo de cloro libre en el agua de granjas avícolas y ganaderas. Permite consultar el estado de cada punto de medición, registrar controles, recibir alertas y conservar los datos de forma local. Desarrollada para la asignatura DSY1105 Desarrollo de Aplicaciones Móviles (Duoc UC), Evaluación Parcial 2.

## Integrantes
- ANTONELLA PIA ACEVEDO CORRALES
- ARTURO EDUARDO EXEQUIEL AYALA HERNANDEZ


## Contexto
Caso "Medición de cloro en línea": los operarios y supervisores necesitan consultar desde terreno los niveles de cloro, identificar valores fuera de rango y registrar acciones correctivas. Se usan solo datos ficticios.

## Funcionalidades implementadas
- Navegación con barra inferior, menú lateral y transiciones animadas entre pantallas.
- Resumen de puntos por estado (normal, advertencia, crítico) y lista de puntos con colores según estado.
- Pantalla de alertas con los puntos fuera de rango.
- Formulario de registro de control con validaciones por campo, íconos y mensajes de error.
- Campo de observación que aparece con animación al marcar acción correctiva.
- Persistencia local con Room: los controles registrados se conservan al cerrar la app.
- Historial de mediciones: lista de todos los controles registrados, de la más reciente a la más antigua, con color según estado.
- Segundo formulario "Agregar punto de medición": granja, nombre del punto y nivel inicial, con validaciones por campo, íconos de error y detección de puntos duplicados.
- Resumen del control registrado con la ubicación GPS obtenida.
- Recurso nativo 1: notificaciones locales cuando un control queda en advertencia o crítico (permiso POST_NOTIFICATIONS).
- Recurso nativo 2: ubicación GPS del control (permisos ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION).
- Diseño adaptable a pantallas pequeñas y grandes.

## Arquitectura
Patrón MVVM con Jetpack Compose y Material 3.

- `data/model`: modelos (PuntoMedicion, estados de formulario)
- `data/local`: base de datos Room (DAO y AppDatabase)
- `data/repository`: repositorio de puntos
- `ui/screen`: pantallas Compose
- `ui/viewmodel`: ViewModels con la lógica y las validaciones
- `ui/navigation`: rutas y navegación
- `util`: ayudantes de notificaciones y ubicación
- `src/test`: pruebas unitarias de las validaciones del formulario (`ControlViewModelTest`)

## Cómo ejecutar
1. Instalar la última versión estable de Android Studio.
2. Clonar el repositorio: `git clone https://github.com/zZeeroooo/CloroMonitor.git`
3. Abrir la carpeta en Android Studio y esperar el Gradle Sync (requiere internet).
4. Ejecutar en un emulador o celular Android (API 24 o superior).
5. Aceptar los permisos de notificaciones y ubicación cuando la app los solicite.

Nota: el archivo `gradle.properties` incluye `android.disallowKotlinSourceSets=false`, necesario para que Room funcione con KSP.

## Pruebas
Las validaciones del formulario de control se prueban sin interfaz con pruebas unitarias. Para ejecutarlas: clic derecho sobre `ControlViewModelTest` > Run.
