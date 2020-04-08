[computer-networks-project](../../index.md) / [threads](../index.md) / [ServerThreadDistribution](index.md) / [run](./run.md)

# run

`fun run(): Unit`

Starts the slave thread.
Runs the callback passed when the thread dies.
Usually, the callback will contain nothing because it's purpose is to close the
socket to avoid any possible memory leaks.

