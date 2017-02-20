<center>
<img src="https://k61.kn3.net/F/3/6/1/6/5/336.jpg">
<br>|
<a href="https://www.facebook.com/solomochila/"> Facebook </a>|
<a href="http://www.solomochila.com.ar/"> Pagina Web </a>|
</center>
<br>
**Aplicación para eventos de escalada modalidad Boulder. [Video](https://www.youtube.com/watch?v=xSSL_0DKkRI)**

Pagina web para manejar los eventos de escalada. Cómo todo evento tiene las cosas básicas:

* Inscripción de los usuarios
* Acreditación el día de la fecha
* Clasificación
* Resultados preliminares
* Finales

Las características especiales que tiene es la siguiente:
Los participantes acreditan su asistencia al evento. Una vez que se cierra la inscripción, comienza la clasificación, se le entrega a cada participante un papelito con una grilla numerada, con nombre, apellido y categoría:

![GitHub Logo](http://image.slidesharecdn.com/cuadricula-numeros-120407134141-phpapp02/95/cuadricula-numeros-1-728.jpg)

La clasificación consta de 2 o 3 horas (puede variar), donde todos los participantes al mismo tiempo intentan realizar una cantidad determinada de problemas (entre 30 y 40). Cada participante anota en el papel cuales boulders (paredes) pudo resolver y al terminar la clasificación lo entrega.

El tema está ahí, cuando todos devuelven los papeles, **hay que contarlos uno por uno y contabilizar cuales y cuantos problemas resolvió** , cada problema tiene un puntaje y de ahí salen los finalista (los primeros 5 de cada categoría)

La ventaja de hacer una APP sería que cada competidor la baje y tenga una grilla de botones numerados, donde cada escalador pueda seleccionar su resultado y que la contabilización sea en línea.
Además de agregar todos los ABM de administración para el evento.


# Tecnologia

* JDK: **1.7**
* Servidor de apliaciones: **Tomcat 7**
* Base de datos: **MySql 5.7** , Driver: [5.x](https://dev.mysql.com/doc/connector-j/6.0/en/connector-j-versions.html)
* Gestión de dependencias: **Maven**
* Framework de persitencia: **EclipseLink 2.5**
* Implementación REST en java: **Jersey**
* Implementación JSON en java: **MOXy**
* Build: **Jenkins** webhook con GitHub sobre en branch production
* Hosting: **OpenShift**
* IDE: **Netbeans 8.1**


# WorkFlow

Se eleimino la branch master. Docs esta como principal.  
Creo una nueva branch desde development por cada feature.
1. (on branch development) $ git merge production

2. (resolve any merge conflicts if there are any)

3. git checkout production

4. git merge development (there won't be any conflicts now)

PD: Para descargar la branch production.
`git checkout -b production origin/production`



# User Stories

Estan ordenadas segun prioridad, pero pueden cambiar en cualquier momento.
(Prodcut Backlog). Hablar con el Product Owner antes de desarrollar.

1. Yo como Escalador, quiero marcar los boulder (Pared con piedras) que termine, para ganar la competencia. <br>
Nota: Grilla de 3 por X para que quden botones grandes y comodos

2. Yo como Organizador, quiero tabla de posiciones por categoria, para saber quienes pasan a competir en la final.<br>
Nota: Califican los 5 primeros

3. Yo como Organizador, quiero iniciar la clasificacion, para que los Escaladores puedan empezar a completar los ejercicios. <br>
Nota: Hora inicio, Hora Fin, Pausar, Reanudar, Terminar Ahora, Extender.

4. Yo como Organizador, quiero hacer el checkin de los inscriptos para asegurarme que vinieron y cobrar el evento.

5. Yo como Escalador, quiero inscribirme en el evento, para luego ir a participar. <br>
Nota: Nombre, Apellido, Mail, Telefono, Categoria (Mujer, Hombre, preguntar que mas..)

6. Yo como Organizador, quiero crear un evento, para que los escaladores puedan inscribirse. <br>
Nota: Lugar, Fecha y Hora de inicio, Nombre, Costo, Modalidad (Festival o Libre), Cantidad de boulder y puntaje de cada uno.
