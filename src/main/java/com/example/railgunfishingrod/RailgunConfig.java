package com.example.railgunfishingrod;

/**
 * All gameplay tuning lives here so the mod stays deliberately simple.
 */
public final class RailgunConfig {
    private RailgunConfig() {
    }

    /** Maximum hitscan distance, in blocks. */
    public static final double MAX_RANGE = 128.0D;

    /** Explosion radius, in blocks. Vanilla TNT is 4.0. */
    public static final float EXPLOSION_RADIUS = 4.0F;

    /** Fixed damage dealt to each entity affected by the explosion. */
    public static final float EXPLOSION_DAMAGE = 12.0F;

    /** Whether the explosion is allowed to destroy blocks. */
    public static final boolean BREAK_BLOCKS = true;

    /** Small extra expansion used when searching for entity hitboxes. */
    public static final double ENTITY_SEARCH_MARGIN = 1.0D;

    /** How many particle samples are used for the straight electrical tracer. */
    public static final int ARC_SAMPLES = 24;

    /** How far the tracer can wobble sideways, in blocks. */
    public static final double ARC_JITTER = 0.075D;
}
