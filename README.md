# MiniLobby

A very, very simple plugin that does 2 things:

- Every time someone joins, it teleports them to a "spawn" (Instead of the last location they were in)
- It sets their inventory and doesn't allow them to pickup, move or drop items

### Why is it called MiniLobby

Because everything it does, are core concepts of a lobby and it's simple. (We all know I know how to name things)

## Using it

### Settings the "spawn" position

Go to the place you want to store as a spawn and run `/minilobby setspawn`

### Setting the set inventory

Make the inventory you want to make, and run `/minilobby setinventory`, and then run `/minilobby setinventoryworld` for
the world which the inventory stuff happens in (When a user goes to a world which it doesn't take place in, it won't
rollback their inventories)