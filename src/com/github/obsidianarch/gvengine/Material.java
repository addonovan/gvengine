package com.github.obsidianarch.gvengine;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

/**
 * @author Austin
 */
public class Material {
    
    //
    // Static Fields
    //
    
    /** An array containing all in-game materials, do not write anything to this. */
    private static final Material[] materials = new Material[ 256 ];
    
    //
    // Constants
    //
    
    /** Used to represent nothing, is not written to the save file at all. */
    public static final Material    AIR       = new Material( new Color( 255, 255, 255, 0 ), false, 0 );
    
    /** The stone material, written as {@code 0000001}. */
    public static final Material    STONE     = new Material( Color.LTGREY, true, 1 );
    
    //
    // Final Fields
    //
    
    /** The color of the material. */
    public final ReadableColor      color;
    
    /** {@code false} if a voxel with this material should not be rendered. */
    public final boolean            active;
    
    /** The id of the voxel in the index. */
    public final int                indexID;
    
    /** The id of the voxel as a byte. (indexID - 128) */
    public final byte               byteID;
    
    //
    // Constructors
    //
    
    /**
     * Creates a material.
     * 
     * @param color
     *            The color of a voxel with this material.
     * @param active
     *            If a voxel with this material should be rendered or not.
     */
    public Material(ReadableColor color, boolean active, int byteID) {
        this.color = color;
        this.active = active;
        this.byteID = ( byte ) byteID;
        indexID = byteID + 128;
        
        materials[ indexID ] = this; // add it to the materials array for lookup
    }
    
    //
    // Static
    //
    
    /**
     * @param b
     *            The byte id for the material.
     * @return The material for the byte id.
     */
    public static Material getMaterial( byte b ) {
        return materials[ b + 128 ];
    }
    
}
