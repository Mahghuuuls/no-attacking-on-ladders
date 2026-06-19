# No Attacking On Ladders

A Minecraft 1.12.2 Forge mod that prevents players from attacking entities or firing bows while actively climbing a ladder or vine.

Starting with version 1.1.0, this mod requires **[Mixin Booter](https://www.curseforge.com/minecraft/mc-mods/mixin-booter)** as a dependency.

Blocked actions display a message in the action bar by default. Blocked melee attacks do not swing, and blocked bow use does not start the drawing animation.

## Configuration

- `blockMelee` controls whether melee attacks are blocked. Default: `true`.
- `blockBow` controls whether firing bows is blocked. Default: `true`.
- `showMessages` controls whether blocked-action messages appear. Default: `true`.
