# Retroalimentación — Laboratorio 1: Codificación del diseño OO

**Grupo:** Grupo7 · **Proyecto:** Papelería
**Fecha límite:** 2026-09-08 23:59 · **Versión revisada:** commit `d111426`

## Nota

| Criterio | Peso | Nota (0-5) |
|---|---|---|
| El código sigue el diagrama UML | 60% | 3.5 |
| Pruebas: creación de objetos en el programa | 20% | 3.0 |
| Buenas prácticas de programación | 20% | 4.5 |
| **Nota del laboratorio** | | **3.60** |

La nota se calcula así: 60% diseño UML + 20% pruebas + 20% buenas prácticas.

## 1. El código sigue el diagrama UML (3.5)
**Lo que hicieron bien:**
- Todas las clases están en el paquete `model.domain`, como se pidió.
- `Producto` es abstracta, protege sus atributos con getters y setters, y deja `calcularPrecioFinal()` para que lo resuelvan las clases hijas.
- `ProductoOficina` y `ProductoEscolar` heredan de `Producto` y calculan bien el precio: 8% de recargo en oficina y 10% de descuento solo para "primaria" en escolar.
- `Venta` e `ItemVenta` están bien relacionadas, y cada `ItemVenta` apunta a su `Producto` en vez de copiar sus datos.
- `Proveedor` y `Pedido` están bien protegidas, y cada `Pedido` conoce a su `Proveedor`.

**Lo que pueden mejorar:**
- `Producto` no dice `implements Vendible`, así que la interfaz `Vendible` quedó sin usar.
- El constructor de `Producto` no revisa los datos: acepta precios en cero o negativos y stock negativo. Debe exigir precio mayor que 0 y stock mayor o igual a 0.
- Los constructores de `Venta`, `ItemVenta`, `Proveedor` y `Pedido` tampoco revisan los datos que reciben, y eso también se pedía.
- En `App.java` crean el `Proveedor` con los datos en otro orden del que espera el constructor. Por eso el nombre queda guardado como categoría y el teléfono como nombre (en pantalla sale el teléfono donde debería salir el nombre).

## 2. Pruebas: creación de objetos (3.0)
**Lo que hicieron bien:**
- `PruebaCreacionObjetos` compila, corre sin errores y está fuera de `model/domain`, como debe ser.
- Crean un `ProductoOficina` y un `ProductoEscolar`, llaman `calcularPrecioFinal()` en cada uno y se ve un resultado distinto para cada tipo, sin usar `instanceof`.

**Lo que pueden mejorar:**
- Faltó meter esos productos dentro de una `Venta` (con sus `ItemVenta`) en la clase de prueba. Eso sí lo hicieron en `App.java`, pero lo que se revisa en este punto es `PruebaCreacionObjetos`.

## 3. Buenas prácticas (4.5)
**Lo que hicieron bien:**
- Hicieron commits seguidos durante varios días, casi todos con mensajes que explican el cambio.
- Los cuatro integrantes tienen commits propios, cada uno trabajó en su rama y luego unieron todo en `main`. Muy bien.
- Los nombres siguen las costumbres de Java: clases como `ProductoOficina` y métodos o variables como `calcularPrecioFinal` y `cantidadStock`.

**Lo que pueden mejorar:**
- Algunos mensajes de commit dicen poco, por ejemplo ":wip: Avance proyecto Laura". Mejor decir qué cambió.

## ¿El programa funciona?
Sí. El proyecto compila y `PruebaCreacionObjetos` muestra bien los dos precios finales. Su demo `App.java` se detiene con un error en el caso de stock insuficiente, pero eso no afectó la nota de pruebas.

## Para el próximo laboratorio
- Agregar `implements Vendible` a `Producto`.
- Revisar los datos en los constructores de `Producto`, `Venta`, `ItemVenta`, `Proveedor` y `Pedido`, y lanzar un error cuando vengan mal.
- Pasar los datos en el orden correcto al crear un `Proveedor`.
- En la clase de prueba, meter los productos creados dentro de una `Venta` con sus `ItemVenta`.
