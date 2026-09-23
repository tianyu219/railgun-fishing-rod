# Railgun Fishing Rod

一个极简的 **Minecraft Fabric 1.21.11** 模组。

玩家手持特殊钓鱼竿右键，即可沿准星方向瞬时发射一发高速“轨道炮”。

不需要 GUI、能源、弹药或机器。

## 功能

* 右键特殊钓鱼竿立即发射
* 沿玩家准星方向进行瞬时射线检测
* 同时检测实体和方块，并取最近命中点
* 命中实体或方块后立即产生爆炸
* 发射时产生闪光、电火花和电弧轨迹
* 命中时产生粒子和音效
* 服务端执行核心射线检测与爆炸逻辑
* 客户端和专用服务端均可加载

## 配置

所有主要参数位于：

```text
src/main/java/com/example/railgunfishingrod/RailgunConfig.java
```

可以修改：

| 参数                 | 说明              |
| ------------------ | --------------- |
| `MAX_RANGE`        | 最大射程，单位：方块      |
| `EXPLOSION_RADIUS` | 爆炸范围            |
| `EXPLOSION_DAMAGE` | 爆炸对受影响实体造成的固定伤害 |
| `BREAK_BLOCKS`     | 是否破坏方块          |
| `ARC_SAMPLES`      | 电弧轨迹采样点数量       |
| `ARC_JITTER`       | 电弧摆动幅度          |

## 获取物品

在开发环境中，可以从创造模式的“工具与实用物品”标签页获取。

也可以使用：

```mcfunction
/give @p railgun_fishing_rod:railgun_fishing_rod
```

## 环境要求

* Minecraft 1.21.11
* Java 21
* Fabric Loader 0.18.2
* Fabric API 0.141.4+1.21.11
* Fabric Loom 1.14.10

项目使用 Fabric 1.21.11 对应的 Mojang mappings。

## 构建

克隆项目后进入项目目录：

```bash
git clone https://github.com/你的用户名/railgun-fishing-rod.git
cd railgun-fishing-rod
```

使用 Gradle Wrapper 构建：

### Windows

```bat
gradlew.bat build
```

### Linux / macOS

```bash
./gradlew build
```

开发环境运行客户端：

```bash
./gradlew runClient
```

运行服务端：

```bash
./gradlew runServer
```

构建完成后，JAR 文件位于：

```text
build/libs/
```

## 安装

将构建生成的 `.jar` 文件放入 Minecraft 的：

```text
mods
```

目录。

客户端和服务端都需要安装本模组以及对应版本的 Fabric API。

## 项目结构

```text
railgun-fishing-rod/
├── gradle/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── railgunfishingrod/
│       └── resources/
├── build.gradle
├── gradle.properties
├── settings.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

