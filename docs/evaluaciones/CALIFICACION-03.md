# Retroalimentación — Pilas y Colas en el proyecto de aula

**Grupo:** Grupo7 · **Proyecto:** Papelería

Muy buen trabajo con las dos estructuras: funcionan bien y están bien separadas por capas.

## Nota

| Criterio | Pilas | Colas |
|---|---|---|
| `Pila<T>` / `Cola<T>` correcta | 29 / 30 | 29 / 30 |
| Casos límite | 10 / 10 | 10 / 10 |
| Entidad dueña de la cola (`PedidosPendientes`) | - | 15 / 15 |
| Integración en la entidad y el `Service` | 23 / 30 | 20 / 25 |
| Menú en `view/` | 6 / 15 | 10 / 10 |
| Buenas prácticas | 13 / 15 | 8 / 10 |
| **Total** | **81 / 100** | **92 / 100** |

**Nota final del laboratorio: 4.33**

La nota se calcula así: (total de Pilas + total de Colas) / 2, dividido entre 20.

## 1. Pilas
**Lo que hicieron bien:**
- `Pila<T>` usa `Nodo<T>`, no usa nada de `java.util` y enlaza el nodo nuevo antes de mover el tope.
- `pop` y `peek` con la pila vacía lanzan error con mensaje, y `push(null)` se rechaza sin dañar la pila.
- La pila vive en `VentaService` (correcto para la Papelería). Deshacer devuelve el stock y no vuelve a apilar.
- El listado con pila auxiliar deja la pila igual que estaba.

**Lo que pueden mejorar:**
- Al listar, salen textos como `model.domain.Venta@2626b418`. Falta un `toString()` en `Venta` para que se lea la venta.
- `MenuPilaView` usa `Scanner` directamente y no `ConsoleUtils`, y usa `try/catch`.
- El menú no pregunta `sinVentasPorDeshacer` antes de deshacer o consultar: se apoya en el `try/catch`. Debe preguntar primero.

## 2. Colas
**Lo que hicieron bien:**
- `Cola<T>` funciona: al sacar al último deja `front` y `back` en `null`, y se puede volver a encolar después de vaciarla.
- `PedidosPendientes` coincide con el diagrama: descripción, fila privada creada en el constructor y sin `setFila`.
- El `Service` tiene los cinco métodos más `listarPedidosPendientes`; procesar saca el pedido de la fila sin quitarlo de la lista de pedidos.
- La rotación para listar deja la fila igual. `MenuColaView` usa `ConsoleUtils`, sin `try/catch`, y revisa si hay pedidos antes de procesar.

**Lo que pueden mejorar:**
- Al listar pedidos también salen textos como `model.domain.Pedido@24d46ca6`: agreguen `toString()` a `Pedido`.
- Los atributos de la cola se llaman `front`, `back` y `size`; en el laboratorio eran `frente`, `fin` y `tamano`.

## 3. Buenas prácticas
- Hay muchos commits con mensajes claros y trabajo de varias personas; la rama `feature/pila-en-el-proyecto` quedó fusionada a `main`.
- La rama `feature/cola-en-el-proyecto` llegó a `main` con commits sueltos y arreglos de conflictos, no con una fusión limpia de la rama.

## Anotación sobre la ejecución
Compila bien y las pilas y colas responden correctamente en nuestras pruebas. Pero `App` se cae en la parte 5 de la demostración (intenta capturar un error de tipo distinto al que lanza el código), así que nunca se llega a los menús de pilas y colas.
