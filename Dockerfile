# Dockerfile
# Este Dockerfile es para una aplicación Spring Boot con un archivo JAR ya compilado.

# Usa una imagen base ligera con JRE 21.
FROM eclipse-temurin:21-jre

# Establece el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor.
# El archivo .jar debe estar en la carpeta 'microservicios/ms-seguridad-apis'.
COPY ./microservicios/bff/*.jar ./app.jar

# --- Sección para agregar el cacerts ---
# Copia el certificado 'server.crt' de tu máquina local al contenedor.
# Asume que el archivo 'server.crt' se encuentra en la misma carpeta que el Dockerfile.
COPY ./microservicios/ssl/server.crt /tmp/server.crt

# Importa el certificado al almacén de confianza de Java.
# NOTA: 'changeit' es la contraseña por defecto del almacén de certificados de Java.
RUN keytool -import -alias dacs2025.local -trustcacerts -file /tmp/server.crt -keystore /opt/java/openjdk/lib/security/cacerts -storepass changeit -noprompt

# Limpia el archivo temporal
RUN rm /tmp/server.crt

# Expone el puerto por defecto de Spring Boot.
EXPOSE 9001