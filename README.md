# eMovie App

Aplicación Android usando la arquitectura **MVVM** con librerías como Retrofit para las llamadas a la API Rest, Room para el caché de la base de datos local y Glide para la carga de imágenes.


## Preguntas

### ¿En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito?

Es un principio de diseño de desarrollo de software, este establece que cada módulo, componente o clase de un sistema debe tener una única responsabilidad o función. Por lo que se basa en la idea de que un componente debe ser lo más simple posible y debe tener una sola razón funcional.

El propósito que tiene es hacer que un proyecto de software sea más sencillo de entender y de mantener. Esto se logra al asegurar que cada módulo o componente tenga una sola responsabilidad, es más fácil entender cómo funciona el sistema y es más fácil localizar y solucionar problemas en caso de que surjan. Además, es más fácil probar y depurar el sistema.

### ¿Qué características tiene, según su opinión, un “buen” código o código limpio?
Hay varias cosas que en mi opinión determinan que se tenga un buen código:

1.  **Legibilidad:** El código puede considerarse como bueno y limpio si es fácil de leer y entender para cualquier programador, fuera de nosotros mismos, que lo vea. Se puede lograr teniendo un formato consistente,  teniendo nombres de variables claros, nombres de funciones en forma de acciones y que sean descriptivos, así como comentarios adecuados para explicar el propósito del código.
    
2.  **Mantenibilidad:** Debe ser fácil de modificar y mantener a lo largo del tiempo. Esto se logra cumpliendo la parte de legibilidad, así como de tener una estructura clara y modular, y seguir principios de diseño **SOLID**, como por ejemplo el principio de responsabilidad única.
    
3.  **Ser reutilizable:** Debe ser fácil de reutilizar en diferentes partes tanto del mismo proyecto como en otros proyectos. Se logra con tener módulos o componentes con una sola responsabilidad y una interfaz clara y sencilla.
    
4.  **Escalabilidad:** Un proyecto de software con buen código debe ser fácil de escalar y adaptar a nuevas necesidades o cambios en el sistema. Esto incluye tener una arquitectura bien diseñada y modular, y evitar acoplamientos fuertes entre diferentes partes del sistema.
    
5.  **Eficiencia:** Un proyecto debe ser eficiente en términos del uso de recursos y el tiempo de ejecución. Por lo que se deben utilizar tanto algoritmos como estructuras de datos adecuadas, así como las estrategias necesarias para optimizar el uso de memoria y CPU.

### Detalla cómo harías todo aquello que no hayas llegado a completar.

Para las pruebas unitarias utilizaría lo siguiente:

- **Junit:** la librería para creación de pruebas unitarias básica, usada en la mayoría de proyectos Android ya sea con Java o con Kotlin. Usándolo para probar que cada uno de mis servicios y métodos funcionen de la manera correcta bajo cada escenario que se pensó, ya sea para casos de uso exitosos, como flujos de error.
- **Mockito:** Es la librería que se utiliza para crear objetos simulados o "mocks" de clases y interfaces en el código de prueba. Esta la utilizaría en conjunto con las pruebas Judit definidas. En este sentido, podría hacer mocks para simular los datos recibidos del API de películas en caso de no tener acceso a este, o para realizar las pruebas de manera más rápida y sencilla. Es muy útil para cuando el desarrollo móvil se ha adelantado al desarrollo del backend y se necesitan hacer pruebas cuando aún no están los servicios disponibles.
- **Espresso:** Al igual que los anteriores, es una librería de pruebas unitarias pero centrada en las pruebas con la interfaz de usuario.Podría escribir pruebas que simulen la interacción del usuario con la aplicación, como al tocar el botón de ver trailer, o al seleccionar una película del listado que se muestra en el home.