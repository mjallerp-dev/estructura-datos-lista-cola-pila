# Aeropuerto - Control de embarque con Lista, Cola y Pila

**Actividad:** Estructuras de datos - Actividad de aprendizaje: *Sistema de gestión con Lista, Cola y Pila*.
Caso asignado: **10. Aeropuerto** - entidad `PasajeroEmbarque`, identificador: **código de reserva**.

## Descripción

Aplicación de consola que controla el embarque de pasajeros en un aeropuerto usando tres estructuras de datos
**implementadas a mano con nodos doblemente enlazados** (no se usa `java.util` para ellas):

| Estructura | Clase | Uso en el aeropuerto |
|------------|-------|----------------------|
| Lista | `Lista` | Registro completo de todos los pasajeros (pendientes, embarcados y cancelados) |
| Cola (FIFO) | `Cola` | Pasajeros pendientes de embarque, atendidos en orden de llegada |
| Pila (LIFO) | `Pila` | Historial de pasajeros embarcados; permite deshacer el último embarque |

Reglas implementadas:

- **Registrar:** `listaGeneral.agregar(pasajero)` y `colaPendientes.encolar(pasajero)`.
- **Embarcar:** `colaPendientes.desencolar()` y `historialProcesados.apilar(pasajero)`.
- **Deshacer:** `historialProcesados.desapilar()` y `colaPendientes.encolar(pasajero)`.
- **Cancelar:** se recorre la cola pasando los pasajeros a una **cola auxiliar** y se omite el cancelado; no se
  elimina desde el centro de la cola.
- **Buscar:** por código de reserva en la lista general (`equals` compara el código de reserva).

Validaciones: código de reserva de 6 letras o números y único, documento de 5 a 12 dígitos, vuelo con formato
`AV9301`, asiento con formato `12C` y no repetido en el mismo vuelo, campos obligatorios, y errores controlados al
embarcar con la cola vacía, deshacer sin historial o cancelar pasajeros que no están pendientes.

## Menú

```
1. Registrar pasajero
2. Ver todos los pasajeros registrados
3. Ver pasajeros pendientes de embarque
4. Embarcar siguiente pasajero
5. Ver historial de pasajeros embarcados
6. Buscar pasajero por código de reserva
7. Cancelar pasajero pendiente
8. Deshacer último embarque
9. Ver cantidad de pasajeros
10. Salir
```

Al iniciar, el programa pregunta si se desean cargar 8 pasajeros de ejemplo.

## Estructura del proyecto

```
src/main/java/com/mjallerp
├── Main.java                         Punto de entrada
├── estructuras                       Nodo, Lista, Cola, Pila
├── modelo                            PasajeroEmbarque (clase del caso)
├── sistema                           SistemaAeropuerto (sistema del caso)
├── consola                           MenuAeropuerto, LectorConsola
├── datos                             DatosEjemplo
└── pruebas                           PruebaLista, PruebaCola, PruebaPila, PruebasAeropuerto
```

## Requisitos y ejecución

Java 17 o superior. Desde IntelliJ IDEA ejecutar `com.mjallerp.Main`. Las clases del paquete `pruebas` también
tienen su propio `main`.

```bash
mvn compile
java -cp target/classes com.mjallerp.Main
```

Sin Maven:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.mjallerp.Main
```

## Documentación

El informe en PDF y la guía de sustentación están en la carpeta [`docs`](docs).
