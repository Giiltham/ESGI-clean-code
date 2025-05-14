package fr.esgi.fx.avis.application.exception;

public class JeuInexistantException extends RuntimeException{
    public JeuInexistantException(String message) {
        super(message);
    }

    public JeuInexistantException() {
        super();
    }

}
