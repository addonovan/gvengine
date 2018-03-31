package com.addonovan.gvengine.core.input;

import com.addonovan.gvengine.io.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class for all input from the user.
 *
 * @author Austin
 * @version 14.08.02
 * @since 14.03.30
 */
public final class Input
{

    //
    // Fields
    //

    /**
     * All current bindings which have been defined.
     */
    private static final HashMap< String, InputBinding > bindings = new HashMap<>();

    //
    // Actions
    //

    /**
     * Initializes the input controls, loads the controllers.
     *
     * @since 14.03.30
     */
    public static void initialize()
    {
//        try
//        {
//            Controllers.create();
//        }
//        catch ( LWJGLException e )
//        {
//            System.err.println( " Failed to create controllers:  " + e.getClass().getName() );
//        }
    }

    /**
     * Polls the keyboard, mouse, and all controllers.
     *
     * @since 14.03.30
     */
    public static void poll()
    {
//        Keyboard.poll();
//        Mouse.poll();
//        Controllers.poll();
    }

    /**
     * Removes all bindings.
     *
     * @since 14.03.30
     */
    public static void clearBindings()
    {
        bindings.clear();
    }

    //
    // I/O 
    //

    /**
     * Adds all input bindings to the configuration object.<BR> This does not save the bindings, rather it merely adds them to the configuration object for
     * later reading and writing.
     *
     * @param c
     *         The configuration object.
     *
     * @since 14.03.30
     */
    public static void addBindings( Config c )
    {
        List< String > data = new ArrayList<>(); // the list of bindings

        // add the bindings to the list
        bindings.entrySet().forEach(
                ( entry ) -> data.add( entry.getKey() + "=" + entry.getValue().toString() )
        );

        c.setTagData( "INPUT", data ); // set the tag data
    }

    /**
     * Loads the input bindings from the configuration object.<BR> The input bindings will be read from the configuration object's data, however any bindings
     * that already exist will be cleared.
     *
     * @param c
     *         The configuration object.
     *
     * @return The total number of bindings loaded.
     *
     * @since 14.03.30
     */
    public static int loadBindings( Config c )
    {
        bindings.clear();

        List< String > data = c.getTagData( "INPUT" ); // get the input data from the config object

        int loadedBindings = 0;
        for ( String s : data )
        {
            if ( !s.contains( "=" ) )
            {
                continue;
            }
            String[] split = s.split( "=" );

            try
            {
                // setBinding( split[ 0 ], new InputBinding( split[ 1 ] ) );
                loadedBindings++;
            }
            catch ( Exception e )
            {
                System.out.println( "Failed to load InputBinding from string [" + s + "]" );
            }
        }

        return loadedBindings;
    }

    //
    // Setters
    //

    /**
     * Creates a new InputBinding and assigns it to the given action name.
     *
     * @param action
     *         The name of the action.
     * @param medium
     *         The input medium the action is bound to.
     * @param mode
     *         How the input is expected.
     * @param mask
     *         Any input masks that are required.
     * @param button
     *         The button to be bound.
     *
     * @version 14.03.30
     * @since 14.03.30
     */
    public static void setBinding( String action, InputMedium medium, InputMode mode, InputMask mask, int button )
    {
        InputBinding binding = new InputBinding( medium, mode, mask, button );
        setBinding( action, binding );
    }

    /**
     * Creates a new InputBinding and assigns it to the given action name. This is a convenience method for {@code setBinding(action, medium, mode,
     *InputMask.NO_MASK, button)}.
     *
     * @param action
     *         The name of the action.
     * @param medium
     *         The input medium the action is bound to.
     * @param mode
     *         How the input is expected.
     * @param button
     *         The button to be bound.
     *
     * @version 14.03.30
     * @see #setBinding(String, InputMedium, InputMode, InputMask, int)
     * @since 14.03.30
     */
    public static void setBinding( String action, InputMedium medium, InputMode mode, int button )
    {
        setBinding( action, medium, mode, InputMask.NO_MASK, button );
    }

    /**
     * Creates a new InputBinding and assigns it to the given action name. This is a convenience method for {@code setBinding(action, medium,
     *InputMode.BUTTON_DOWN, InputMask.NO_MASK, button)} .
     *
     * @param action
     *         The name of the action.
     * @param medium
     *         The input medium the action is bound to.
     * @param button
     *         The button to be bound.
     *
     * @version 14.03.30
     * @see #setBinding(String, InputMedium, InputMode, InputMask, int)
     * @since 14.03.30
     */
    public static void setBinding( String action, InputMedium medium, int button )
    {
        setBinding( action, medium, InputMode.BUTTON_DOWN, InputMask.NO_MASK, button );
    }

    /**
     * Sets the InputBinding that will trigger the action.
     *
     * @param action
     *         The action that will be triggered.
     * @param binding
     *         The binding which will fire the action.
     *
     * @version 14.03.30
     * @since 14.03.30
     */
    public static void setBinding( String action, InputBinding binding )
    {
        bindings.put( action.toLowerCase().trim(), binding );
    }

    //
    // Getters
    //

    /**
     * Checks to see if an InputMask is active or not.
     *
     * @param mask
     *         The InputMask.
     *
     * @return If either keyboard key mask is down, or if {@code mask} is {@code NO_MASK}, {@code true}.
     *
     * @version 14.03.30
     * @since 14.03.30
     */
    public static boolean isMaskActive( InputMask mask )
    {

        switch ( mask )
        {

            case CONTROL_MASK:
//                return Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) || Keyboard.isKeyDown( Keyboard.KEY_RCONTROL );
                return false;

            case MENU_MASK:
//                return Keyboard.isKeyDown( Keyboard.KEY_LMENU ) || Keyboard.isKeyDown( Keyboard.KEY_RMENU );
                return false;

            case META_MASK:
//                return Keyboard.isKeyDown( Keyboard.KEY_LMETA ) || Keyboard.isKeyDown( Keyboard.KEY_RMETA );
                return false;

            default:
                return !( isMaskActive( InputMask.CONTROL_MASK ) || isMaskActive( InputMask.MENU_MASK ) || isMaskActive( InputMask.META_MASK ) );

        }

    }

    /**
     * Returns the InputBinding with the given name.
     *
     * @param action
     *         The InputBinding's action name.
     *
     * @return The InputBinding bound to the action.
     *
     * @version 14.03.30
     * @since 14.03.30
     */
    public static InputBinding getInputBinding( String action )
    {
        return bindings.get( action.toLowerCase().trim() );
    }

    /**
     * Determines if the binding to the action is currently active or not.
     *
     * @param action
     *         The action.
     *
     * @return If the action has been triggered.
     *
     * @version 14.03.30
     * @since 14.03.30
     */
    public static boolean isBindingActive( String action )
    {
        InputBinding binding = bindings.get( action.toLowerCase().trim() ); // get the binding from the map

        if ( binding == null )
        {
            return false; // the action doesn't exist
        }

        return binding.isActive(); // return if the binding is active or not
    }

}