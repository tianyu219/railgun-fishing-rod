package com.example.railgunfishingrod;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;

import java.util.function.Predicate;

public final class RailgunFishingRodItem extends FishingRodItem {
    private static final ExplosionDamageCalculator RAILGUN_DAMAGE_CALCULATOR =
            new ExplosionDamageCalculator() {
                @Override
                public float getEntityDamageAmount(Explosion explosion, Entity entity, float amount) {
                    return RailgunConfig.EXPLOSION_DAMAGE;
                }
            };

    public RailgunFishingRodItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            fire((ServerLevel) level, player);
        }

        return InteractionResult.SUCCESS;
    }

    private static void fire(ServerLevel level, Player player) {
        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getViewVector(1.0F).normalize();
        Vec3 end = start.add(direction.scale(RailgunConfig.MAX_RANGE));

        BlockHitResult blockHit = level.clip(new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 blockHitPos = blockHit.getType() == HitResult.Type.MISS ? end : blockHit.getLocation();
        double blockDistanceSqr = blockHitPos.distanceToSqr(start);

        AABB searchBox = player.getBoundingBox()
                .expandTowards(direction.scale(RailgunConfig.MAX_RANGE))
                .inflate(RailgunConfig.ENTITY_SEARCH_MARGIN);

        Predicate<Entity> entityFilter = entity ->
                entity != player
                        && entity.isPickable()
                        && !entity.isSpectator()
                        && entity.isAlive();

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                level,
                player,
                start,
                end,
                searchBox,
                entityFilter,
                RailgunConfig.MAX_RANGE
        );

        Vec3 impact = blockHitPos;
        if (entityHit != null) {
            Vec3 entityHitPos = entityHit.getLocation();
            if (entityHitPos.distanceToSqr(start) <= blockDistanceSqr) {
                impact = entityHitPos;
            }
        }

        spawnRailgunEffects(level, start, impact);

        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.TRIDENT_THROW,
                SoundSource.PLAYERS,
                1.0F,
                1.55F
        );

        level.explode(
                player,
                Explosion.getDefaultDamageSource(level, player),
                RAILGUN_DAMAGE_CALCULATOR,
                impact.x,
                impact.y,
                impact.z,
                RailgunConfig.EXPLOSION_RADIUS,
                false,
                RailgunConfig.BREAK_BLOCKS
                        ? Level.ExplosionInteraction.BLOCK
                        : Level.ExplosionInteraction.NONE
        );
    }

    private static void spawnRailgunEffects(ServerLevel level, Vec3 start, Vec3 end) {
        Vec3 direction = end.subtract(start);
        double length = direction.length();
        if (length < 0.001D) {
            return;
        }

        Vec3 forward = direction.normalize();
        Vec3 reference = Math.abs(forward.y) < 0.9D ? new Vec3(0.0D, 1.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
        Vec3 side = forward.cross(reference).normalize();
        Vec3 up = forward.cross(side).normalize();

        // Bright muzzle flash.
        level.sendParticles(ParticleTypes.FLASH, start.x, start.y, start.z, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, start.x, start.y, start.z, 12, 0.08D, 0.08D, 0.08D, 0.05D);

        // A jagged, instant electrical tracer between the player and the hit point.
        int samples = Math.max(2, RailgunConfig.ARC_SAMPLES);
        for (int i = 0; i <= samples; i++) {
            double t = i / (double) samples;
            double along = length * t;
            double wobble = Math.sin(t * Math.PI * 10.0D) * RailgunConfig.ARC_JITTER;
            double wobble2 = Math.cos(t * Math.PI * 7.0D) * RailgunConfig.ARC_JITTER * 0.6D;
            Vec3 point = start.add(forward.scale(along))
                    .add(side.scale(wobble))
                    .add(up.scale(wobble2));

            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, point.x, point.y, point.z, 2, 0.01D, 0.01D, 0.01D, 0.0D);
        }

        // Stronger impact flash/electric burst.
        level.sendParticles(ParticleTypes.FLASH, end.x, end.y, end.z, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, end.x, end.y, end.z, 24, 0.18D, 0.18D, 0.18D, 0.12D);
        level.playSound(null, end.x, end.y, end.z, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 1.0F, 1.8F);
    }

}
