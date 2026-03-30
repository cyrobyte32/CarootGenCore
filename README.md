# RootCore
**The foundational utility engine for the CarootGen Minecraft Network.**

RootCore provides essential infrastructure and automation for the "Gens" gamemode. It focuses on player onboarding, administrative efficiency, and high-performance item processing.

---
**The Server had a full rebranding and this plugin is now fully archived and no longer needed.**
---

## Key Features

### Gens Automation (Auto-Compress)
To optimize player inventory management in the "Gens" gamemode, RootCore features an integrated **Auto-Pickup** and **Auto-Compression** system.
* **Smart Compression:** Admins can define custom recipes to automatically upgrade item types once specific quantity thresholds are met.
* **Efficiency:** Minimizes inventory clutter and enhances the core gameplay loop.

### Dynamic Leaderboards
Leveraging external display APIs, RootCore manages real-time player statistics.
* **Tracked Metrics:** Kills, blocks broken, and total playtime.
* **Visuals:** Integrated with holograms for immersive spawn-area displays.

### Moderation & Engagement
* **Chat Security:** A configurable filter system to maintain a positive community environment.
* **Custom Messaging:** Tailored events for first-time joins and returning players to enhance the "Minekeep" brand identity.
* **Tutorial System:** On-demand guides for various gamemodes via `/tutorial`.

---

## Technical Stack & Integrations

RootCore acts as a bridge to several community-standard APIs:

| Dependency | Purpose |
| :--- | :--- |
| **Java 17+** | Execution Environment |
| **DecentHolograms API** | Rendering of dynamic 3D Leaderboards |
| **Spigot API** | Core event handling and world management |

---

## Administrative Commands (Selection)

| Command | Description | Permission |
| :--- | :--- | :--- |
| `/autocomp <add/list>` | Manage auto-compression recipes | `rootcore.admin` |
| `/leaderboard set <type>` | Deploy statistic holograms | `rootcore.admin` |
| `/rootcorereloadconfig` | Hot-reload of the `config.yml` | `rootcore.admin` |

---

## 🚀 Development Setup
Built with **Maven**. To compile the project, ensure you have the required repositories for *DecentHolograms* in your `pom.xml`.
