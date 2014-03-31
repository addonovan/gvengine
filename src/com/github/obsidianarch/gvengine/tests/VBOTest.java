package com.github.obsidianarch.gvengine.tests;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.github.obsidianarch.gvengine.core.ColorSystem;
import com.github.obsidianarch.gvengine.core.NormalSystem;
import com.github.obsidianarch.gvengine.core.PositionSystem;
import com.github.obsidianarch.gvengine.core.Scheduler;
import com.github.obsidianarch.gvengine.core.VertexBufferObject;

/**
 * The first test, tests the validity of the methods of VertexBufferObject.
 * 
 * @author Austin
 * 
 * @since 14.03.30
 * @version 14.03.30
 */
public class VBOTest {
    
    /** the VertexBufferObject we're testing. */
    private static VertexBufferObject vbo;
    
    /** A second VBO for testing two at once. */
    private static VertexBufferObject vbo2;
    
    /**
     * Starts the test.
     * 
     * @throws Exception
     *             If something couldn't be initialized.
     * 
     * @since 14.03.30
     * @version 14.03.30
     */
    private static void init() throws Exception {
        Display.setDisplayMode( new DisplayMode( 640, 480 ) );
        Display.create();
        
        glMatrixMode( GL_PROJECTION );
        glLoadIdentity();
        glOrtho( 0, 1, 0, 1, 1, -1 );
        glMatrixMode( GL_MODELVIEW );
        
        glEnableClientState( GL_VERTEX_ARRAY ); // enable vertex arrays
        glEnableClientState( GL_COLOR_ARRAY ); // enable color arrays
        
        vbo = new VertexBufferObject( PositionSystem.XY, ColorSystem.RGB, NormalSystem.DISABLED, 6, 9, 0 );
        vbo.setCoordinates( 0, 0, 1, 0, 0, 1 );
        vbo.setChannels( 1, 1, 1, 1, 1, 1, 1, 1, 1 );
        
        vbo2 = new VertexBufferObject( PositionSystem.XY, ColorSystem.RGB, NormalSystem.DISABLED, 6, 9, 0 );
        vbo2.setCoordinates( 1, 1, 1, 0, 0, 1 );
        vbo2.setChannels( 1, 0, 0, 1, 0, 0, 1, 0, 0 );
    }
    
    /**
     * Runs the test.
     * 
     * @since 14.03.30
     * @version 14.03.30
     */
    private static void run() {
        while ( !Display.isCloseRequested() ) {
            glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
            
            vbo.render();
            vbo2.render();
            
            Scheduler.doTick();
            
            Display.update();
            Display.sync( 60 );
        }
    }
    
    /**
     * Destroys the test.
     * 
     * @since 14.03.30
     * @version 14.03.30
     */
    private static void destroy() {
        Display.destroy();
    }
    
    /**
     * Starts the test.
     * 
     * @param args
     *            The command line arguments.
     * @throws Exception
     *             If a problem happened setting up or running the test.
     * 
     * @since 14.03.30
     * @version 14.03.30
     */
    public static void main( String[] args ) throws Exception {
        init();
        run();
        destroy();
    }
}
