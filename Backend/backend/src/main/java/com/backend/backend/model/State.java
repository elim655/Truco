package com.backend.backend.model;

/**
 * Enum representing the possible states of a game.
 */
public enum State {
    /**
     * Normal state of the game.
     */
    NORMAL,

    /**
     * State when the "Truco" game variant is active.
     */
    TRUCO,

    /**
     * State when the "Retruco" game variant is active.
     */
    RETRUCO,

    /**
     * State when the "Vale Cuatro" game variant is active.
     */
    VALECUATRO,

    /**
     * State when the "Envido" game variant is active.
     */
    ENVIDO,

    /**
     * State when the "Real Envido" game variant is active.
     */
    REAL_ENVIDO,

    /**
     * State representing the end of the game.
     */
    GAMEOVER
}


