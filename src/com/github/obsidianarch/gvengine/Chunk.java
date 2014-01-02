package com.github.obsidianarch.gvengine;

import static com.github.obsidianarch.gvengine.MathHelper.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.github.obsidianarch.gvengine.core.ColorSystem;
import com.github.obsidianarch.gvengine.core.ExpandingArray;
import com.github.obsidianarch.gvengine.core.NormalSystem;
import com.github.obsidianarch.gvengine.core.PositionSystem;
import com.github.obsidianarch.gvengine.core.VertexBufferObject;

/**
 * A container for a 16x16x16 selection of voxels.
 * 
 * @author Austin
 */
public class Chunk {
    
    //
    // Fields
    //
    
    /** The voxels in this chunk. */
    private final byte[]       voxels = new byte[ 4096 ];
    
    /** The position of this chunk on the chunk grid. */
    public final int           x;
    
    /** The position of this chunk on the chunk grid. */
    public final int           y;
    
    /** The position of this chunk on the chunk grid. */
    public final int           z;
    
    /** The VBO for this chunk. */
    private VertexBufferObject vbo;
    
    //
    // Constructors
    //
    
    /**
     * Creates a chunk at the given chunk coordiantes.
     * 
     * @param x
     *            The chunk's x coordinate.
     * @param y
     *            The chunk's y coordinate.
     * @param z
     *            The chunk's z coordinate.
     */
    public Chunk(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    //
    // Actions
    //
    
    /**
     * Builds the mesh for the chunk.
     */
    public void buildMesh() {
        ExpandingArray positions = new ExpandingArray( 221184 );
        ExpandingArray colors = new ExpandingArray( 221184 );
        
        for ( int i = 0; i < 4096; i++ ) {
            // get the local positions from i
            int x = i % 16;
            int y = ( i / 16 ) % 16;
            int z = ( i - x - ( 16 * y ) ) / 256;
            
            Voxel.createVoxel( positions, colors, this, x, y, z );
        }
        
        vbo = new VertexBufferObject( PositionSystem.XYZ, ColorSystem.RGB, NormalSystem.DISABLED, positions, colors, null );
        
        vbo.validate(); // manually validate the VBO
    }
    
    /**
     * Renders the VertexBufferObject for this chunk.
     */
    public void render() {
        
        // TODO REMOVE THESE AFTER TESTING
        if ( Keyboard.isKeyDown( Keyboard.KEY_1 ) ) {
            vbo.setGLMode( GL11.GL_POINTS );
        }
        else if ( Keyboard.isKeyDown( Keyboard.KEY_2 ) ) {
            vbo.setGLMode( GL11.GL_LINES );
        }
        else if ( Keyboard.isKeyDown( Keyboard.KEY_3 ) ) {
            vbo.setGLMode( GL11.GL_TRIANGLES );
        }
        
        vbo.render();
    }
    
    //
    // Setters
    //
    
    /**
     * Sets the voxel material at the given local position.
     * 
     * @param mat
     *            The new material.
     * @param x
     *            The local x position.
     * @param y
     *            The local y position.
     * @param z
     *            The local z position.
     */
    public void setMaterialAt( Material mat, int x, int y, int z ) {
        voxels[ x + ( y * 16 ) + ( z * 256 ) ] = mat.byteID;
    }
    
    //
    // Getters
    //
    
    /**
     * @return The number of vertices in the chunk.
     */
    public int getVertexCount() {
        return vbo.getCoordinates().getLength();
    }
    
    /**
     * Gets the material at the given <b>global</b> position.
     * 
     * @param x
     *            The global x position.
     * @param y
     *            The global y position.
     * @param z
     *            The global z position.
     * @return The material at the global position.
     */
    public Material getMaterialAt( float x, float y, float z ) {
        // convert the global positions to the local chunk positions
        int localX = toLocalPosition( this.x, x );
        int localY = toLocalPosition( this.y, y );
        int localZ = toLocalPosition( this.z, z );
        
        return getMaterialAt( localX, localY, localZ ); // return the materials at the local chunk position
    }
    
    /**
     * Gets the material at the given <b>local</b> position.
     * 
     * @param x
     *            The local x position.
     * @param y
     *            The local y position.
     * @param z
     *            The local z position.
     * @return The material at the local position.
     */
    public Material getMaterialAt( int x, int y, int z ) {
        if ( !inRange( x, 0, 15 ) || !inRange( y, 0, 15 ) || !inRange( z, 0, 15 ) ) {
            return Material.AIR;
        }
        
        return Material.getMaterial( voxels[ x + ( y * 16 ) + ( z * 256 ) ] );
    }
    
    //
    // Static
    //
    
    /**
     * Converts a local chunk coordinate to the global coordinate.
     * 
     * @param chunkCoordinate
     *            The chunk's coordiante on the plane.
     * @param localPosition
     *            The local position.
     * @return The global coordinate.
     */
    public static float toGlobalPosition( int chunkCoordinate, int localPosition ) {
        return localPosition + ( chunkCoordinate * 16 );
    }
    
    /**
     * Converts a global coordinate to a local chunk coordinate.
     * 
     * @param chunkCoordinate
     *            The chunk's coordinate on the plane.
     * @param globalPosition
     *            The global position.
     * @return The local coordinate.
     */
    public static int toLocalPosition( int chunkCoordinate, float globalPosition ) {
        return ( int ) Math.floor( globalPosition - ( chunkCoordinate * 16 ) );
    }
}
