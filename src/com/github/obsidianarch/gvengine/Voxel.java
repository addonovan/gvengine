package com.github.obsidianarch.gvengine;

import org.magicwerk.brownies.collections.primitive.FloatGapList;

import com.github.obsidianarch.gvengine.core.Face;
import com.github.obsidianarch.gvengine.core.MathHelper;
import com.github.obsidianarch.gvengine.core.RepeatingArray;

/**
 * Everything involved with the creation of individual voxels.
 * 
 * @author Austin
 */
public class Voxel {
    
    /**
     * Creates a voxel with only the needed faces.
     * 
     * @param positions
     *            The positioning array to which the voxel's face positions will be
     *            appended.
     * @param colors
     *            The color array to which the voxel's colors will be appended.
     * @param c
     *            The chunk the voxel is a part of.
     * @param x
     *            The local x coordinate of the voxel.
     * @param y
     *            The local y coordinate of the voxel.
     * @param z
     *            The local z coordinate of the voxel.
     */
    public static final void createVoxel( FloatGapList positions, FloatGapList colors, Chunk c, int x, int y, int z ) {
        if ( !c.shouldBeRendered( x, y, z ) ) return; // this voxel shouldn't be rendered
            
        Material material = c.getMaterialAt( x, y, z ); // the material of this voxel
        
        float[] colorSource = { material.color.getRed() / 255f, material.color.getGreen() / 255f, material.color.getBlue() / 255f };
        RepeatingArray repeatingColors = new RepeatingArray( colorSource );
        float[] repeatedColors = repeatingColors.createArray( 18 ); // this will be added for every face, for the voxel's color
        
        // get the global positions of the voxel
        float[] global = MathHelper.getGlobalPosition( c, new int[ ] { x, y, z } );
        float gX = global[ 0 ];
        float gY = global[ 1 ];
        float gZ = global[ 2 ];
        
        //
        // X-Faces
        //
        
        if ( !c.getMaterialAt( x - 1, y, z ).active && !c.isEclipsed( x - 1, y, z ) ) {
            positions.addAll( createFace( Face.LEFT, gX, gY, gZ ) );
            colors.addAll( repeatedColors );
        }
        
        if ( !c.getMaterialAt( x + 1, y, z ).active && !c.isEclipsed( x + 1, y, z ) ) {
            positions.addAll( createFace( Face.RIGHT, gX, gY, gZ ) );
            colors.addAll( repeatedColors );
        }
        
        //
        // Y-Faces
        //
        
        if ( !c.getMaterialAt( x, y - 1, z ).active && !c.isEclipsed( x, y - 1, z ) ) {
            positions.addAll( createFace( Face.BOTTOM, gX, gY, gZ ) );
            colors.addAll( repeatedColors );
        }
        
        if ( !c.getMaterialAt( x, y + 1, z ).active && !c.isEclipsed( x, y + 1, z ) ) {
            positions.addAll( createFace( Face.TOP, gX, gY, gZ ) );
            colors.addAll( repeatedColors );
        }
        
        //
        // Z-Faces
        //
        
        if ( !c.getMaterialAt( x, y, z - 1 ).active && !c.isEclipsed( x, y, z - 1 ) ) {
            positions.addAll( createFace( Face.FRONT, gX, gY, gZ ) );
            colors.addAll( repeatedColors );
        }
        
        if ( !c.getMaterialAt( x, y, z + 1 ).active && !c.isEclipsed( x, y, z + 1 ) ) {
            positions.addAll( createFace( Face.BACK, gX, gY, gZ ) );
            colors.addAll( repeatedColors );
        }
    }
    
    /**
     * Creates a float array for the positioning of a voxel face.
     * 
     * @param direction
     *            The face.
     * @param x
     *            The global x coordinate of the face.
     * @param y
     *            The global y coordinate of the face.
     * @param z
     *            The global z coordinate of the face.
     * @return The face's position data.
     */
    public static final float[] createFace( Face direction, float x, float y, float z ) {
        float[] points = null; // the point's we'll send back
        
        // these make the far (positive) spaces easier to reach
        float xp = x + 1;
        float yp = y + 1;
        float zp = z + 1;
        
        switch ( direction ) {
        case LEFT: // LBF-RBF-LTF | RTF-RBF-LTF
            points = new float[ ] { x, yp, z, x, y, z, x, y, zp, x, yp, z, x, y, zp, x, yp, zp };
            break;
        
        case BOTTOM: // LBF-RBF-LBB | RBB-RBF-LBB
            points = new float[ ] { x, y, zp, x, y, z, xp, y, zp, x, y, z, xp, y, z, xp, y, zp };
            break;
        
        case FRONT: // LBF-RBF-LTF | RTF-RBF-LTF
            points = new float[ ] { xp, yp, z, xp, y, z, x, y, z, xp, yp, z, x, y, z, x, yp, z };
            break;
        
        case RIGHT: // same as left, but x is shifted
            points = new float[ ] { xp, yp, zp, xp, y, zp, xp, y, z, xp, yp, zp, xp, y, z, xp, yp, z };
            break;
        
        case TOP: // same as bottom, but y is shifted
            points = new float[ ] { xp, yp, zp, x, yp, z, x, yp, zp, xp, yp, zp, xp, yp, z, x, yp, z };
            break;
        
        case BACK: // same as front, but z is shifted
            points = new float[ ] { x, y, zp, xp, y, zp, xp, yp, zp, x, yp, zp, x, y, zp, xp, yp, zp };
            break;
        }
        
        return points;
    }
}
