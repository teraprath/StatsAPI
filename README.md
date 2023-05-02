<!--suppress HtmlDeprecatedAttribute -->
<div align="center">

[![](https://jitpack.io/v/teraprath/StatsAPI.svg)](https://jitpack.io/#teraprath/StatsAPI)
<div>
    <h1>StatsAPI</h1>
    <p>for Spigot 1.19+<p>
</div>
</div>

## Features

- Easy to use and lightweight
- MySQL-Support
- Stats Command (100% Configurable)
- Kills, Deaths, Wins, Loses, Played Games, Streaks & Game Points
- Compatible with [PointsAPI](https://github.com/teraprath/PointsAPI/releases/latest)

## Implementation

You can see the latest version [here](https://github.com/teraprath/StatsAPI/releases/latest).

**Using Maven:**

````xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
````

````xml

<dependency>
    <groupId>com.github.teraprath</groupId>
    <artifactId>StatsAPI</artifactId>
    <version>INSERT_VERSION_HERE</version>
</dependency>
````

**Using Gradle:**
````groovy
repositories {
    maven { url 'https://jitpack.io' }
}
````
````
dependencies {
    implementation 'com.github.teraprath:StatsAPI:INSERT_VERSION_HERE'
}
````

## Basic Usage
An example code using the StatsAPI.

```java

PlayerStats stats = StatsAPI.getPlayer(player);

stats.setKills(amount)
stats.setWins(stats.getWins() + 1)

StatsAPI.save(stats);

```
Visit [wiki](https://github.com/teraprath/StatsAPI/wiki/) page to see usage guide.
