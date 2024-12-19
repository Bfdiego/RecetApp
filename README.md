# RecetApp

RecetApp es una aplicación móvil desarrollada en Kotlin para gestionar tus recetas favoritas y creaciones culinarias. Este archivo proporciona las instrucciones necesarias para configurar y ejecutar la aplicación en tu entorno local.

## Requisitos previos

Asegúrate de tener instalados los siguientes programas antes de comenzar:

- [Git](https://git-scm.com/) para clonar el repositorio.
- [Android Studio](https://developer.android.com/studio) para compilar y ejecutar la aplicación.
- El archivo `google-services.json` proporcionado, que debe colocarse en la carpeta adecuada.

## Instalación y configuración

Sigue los pasos a continuación para clonar y configurar el proyecto:

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Bfdiego/RecetApp.git
   ```

2. Navega al directorio del proyecto:
   ```bash
   cd RecetApp
   ```

3. Abre el proyecto en **Android Studio**:
   - Inicia Android Studio.
   - Selecciona "Open" y busca la carpeta donde clonaste el repositorio.

4. Verifica la versión de Gradle:
   - Asegúrate de que la versión de Gradle utilizada sea **7.5** o superior.
   - Comprueba que el plugin de Gradle para Android esté configurado en la versión **3.5** en el archivo `build.gradle` del proyecto.

5. Coloca el archivo `google-services.json`:
   - Asegúrate de tener el archivo `google-services.json` proporcionado por Firebase.
   - Copia este archivo en la carpeta `app` del proyecto.

## Ejecución

1. Sincroniza el proyecto con Gradle:
   - En Android Studio, haz clic en "Sync Now" cuando aparezca la notificación o ve al menú "File" > "Sync Project with Gradle Files".

2. Compila y ejecuta la aplicación:
   - Conecta un dispositivo Android físico o inicia un emulador.
   - Haz clic en el botón **Run** (icono de triángulo verde) en Android Studio.

3. La aplicación debería instalarse y ejecutarse automáticamente en el dispositivo/emulador seleccionado.

## Notas adicionales

- Si encuentras problemas con la sincronización de Gradle, verifica que tu entorno de Android Studio esté correctamente configurado y que tienes acceso a internet para descargar las dependencias necesarias.

