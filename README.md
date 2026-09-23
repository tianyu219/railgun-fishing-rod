# 轨道炮钓鱼竿 / Railgun Fishing Rod

Fabric 1.21.11 的极简小模组：手持特殊钓鱼竿右键，沿玩家准星方向瞬时发射一发“轨道炮”。没有 GUI、能源、弹药或机器。

## 功能

- 右键特殊钓鱼竿立即射击。
- 服务端进行方块 + 实体射线检测，取视线方向上最近的命中点。
- 命中实体或方块后立刻产生爆炸。
- 发射和命中会产生电火花、闪光、电弧轨迹与音效。
- `environment` 为 `*`，没有客户端专属代码；单机、专用服务端都可以加载。

## 核心配置

所有参数都在：

`src/main/java/com/example/railgunfishingrod/RailgunConfig.java`

主要参数：

- `MAX_RANGE`：最大射程，单位方块。
- `EXPLOSION_RADIUS`：爆炸范围。
- `EXPLOSION_DAMAGE`：爆炸对每个受影响实体造成的固定伤害。
- `BREAK_BLOCKS`：是否破坏方块。
- `ARC_SAMPLES`：电弧采样点数量。
- `ARC_JITTER`：电弧摆动幅度。

## 获取物品

开发环境可以直接在创造模式的“工具与实用物品”标签页拿到。

正式服务器中也可以使用：

`/give @p railgun_fishing_rod:railgun_fishing_rod`

## 构建

要求 JDK 21。Fabric 官方 1.21.11 开发文档也以 JDK 21 为开发环境。

在项目目录执行：

```text
gradle build
```

开发测试也可以运行：

```text
gradle runClient
gradle runServer
```

构建完成后的正式 JAR 在（文件名会随版本变化）：

```text
build/libs/railgun-fishing-rod-1.0.0.jar
```

把它和对应版本的 Fabric API 一起放进客户端或服务端的 `mods` 文件夹即可。服务端不需要额外安装客户端专属内容。

## 说明

本项目使用 Fabric 1.21.11、Fabric Loader 0.18.2、Fabric API 0.141.4+1.21.11 和 Fabric Loom 1.14.10，并采用 Fabric 1.21.11 官方推荐的 Mojang mappings。

## 当前环境的构建说明

项目文件已经完整包含 Gradle 构建脚本、Fabric 模组元数据、源码和资源。此工作环境没有可用的 Gradle 可执行文件，同时无法通过容器网络下载 Gradle 发行包，因此这里不能诚实地声称已经实际执行过 `gradle build`。本地安装 JDK 21 和 Gradle 后，可直接在项目目录运行上面的构建命令。
