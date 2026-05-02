# RootBridge Sample App

## Propósito
Esta aplicación es un "target controlado" diseñado específicamente para demostrar y probar herramientas de escaneo y manipulación de memoria en Android, como RootBridge. 

La aplicación mantiene valores numéricos simples en memoria sin ofuscación ni cifrado, lo que la hace ideal para pruebas de concepto, aprendizaje y desarrollo de utilidades de escaneo de memoria.

## Instrucciones de Uso
1. Instala y abre la aplicación en un dispositivo físico o emulador (requiere Android 7.0 / API 24 o superior).
2. Verás en pantalla el valor actual de **Coins** (inicialmente 500).
3. Utiliza los botones `+1 Coins`, `+10 Coins` o `Reset` para modificar el valor y asegurarte de que cambia en la interfaz.

## Caso de Uso con RootBridge
Para probar un escáner de memoria (ej. RootBridge):
1. **Abrir la app**: Inicia RootBridge-SampleApp. El valor inicial será 500.
2. **Escanear memoria**: Usa tu herramienta para escanear el valor `500` (como un entero de 4 bytes / Int32).
3. **Cambiar valor**: Toca el botón `+10 Coins` en la app para que el valor sea `510`.
4. **Filtrar escaneo**: Realiza un nuevo escaneo filtrando por el valor `510`. Repite este proceso hasta aislar la dirección de memoria exacta.
5. **Modificar valor**: Desde tu herramienta de escaneo, sobrescribe el valor en esa dirección de memoria (por ejemplo, a `9999`). 
6. **Verificar**: Toca `+1 Coins` en la aplicación; la interfaz debería actualizarse mostrando el valor modificado (ej. `10000`).

## Detalles Técnicos
- **Arquitectura**: MVVM simplificado (Clean Architecture y Repositorios omitidos para máxima simplicidad).
- **Variables**: Se utiliza un `StateFlow<Int>` dentro de `MainViewModel` para mantener el estado visible. Adicionalmente, existe una variable `shadowCoins` (no visible en la UI) que se sincroniza con el valor principal, útil para probar escaneos de múltiples direcciones y valores en paralelo.
- **Almacenamiento**: Ninguno. No se utiliza Room, DataStore ni SharedPreferences. Los valores residen únicamente en la memoria RAM durante el ciclo de vida del ViewModel.
- **UI**: Construido completamente con Jetpack Compose y Material Design 3.

---
**⚠️ Disclaimer**
Esta aplicación ha sido creada exclusivamente para uso educativo y de investigación. No contiene información sensible ni mecanismos de seguridad. Úsala bajo tu propio riesgo y solo en entornos de pruebas controlados (pentesting ético y desarrollo de herramientas de análisis de memoria).
