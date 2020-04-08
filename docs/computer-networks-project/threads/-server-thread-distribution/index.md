[computer-networks-project](../../index.md) / [threads](../index.md) / [ServerThreadDistribution](./index.md)

# ServerThreadDistribution

`class ServerThreadDistribution : Thread`

Slave thread, for each request

### Parameters

`server` - the server object to update information based on type of packet &amp; permissions

`packet` - the incoming packet of type Packet

`replyTo` - the output stream to write results to

**Since**
0.0.3

### Constructors

| [&lt;init&gt;](-init-.md) | Slave thread, for each request`ServerThreadDistribution(server: `[`Server`](../../models/-server/index.md)`, packet: Any, replyTo: ObjectOutputStream, callback: () -> Unit)` |

### Functions

| [run](run.md) | Starts the slave thread. Runs the callback passed when the thread dies. Usually, the callback will contain nothing because it's purpose is to close the socket to avoid any possible memory leaks.`fun run(): Unit` |

