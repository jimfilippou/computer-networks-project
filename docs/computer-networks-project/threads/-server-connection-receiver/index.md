[computer-networks-project](../../index.md) / [threads](../index.md) / [ServerConnectionReceiver](./index.md)

# ServerConnectionReceiver

`class ServerConnectionReceiver : Thread`

The master thread of the application.

This class handles connections and spawns proper threads for serving clients.

### Parameters

`server` - the server instance to operate on

### Constructors

| [&lt;init&gt;](-init-.md) | Creates a new master thread which can be executed by the start() function.`ServerConnectionReceiver(server: `[`Server`](../../models/-server/index.md)`)` |

### Functions

| [run](run.md) | Starts the master thread. This thread stays alive forever`fun run(): Unit` |

