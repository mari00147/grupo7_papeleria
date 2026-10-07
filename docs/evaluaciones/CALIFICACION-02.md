# Retroalimentación — Lista Simple (Momento 2)

**Grupo:** Grupo7 · **Proyecto:** Papelería

## Nota

| Criterio | Peso | Nota (0-5) |
|---|---|---|
| Identificación de las relaciones uno-a-muchos | 20 % | 3.0 |
| `ListaSimple<T>` integrada al `Service` | 30 % | 1.5 |
| Menú en consola funcional | 15 % | 0.0 |
| Reemplazo del arreglo previo, sin código muerto | 20 % | 3.0 |
| Buenas prácticas (commits y nombres) | 15 % | 3.5 |
| **Nota del laboratorio** | | **2.18** |

La nota se calcula así: 20% relaciones + 30% integración de la lista + 15% menú + 20% reemplazo sin código muerto + 15% buenas prácticas.

## 1. Relaciones uno-a-muchos (3.0)
**Lo que hicieron bien:**
- `Venta` → `ItemVenta` es una relación uno-a-muchos real y bien elegida.
- También identificaron `Proveedor` → `Pedido` en el programa de prueba.

**Lo que pueden mejorar:**
- La segunda relación solo existe "a medias": `Pedido` conoce a su `Proveedor`, pero `Proveedor` no guarda sus pedidos. Falta el lado "uno" con su lista.

## 2. `ListaSimple<T>` integrada al `Service` (1.5)
**Lo que hicieron bien:**
- `ListaSimple<T>` y `Nodo<T>` están bien armadas, en `model/structures`, con insertar, buscar, eliminar y listar.
- `Venta` guarda sus ítems en un atributo `ListaSimple<ItemVenta>` y agrega con `insertarFinal`.

**Lo que pueden mejorar:**
- No hay ninguna clase `Service`: nadie consulta ni elimina ítems usando la lista, solo se agregan desde la prueba.
- `Proveedor` no tiene un atributo `ListaSimple<Pedido>`.
- `ListaSimple` tiene dos métodos que hacen lo mismo (`getTamano()` y `size()`).

## 3. Menú en consola (0.0)
- `MenuListasView` existe, pero está vacía: no permite agregar, buscar, listar ni eliminar nada.

## 4. Reemplazo del arreglo previo (3.0)
**Lo que hicieron bien:**
- No quedaron arreglos ni `ArrayList` sin usar en el proyecto.

**Lo que pueden mejorar:**
- Solo una de las dos relaciones usa `ListaSimple<T>`, así que el reemplazo está incompleto.

## 5. Buenas prácticas (3.5)
**Lo que hicieron bien:**
- Commits frecuentes con mensajes claros y trabajo de varios integrantes.
- Nombres de clases, métodos y variables siguen las convenciones de Java.

**Lo que pueden mejorar:**
- Desde el 26 de septiembre casi todo se subió directo a `main`; usen ramas y únanlas con merge.
- No siguieron la estructura de carpetas acordada en clase: falta la carpeta `service/` con sus clases.

## ¿El programa funciona?
Compila sin errores. Al ejecutar `App` el programa se detiene con un error en la parte de validaciones, porque intenta vender más cuadernos de los que hay. La prueba `PruebaCreacionObjetos` sí termina bien.

## Para el próximo laboratorio
- Agreguen `ListaSimple<Pedido>` a `Proveedor` y úsenla para guardar sus pedidos.
- Creen la carpeta `service/` con un `Service` para cada relación (agregar, buscar y eliminar con los métodos de `ListaSimple`).
- Construyan el menú en `MenuListasView` llamando solo al `Service`.
- Trabajen en ramas y únanlas a `main` con merge.
