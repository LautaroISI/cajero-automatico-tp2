# Cajero Automático - TP 1.2

## Integrantes
- Martin Francisco - EISI1534 - ISI - 46.788.188
- Avila Wara - EISI1510 - ISI - 46.870.396
- Peralta Lautaro - EISI1589 - ISI - 46.788.963
- Nuñez Castelli Santiago - EISI1587 - ISI - 47.242.651

## Descripción
Este sistema simula un cajero automático (ATM) que permite gestionar cuentas bancarias aplicando principios de POO como encapsulamiento, manejo de excepciones personalizadas, inmutabilidad y registro de transacciones.

## Estructura del proyecto
```
src/
├── model/       → CuentaBancaria, Transaccion, TipoTransaccion
├── exception/   → SaldoInsuficienteException, LimiteExtraccionException, CuentaInactivaException, PinInvalidoException
├── service/     → CuentaService
├── ui/          → MenuCajero
├── util/        → Formateador
└── Main.java    → Simulación de un día de operaciones con 3 cuentas y 15 transacciones
```

## Decisiones de diseño
- `numeroCuenta` es `final` en `CuentaBancaria`: es inmutable, no puede cambiar una vez creada la cuenta
- `ArrayList<Transaccion>` para el historial: permite recorrer las transacciones en orden y mostrar las últimas 10 fácilmente
- Clase `Transaccion` separada: encapsula tipo, monto, fecha/hora y descripción de cada movimiento
- `StringBuilder` para construir los mensajes de log: requerimiento explícito del TP
- `Formateador` en paquete `util`: centraliza el formato `$XXX,XXX.00` para no repetirlo en todas las clases

## Cómo ejecutar
Este programa no cuenta con un menú de entrada de datos. Las operaciones se prueban directamente desde `Main.java`, donde se crean las cuentas, se llaman los métodos y se muestran los resultados por consola.

## Salida esperada
```

>>> Operaciones de Juan Pérez (Cuenta 001):
  [OK] Depósito $5,000.00
  [OK] Extracción $3,000.00
  [EXCEPCIÓN CAPTURADA - LimiteExtraccion] El monto $15,000.00 supera el límite de extracción de $10,000.00
  [OK] Consulta → Saldo de Juan: $22,000.00
  [OK] Transferencia $2,000.00 → Ana

>>> Operaciones de Ana García (Cuenta 002):
  [OK] Depósito $8,000.00
  [OK] Extracción $5,000.00
  [EXCEPCIÓN CAPTURADA - SaldoInsuficiente] Saldo insuficiente. Tiene $20,000.00 y quiere extraer $50,000.00
  [OK] Consulta → Saldo de Ana: $20,000.00
  [OK] Transferencia $1,500.00 → Luis

>>> Operaciones de Luis Torres (Cuenta 003):
  [OK] Depósito $3,000.00
  [OK] Extracción $4,000.00
  Cuenta 003 desactivada.
  [EXCEPCIÓN CAPTURADA - CuentaInactiva] La cuenta 003 está inactiva.
  Cuenta 003 activada.
  [OK] Cuenta reactivada. Saldo de Luis: $9,500.00

  ======== HISTORIAL DE TRANSACCIONES ========

  === HISTORIAL - Cuenta 001 (Juan Pérez) ===
  [fecha] DEPOSITO: $5,000.00 | Depósito. Saldo resultante: $25,000.00
  [fecha] EXTRACCION: $3,000.00 | Extracción. Saldo resultante: $22,000.00
  [fecha] CONSULTA: $0.00 | Consulta de saldo. Saldo actual: $22,000.00
  [fecha] TRANSFERENCIA: $2,000.00 | Transferencia a cuenta 002. Saldo resultante: $20,000.00

  === HISTORIAL - Cuenta 002 (Ana García) ===
  [fecha] DEPOSITO: $2,000.00 | Depósito. Saldo resultante: $17,000.00
  [fecha] DEPOSITO: $8,000.00 | Depósito. Saldo resultante: $25,000.00
  [fecha] EXTRACCION: $5,000.00 | Extracción. Saldo resultante: $20,000.00
  [fecha] CONSULTA: $0.00 | Consulta de saldo. Saldo actual: $20,000.00
  [fecha] TRANSFERENCIA: $1,500.00 | Transferencia a cuenta 003. Saldo resultante: $18,500.00

  === HISTORIAL - Cuenta 003 (Luis Torres) ===
  [fecha] DEPOSITO: $1,500.00 | Depósito. Saldo resultante: $11,500.00
  [fecha] DEPOSITO: $3,000.00 | Depósito. Saldo resultante: $14,500.00
  [fecha] EXTRACCION: $4,000.00 | Extracción. Saldo resultante: $10,500.00
  [fecha] CONSULTA: $0.00 | Consulta de saldo. Saldo actual: $9,500.00

  = SALDOS FINALES DEL DÍA ==
  Juan:  $20,000.00
  Ana:   $18,500.00
  Luis:  $9,500.00
```

## Excepciones demostradas

| `LimiteExtraccionException` | Se intenta extraer $15,000 (supera el límite de $10,000 por operación) |
| `SaldoInsuficienteException` | Se intenta extraer $50,000 teniendo $20,000 disponibles |
| `CuentaInactivaException` | Se intenta depositar en una cuenta desactivada |

## Tecnologías
- Java 21
- java.time (LocalDateTime)
- ArrayList para historial de transacciones
